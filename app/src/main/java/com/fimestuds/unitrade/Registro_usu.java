package com.fimestuds.unitrade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registro_usu extends AppCompatActivity {
    private Button crear, regreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usu);
        crear=findViewById(R.id.btn_registro);
        regreso=findViewById(R.id.btn_reg_log);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearusu();
            }
        });
        regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });
    }

    private void crearusu(){

    }
    private void regresar(){
        Intent intentreg = new Intent(this, Login.class);
        startActivity(intentreg);
    }
}
