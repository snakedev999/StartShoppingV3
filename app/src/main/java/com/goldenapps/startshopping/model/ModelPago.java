package com.goldenapps.startshopping.model;

public class ModelPago {

    private String idPago;
    private String idUsuario;
    private String nombrePropi;
    private String tipoTarjeta;
    private int numeroTarjeta;
    private String fecha;
    private int cvv;

    public ModelPago() {
    }

    public ModelPago(String idUsuario, String nombrePropi, int numeroTarjeta,String tipoTarjeta,String fecha, int cvv) {
        this.idUsuario = idUsuario;
        this.nombrePropi = nombrePropi;
        this.numeroTarjeta = numeroTarjeta;
        this.fecha = fecha;
        this.cvv = cvv;
        this.tipoTarjeta = tipoTarjeta;
    }

    public ModelPago(String idPago, String idUsuario, String nombrePropi, int numeroTarjeta, String fecha, int cvv) {
        this.idPago = idPago;
        this.idUsuario = idUsuario;
        this.nombrePropi = nombrePropi;
        this.numeroTarjeta = numeroTarjeta;
        this.fecha = fecha;
        this.cvv = cvv;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombrePropi() {
        return nombrePropi;
    }

    public void setNombrePropi(String nombrePropi) {
        this.nombrePropi = nombrePropi;
    }

    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(int numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}
