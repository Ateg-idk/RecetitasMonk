package com.example.recetitasmonk.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Inicio;
import com.google.android.material.navigation.NavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ininFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ininFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, Inicio {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ininFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ininFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ininFragment newInstance(String param1, String param2) {
        ininFragment fragment = new ininFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private DrawerLayout drawerLayout;
    Fragment[] fragments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inin, container, false);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        fragments = new Fragment[5];
        fragments[0] = new ininFragment();
        fragments[1] = new busquedaFragment();
        fragments[2] = new otroFragment();
        fragments[3] = new user1Fragment();
        fragments[4] = new nuevarecetaFragment();
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_barras);

        drawerLayout = v.findViewById(R.id.drawerLayout);
        NavigationView navigationView = v.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Cerrar el DrawerLayout si está abierto al iniciar el fragmento
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }

        // Agregar listener para el icono de la derecha
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });

        return v;
    }

    @Override
    public void onClickInicio(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menunav, fragments [idBoton]);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(R.id.nav_receta == menuItem.getItemId()){
            int idBoton = 4;
            drawerLayout.closeDrawer(GravityCompat.END);
            onClickInicio(idBoton);
        }
        if (R.id.nav_cerrarSesion == menuItem.getItemId()) {
            // Reemplazar el fragmento actual con el fragmento de búsqueda
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new busquedaFragment()).commit();
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        }
        return false;
    }
}