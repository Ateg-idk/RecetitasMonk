package com.example.recetitasmonk.actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.recetitasmonk.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class recetaActivity extends AppCompatActivity implements View.OnClickListener{
    /*intent.putExtra("idReceta", receta.getIdRecetas());
    intent.putExtra("nombreReceta", receta.getNombreReceta());
    intent.putExtra("ingredientes", receta.getIngredientes());
    intent.putExtra("preparacion", receta.getPreparacion());*/
    TextView txtNombreReceta,txtIngredientes,txtPreparacion,txtIdReceta,txtUsuario;
    ImageView imgReceta;
    ImageView atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_receta);
        Intent intent = getIntent();
        txtNombreReceta = findViewById(R.id.txtNombreReceta);
        txtIngredientes = findViewById(R.id.txtIngredientes);
        txtPreparacion = findViewById(R.id.txtPreparacion);
        imgReceta = findViewById(R.id.imgReceta);
        txtUsuario = findViewById(R.id.txtUsuario);
        String nombreReceta = intent.getStringExtra("nombreReceta");
        String ingrediente = intent.getStringExtra("ingredientes");
        String preparacion = intent.getStringExtra("preparacion");
        Integer idRecegit merta = intent.getIntExtra("idReceta",0);
        //String foto = intent.getStringExtra("foto");
        atras = findViewById(R.id.backButton);
        atras.setOnClickListener(this);
        txtNombreReceta.setText(nombreReceta);
        //txtIngredientes.setText(ingrediente);
        //txtPreparacion.setText(preparacion);

        obtenerImagenReceta(idReceta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    private void obtenerImagenReceta(int idReceta) {
        String urlImagen = "http://recetitasmonk.atwebpages.com/servicios/mostrarRecetasPorId.php?idReceta=" + idReceta;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlImagen, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200) {
                    try {
                        // Supongamos que la respuesta contiene la imagen codificada en Base64
                        String imagenBase64 = new JSONArray(rawJsonResponse).getJSONObject(0).getString("imagen");
                        String usuario = new JSONArray(rawJsonResponse).getJSONObject(0).getString("nombreUsuario");
                        byte[] imgByte = Base64.decode(imagenBase64, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                        imgReceta.setImageBitmap(bitmap);
                        txtUsuario.setText("Por: "+usuario);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.backButton)
            onBackPressed();
    }
}
