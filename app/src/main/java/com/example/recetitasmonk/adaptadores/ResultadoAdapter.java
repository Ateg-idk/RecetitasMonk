package com.example.recetitasmonk.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Categoria;
import com.example.recetitasmonk.clases.Resultado;

import android.net.Uri;

import java.util.List;
public class ResultadoAdapter extends RecyclerView.Adapter<ResultadoAdapter.ViewHolder>{
    private List<Resultado> listaResultado;
    private Context context;
    private int idResultado = -1;

    public ResultadoAdapter(List<Resultado> listaResultado) {
        this.listaResultado = listaResultado;
    }

    @NonNull
    @Override
    public ResultadoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_busqueda, parent, false);
        return new ResultadoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultadoAdapter.ViewHolder holder, int position) {
        Resultado resultado = listaResultado.get(position);
        holder.txtNombre.setText(resultado.getNombreReceta());
        holder.txtDepartamento.setText(resultado.getDepartamento());

        // Cargar imagen en formato Base64
        String imagenBase64 = resultado.getImagen();
        if (imagenBase64 != null && !imagenBase64.isEmpty()) {
            Log.d("RecetaAdapter", "Cargando imagen en Base64");
            byte[] decodedString = Base64.decode(imagenBase64, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imagen.setImageBitmap(decodedBitmap);
        } else {
            // Si no hay imagen disponible, cargar una imagen de placeholder
            Log.d("RecetaAdapter", "No se encontró imagen en Base64, cargando placeholder.");
            holder.imagen.setImageResource(R.drawable.desayno);
        }
    }
    @Override
    public int getItemCount() {
        return listaResultado.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombre,txtDepartamento;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.nameReceta);
            txtDepartamento = itemView.findViewById(R.id.nameDepartamento);
            imagen = itemView.findViewById(R.id.imgBusqueda);

        }
    }
}