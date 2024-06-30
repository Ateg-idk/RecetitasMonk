package com.example.recetitasmonk.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Cliente;
import com.example.recetitasmonk.fragmentos.otroFragment;
import com.example.recetitasmonk.fragmentos.user1Fragment;

public class EditarActivity extends AppCompatActivity implements View.OnClickListener {
    Cliente cliente;
    ImageView atras;
    TextView nombreuser, apellidouser, correouser, passuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);

        atras = findViewById(R.id.backButton);
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        nombreuser = findViewById(R.id.name_editText);
        nombreuser.setText(cliente.getNombre());
        apellidouser = findViewById(R.id.last_name_editText);
        apellidouser.setText(cliente.getApellidoP());
        correouser = findViewById(R.id.email_editText);
        correouser.setText(cliente.getCorreo());
        passuser = findViewById(R.id.txtContraseña);
        passuser.setText(cliente.getContraseña());
        atras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.backButton)
        onBackPressed();
    }
}