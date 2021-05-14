package com.mobile2click.fiyatavantaj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.AdapterView.OnItemClickListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EnUcuz extends AppCompatActivity {
    ListView listView;
    ImageView home;







   public static TextView textView4;

    List<Fiyatlar> fiyatlars = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final Context context = this;




    TextView urunadi;
    Fiyatlar fiyatlar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_en_ucuz);


        textView4 = findViewById(R.id.textView4);

        home = findViewById(R.id.imageView3);


        urunadi = findViewById(R.id.urunadi);

        listView = findViewById(R.id.liste);
        Intent intent = getIntent();
        final String gelendeger = intent.getStringExtra("URUNADI");
        final String gelendeger2 = intent.getStringExtra("URUNADI2");

        urunadi.setText(gelendeger2);


        sayfayigoster(gelendeger);









        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(EnUcuz.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });








    }

    private void sayfayigoster(final String gelendeger) {

        DatabaseReference databaseReference = database.getReference().child("Veritabani").child(gelendeger).child("Guncelfiyat");

        databaseReference.orderByChild("fiyat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                for (DataSnapshot post : dataSnapshot.getChildren()) {




                    final String magazaadi = post.getKey();




                    final DatabaseReference databaseReference1 = database.getReference().child("Veritabani").child(gelendeger).child("Guncelfiyat").child(magazaadi);
                    databaseReference1.orderByChild("fiyat").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            fiyatlars.clear();

                             CustomAdap customAdap = new CustomAdap(context, fiyatlars);


                             listView.setAdapter(customAdap);

                             customAdap.notifyDataSetChanged();



                             listView.setOnItemClickListener(new OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                                 }
                             });


                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {





                               // fiyatlar = dataSnapshot.getValue(Fiyatlar.class);






                                final String guncelfiyat = childDataSnapshot.getValue().toString();

                              //  fiyatlars.add(new Fiyatlar(magazaadi,guncelfiyat));


                                final DatabaseReference databaseReference2 = database.getReference().child("Veritabani").child(gelendeger).child("Eskifiyat").child(magazaadi);
                                databaseReference2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {





                                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {





                                            // fiyatlar = dataSnapshot.getValue(Fiyatlar.class);






                                            String eskifiyat = childDataSnapshot.getValue().toString();

                                            fiyatlars.add(new Fiyatlar(magazaadi,guncelfiyat,eskifiyat));


                                            CustomAdap customAdapt = new CustomAdap(context, fiyatlars);


                                            listView.setAdapter(customAdapt);
                                            customAdapt.notifyDataSetChanged();
                                        }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });






                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

////////////////////




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }



    public class CustomAdap extends BaseAdapter {
        List<Fiyatlar> fiyatlars = new ArrayList<>();




        LayoutInflater inflater;
        Context context ;
        public CustomAdap(Context context, List<Fiyatlar> fiyatlars) {
            this.fiyatlars = fiyatlars;
            this.context = context;
        }




        @Override
        public int getCount() {
            return fiyatlars.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            inflater = LayoutInflater.from(context);
            final View satir = inflater.inflate(R.layout.satir,null);
            TextView textView = satir.findViewById(R.id.magazaadi);
            TextView textView1 = satir.findViewById(R.id.fiyat);
            TextView textView2 = satir.findViewById(R.id.eskifiyat);

            final ImageView konumbul = satir.findViewById(R.id.konumbul);


            konumbul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int pp = position;
                    Fiyatlar fiyatlarss = fiyatlars.get(pp);
                   String konumbulunacakyer = fiyatlarss.getMagaza();

                    Intent intent = new Intent(EnUcuz.this,MapsActivity.class);
                    intent.putExtra("KONUM",konumbulunacakyer);
                    startActivity(intent);

                }
            });



            ImageView durumlogo = satir.findViewById(R.id.durumlogo);


            if(position == 0){

                satir.setBackgroundColor(Color.LTGRAY);

            }









            Fiyatlar fiyatlar = fiyatlars.get(position);
            textView.setText(fiyatlar.getMagaza());
            textView1.setText(fiyatlar.getFiyat()+" TL");
            textView2.setText(fiyatlar.getFark());


            float guncel = Float.parseFloat(fiyatlar.getFiyat());
            float onceki = Float.parseFloat(fiyatlar.getFark());


            float fark = guncel-onceki;


            textView2.setText(String.format("%.02f",fark)+" TL");

            if(guncel>onceki){
                durumlogo.setImageResource(R.drawable.yuksek);
            }else if(guncel<onceki) {

                durumlogo.setImageResource(R.drawable.dusuk);
            }else {
                durumlogo.setImageResource(R.drawable.ayni);
            }





            return satir;
        }



    }



}
