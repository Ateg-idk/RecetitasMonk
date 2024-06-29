package com.example.recetitasmonk.fragmentos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recetitasmonk.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class nuevarecetaFragment extends Fragment {
    private EditText etNombreReceta, etIngredientes, etPreparacion;
    private Spinner spinnerCategoria, spinnerDepartamento;
    private Button btnPublicar;
    private ImageView image16;
    private static final String TAG = "RecetasNuevasFragment";
    private Uri selectedImageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuevareceta, container, false);

        etNombreReceta = view.findViewById(R.id.rectangle_2);
        etIngredientes = view.findViewById(R.id.rectangle_3);
        etPreparacion = view.findViewById(R.id.rectangle_4);
        spinnerCategoria = view.findViewById(R.id.spinner_categoria);
        spinnerDepartamento = view.findViewById(R.id.spinner_departamento);
        btnPublicar = view.findViewById(R.id.publicar);
        image16 = view.findViewById(R.id.image_16);

        ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(getContext(),
                R.array.categorias_array, android.R.layout.simple_spinner_item);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);

        ArrayAdapter<CharSequence> adapterDepartamento = ArrayAdapter.createFromResource(getContext(),
                R.array.departamentos_array, android.R.layout.simple_spinner_item);
        adapterDepartamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamento.setAdapter(adapterDepartamento);

        image16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMenuSeleccionImagen();
            }
        });

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarReceta();
            }
        });

        return view;
    }

    private void mostrarMenuSeleccionImagen() {
        final CharSequence[] options = {"Tomar Foto", "Seleccionar de Galería", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Seleccionar Foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Tomar Foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Seleccionar de Galería")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    image16.setImageBitmap(photo);
                    // Reinicia la URI seleccionada, ya que es una nueva foto tomada
                    selectedImageUri = null;
                    break;
                case 2:
                    selectedImageUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                        image16.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void agregarReceta() {
        String nombreReceta = etNombreReceta.getText().toString();
        String ingredientes = etIngredientes.getText().toString();
        String preparacion = etPreparacion.getText().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();
        String departamento = spinnerDepartamento.getSelectedItem().toString();

        String idCategoria = obtenerIdCategoria(categoria);

        SharedPreferences sharedPref = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", "");

        if (userId.isEmpty()) {
            Toast.makeText(requireContext(), "Error: no se encontró el ID del usuario.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://recetitasmonk.atwebpages.com/servicios/agregarReceta.php";

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response from server: " + response);
                        Toast.makeText(requireContext(), "Receta agregada con éxito", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error response from server: ", error);
                Toast.makeText(requireContext(), "Error al agregar receta", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombreReceta", nombreReceta.isEmpty() ? null : nombreReceta);
                params.put("ingredientes", ingredientes.isEmpty() ? null : ingredientes);
                params.put("preparacion", preparacion.isEmpty() ? null : preparacion);
                if (selectedImageUri != null) {
                    params.put("imagen", selectedImageUri.toString()); // Guardar la referencia de la imagen en la base de datos
                }
                params.put("idUsuarios", userId); //  ID del usuario recuperado
                params.put("idCategoria", idCategoria); // ID de la categoría basada en la selección del Spinner
                params.put("departamento", departamento); // Nuevo parámetro
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private String obtenerIdCategoria(String categoria) {
        switch (categoria) {
            case "Desayuno":
                return "1";
            case "Almuerzo":
                return "2";
            case "Navideñas":
                return "3";
            case "Ensaladas":
                return "4";
            case "Bebidas":
                return "5";
            case "Panes":
                return "6";
            case "Comida para Niños":
                return "7";
            case "Sopas":
                return "8";
            case "Saludables":
                return "9";
            case "Bocaditos":
                return "10";
            default:
                return "0";
        }
    }

    public void limpiarImagen() {
        if (selectedImageUri != null && !selectedImageUri.toString().equals("android.resource://com.example.recetitasmonk/drawable/image_16")) {
            image16.setImageResource(R.drawable.image_16);
            selectedImageUri = null;
        }
    }

    public void limpiarCampos() {
        etNombreReceta.setText("");
        etIngredientes.setText("");
        etPreparacion.setText("");
        spinnerCategoria.setSelection(0);
        spinnerDepartamento.setSelection(0);
        etNombreReceta.requestFocus();

        if (selectedImageUri != null) {
            limpiarImagen();
        }
    }
}
