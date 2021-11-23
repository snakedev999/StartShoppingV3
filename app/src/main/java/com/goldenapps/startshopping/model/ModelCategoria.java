package com.goldenapps.startshopping.model;

public class ModelCategoria {
    private String idCategoria;
    private String nombreCategoria;
    private String imagenCategoria;

    public ModelCategoria() {
    }

    public ModelCategoria(String nombreCategoria, String imagenCategoria) {
        this.nombreCategoria = nombreCategoria;
        this.imagenCategoria = imagenCategoria;
    }

    public ModelCategoria(String idCategoria, String nombreCategoria, Boolean b) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getImagenCategoria() {
        return imagenCategoria;
    }

    public void setImagenCategoria(String imagenCategoria) {
        this.imagenCategoria = imagenCategoria;
    }

    @Override
    public String toString() {
        return nombreCategoria;
    }
}
