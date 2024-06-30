package com.example.recetitasmonk.actividades;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Inicio;
import com.example.recetitasmonk.fragmentos.MapFragment;
import com.example.recetitasmonk.fragmentos.busquedaFragment;
import com.example.recetitasmonk.fragmentos.ininFragment;
import com.example.recetitasmonk.fragmentos.otroFragment;
import com.example.recetitasmonk.fragmentos.user1Fragment;

public class menfracActivity extends AppCompatActivity implements Inicio {

    Fragment[] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menfrac);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fragments = new Fragment[5];
        fragments [0] = new ininFragment();
        fragments [1] = new busquedaFragment();
        fragments [2] = new otroFragment();
        fragments [3] = new user1Fragment();
        fragments [4] = new MapFragment();

    }

    @Override
    public void onClickInicio(int idBoton) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navArea, fragments[idBoton]);
        ft.commit();
    }
}