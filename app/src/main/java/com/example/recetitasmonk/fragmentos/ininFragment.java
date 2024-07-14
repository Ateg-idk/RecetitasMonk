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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Categoria;
import com.example.recetitasmonk.clases.Inicio;
import com.example.recetitasmonk.clases.Receta;
import com.google.android.material.navigation.NavigationView;
import com.example.recetitasmonk.adaptadores.RecetaAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class ininFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, Inicio {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ininFragment() {
        // Required empty public constructor
    }

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

    final static String urlMostrarReceta = "http://recetitasmonk.atwebpages.com/servicios/mostrarReceta.php";

    ArrayList<Receta> listaReceta;
    RecetaAdapter adapter;

    RecyclerView recyclerVistas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }

        recyclerVistas = v.findViewById(R.id.recyclerVista);
        listaReceta = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerVistas.setLayoutManager(manager);
        adapter = new RecetaAdapter(listaReceta,this.getContext());
        recyclerVistas.setAdapter(adapter);

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

        MostrarReceta();

        return v;
    }

    private void MostrarReceta() {
        AsyncHttpClient ahcMostrarReceta = new AsyncHttpClient();
        ahcMostrarReceta.get(urlMostrarReceta, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        listaReceta.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            listaReceta.add(new Receta(
                                    jsonArray.getJSONObject(i).getInt("idRecetas"),
                                    jsonArray.getJSONObject(i).getString("nombreReceta"),
                                    jsonArray.getJSONObject(i).getString("departamento"),
                                    "",
                                    jsonArray.getJSONObject(i).getString("imagen"),
                                    jsonArray.getJSONObject(i).getString("departamento"),
                                    jsonArray.getJSONObject(i).getInt("idUsuarios"),
                                    jsonArray.getJSONObject(i).getInt("idCategoria")
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                // Manejo de errores
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public void onClickInicio(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menunav, fragments[idBoton]);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (R.id.nav_receta == menuItem.getItemId()) {
            int idBoton = 4;
            drawerLayout.closeDrawer(GravityCompat.END);
            onClickInicio(idBoton);
            return true; // Retorna true para indicar que el ítem fue manejado
        } else if(R.id.nav_Buscar == menuItem.getItemId()) {
            int idBoton = 1;
            drawerLayout.closeDrawer(GravityCompat.END);
            onClickInicio(idBoton);
            return true; // Retorna true para indicar que el ítem fue manejado
        }else if (R.id.nav_cerrarSesion == menuItem.getItemId()) {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new busquedaFragment()).commit();
            drawerLayout.closeDrawer(GravityCompat.END);
            return true; // Retorna true para indicar que el ítem fue manejado
        }
        return false;
    }
}
