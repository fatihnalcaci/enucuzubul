package com.mobile2click.fiyatavantaj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdap extends BaseAdapter {
    List<Fiyatlar> fiyatlars = new ArrayList<>();

    NumberFormat format = NumberFormat.getInstance(Locale.US);


    LayoutInflater inflater;
    Context context ;
    public CustomAdap(Context context, List<Fiyatlar> fiyatlars) {
        this.fiyatlars = fiyatlars;
        this.context = context;
        inflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(context);
        final View satir = inflater.inflate(R.layout.satir,null);
        final TextView textView = satir.findViewById(R.id.magazaadi);
        TextView textView1 = satir.findViewById(R.id.fiyat);
        TextView textView2 = satir.findViewById(R.id.eskifiyat);














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
