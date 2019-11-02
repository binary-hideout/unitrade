package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Vender_artic extends AppCompatActivity {
    private EditText edt_costo, ed_nombre, ed_descrip, contacto;
    private Button vender, add_img;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_artic);
        ed_descrip=findViewById(R.id.vender_descripción);
        ed_nombre=findViewById(R.id.vender_nombre);
        edt_costo=findViewById(R.id.vender_precio);
        contacto=findViewById(R.id.ven_contacto);
        vender=findViewById(R.id.btn_vender_post);
        add_img=findViewById(R.id.agregar_img);

        db = FirebaseFirestore.getInstance();

        vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VenderArticulo(v);
            }
        });

    }

    public void VenderArticulo(View view){
        String nombre = this.ed_nombre.getText().toString().trim();
        String costo = this.edt_costo.getText().toString().trim();
        String contac = this.contacto.getText().toString().trim();
        String desc = this.ed_descrip.getText().toString().trim();

        if (nombre.isEmpty() || costo.isEmpty() ||
                contac.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> data = new HashMap<>();
            data.put("timestamp", System.currentTimeMillis());
            data.put("art_name", nombre);
            data.put("costo", costo);
            data.put("celular", contac);
            data.put("descripcion", desc);
            //Falta imagen tmb, no se muy bien qp
            // Este campo es importante, hará que no se muestre en la app hasta su aprobacióno podemos dejarlo en 1 para que se vea
            data.put("visible", 1);
            data.put("type", 1);
            data.put("imagen", "");

            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Cargando");
            dialog.show();
            //accedemos a la coleccion de firebase
            db.collection("articulos")
                    .add(data)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            dialog.dismiss();

                            if (task.isSuccessful()) {
                                Vender_artic.this.edt_costo.getText().clear();
                                Vender_artic.this.ed_descrip.getText().clear();
                                Vender_artic.this.ed_nombre.getText().clear();
                                Vender_artic.this.contacto.getText().clear();

                                Toast.makeText(Vender_artic.this,
                                        "Su publicación fue enviada",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Vender_artic.this,
                                        "Algo no fue bien, intente de nuevo más tarde",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

        }
