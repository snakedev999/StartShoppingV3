package com.goldenapps.startshopping.model;

public class ModelItemCarrito {
    private String idItemCarrito;
    private String idCarrito;
    private String idProducto;
    private int cantidadItem;
    private String imagen;
    private String nombre;
    private double subTotalItem;

    public ModelItemCarrito(){
    }

    public ModelItemCarrito(String idCarritoItem) {
        this.idItemCarrito = idItemCarrito;
    }

    public ModelItemCarrito(String idProducto, int cantidadItem, double subTotalItem) {
        this.idProducto = idProducto;
        this.cantidadItem = cantidadItem;
        this.subTotalItem = subTotalItem;
    }

    public ModelItemCarrito(String idCarrito, String idProducto, int cantidadItem, double subTotalItem, String imagen,String nombre) {
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.cantidadItem = cantidadItem;
        this.subTotalItem = subTotalItem;
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public ModelItemCarrito(String idCarritoItem, String idCarrito, String idProducto, int cantidadItem, double subTotalItem,String imagen,String nombre) {
        this.idItemCarrito = idCarritoItem;
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.cantidadItem = cantidadItem;
        this.imagen = imagen;
        this.nombre = nombre;
        this.subTotalItem = subTotalItem;
    }

    public String getIdItemCarrito() {
        return idItemCarrito;
    }

    public void setIdItemCarrito(String idItemCarrito) {
        this.idItemCarrito = idItemCarrito;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
