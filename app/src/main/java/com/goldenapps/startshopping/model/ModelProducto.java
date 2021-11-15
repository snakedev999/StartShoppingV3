package com.goldenapps.startshopping.model;

import android.view.Display;

import java.util.Date;

public class ModelProducto {

    private String nombreProducto;
    private String marcaProducto;
    private String precioProducto;
    private String imagenProducto;

    public ModelProducto() {

    }

    public ModelProducto(String marcaProducto, String nombreProducto, String precioProducto, String imagenProducto) {
        this.nombreProducto = nombreProducto;
        this.marcaProducto = marcaProducto;
        this.precioProducto = precioProducto;
        this.imagenProducto = imagenProducto;
    }


    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }


    public String getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(String precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }
}
