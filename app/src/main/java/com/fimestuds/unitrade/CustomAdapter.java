package com.fimestuds.unitrade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

class CustomAdapter extends BaseAdapter {
    Context context;
    final List<Imagen> arrayimagenes;

    LayoutInflater inflater;



    public CustomAdapter(Context applicationContext, List<Imagen> arrayimagenes) {
        this.context = applicationContext;
        this.arrayimagenes = arrayimagenes;
        inflater = (LayoutInflater.from(applicationContext));

    }


    @Override
    public int getCount() {
        return arrayimagenes.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayimagenes.get(position);
        // return null;
    }
    @Override
    public long getItemId(int position) {
        return arrayimagenes.get(position).getId();
        // return 0;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.imagen_flipper, null);
        ImageView image = (ImageView) view.findViewById(R.id.imagen_flip);
        int tipo = arrayimagenes.get(position).getIdType();
        if(tipo== 1){
            Glide.with(view).load(arrayimagenes.get(position).getUrl()).into(image);
        }

        // image.setImageResource(images_ven[position]);
        return view;

    } }
