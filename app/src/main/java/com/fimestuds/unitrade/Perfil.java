package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Perfil extends AppCompatActivity {
    private Button cierre, productos;
    private TextView saludo;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        cierre=findViewById(R.id.btn_cierre);
        productos=findViewById(R.id.btn_mis_productos);
        saludo=findViewById(R.id.saludo);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        saludo.setText("¡Hola "+user.getEmail()+"!");


        String user_id = user.getUid();

        cierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoalerta();
            }
        });
        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verproductos();
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
    private void verproductos(){
        Intent prodintent=new Intent(this, Mis_productos.class);
        startActivity(prodintent);
    }

    private void cerrar_sesion(){
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
    }




}
