package com.example.recetitasmonk.actividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Hash;
import com.example.recetitasmonk.sqlite.RecetitasMonk;

public class InicioSesionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtCorreo, txtClave;
    CheckBox chkRecordar;
    Button  btnIniciar;

    TextView lblRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        txtCorreo=findViewById(R.id.emailEditText);
        txtClave=findViewById(R.id.passwordEditText);
        btnIniciar=findViewById(R.id.btnIniciar);
        chkRecordar = findViewById(R.id.logChkRecordar);
        lblRegistrar = findViewById(R.id.txtRegistrarseAhora);

        btnIniciar.setOnClickListener(this);
        lblRegistrar.setOnClickListener(this);

        validarRecordarSesion();
    }

    private void validarRecordarSesion() {
        RecetitasMonk tm = new RecetitasMonk(this);
        if(tm.recordoSesion())
            iniciarSesion(tm.getValue("correo"), tm.getValue("clave"), true);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnIniciar)
             iniciarSesion(txtCorreo.getText().toString().trim().toLowerCase(),txtClave.getText().toString(), false);
        else if(v.getId() == R.id.txtRegistrarseAhora)
            registrar();
    }

    private void iniciarSesion(String correo, String clave, boolean recordar){
        RecetitasMonk rm = new RecetitasMonk(this);
        Hash hash = new Hash();
        clave = recordar == true ? clave : hash.StringToHash(clave,"SHA256").toLowerCase();
        if (correo.equals("user") && clave.equals("8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414")){
            if (chkRecordar.isChecked()){
                //Guardar credenciales a SQlite
                rm.agregarUsuario(1,correo,clave);
            }
            Intent iBienvenida = new Intent(this, DrawerBaseActivity.class);
            iBienvenida.putExtra("nombre ","Fabian");
            startActivity(iBienvenida);
            finish();
        }else {
            Toast.makeText(this,"Correo o clave incorrecta",Toast.LENGTH_SHORT).show();
        }
    }

    private void registrar() {
        Intent iRegistro = new Intent(this, ActRegistro.class);
        startActivity(iRegistro);
        finish();
    }

}

