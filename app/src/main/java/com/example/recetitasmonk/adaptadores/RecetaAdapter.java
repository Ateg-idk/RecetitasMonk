package com.example.recetitasmonk.adaptadores;

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
import com.example.recetitasmonk.clases.Receta;

import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder> {

    private List<Receta> listaReceta;

    public RecetaAdapter(List<Receta> listaReceta) {
        this.listaReceta = listaReceta;
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta, parent, false);
        return new RecetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecetaViewHolder holder, int position) {
        Receta receta = listaReceta.get(position);
        holder.tvNombreReceta.setText(receta.getNombreReceta());
        holder.tvIngredientes.setText(receta.getIngredientes());
        holder.tvPreparacion.setText(receta.getPreparacion());

        // Cargar imagen en formato Base64
        String imagenBase64 = receta.getImagen();
        if (imagenBase64 != null && !imagenBase64.isEmpty()) {
            Log.d("RecetaAdapter", "Cargando imagen en Base64");
            byte[] decodedString = Base64.decode(imagenBase64, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ivImagen.setImageBitmap(decodedBitmap);
        } else {
            // Si no hay imagen disponible, cargar una imagen de placeholder
            Log.d("RecetaAdapter", "No se encontró imagen en Base64, cargando placeholder.");
            holder.ivImagen.setImageResource(R.drawable.desayno);
        }
    }

    @Override
    public int getItemCount() {
        return listaReceta.size();
    }

    public static class RecetaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreReceta, tvIngredientes, tvPreparacion;
        ImageView ivImagen;

        public RecetaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreReceta = itemView.findViewById(R.id.nombre_receta);
            tvIngredientes = itemView.findViewById(R.id.ingredientes_receta);
            tvPreparacion = itemView.findViewById(R.id.preparacion_receta);
            ivImagen = itemView.findViewById(R.id.imagen_receta);
        }
    }
}
