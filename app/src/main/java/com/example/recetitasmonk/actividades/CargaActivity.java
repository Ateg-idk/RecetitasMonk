package com.example.recetitasmonk.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import com.example.recetitasmonk.R;

public class CargaActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "app_preferences";
    private static final String TERMS_ACCEPTED = "terms_accepted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        boolean termsAccepted = preferences.getBoolean(TERMS_ACCEPTED, false);

        if (!termsAccepted) {
            showTermsAndConditionsDialog();
        } else {
            proceedToNextActivity();
        }
    }

    private void showTermsAndConditionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.terms_conditions_title));
        builder.setMessage(getString(R.string.terms_conditions));

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            // El usuario aceptó los términos y condiciones
            SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(TERMS_ACCEPTED, true);
            editor.apply();

            dialog.dismiss();
            proceedToNextActivity();
        });

        builder.setNegativeButton("Rechazar", (dialog, which) -> {
            // El usuario rechazó los términos y condiciones
            dialog.dismiss();
            finish(); // Finaliza CargaActivity y cierra la aplicación
        });

        builder.setCancelable(false); // Evita que el usuario pueda cerrar el diálogo sin tomar una decisión
        builder.show();
    }

    private void proceedToNextActivity() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(CargaActivity.this, InicioSesionActivity.class));
            finish();
        }, 2500);
    }
}
