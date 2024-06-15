package com.example.recetitasmonk.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recetitasmonk.R;


public class CargaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(CargaActivity.this, DrawerBaseActivity.class ));
                finish();
            }
        },2500);
    }
}

