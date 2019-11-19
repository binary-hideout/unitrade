package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Vender_artic extends AppCompatActivity {
    public static final int PICK_IMAGE_REQUEST=1;
    private Uri imguri;



    private EditText edt_costo, ed_nombre, ed_descrip, contacto;
    private Button vender;
    private ImageButton add_img;
    private ImageView vistaprevia;
    public ProgressBar ProgrBar;
    //Firebase
    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseFirestore db;
    private DatabaseReference dbref;

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
        vistaprevia=findViewById(R.id.img_vista_previa);
        ProgrBar=findViewById(R.id.ProgBar);

        //storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReference("imgs_art");
        db = FirebaseFirestore.getInstance();
       // dbref= FirebaseDatabase.getInstance().getReference("imgs_art");

        vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VenderArticulo(v);
               // uploadimage();
            }
        });
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_image();
            }
        });

    }



    public void add_image(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una Imagen"), PICK_IMAGE_REQUEST);

    }
    //Checa que la imagen no este vacía antes de mostrarla
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode== RESULT_OK
                && data!=null && data.getData()!= null){

            imguri=data.getData();
            Glide.with(this).load(imguri).apply(new RequestOptions().override(190, 200)).into(vistaprevia);
        }
    }

    public void VenderArticulo(View view){
        String nombre = this.ed_nombre.getText().toString().trim();
        String costo = this.edt_costo.getText().toString().trim();
        String contac = this.contacto.getText().toString().trim();
        String desc = this.ed_descrip.getText().toString().trim();


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String user_id = user.getUid();

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

            data.put("user_id", user_id);

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
                                onBackPressed();
                            } else {
                                Toast.makeText(Vender_artic.this,
                                        "Algo no fue bien, intente de nuevo más tarde",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

   /* private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //Subir las imagenes a Firebase
    private void uploadimage(){
        if(imguri!= null){

            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
            +"."+getFileExtension(imguri));
            fileReference.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Vender_artic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    ProgrBar.setProgress((int) progress);
                }
            });
        }else {
            Toast.makeText(this, "No hay imagen seleccionada", Toast.LENGTH_SHORT).show();
        }

    }*/

        }
