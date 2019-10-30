package com.fimestuds.unitrade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class main_page extends AppCompatActivity {

    public ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        back=findViewById(R.id.back_login);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back2login();
            }
        });
    }

    public void back2login(){
        Intent intent1 = new Intent(this, Login.class );
        startActivity(intent1);
    }
}
