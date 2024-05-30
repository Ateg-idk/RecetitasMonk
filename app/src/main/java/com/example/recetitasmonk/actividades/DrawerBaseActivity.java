package com.example.recetitasmonk.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Inicio;
import com.example.recetitasmonk.fragmentos.busquedaFragment;
import com.example.recetitasmonk.fragmentos.ininFragment;
import com.example.recetitasmonk.fragmentos.otroFragment;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Inicio {

    private DrawerLayout drawerLayout;

    Fragment [] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base);

        fragments = new Fragment[3];
        fragments [0] = new ininFragment();
        fragments [1] = new busquedaFragment();
        fragments [2] = new otroFragment();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        if (savedInstanceState == null){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ininFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_perfil);

        }


    }
    @Override
    public void onClickInicio(int idBoton) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menunav, fragments[idBoton]);
        ft.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}