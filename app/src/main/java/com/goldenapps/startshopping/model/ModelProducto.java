package com.goldenapps.startshopping.model;

import android.view.Display;

import java.util.Date;

public class ModelProducto {

    private String idProducto;
    private String idCategoriaProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private String fechaRegistroProducto;
    private String imagenProducto;
    private int cantidadProducto;
    private double precioProducto;
    private double puntajeProducto;

    public ModelProducto() {
    }

    public ModelProducto(String idProducto, double puntajeProducto) {
        this.idProducto = idProducto;
        this.puntajeProducto = puntajeProducto;
    }

    public ModelProducto(String idCategoriaProducto,String nombreProducto, String descripcionProducto, String fechaRegistroProducto, int cantidadProducto, double precioProducto,String imagenProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.fechaRegistroProducto = fechaRegistroProducto;
        this.imagenProducto = imagenProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioProducto = precioProducto;
    }

    public String getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    public void setIdCategoriaProducto(String idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getFechaRegistroProducto() {
        return fechaRegistroProducto;
    }

    public void setFechaRegistroProducto(String fechaRegistroProducto) {
        this.fechaRegistroProducto = fechaRegistroProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public double getPuntajeProducto() {
        return puntajeProducto;
    }

    public void setPuntajeProducto(double puntajeProducto) {
        this.puntajeProducto = puntajeProducto;
    }
}