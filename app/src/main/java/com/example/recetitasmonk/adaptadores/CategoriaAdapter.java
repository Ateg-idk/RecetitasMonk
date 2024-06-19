package com.example.recetitasmonk.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recetitasmonk.R;
import com.example.recetitasmonk.clases.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {
    private List<Categoria> listaCategoria;
    private Context context;
    private int idCategoria = -1;

    public CategoriaAdapter(List<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    @NonNull
    @Override
    public CategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.ViewHolder holder, int position) {
        Categoria categoria = listaCategoria.get(position);
        String imagen = categoria.getImgCategoria();
        byte[] imgByte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
        holder.txtNombre.setText(categoria.getNombreCategoria());
        holder.imgCategoria.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombre;
        ImageView imgCategoria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.nameCategoria);
            imgCategoria = itemView.findViewById(R.id.imgCat);
        }
    }
}
