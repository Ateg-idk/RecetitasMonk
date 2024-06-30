package com.example.recetitasmonk.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Cliente;
import com.example.recetitasmonk.fragmentos.otroFragment;
import com.example.recetitasmonk.fragmentos.user1Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class EditarActivity extends AppCompatActivity implements View.OnClickListener {
    Cliente cliente;
    ImageView atras;
    TextView nombreuser, apellidouser, correouser, passuser;
    Button editar, aplicar;
    Handler handler;
    final static String urlguardarcliente = "http://recetitasmonk.atwebpages.com/servicios/guardarCliente.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        atras = findViewById(R.id.backButton);

        nombreuser = findViewById(R.id.name_editText);
        nombreuser.setText(cliente.getNombre());
        apellidouser = findViewById(R.id.last_name_editText);
        apellidouser.setText(cliente.getApellidoP());
        correouser = findViewById(R.id.email_editText);
        correouser.setText(cliente.getCorreo());
        passuser = findViewById(R.id.txtContraseña);
        passuser.setText(cliente.getContraseña());

        editar = findViewById(R.id.edit_button);
        aplicar = findViewById(R.id.apply_button);

        atras.setOnClickListener(this);

        // Deshabilitar los campos de edición al inicio
        setFieldsEnabled(false);

        // Configurar el botón de retroceso
        atras.setOnClickListener(v -> onBackPressed());

        // Configurar el botón de editar
        editar.setOnClickListener(v -> setFieldsEnabled(true));

        // Configurar el botón de aplicar
        aplicar.setOnClickListener(v -> applyChanges());

        handler = new Handler(Looper.getMainLooper());
    }

    private void setFieldsEnabled(boolean enabled) {
        nombreuser.setEnabled(enabled);
        apellidouser.setEnabled(enabled);
        correouser.setEnabled(enabled);
        passuser.setEnabled(enabled);
        aplicar.setEnabled(enabled);
    }

    private void applyChanges() {
        cliente.setNombre(nombreuser.getText().toString());
        cliente.setApellidoP(apellidouser.getText().toString());
        cliente.setCorreo(correouser.getText().toString());
        cliente.setContraseña(passuser.getText().toString());

        // Ejecutar tarea asíncrona para guardar los cambios en el servidor
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                guardarCambiosServidor(cliente);
            }
        });
        thread.start();
    }

    private void guardarCambiosServidor(Cliente cliente) {
        try {
            URL url = new URL(urlguardarcliente);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // Construir los parámetros a enviar
            String data = "idUsuarios=" + cliente.getId() +
                    "&nombre=" + cliente.getNombre() +
                    "&apellidoP=" + cliente.getApellidoP() +
                    "&correo=" + cliente.getCorreo() +
                    "&clave=" + cliente.getContraseña();
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            // Leer la respuesta del servidor
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line).append("\n");
            }
            bufferedReader.close();

            Log.d("EditarActivity", "Response from server: " + response.toString());

            // Procesar la respuesta JSON en el hilo principal
            JSONObject jsonObject = new JSONObject(response.toString());
            boolean success = jsonObject.getBoolean("success");
            String message = jsonObject.optString("message", "Error al guardar cambios");


            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (success) {
                        // Actualizar los campos de edición con los nuevos valores del cliente
                        nombreuser.setText(cliente.getNombre());
                        apellidouser.setText(cliente.getApellidoP());
                        correouser.setText(cliente.getCorreo());
                        passuser.setText(cliente.getContraseña());

                        Toast.makeText(EditarActivity.this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
                        setFieldsEnabled(false); // Deshabilitar los campos después de guardar cambios
                    } else {
                        Toast.makeText(EditarActivity.this, "Error al guardar cambios: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (IOException | JSONException e) {
            Log.e("EditarActivity", "Error en la solicitud HTTP: " + e.toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(EditarActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.backButton)
        onBackPressed();
    }
}