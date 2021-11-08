package com.goldenapps.startshopping.model;

public class ModelCategoria {
    protected int idCategoria;
    protected String nombreCategoria;
    protected int imagenCategoria_row_item;

    public ModelCategoria(int idCategoria, String nombreCategoria, int imagenCategoria_row_item) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.imagenCategoria_row_item = imagenCategoria_row_item;
    }

    public ModelCategoria(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public int getImagenCategoria_row_item() {
        return imagenCategoria_row_item;
    }

    public void setImagenCategoria_row_item(int imagenCategoria_row_item) {
        this.imagenCategoria_row_item = imagenCategoria_row_item;
    }
}
