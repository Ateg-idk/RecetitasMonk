package com.example.recetitasmonk.adaptadores;

import android.content.Context;
import android.content.Intent;
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
import com.example.recetitasmonk.actividades.RecetasPorCategoriaActivity;
import com.example.recetitasmonk.actividades.recetaActivity;
import com.example.recetitasmonk.clases.Receta;

import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder>{

    private List<Receta> listaReceta;
    private static final String TAG = "MainActivity";
    private Context context;
    public RecetaAdapter(List<Receta> listaReceta,Context context) {

        this.listaReceta = listaReceta;
        this.context = context;
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
        byte[] imgByte = Base64.decode(receta.getImagen(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        holder.ivImagen.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Entrando a la receta");
                Intent intent = new Intent(context, recetaActivity.class);
                intent.putExtra("idReceta", receta.getIdRecetas());
                intent.putExtra("nombreReceta", receta.getNombreReceta());
                intent.putExtra("ingredientes", receta.getIngredientes());
                intent.putExtra("preparacion", receta.getPreparacion());

                context.startActivity(intent);
            }
        });
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
