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
import com.example.recetitasmonk.clases.Cliente;
import com.example.recetitasmonk.clases.Inicio;
import com.example.recetitasmonk.fragmentos.CategoriasFragment;
import com.example.recetitasmonk.fragmentos.MapFragment;
import com.example.recetitasmonk.fragmentos.busquedaFragment;
import com.example.recetitasmonk.fragmentos.ininFragment;
import com.example.recetitasmonk.fragmentos.nuevarecetaFragment;
import com.example.recetitasmonk.fragmentos.otroFragment;
import com.example.recetitasmonk.fragmentos.user1Fragment;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements Inicio {

    Cliente cliente ;


    Fragment [] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base);

        fragments = new Fragment[5];
        fragments [0] = new ininFragment();
        fragments [1] = new CategoriasFragment();
        fragments [2] = new nuevarecetaFragment();
        fragments [3] = new user1Fragment();
        fragments [4] = new MapFragment();

        int idBoton = getIntent().getIntExtra("idBoton", -1);
        onClickInicio(idBoton);

        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

    }
    @Override
    public void onClickInicio(int idBoton) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menunav, fragments[idBoton]);
        ft.commit();
    }


}