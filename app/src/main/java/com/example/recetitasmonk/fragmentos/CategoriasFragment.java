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
import android.widget.LinearLayout;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.adaptadores.CategoriaAdapter;
import com.example.recetitasmonk.clases.Categoria;
import com.example.recetitasmonk.clases.Inicio;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriasFragment extends Fragment implements View.OnClickListener , Inicio {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriasFragment newInstance(String param1, String param2) {
        CategoriasFragment fragment = new CategoriasFragment();
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

    final static String urlMostrarCategorias = "http://recetitasmonk.atwebpages.com/servicios/mostrarCategoria.php";
    Fragment[] fragments;
    ArrayList<Categoria> lista;
    CategoriaAdapter adapter = null;
    RecyclerView recCategorias;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categorias, container, false);

        fragments = new Fragment[5];
        fragments[0] = new ininFragment();
        fragments[1] = new busquedaFragment();
        fragments[2] = new otroFragment();
        fragments[3] = new user1Fragment();
        fragments[4] = new nuevarecetaFragment();


        recCategorias = v.findViewById(R.id.cardCategoria);
        lista = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recCategorias.setLayoutManager(manager);
        adapter = new CategoriaAdapter(lista);
        recCategorias.setAdapter(adapter);

        mostrarCategorias();

        return v;
    }

    private void mostrarCategorias() {
        AsyncHttpClient ahcMostrarCategorias = new AsyncHttpClient();
        ahcMostrarCategorias.get(urlMostrarCategorias, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            lista.add(new Categoria(jsonArray.getJSONObject(i).getInt("idCategoria"),
                                    jsonArray.getJSONObject(i).getString("nombreCategoria"),
                                    jsonArray.getJSONObject(i).getString("imagenCategoria")));
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