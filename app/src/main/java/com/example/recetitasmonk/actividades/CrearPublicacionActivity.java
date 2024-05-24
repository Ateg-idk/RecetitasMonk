package com.example.recetitasmonk.actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.sqlite.RecetitasMonk;

public class CrearPublicacionActivity extends AppCompatActivity implements View.OnClickListener{
    EditText nombrePlato, ingredientes, preparacion;
    Button publicar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_publicacion);

        ingredientes=findViewById(R.id.ingredientesEditText);
        preparacion=findViewById(R.id.preparacionEditText);
        nombrePlato=findViewById(R.id.nombrePlatoEditText);
        publicar = findViewById(R.id.publicar);
        publicar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.publicar)
            CrearPublicacion(nombrePlato.getText().toString().trim().toLowerCase(),ingredientes.getText().toString(),preparacion.getText().toString());
    }

    private void CrearPublicacion(String nombrePlato, String ingredientes, String preparacion) {
        RecetitasMonk rm = new RecetitasMonk(this);
        rm.publicarReceta(nombrePlato,ingredientes,preparacion);
    }
}