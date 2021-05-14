package com.mobile2click.fiyatavantaj;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements RecognitionListener {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    public Context context = this;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference urundb = database.getReference("Veritabani");
    ConstraintLayout constraintLayout;
    private TextView baslik,baslik1;
    private FirebaseAuth mAuth;
    Typeface typeface;
    TextView favoriurunsayisi,soylenen,us;
    public static EditText filtre;
    ImageView barkod,mikrofonum;
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    private SpeechRecognizer sr;
    public static RecyclerView recyclerView;
    public static RecyclerView recyclerView2;
    DatabaseReference Favoriekle = database.getReference();
    public static ArrayList<String> cardArrayList = new ArrayList<>();
    public static ArrayList<String> barkodnoarraylist = new ArrayList<>();
    public static ArrayList<String> cardArrayList2 = new ArrayList<>();

    Urunler urunler;
    ArrayList<String> favorilistesi = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.Anasayfa:

                    favoriurunsayisi.setVisibility(View.GONE);
                    filtre.setVisibility(View.VISIBLE);
                    barkod.setVisibility(View.VISIBLE);
                    mikrofonum.setVisibility(View.VISIBLE);                   
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.INVISIBLE);
                    return true;

                case R.id.Favorilerim:

                    favoriurunsayisi.setVisibility(View.VISIBLE);
                    filtre.setVisibility(View.GONE);
                    barkod.setVisibility(View.GONE);
                    mikrofonum.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    recyclerView2.setVisibility(View.VISIBLE);

                    String kullaniciidisi = mAuth.getCurrentUser().getUid();
                    final FirebaseDatabase databasem = FirebaseDatabase.getInstance();
                    DatabaseReference favoridb = databasem.getReference().child("Favoriler").child(kullaniciidisi);

                    favoridb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            favorilistesi.clear();

                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                                favorilistesi.add(childDataSnapshot.getValue().toString());


                            }

                            CustomAdapter3 customAdapter3 = new CustomAdapter3(favorilistesi,context);
                            recyclerView2.setAdapter(customAdapter3);

                            int favorisayisi = favorilistesi.size();


                            if(favorisayisi>0){

                                favoriurunsayisi.setText("Favorilerinizdeki "+String.valueOf(favorisayisi)+ " adet ürün listelenmektedir.");

                            }else {
                                favoriurunsayisi.setText("Favorilerinizde takip ettiğiniz ürün bulunmamaktadır.");

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    return true;

                case R.id.Hesabim:

                    favoriurunsayisi.setVisibility(View.GONE);
                    filtre.setVisibility(View.GONE);
                    barkod.setVisibility(View.GONE);
                    mikrofonum.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.GONE);




                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        constraintLayout = findViewById(R.id.layout);
        mAuth = FirebaseAuth.getInstance();
        favoriurunsayisi =findViewById(R.id.favoriurunsayisi);
        filtre = findViewById(R.id.editText);
        filtre.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        barkod = findViewById(R.id.barkodokuyucu);
        mikrofonum = findViewById(R.id.mikrofon);
        mAuth = FirebaseAuth.getInstance();
        baslik = (TextView) findViewById(R.id.message);
        baslik1 = (TextView) findViewById(R.id.message1);
        favoriurunsayisi =findViewById(R.id.favoriurunsayisi);
        soylenen = findViewById(R.id.soylenen);
        soylenen.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView2 = findViewById(R.id.recycler_view2);
        us = findViewById(R.id.us);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,2);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(this);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/AGENCYB.TTF");
        baslik.setTypeface(typeface);
        baslik1.setTypeface(typeface);
        filtre.setVisibility(View.VISIBLE);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        String kullaniciidisi = mAuth.getCurrentUser().getUid();
        final FirebaseDatabase databasem = FirebaseDatabase.getInstance();
        DatabaseReference favoridb = databasem.getReference().child("Favoriler").child(kullaniciidisi);

        favoridb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                favorilistesi.clear();

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                    favorilistesi.add(childDataSnapshot.getValue().toString());


                }

                CustomAdapter3 customAdapter3 = new CustomAdapter3(favorilistesi,context);
                recyclerView2.setAdapter(customAdapter3);

                int favorisayisi = favorilistesi.size();


                if(favorisayisi>0){

                    us.setText(String.valueOf(favorisayisi));

                }else {
                    us.setText("0");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       
        mikrofonum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mikrofonum.setBackgroundColor(Color.DKGRAY);
                filtre.setText("");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String requiredPermission = Manifest.permission.RECORD_AUDIO;

                    if (checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
                        requestPermissions(new String[]{requiredPermission}, 101);
                    }
                }

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }catch (Exception e){

                    e.printStackTrace();
                }

                sr.startListening(intent);
            }
        });
        barkod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    filtre.setText("");

                    if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(LoginActivity.this, "Ürün barkodu taratarak en ucuz ürünü bulabilirsiniz", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this, ScanActivity.class);
                        startActivity(intent);
                    } else {
                        // Request Camera Permission
                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                    }
                }catch (Exception e){


                    e.printStackTrace();
                }


            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {


                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                String kullaniciidisi = mAuth.getCurrentUser().getUid();
                final FirebaseDatabase databasem = FirebaseDatabase.getInstance();
                DatabaseReference favoridb = databasem.getReference().child("Favoriler").child(kullaniciidisi);
                int posizyon = viewHolder.getAdapterPosition();
                String silincekolan = favorilistesi.get(posizyon);
                favorilistesi.remove(viewHolder.getAdapterPosition());
                favoridb.child(silincekolan).removeValue();
            }
        }).attachToRecyclerView(recyclerView2);

        filtre.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {




            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                final ArrayList<String> templist = new ArrayList<>();
                templist.clear();


                for (int i = 0; i < cardArrayList.size(); i++) {

                    if (cardArrayList.get(i).toUpperCase().contains(s.toString().toUpperCase())) {

                        templist.add(cardArrayList.get(i));


                    } else {


                    }
                }

                if (templist != null && templist.size() > 0) {


                    if (filtre.length() < 3) {


                        templist.clear();
                        CustomAdapter customAdapter = new CustomAdapter(templist, context);
                        recyclerView.setAdapter(customAdapter);


                    } else {



                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        //Find the currently focused view, so we can grab the correct window token from it.
                        View view = LoginActivity.this.getCurrentFocus();
                        //If no view currently has focus, create a new one, just so we can grab a window token from it
                        if (view == null) {
                            view = new View(context);
                        }
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


                        CustomAdapter customAdapter = new CustomAdapter(templist, context);
                        recyclerView.setAdapter(customAdapter);
                    }


                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {


                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                            filtre.setText("");


                        }
                    }).attachToRecyclerView(recyclerView);


                }

            }




            @Override
            public void afterTextChanged(Editable s) {
                String enson =s.toString();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LoginActivity.this, "Ürün barkodu taratarak en ucuz ürünü bulabilirsiniz", Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(LoginActivity.this, ScanActivity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(LoginActivity.this, "Camera  izninizi kontrol edin", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();


        urundb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cardArrayList.clear();
                barkodnoarraylist.clear();

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    urunler = childDataSnapshot.getValue(Urunler.class);
                    cardArrayList.add(urunler.getUrunadi());
                    cardArrayList2.add(urunler.getUrunadi());
                    barkodnoarraylist.add(urunler.getBarkodno());




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {



        new AlertDialog.Builder(this)
                .setTitle("Fiyat Avantaj")
                .setMessage("Uygulamadan çıkmak mı istiyorsunuz?")
                .setNegativeButton("HAYIR", null)
                .setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(LoginActivity.this,exit.class);
                        startActivity(intent);
                        LoginActivity.this.finishAffinity();
                    }
                }).create().show();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

        soylenen.setVisibility(View.GONE);

        mikrofonum.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onResults(Bundle results) {
        final ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String s = "";
        for (String result:matches)
            s += result + "\n";



        String GELEN = matches.get(0);
        filtre.setText(GELEN);

        soylenen.setVisibility(View.GONE);

        mikrofonum.setBackgroundColor(Color.TRANSPARENT);
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        final ArrayList<String> matches = partialResults
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";


        soylenen.setVisibility(View.VISIBLE);
        soylenen.setText(text);

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

 


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


        ArrayList<String> cardArrayList = new ArrayList<>();

        LayoutInflater layoutInflater;
        Context context;

        public CustomAdapter(ArrayList<String> cardArrayList, Context context) {
            this.cardArrayList = cardArrayList;
            this.context = context;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            layoutInflater = LayoutInflater.from(context);
            View v = layoutInflater.inflate(R.layout.row_list, viewGroup, false);
            ViewHolder vh = new ViewHolder(v);





            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

            viewHolder.cardbaslik.setText(cardArrayList.get(i).toUpperCase());




                    int position = cardArrayList2.indexOf(cardArrayList.get(i));




            viewHolder.barkodno.setText(barkodnoarraylist.get(position).toUpperCase());




            viewHolder.favorilerim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String kullaniciid = mAuth.getCurrentUser().getUid();
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                    String secilen = viewHolder.cardbaslik.getText().toString();



                    Favoriekle.child("Favoriler").child(kullaniciid).child(secilen).setValue(secilen);



                    Toast.makeText(LoginActivity.this, " Favoriler menüsünde ürünü takip edebilirsiniz", Toast.LENGTH_SHORT).show();
                }
            });



            viewHolder.enucuz_nerede.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





                    String secilenim = viewHolder.barkodno.getText().toString();
                    String secilenim2 = viewHolder.cardbaslik.getText().toString();

                    Intent intent = new Intent(LoginActivity.this,EnUcuz.class);
                    intent.putExtra("URUNADI",secilenim);
                    intent.putExtra("URUNADI2",secilenim2);
                    startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return cardArrayList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            View mView;
            TextView cardbaslik;
            TextView barkodno;
            ImageView favorilerim, enucuz_nerede;


            public ViewHolder(@NonNull final View itemView) {
                super(itemView);


                mView = itemView;


                cardbaslik = itemView.findViewById(R.id.cardbaslik);
                barkodno = itemView.findViewById(R.id.barkodnumara);
                favorilerim = itemView.findViewById(R.id.favorilerime_ekle);
                enucuz_nerede = itemView.findViewById(R.id.en_ucuz_nerede);





            }


        }
    }



    public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.ViewHolder> {


        ArrayList<String> cardArrayList = new ArrayList<>();

        LayoutInflater layoutInflater;
        Context context;

        public CustomAdapter3(ArrayList<String> cardArrayList, Context context) {
            this.cardArrayList = cardArrayList;
            this.context = context;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            layoutInflater = LayoutInflater.from(context);
            View v = layoutInflater.inflate(R.layout.row_list3, viewGroup, false);
            ViewHolder vh = new ViewHolder(v);





            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

            viewHolder.cardbaslik.setText(cardArrayList.get(i).toUpperCase());



            int position = cardArrayList2.indexOf(cardArrayList.get(i));
            viewHolder.barkodno.setText(barkodnoarraylist.get(position).toUpperCase());







            viewHolder.enucuz_nerede.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    //Find the currently focused view, so we can grab the correct window token from it.
                    View view = LoginActivity.this.getCurrentFocus();
                    //If no view currently has focus, create a new one, just so we can grab a window token from it
                    if (view == null) {
                        view = new View(context);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);



                    String secilen = viewHolder.barkodno.getText().toString();
                    String secilen2 = viewHolder.cardbaslik.getText().toString();

                    Intent intent = new Intent(LoginActivity.this,EnUcuz.class);
                    intent.putExtra("URUNADI",secilen);
                    intent.putExtra("URUNADI2",secilen2);
                    startActivity(intent);

                }
            });



        }

        @Override
        public int getItemCount() {
            return cardArrayList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            View mView;
            TextView cardbaslik;
            TextView barkodno;
            ImageView enucuz_nerede;


            public ViewHolder(@NonNull final View itemView) {
                super(itemView);


                mView = itemView;


                cardbaslik = itemView.findViewById(R.id.cardbaslik);
                barkodno = itemView.findViewById(R.id.barkodnumarasi);

                enucuz_nerede = itemView.findViewById(R.id.en_ucuz_nerede);




            }


        }
    }
    public static void getir(String s){

        int sirasi = barkodnoarraylist.indexOf(s);

        String urunadi = cardArrayList.get(sirasi);


        filtre.setText(urunadi);

    }

}
