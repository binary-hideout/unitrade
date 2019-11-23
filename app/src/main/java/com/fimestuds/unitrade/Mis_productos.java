package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class Mis_productos extends AppCompatActivity implements RecyclerViewAdapter.OnArticuloListener {
    private List<Articulo> mis_articulos;
    RecyclerView recyclerViewp;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_productos);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        recyclerViewp = (RecyclerView) findViewById(R.id.recyclerView_productos);
        recyclerViewp.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewp.setLayoutManager(layoutManager);
        String user_id = user.getUid();



        addData(user_id);
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
                                            Mis_productos.this
                                    );
                            recyclerViewp.setAdapter(adapter);

                        } else {
                            Toast.makeText(Mis_productos.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }


    @Override
    public void onArticuloClick(int position, Articulo articulo) {
        mis_articulos.get(position);
        //checar y mandar al articulo correspondiente
        Intent vermisart_intent = new Intent(this, Pantalla_venta.class);
        vermisart_intent.putExtra("articulo", articulo);
        startActivity(vermisart_intent);
    }
}
