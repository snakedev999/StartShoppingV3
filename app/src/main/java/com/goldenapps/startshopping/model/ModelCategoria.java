package com.goldenapps.startshopping.model;

public class ModelCategoria {
    private String nombreCategoria;
    private String imagenCategoria;

    public ModelCategoria(){
    }

    public ModelCategoria(String nombreCategoria, String imagenCategoria) {
        this.nombreCategoria = nombreCategoria;
        this.imagenCategoria = imagenCategoria;
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
}
