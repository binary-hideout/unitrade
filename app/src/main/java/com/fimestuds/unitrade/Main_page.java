package com.fimestuds.unitrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Main_page extends AppCompatActivity {
    private AdapterViewFlipper flip_inicio;
    private List<Articulo> arrayarticulos;
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

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verperfil();
            }
        });

        /*recyclerView = (RecyclerView) findViewById(R.id.recyclerView_inicio);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

*/

    }



    public void verperfil(){
        Intent intentper = new Intent(this, Perfil.class );
        startActivity(intentper);
    }
}
