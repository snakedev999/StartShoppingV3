package com.goldenapps.startshopping.model;

public class ModelItemCarrito {
    private String idCarritoItem;
    private String idCarrito;
    private String itemCarrito;
    private int cantidadItem;
    private double subTotalItem;

    public ModelItemCarrito(){

    }


    public ModelItemCarrito(String idCarrito, String itemCarrito, int cantidadItem, double subTotalItem) {
        this.idCarrito = idCarrito;
        this.itemCarrito = itemCarrito;
        this.cantidadItem = cantidadItem;
        this.subTotalItem = subTotalItem;
    }

    public ModelItemCarrito(String idCarritoItem, String idCarrito, String itemCarrito, int cantidadItem, double subTotalItem) {
        this.idCarritoItem = idCarritoItem;
        this.idCarrito = idCarrito;
        this.itemCarrito = itemCarrito;
        this.cantidadItem = cantidadItem;
        this.subTotalItem = subTotalItem;
    }

    public String getIdCarritoItem() {
        return idCarritoItem;
    }

    public void setIdCarritoItem(String idCarritoItem) {
        this.idCarritoItem = idCarritoItem;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getItemCarrito() {
        return itemCarrito;
    }

    public void setItemCarrito(String itemCarrito) {
        this.itemCarrito = itemCarrito;
    }

    public int getCantidadItem() {
        return cantidadItem;
    }

    public void setCantidadItem(int cantidadItem) {
        this.cantidadItem = cantidadItem;
    }

    public double getSubTotalItem() {
        return subTotalItem;
    }

    public void setSubTotalItem(double subTotalItem) {
        this.subTotalItem = subTotalItem;
    }
}
