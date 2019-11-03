package com.fimestuds.unitrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText nom_usu, contra_usu;
    private Button iniciar, registrarse;
    private String username, pssd;
    private TextView changepssd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nom_usu=findViewById(R.id.edtxt_nom_usu);
        contra_usu=findViewById(R.id.edtxt_contra_usu);
        iniciar=findViewById(R.id.btn_iniciarses);
        registrarse=findViewById(R.id.btn_a_registrarse);
        changepssd=findViewById(R.id.txt_change_pssd);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticar();
            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a_registro();
            }
        });
        changepssd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_change_pssd();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        dialog.show();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        iniciodesesion();
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        } else {
            dialog.dismiss();
        }
    }

    private void iniciodesesion(){
        Intent intentini = new Intent(this, Main_page.class);
        startActivity(intentini);
        finish();
    }
    public void user_change_pssd(){
        Intent change = new Intent(this, User_pssd.class);
        startActivity(change);
        finish();
    }
    private void autenticar(){

        final String pssd = this.contra_usu.getText().toString().trim();
        final String username=this.nom_usu.getText().toString().trim();
        if (username.length() == 0) {
            nom_usu.setError("Ingresa un correo válido");
        } else {
            mAuth.signInWithEmailAndPassword(username, pssd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        FirebaseUser user = mAuth.getCurrentUser();
                        // user.getDisplayName();
                        iniciodesesion(); }
                    else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(Login.this, "Autenticacion fallida",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }


    private void a_registro(){
        Intent intentreg = new Intent(this, Registro_usu.class);
        startActivity(intentreg);
        finish();
    }
}
