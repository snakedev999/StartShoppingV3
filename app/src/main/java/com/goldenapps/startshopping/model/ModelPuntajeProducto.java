package com.goldenapps.startshopping.model;

public class ModelPuntajeProducto {

    String idPuntaje;
    String idProducto;
    String idUsuario;
    double puntajeProducto;

    public ModelPuntajeProducto(){

    }

    public ModelPuntajeProducto(String idProducto, String idUsuario, double puntajeProducto) {
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
        this.puntajeProducto = puntajeProducto;
    }

    public ModelPuntajeProducto(String idPuntaje, String idProducto, String idUsuario, double puntajeProducto) {
        this.idPuntaje = idPuntaje;
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
        this.puntajeProducto = puntajeProducto;
    }

    public String getIdPuntaje() {
        return idPuntaje;
    }

    public void setIdPuntaje(String idPuntaje) {
        this.idPuntaje = idPuntaje;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getPuntajeProducto() {
        return puntajeProducto;
    }

    public void setPuntajeProducto(double puntajeProducto) {
        this.puntajeProducto = puntajeProducto;
    }
}
