package com.goldenapps.startshopping.model;

import java.util.Date;

public class ModelProducto {

    private int idProducto;
    private String nombreProducto;
    private int cantidadProducto;
    private String descripcion;
    private Date fechaRegistroProducto;
    private int precioProducto;
    private int imagenProducto;
    private double puntajeProducto;

    public ModelProducto(int idProducto, String nombreProducto, int cantidadProducto, String descripcion, int precioProducto, int imagenProducto, double puntajeProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.descripcion = descripcion;
        this.precioProducto = precioProducto;
        this.imagenProducto = imagenProducto;
        this.puntajeProducto = puntajeProducto;
    }

    public ModelProducto(int idProducto, String nombreProducto, String descripcion, int precioProducto, int imagenProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precioProducto = precioProducto;
        this.imagenProducto = imagenProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRegistroProducto() {
        return fechaRegistroProducto;
    }

    public void setFechaRegistroProducto(Date fechaRegistroProducto) {
        this.fechaRegistroProducto = fechaRegistroProducto;
    }

    public int getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(int imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public double getPuntajeProducto() {
        return puntajeProducto;
    }

    public void setPuntajeProducto(double puntajeProducto) {
        this.puntajeProducto = puntajeProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }
}
