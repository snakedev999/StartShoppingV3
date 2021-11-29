package com.goldenapps.startshopping.model;

public class ModelCarrito {

    protected String idCarrito;
    protected String idUsuarioCarrito;
    protected int estadoCarrito; //0 no comprado // 1 pendiente // 2 entregado
    protected double valorTotal;

    public ModelCarrito(){

    }

    public ModelCarrito(String idCarrito){
        this.idCarrito = idCarrito;
    }

    public ModelCarrito(String idCarrito, int estadoCarrito) {
        this.idCarrito = idCarrito;
        this.estadoCarrito = estadoCarrito;
    }

    public ModelCarrito(String idUsuarioCarrito, int estadoCarrito, double valorTotal) {
        this.idUsuarioCarrito = idUsuarioCarrito;
        this.estadoCarrito = estadoCarrito;
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

    public int getEstadoCarrito() {
        return estadoCarrito;
    }

    public void setEstadoCarrito(int estadoCarrito) {
        this.estadoCarrito = estadoCarrito;
    }
}
