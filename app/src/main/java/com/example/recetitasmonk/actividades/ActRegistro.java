package com.example.recetitasmonk.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.AsyncHttpStack;
import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Hash;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class ActRegistro extends AppCompatActivity implements View.OnClickListener {
    private final static String urlAgregarCliente = "http://recetitasmonk.atwebpages.com/servicios/agregarCliente.php";
    EditText txtRNombre, txtRApelliP, txtRApelliM, txtRDni, txtRCelular, txtREmail, txtRContra;
    RadioGroup rgrSexo;
    RadioButton rbtRMaculino, rbtRFemenino;
    Button btnRRegis, btnRCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_cliente);

        txtRNombre = findViewById(R.id.txtRegNombre);
        txtRApelliP = findViewById(R.id.txtRegApelliP);
        txtRApelliM = findViewById(R.id.txtRegApelliM);
        txtRDni = findViewById(R.id.txtRegDni);
        txtRCelular = findViewById(R.id.txtRegCelular);
        txtREmail = findViewById(R.id.txtRegEmail);
        txtRContra = findViewById(R.id.txtRegContra);

        rgrSexo = findViewById(R.id.regRgrSexo);
        rbtRMaculino = findViewById(R.id.regRbtMaculino);
        rbtRFemenino = findViewById(R.id.regRbtFemenino);


        btnRRegis = findViewById(R.id.regbtnRegis);
        btnRCancel = findViewById(R.id.regbtnCancel);

        btnRRegis.setOnClickListener(this);
        btnRCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.regbtnRegis)
            registrarCliente();
        else if (v.getId() == R.id.regbtnCancel)
            cancelarRegistro();

    }

    private void registrarCliente() {
        AsyncHttpClient ahcregistrar = new AsyncHttpClient();
        RequestParams params =  new RequestParams();
        Hash hash = new Hash();
        int rbtSelecionado;

        if (validarFormulario()){
            params.add("nombre",txtRNombre.getText().toString());
            params.add("apellidoP",txtRApelliP.getText().toString());
            params.add("apellidoM",txtRApelliM.getText().toString());
            params.add("dni",txtRDni.getText().toString());
            rbtSelecionado = rgrSexo.getCheckedRadioButtonId();
            if (rbtSelecionado == R.id.regRbtMaculino)
                params.add("sexo","M");
            else if (rbtSelecionado == R.id.regRbtFemenino)
                params.add("sexo","F");
            params.add("celular",txtRCelular.getText().toString());
            params.add("correo",txtREmail.getText().toString());
            params.add("clave",hash.StringToHash(txtRContra.getText().toString(),"SHA256"));

            ahcregistrar.post(urlAgregarCliente, params, new BaseJsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    if (statusCode == 200){
                        int retVal = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                        if (retVal == 1) {
                            Toast.makeText(getApplicationContext(),"Usuario Agregado", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent iInicioSesion = new Intent(getApplicationContext(),InicioSesionActivity.class);
                            startActivity(iInicioSesion);
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
        else {
            Toast.makeText(this,"complete correctamente el formulario",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarFormulario() {
        if (txtRNombre.getText().toString().isEmpty())
            return false;
        if (txtRApelliP.getText().toString().isEmpty())
            return false;
        if (txtRApelliM.getText().toString().isEmpty())
            return false;

        if (txtRDni.getText().toString().isEmpty() || txtRDni.getText().toString().length() != 8)
            return false;
        if (txtRCelular.getText().toString().isEmpty())
            return false;
        if (txtREmail.getText().toString().isEmpty())
            return false;
        if (txtRContra.getText().toString().isEmpty())
            return false;

        return true;
    }

    private void cancelarRegistro() {
        finish();
        Intent iInicioSesion = new Intent(this, InicioSesionActivity.class);
        startActivity(iInicioSesion);
    }
}
