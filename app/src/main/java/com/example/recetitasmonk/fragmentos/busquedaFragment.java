package com.example.recetitasmonk.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Inicio;
import com.example.recetitasmonk.sqlite.RecetitasMonk;

import java.util.ArrayList;
import com.example.recetitasmonk.adaptadores.ResultadoAdapter;
import com.example.recetitasmonk.clases.Categoria;
import com.example.recetitasmonk.clases.Resultado;
import com.example.recetitasmonk.clases.Inicio;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.EditText;
import java.util.ArrayList;

import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.recetitasmonk.sqlite.RecetitasMonk;
import com.example.recetitasmonk.adaptadores.ResultadoAdapter;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link busquedaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class busquedaFragment extends Fragment implements View.OnClickListener , Inicio {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchView searchView;

    private RecetitasMonk dbHelper;
    public busquedaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment busquedaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static busquedaFragment newInstance(String param1, String param2) {
        busquedaFragment fragment = new busquedaFragment();
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
    final static String urlMostrarResultados = "http://recetitasmonk.atwebpages.com/servicios/mostrarBusqueda.php";
    Fragment[] fragments;
    ArrayList<Resultado> listaResultado;
    ResultadoAdapter adapter = null;
    RecyclerView recResultados;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_busqueda, container, false);
        // Inicializar SearchView y RecetitasMonk
        searchView = v.findViewById(R.id.searchView);
        dbHelper = new RecetitasMonk(getContext());

        fragments = new Fragment[5];
        fragments[0] = new ininFragment();
        fragments[1] = new busquedaFragment();
        fragments[2] = new otroFragment();
        fragments[3] = new user1Fragment();
        fragments[4] = new busquedaFragment();

        recResultados = v.findViewById(R.id.cardResultado);
        listaResultado = new ArrayList<>();

        // Usar LinearLayoutManager con orientación vertical
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recResultados.setLayoutManager(manager);

        adapter = new ResultadoAdapter(listaResultado,this.getContext());
        recResultados.setAdapter(adapter);



        // Inflate the layout for this fragment
        // Configurar el listener para el SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("ininFragment", "Guardando búsqueda: " + query);
                try {
                    Log.d("ininFragment", "Guardando búsqueda: " + query);
                    boolean guardadoExitoso = dbHelper.agregarHistorial(query);
                    Log.d("ininFragment", "Búsqueda guardada exitosamente: " + guardadoExitoso);

                } catch (Exception e) {
                    Log.e("ininFragment", "Error al guardar la búsqueda", e);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Aquí puedes manejar la acción de la tecla Enter
                // Realiza alguna acción con el texto capturado

                return true; // Indica que el evento ha sido manejado
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aquí puedes manejar el cambio de texto mientras se escribe
                Log.d("ininFragment", "Guardando búsqueda: ");
                boolean guardadoExitoso = dbHelper.agregarHistorial(newText);
                mostrarResultadosPorNombre(newText);
                return false;
            }
        });


        mostrarResultados();
        return v;
    }

    private void mostrarResultados() {
        AsyncHttpClient ahcMostrarResultados = new AsyncHttpClient();
        ahcMostrarResultados.get(urlMostrarResultados, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        listaResultado.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            listaResultado.add(new Resultado(jsonArray.getJSONObject(i).getInt("idRecetas"),
                                    jsonArray.getJSONObject(i).getString("nombreReceta"),
                                    jsonArray.getJSONObject(i).getString("ingredientes"),
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

            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void mostrarResultadosPorNombre(String nombre) {
        AsyncHttpClient ahcMostrarResultados = new AsyncHttpClient();
        String newUrlMostrarResultados = "http://recetitasmonk.atwebpages.com/servicios/mostrarReceta.php?nombre="+nombre;
        Log.d("ininFragment", newUrlMostrarResultados);
        ahcMostrarResultados.get(newUrlMostrarResultados, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        listaResultado.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            listaResultado.add(new Resultado(jsonArray.getJSONObject(i).getInt("idRecetas"),
                                    jsonArray.getJSONObject(i).getString("nombreReceta"),
                                    jsonArray.getJSONObject(i).getString("ingredientes"),
                                    jsonArray.getJSONObject(i).getString("preparacion"),
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

            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClickInicio(int idBoton) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.menunav, fragments [idBoton]);
        ft.commit();
    }
}