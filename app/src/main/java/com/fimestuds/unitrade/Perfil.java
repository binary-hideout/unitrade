package com.fimestuds.unitrade;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends AppCompatActivity {
    private Button cierre;
    private TextView saludo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        cierre=findViewById(R.id.btn_cierre);
        saludo=findViewById(R.id.saludo);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        saludo.setText("¡Hola "+user.getEmail()+"!");

        cierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoalerta();
            }
        });
    }


    private void dialogoalerta() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Cierre de Sesión");
        builder.setMessage("¿Quieres cerrar sesión?");
        //listeners de los botones
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cerrar_sesion();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void cerrar_sesion(){
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
    }
}
