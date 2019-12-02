package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Main_page extends AppCompatActivity implements RecyclerViewAdapter.OnArticuloListener{
    private AdapterViewFlipper flip_inicio;
    private EditText buscadortxt;
    private ImageButton buscar;
    private List<Articulo> arrayarticulos;
    private List<Imagen> arrayimagenes;
    private FirebaseFirestore db;
    FloatingActionButton vender;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    Context context;
    public ImageView perfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();
        perfil=findViewById(R.id.btn_perfil);
        flip_inicio=findViewById(R.id.flipper);
        buscadortxt=findViewById(R.id.edt_buscador);
        buscar=findViewById(R.id.btn_buscar);
        vender=findViewById(R.id.floatingvender);
        refreshLayout=findViewById(R.id.refresh_layout);
        db=FirebaseFirestore.getInstance();

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verperfil();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addData();
                addImages();
            }
        });

        vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                venderarts();
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textobuscar=buscadortxt.getText().toString().trim();
                if(textobuscar.isEmpty()){
                    Toast.makeText(Main_page.this, "No hay texto a buscar", Toast.LENGTH_LONG).show();
                }
                else{
                    buscarpost(textobuscar);
                }

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

    public void buscarpost(final String txt){
        //Buscar por titulo
        db.collection("articulos")
                .orderBy("art_name").startAt(txt).endAt(txt + "\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Main_page.this, "Se encontro el post "+ txt, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(Main_page.this, "No se encontro el post", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void addData() {
        // Los datos en Firestore deben tener la estructura de modelo en JSON.

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");
        progressDialog.show();
        refreshLayout.setRefreshing(false);

        //Falta corregir el whereEqualto porque no jala :(


        db.collection("articulos")
                .orderBy("timestamp", Query.Direction.DESCENDING)
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
                            refreshLayout.setRefreshing(false);
                            //  int position =0;
                            for (DocumentSnapshot doc : task.getResult()) {
                                Imagen imagen = doc.toObject(Imagen.class);
                                arrayimagenes.add(imagen);

                            }
                            setFlip_inicio();


                        } else {
                            refreshLayout.setRefreshing(false);
                            Toast.makeText(Main_page.this, "No hay imagenes que cargar", Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }

    public void setFlip_inicio(){
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), arrayimagenes);
        flip_inicio.setAdapter(customAdapter);

    }
    public void onFlipperArrowLeftClick(View view) {
        if (flip_inicio != null) {
            flip_inicio.showPrevious();
        }
    }

    public void onFlipperArrowRightClick(View view) {
        if (flip_inicio != null) {
            flip_inicio.showNext();
        }
    }

    @Override
    public void onArticuloClick(int position, Articulo articulo) {
        arrayarticulos.get(position);
        //checar y mandar al articulo correspondiente
        Intent verart_intent = new Intent(this, Pantalla_venta.class);
        verart_intent.putExtra("articulo", articulo);
        startActivity(verart_intent);
    }

    public void venderarts(){
        Intent venint = new Intent(this, Vender_artic.class);
        startActivity(venint);
    }



    public void filtrarRating(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");
        progressDialog.show();
        refreshLayout.setRefreshing(false);

        db.collection("articulos")
                .orderBy("rating", Query.Direction.DESCENDING)
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
}

