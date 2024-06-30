package com.example.recetitasmonk.fragmentos;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.actividades.DrawerBaseActivity;
import com.example.recetitasmonk.clases.Inicio;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link navFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class navFragment extends Fragment {
    private final int[] BOTONS = {R.id.frgMenhomeNav, R.id.frgMenchatNav, R.id.frgMensettingnav, R.id.frgMenpersonav, R.id.frgMenmapav};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public navFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment navFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static navFragment newInstance(String param1, String param2) {
        navFragment fragment = new navFragment();
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
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_nav, container, false);
        ImageView imbButton;

        for(int i = 0; i < BOTONS.length; i++){
            imbButton = vista.findViewById(BOTONS[i]);
            final int REQUEST_BUTTON = i;
            imbButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity actActual = getActivity();
                    ((DrawerBaseActivity)actActual).onClickInicio(REQUEST_BUTTON);
                }
            });



            }
        return vista;
        }



    }
