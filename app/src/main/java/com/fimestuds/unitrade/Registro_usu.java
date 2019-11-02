package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Registro_usu extends AppCompatActivity {
    private Button crear, regreso;
    public EditText username,emailedtxt, pssd, conf_pssd;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usu);
        crear=findViewById(R.id.btn_registro);
        regreso=findViewById(R.id.btn_reg_log);
        username=findViewById(R.id.reg_nom_usu);
        pssd=findViewById(R.id.reg_contra_usu);
        emailedtxt=findViewById(R.id.email);
        conf_pssd=findViewById(R.id.reg_contraconf_usu);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");

        getSupportActionBar().hide();
        //Firebase instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearusu();
            }
        });
        regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });
    }

    // falta validacion
    private void crearusu(){
        final String username= this.username.getText().toString().trim();
        final String password= this.pssd.getText().toString().trim();
        final String conf_password= this.conf_pssd.getText().toString().trim();
        final String email= this.emailedtxt.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Ingrese un nombre de usuario", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese una contraseña valida", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(conf_password)) {
            Toast.makeText(this, "Confirme su contraseña adecuadamente", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // TODO: Inicio de sesión
                            // Una vez se crea la cuenta, se procede a crear sus datos en la db
                            // Usamos la colección usuarios, y su documento será el uid del usuario,
                            // que es su id unico en firebase.
                            // si no se pueden crear los datos en la db, se debería eliminar la cuenta
                            // creada

                            // Sign in success, update UI with the signed-in user's information


                            HashMap<String, Object> datos = new HashMap<>();
                            datos.put("username", username);
                            datos.put("email", email);

                            FirebaseUser user = mAuth.getCurrentUser();
                            createUserData(user, datos);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Registro_usu.this, "Fallo el registro, verifique sus datos",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });

    }


    private void createUserData(final FirebaseUser user, HashMap<String, Object> datos) {
        db.collection("usuarios")
                .document(user.getUid())
                .set(datos)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Registro_usu.this, "Se ha registrado exitosamente",
                                    Toast.LENGTH_SHORT).show();

                            iniciarsesion();
                        } else {
                            user.delete();

                            Toast.makeText(Registro_usu.this, "Fallo el registro, verifique sus datos",
                                    Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }




    private void regresar(){
        Intent intentreg = new Intent(this, Login.class);
        startActivity(intentreg);
    }
    private void iniciarsesion(){
        Intent intentinit = new Intent(this, Main_page.class);
        startActivity(intentinit);
    }
}

