package com.fimestuds.unitrade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Compra extends AppCompatActivity {
    private ImageView img_art;
    private TextView costo, nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        img_art=findViewById(R.id.comprar_img_art);
        costo=findViewById(R.id.comprar_costo_art);
        nombre=findViewById(R.id.comprar_art_name);

        final Articulo articulo_venta = getIntent().getParcelableExtra("articulo");
        nombre.setText(articulo_venta.getArt_name());
        Glide.with(this).load(articulo_venta.getImagen()).into(img_art);
        //costo.setText(articulo_venta.getCosto());

    }
}
