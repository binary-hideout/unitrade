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

public class Perfil extends AppCompatActivity implements RecyclerViewAdapter.OnArticuloListener{
    private Button cierre;
    private TextView saludo;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<Articulo> mis_articulos;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        cierre=findViewById(R.id.btn_cierre);
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

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_inicio);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        addData(user_id);
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

    private void addData(final String user_id){


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");
        progressDialog.show();



        db.collection("articulos")
                .orderBy("user_id", Query.Direction.ASCENDING)
                //.whereEqualTo("visible", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mis_articulos = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                // Se obtienen los articulos realizados por el usuario
                                if(doc.get("user_id").equals(user_id)){
                                    Articulo articulo = doc.toObject(Articulo.class);
                                    mis_articulos.add(articulo);

                                }
                            }

                            RecyclerViewAdapter adapter =
                                    new RecyclerViewAdapter(
                                            mis_articulos,
                                            Perfil.this
                                    );
                            recyclerView.setAdapter(adapter);

                        } else {
                            Toast.makeText(Perfil.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }


    @Override
    public void onArticuloClick(int position, Articulo articulo) {
        mis_articulos.get(position);
        //checar y mandar al articulo correspondiente
        Intent verart_intent = new Intent(this, Pantalla_venta.class);
        verart_intent.putExtra("articulo", articulo);
        startActivity(verart_intent);
    }
}
