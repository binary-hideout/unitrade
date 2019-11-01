package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Main_page extends AppCompatActivity {
    private AdapterViewFlipper flip_inicio;
    private List<Articulo> arrayarticulos;
    private FirebaseFirestore db;
    FloatingActionButton vender;
    RecyclerView recyclerView;
    Context context;
    public ImageView perfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        perfil=findViewById(R.id.btn_perfil);
        vender=findViewById(R.id.floatingvender);
        db=FirebaseFirestore.getInstance();

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verperfil();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_inicio);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayarticulos);


        addData();

    }



    public void verperfil(){
        Intent intentper = new Intent(this, Perfil.class );
        startActivity(intentper);
    }

    private void addData() {
        // Los datos en Firestore deben tener la estructura de modelo en JSON.

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");
        progressDialog.show();

        db.collection("articulos")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            arrayarticulos = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                Articulo articulo = doc.toObject(Articulo.class);
                                arrayarticulos.add(articulo);
                            }

                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayarticulos);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(Main_page.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }
}
