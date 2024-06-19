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
import com.example.recetitasmonk.clases.Cliente;
import com.example.recetitasmonk.clases.Hash;
import com.example.recetitasmonk.sqlite.RecetitasMonk;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class InicioSesionActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String urlIniciarSesion = "http://recetitasmonk.atwebpages.com/servicios/iniciarSesion.php";

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



    private void iniciarSesion(String correo, String clave, boolean recordar){
        RecetitasMonk rm = new RecetitasMonk(this);
        Hash hash = new Hash();
        AsyncHttpClient ahcIniciarSesion = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        clave = recordar == true ? clave : hash.StringToHash(clave,"SHA256").toLowerCase();
        params.add("correo", correo);
        params.add("clave", clave);

        ahcIniciarSesion.post(urlIniciarSesion,params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200){

                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        if (jsonArray.length()>0){
                            int id = jsonArray.getJSONObject(0).getInt("idUsuarios");
                            if (id == -1)
                                Toast.makeText(getApplicationContext(),"Credenciales Incorrectas",Toast.LENGTH_SHORT).show();
                            else {
                                Cliente cliente = new Cliente();
                                cliente.setId(id);
                                cliente.setNombre(jsonArray.getJSONObject(0).getString("nombre"));
                                cliente.setApellidoP(jsonArray.getJSONObject(0).getString("apellidoP"));
                                cliente.setApellidoM(jsonArray.getJSONObject(0).getString("apellidoM"));
                                cliente.setDni(jsonArray.getJSONObject(0).getString("dni"));
                                cliente.setGenero(jsonArray.getJSONObject(0).getString("genero").charAt(0));
                                cliente.setCelular(jsonArray.getJSONObject(0).getString("celular"));
                                cliente.setCorreo(jsonArray.getJSONObject(0).getString("correo"));
                                cliente.setContraseña(jsonArray.getJSONObject(0).getString("contraseña"));

                                if (chkRecordar.isChecked())
                                    rm.agregarUsuario(cliente.getId(),cliente.getNombre(),cliente.getApellidoP());

                                Intent iBenvenida = new Intent(getApplicationContext(),DrawerBaseActivity.class);
                                iBenvenida.putExtra("cliente", cliente);
                                iBenvenida.putExtra("idBoton",0);
                                startActivity(iBenvenida);
                                finish();

                            }

                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(),"ERROR: " +statusCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });

    }

    private void registrar() {
        Intent iRegistro = new Intent(this, ActRegistro.class);
        startActivity(iRegistro);
        finish();
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnIniciar)
            iniciarSesion(txtCorreo.getText().toString().trim().toLowerCase(),txtClave.getText().toString(), false);
        else if(v.getId() == R.id.txtRegistrarseAhora)
            registrar();
    }

}

