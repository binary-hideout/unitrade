package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

public class Main_page extends AppCompatActivity implements RecyclerViewAdapter.OnArticuloListener{
    private AdapterViewFlipper flip_inicio;
    private List<Articulo> arrayarticulos;
    private List<Imagen> arrayimagenes;
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
        flip_inicio=findViewById(R.id.flipper);
        vender=findViewById(R.id.floatingvender);
        db=FirebaseFirestore.getInstance();

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verperfil();
            }
        });

        vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                venderarts();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_inicio);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayarticulos, this);


        addData();
        addImages();

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

        //Falta corregir el whereEqualto porque si se deshace el comentario, no jala :(
        db.collection("articulos")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                //.whereEqualTo("visible", 1)
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

                            RecyclerViewAdapter adapter =
                                    new RecyclerViewAdapter(
                                            arrayarticulos,
                                            Main_page.this
                                    );
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(Main_page.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    public void addImages(){
        db.collection("imagenes")
                .whereEqualTo("idType", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            arrayimagenes = new ArrayList<>();
                            //  int position =0;
                            for (DocumentSnapshot doc : task.getResult()) {
                                Imagen imagen = doc.toObject(Imagen.class);
                                arrayimagenes.add(imagen);

                            }
                            setFlip_inicio();

                        } else {
                            Toast.makeText(Main_page.this, "No hay imagenes que cargar", Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }

    public void setFlip_inicio(){
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), arrayimagenes);
        flip_inicio.setAdapter(customAdapter);
        flip_inicio.setFlipInterval(2700);
        flip_inicio.setAutoStart(true);
    }

    @Override
    public void onArticuloClick(int position, Articulo articulo) {
        arrayarticulos.get(position);
        //checar y mandar al articulo correspondiente
        Intent verart_intent = new Intent(this, Pantalla_venta.class);
       // verart_intent.putExtra("articulo", (Parcelable) articulo);
        startActivity(verart_intent);
    }

    public void venderarts(){
        Intent venint = new Intent(this, Pantalla_venta.class);
        startActivity(venint);
    }
}

