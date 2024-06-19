package com.example.recetitasmonk.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Inicio;
import com.example.recetitasmonk.fragmentos.CategoriasFragment;
import com.example.recetitasmonk.fragmentos.busquedaFragment;
import com.example.recetitasmonk.fragmentos.ininFragment;
import com.example.recetitasmonk.fragmentos.otroFragment;
import com.example.recetitasmonk.fragmentos.user1Fragment;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements Inicio {


    Fragment [] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base);

        fragments = new Fragment[4];
        fragments [0] = new ininFragment();
        fragments [1] = new busquedaFragment();
        fragments [2] = new otroFragment();
        fragments [3] = new user1Fragment();

        int idBoton = getIntent().getIntExtra("idBoton", -1);
        onClickInicio(idBoton);

    }
    @Override
    public void onClickInicio(int idBoton) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menunav, fragments[idBoton]);
        ft.commit();
    }


}