package com.goldenapps.startshopping.model;

public class ModelCarrito {

    protected int idCarrito;
    protected int total;

    public ModelCarrito(int idCarrito, int total) {
        this.idCarrito = idCarrito;
        this.total = total;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
