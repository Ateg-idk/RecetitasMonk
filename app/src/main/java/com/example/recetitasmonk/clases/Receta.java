package com.example.recetitasmonk.clases;

public class Receta {

    private int idRecetas;



    private String nombreReceta;
    private String ingredientes;
    private String preparacion;
    private String imagen;
    private String departamento;
    private int idUsuarios;
    private int idCategoria;

    public Receta(int idRecetas, String nombreReceta, String ingredientes, String preparacion, String imagen, String departamento, int idUsuarios, int idCategoria) {
        this.idRecetas = idRecetas;
        this.nombreReceta = nombreReceta;
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;
        this.imagen = imagen;
        this.departamento = departamento;
        this.idUsuarios = idUsuarios;
        this.idCategoria = idCategoria;
    }

    public int getIdRecetas() {
        return idRecetas;
    }

    public void setIdRecetas(int idRecetas) {
        this.idRecetas = idRecetas;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
