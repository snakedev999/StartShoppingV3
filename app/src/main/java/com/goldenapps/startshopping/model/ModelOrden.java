package com.goldenapps.startshopping.model;

public class ModelOrden {

    private String idOrden;
    private String idUsuario;
    private String idCarrito;
    private String idPago;
    private String idDomicilio;

    public ModelOrden() {
    }

    public ModelOrden(String idUsuario, String idCarrito, String idPago, String idDomicilio) {
        this.idUsuario = idUsuario;
        this.idCarrito = idCarrito;
        this.idPago = idPago;
        this.idDomicilio = idDomicilio;
    }

    public ModelOrden(String idOrden, String idUsuario, String idCarrito, String idPago, String idDomicilio) {
        this.idOrden = idOrden;
        this.idUsuario = idUsuario;
        this.idCarrito = idCarrito;
        this.idPago = idPago;
        this.idDomicilio = idDomicilio;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public String getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(String idDomicilio) {
        this.idDomicilio = idDomicilio;
    }
}
