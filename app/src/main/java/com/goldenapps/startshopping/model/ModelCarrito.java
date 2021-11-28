package com.goldenapps.startshopping.model;

public class ModelCarrito {

    protected String idCarrito;
    protected String idUsuarioCarrito;
    protected double valorTotal;

    public ModelCarrito(){

    }

    public ModelCarrito(String idCarrito){
        this.idCarrito = idCarrito;
    }

    public ModelCarrito(String idUsuarioCarrito, double valorTotal) {
        this.idUsuarioCarrito = idUsuarioCarrito;
        this.valorTotal = valorTotal;
    }

    public ModelCarrito(String idCarrito, String idUsuarioCarrito, double valorTotal) {
        this.idCarrito = idCarrito;
        this.idUsuarioCarrito = idUsuarioCarrito;
        this.valorTotal = valorTotal;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getIdUsuarioCarrito() {
        return idUsuarioCarrito;
    }

    public void setIdUsuarioCarrito(String idUsuarioCarrito) {
        this.idUsuarioCarrito = idUsuarioCarrito;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
