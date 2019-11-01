package com.fimestuds.unitrade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Pantalla_venta extends AppCompatActivity {
    private Button comprar;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_venta);
        comprar=findViewById(R.id.btn_comprar);

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactarvendedor();
            }
        });
    }

    public void contactarvendedor(){
        Intent contactar = new Intent(this, Compra.class);
        startActivity(contactar);
    }
}
