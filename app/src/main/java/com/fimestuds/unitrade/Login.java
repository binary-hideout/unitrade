package com.fimestuds.unitrade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    private EditText nom_usu, contra_usu;
    private Button iniciar, registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nom_usu=findViewById(R.id.edtxt_nom_usu);
        contra_usu=findViewById(R.id.edtxt_contra_usu);
        iniciar=findViewById(R.id.btn_iniciarses);
        registrarse=findViewById(R.id.btn_a_registrarse);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarsesion();
            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a_registro();
            }
        });
    }

    private void iniciarsesion(){
        Intent intentini = new Intent(this, Main_page.class);
        startActivity(intentini);
    }
    private void a_registro(){
        Intent intentreg = new Intent(this, Registro_usu.class);
        startActivity(intentreg);
    }
}
