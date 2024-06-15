package com.example.recetitasmonk.fragmentos;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Locale;
import android.os.Build;
import android.content.res.Configuration;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.actividades.EditarActivity;
import com.example.recetitasmonk.actividades.InicioSesionActivity;
import com.example.recetitasmonk.actividades.MisFavoritosActivity;
import com.example.recetitasmonk.actividades.MisRecetasActivity;
import com.example.recetitasmonk.actividades.PoliticasPrivacidadActivity;
import com.example.recetitasmonk.actividades.TerminoCondicionesActivity;
import com.example.recetitasmonk.sqlite.RecetitasMonk;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user1Fragment extends Fragment implements View.OnClickListener{


    Spinner cboIdioma;
    Button BtnGuardar, BtnCerrarses;
    LinearLayout btnCerrarSesion;

    LinearLayout editarPerfil,misrecetas,misfavoritos,politicaPrivacidad,TerminoCondiciones;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static user1Fragment newInstance(String param1, String param2) {
        user1Fragment fragment = new user1Fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_user1, container, false);

        btnCerrarSesion = vista.findViewById(R.id.btnCerrarSesion);
        editarPerfil = vista.findViewById(R.id.editarPerfil);
        misrecetas = vista.findViewById(R.id.misrecetas);
        misfavoritos = vista.findViewById(R.id.misfavoritos);
        politicaPrivacidad = vista.findViewById(R.id.politicaPrivacidad);
        TerminoCondiciones = vista.findViewById(R.id.TerminoCondiciones);
        BtnGuardar = vista.findViewById(R.id.frgCfgGuardar);
        cboIdioma = vista.findViewById(R.id.frgCfgIdioma);
        BtnCerrarses = vista.findViewById(R.id.frgCerrarses);

        btnCerrarSesion.setOnClickListener(this);
        misfavoritos.setOnClickListener(this);
        editarPerfil.setOnClickListener(this);
        misrecetas.setOnClickListener(this);
        politicaPrivacidad.setOnClickListener(this);
        TerminoCondiciones.setOnClickListener(this);
        BtnGuardar.setOnClickListener(this);
        BtnCerrarses.setOnClickListener(this);
        //cargamos idiomas al combobox
        llenarIdioma();
        cargarPreferencias();


        return vista;
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Reiniciar la actividad para aplicar los cambios
        getActivity().recreate();
    }

    private void llenarIdioma() {
        String[] idiomas = {"Elija un idioma", "Español", "Inglés"};
        cboIdioma.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                idiomas));

    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        int idioma = preferences.getInt("Idioma", 0);
        cboIdioma.setSelection(idioma);
    }



    @Override
    public void onClick(View v) {
        
        if(v.getId() == R.id.btnCerrarSesion)
            cerrarSesion();
        if(v.getId() == R.id.frgCfgGuardar)
            Guardar();
        else if(v.getId() == R.id.frgCerrarses)
            CerrarSesion();
        else if (v.getId()==R.id.editarPerfil){
            cargarperfil();
        }else if (v.getId()==R.id.misrecetas){
            cargarRecetas();
        }else if (v.getId()==R.id.misfavoritos){
            cargarFavoritos();
        }else if (v.getId()==R.id.politicaPrivacidad){
            cargarPolitica();
        }else if (v.getId()==R.id.TerminoCondiciones){
            cargarTerminos();
        }
    }

    private void CerrarSesion() {
    }

    private void Guardar() {
        if (cboIdioma.getSelectedItemPosition() != 0) {
            SharedPreferences preferences = getActivity().getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();



            editor.putInt("Idioma", cboIdioma.getSelectedItemPosition());
            editor.commit();

            // Obtener el idioma seleccionado
            String selectedLanguage = "";
            switch (cboIdioma.getSelectedItemPosition()) {
                case 1:
                    selectedLanguage = "es"; // Español
                    break;
                case 2:
                    selectedLanguage = "en"; // Inglés
                    break;
            }

            // Cambiar el idioma
            setLocale(selectedLanguage);

            Toast.makeText(getContext(), "Preferencias Guardadas", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Debes elegir un idioma", Toast.LENGTH_SHORT).show();
        }
    }


    private void cerrarSesion() {
        RecetitasMonk tm = new RecetitasMonk(getContext());
        tm.eliminarUsuario(1);
        getActivity().finish();
        Intent inicioSesion = new Intent(getContext(), InicioSesionActivity.class);
        startActivity(inicioSesion);
    }
    private void cargarperfil(){
        Intent iPerfil = new Intent(getContext(), EditarActivity.class);
        startActivity(iPerfil);
    }
    private void cargarRecetas(){
        Intent iRecetas = new Intent(getContext(), MisRecetasActivity.class);
        startActivity(iRecetas);
    }
    private void cargarFavoritos(){
        Intent iFavoritos = new Intent(getContext(), MisFavoritosActivity.class);
        startActivity(iFavoritos);
    }
    private void cargarPolitica(){
        Intent iPolitica = new Intent(getContext(), PoliticasPrivacidadActivity.class);
        startActivity(iPolitica);
    }
    private void cargarTerminos(){
        Intent iTerminos = new Intent(getContext(), TerminoCondicionesActivity.class);
        startActivity(iTerminos);
    }


}