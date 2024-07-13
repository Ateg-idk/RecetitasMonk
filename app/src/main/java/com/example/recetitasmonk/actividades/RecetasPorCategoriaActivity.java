package com.example.recetitasmonk.actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recetitasmonk.R;
import com.example.recetitasmonk.adaptadores.RecetaAdapter;
import com.example.recetitasmonk.clases.Receta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RecetasPorCategoriaActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView atras;
    RecyclerView recyclerView;
    RecetaAdapter recetaAdapter;
    List<Receta> recetaList;
    TextView txtCategoria;
    ImageView imgCategoria;
    String url = "http://recetitasmonk.atwebpages.com/servicios/mostrarRecetasPorCategoria.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_por_categoria);

        recyclerView = findViewById(R.id.recyclerViewRecetas);
        txtCategoria = findViewById(R.id.txtCategoria);
        imgCategoria = findViewById(R.id.imagen_receta);
        atras = findViewById(R.id.backButton);
        atras.setOnClickListener(this);

        recetaList = new ArrayList<>();
        recetaAdapter = new RecetaAdapter(recetaList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recetaAdapter);

        Intent intent = getIntent();
        int idCategoria = intent.getIntExtra("idCategoria", -1);
        String nombreCategoria = intent.getStringExtra("nombreCategoria");
        //String imgCategoriaBase64 = intent.getStringExtra("imgCategoria");

        txtCategoria.setText(nombreCategoria);
        //byte[] imgByte = Base64.decode(imgCategoriaBase64, Base64.DEFAULT);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        //imgCategoria.setImageBitmap(bitmap);
        obtenerImagenCategoria(idCategoria);

        cargarRecetasPorCategoria(idCategoria);
    }

    private void obtenerImagenCategoria(int idCategoria) {
        String urlImagen = "http://recetitasmonk.atwebpages.com/servicios/obtenerImagenCategoria.php?idCategoria=" + idCategoria;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlImagen, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200) {
                    try {
                        // Supongamos que la respuesta contiene la imagen codificada en Base64
                        String imagenBase64 = new JSONArray(rawJsonResponse).getJSONObject(0).getString("imagen");
                        byte[] imgByte = Base64.decode(imagenBase64, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                        imgCategoria.setImageBitmap(bitmap);
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

    private void cargarRecetasPorCategoria(int idCategoria) {
        AsyncHttpClient client = new AsyncHttpClient();
        String urlConParametro = url + "?idCategoria=" + idCategoria;
        client.get(urlConParametro, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        recetaList.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            recetaList.add(new Receta(
                                    jsonArray.getJSONObject(i).getInt("idRecetas"),
                                    jsonArray.getJSONObject(i).getString("nombreReceta"),
                                    jsonArray.getJSONObject(i).getString("ingredientes"),
                                    jsonArray.getJSONObject(i).getString("preparacion"),
                                    jsonArray.getJSONObject(i).getString("imagen"),
                                    jsonArray.getJSONObject(i).getString("departamento"),
                                    jsonArray.getJSONObject(i).getInt("idUsuarios"),
                                    jsonArray.getJSONObject(i).getInt("idCategoria")
                            ));
                        }
                        recetaAdapter.notifyDataSetChanged();
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