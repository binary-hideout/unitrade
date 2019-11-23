package com.fimestuds.unitrade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Pantalla_venta extends AppCompatActivity {
    private Button comprar;
    private ImageView imagen;
    private TextView costo, descripcion, arti_name, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_venta);
        comprar=findViewById(R.id.btn_comprar);
        imagen=findViewById(R.id.img_venta);
        costo=findViewById(R.id.pv_costo);
        descripcion=findViewById(R.id.descripcion);
        arti_name=findViewById(R.id.art_nombre);
        rating=findViewById(R.id.rating_art);

        final Articulo articulo_venta = getIntent().getParcelableExtra("articulo");

        Glide.with(this).load(articulo_venta.getImagen()).into(imagen);
        costo.setText("$ "+articulo_venta.getCosto());
        descripcion.setText(articulo_venta.getDescripcion());
        arti_name.setText("'"+articulo_venta.getUser_id()+"'");
        int r=articulo_venta.getRating();

        rating.setText("Rating: "+ r);

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactarvendedor(articulo_venta);
            }
        });



    }

    public void contactarvendedor(Articulo articulo){
        Intent contactar = new Intent(this, Compra.class);
        contactar.putExtra("articulo", articulo);
        startActivity(contactar);
    }
}
