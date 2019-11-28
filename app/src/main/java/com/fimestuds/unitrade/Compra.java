package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Compra extends AppCompatActivity {
    public static final int REQUEST_CALL = 1;
    private ImageView img_art, whats;
    private TextView costo, nombre, rating, cel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        img_art=findViewById(R.id.comprar_img_art);
        costo=findViewById(R.id.comprar_costo_art);
        nombre=findViewById(R.id.comprar_art_name);
        rating=findViewById(R.id.comprar_rating_art);
        cel=findViewById(R.id.comprar_contacto_art);
        whats=findViewById(R.id.imgbtn_whatsapp);

        final Articulo articulo_venta = getIntent().getParcelableExtra("articulo");
        nombre.setText(articulo_venta.getArt_name());
        Glide.with(this).load(articulo_venta.getImagen()).into(img_art);
        costo.setText("$"+articulo_venta.getCosto());
        rating.setText("Rating: "+articulo_venta.getRating());
        cel.setText("Llamar a: "+articulo_venta.getCelular());
        cel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamada();
            }
        });

        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(v);
            }
        });
    }

    private void llamada(){
        Articulo articulo = getIntent().getParcelableExtra("articulo");
        if(ContextCompat.checkSelfPermission(Compra.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Compra.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else{
            String celular =articulo.getCelular().trim();
            String dial = "tel: "+ celular;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    public void whatsapp(View view){
        Articulo articulo = getIntent().getParcelableExtra("articulo");
        String celular =articulo.getCelular().trim();
        String text ="Hola!";


        try {


            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+ celular +"&text="+text));
            startActivity(intent);

        } catch (Exception e){
            e.printStackTrace();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                llamada();
            }else {
                Toast.makeText(this, "No se ha concedido el permiso de llamada", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
