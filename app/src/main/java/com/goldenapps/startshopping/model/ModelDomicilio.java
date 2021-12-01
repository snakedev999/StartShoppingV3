package com.goldenapps.startshopping.model;

public class ModelDomicilio {
    private String idDomicilio;
    private String idUsuarioDomicilo;
    private String idComunaDomicilio;
    private String direccionDomicilio;
    private int numeroDomicilio;
    private int numeroTelefonoDomicilio;
    private String rutReceptorDomicilio;
    private String nombreReceptorDomicilio;
    private double latitud;
    private double longitud;

    public ModelDomicilio(){

    }

    public ModelDomicilio(String idUsuarioDomicilo, String idComunaDomicilio, String direccionDomicilio, int numeroDomicilio, int numeroTelefonoDomicilio, String rutReceptorDomicilio, String nombreReceptorDomicilio, double latitud, double longitud) {
        this.idUsuarioDomicilo = idUsuarioDomicilo;
        this.idComunaDomicilio = idComunaDomicilio;
        this.direccionDomicilio = direccionDomicilio;
        this.numeroDomicilio = numeroDomicilio;
        this.numeroTelefonoDomicilio = numeroTelefonoDomicilio;
        this.rutReceptorDomicilio = rutReceptorDomicilio;
        this.nombreReceptorDomicilio = nombreReceptorDomicilio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public ModelDomicilio(String idDomicilio, String idUsuarioDomicilo, String idComunaDomicilio, String direccionDomicilio, int numeroDomicilio, int numeroTelefonoDomicilio, String rutReceptorDomicilio, String nombreReceptorDomicilio, double latitud, double longitud) {
        this.idDomicilio = idDomicilio;
        this.idUsuarioDomicilo = idUsuarioDomicilo;
        this.idComunaDomicilio = idComunaDomicilio;
        this.direccionDomicilio = direccionDomicilio;
        this.numeroDomicilio = numeroDomicilio;
        this.numeroTelefonoDomicilio = numeroTelefonoDomicilio;
        this.rutReceptorDomicilio = rutReceptorDomicilio;
        this.nombreReceptorDomicilio = nombreReceptorDomicilio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(String idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public String getIdUsuarioDomicilo() {
        return idUsuarioDomicilo;
    }

    public void setIdUsuarioDomicilo(String idUsuarioDomicilo) {
        this.idUsuarioDomicilo = idUsuarioDomicilo;
    }

    public String getIdComunaDomicilio() {
        return idComunaDomicilio;
    }

    public void setIdComunaDomicilio(String idComunaDomicilio) {
        this.idComunaDomicilio = idComunaDomicilio;
    }

    public String getDireccionDomicilio() {
        return direccionDomicilio;
    }

    public void setDireccionDomicilio(String direccionDomicilio) {
        this.direccionDomicilio = direccionDomicilio;
    }

    public int getNumeroDomicilio() {
        return numeroDomicilio;
    }

    public void setNumeroDomicilio(int numeroDomicilio) {
        this.numeroDomicilio = numeroDomicilio;
    }

    public int getNumeroTelefonoDomicilio() {
        return numeroTelefonoDomicilio;
    }

    public void setNumeroTelefonoDomicilio(int numeroTelefonoDomicilio) {
        this.numeroTelefonoDomicilio = numeroTelefonoDomicilio;
    }

    public String getRutReceptorDomicilio() {
        return rutReceptorDomicilio;
    }

    public void setRutReceptorDomicilio(String rutReceptorDomicilio) {
        this.rutReceptorDomicilio = rutReceptorDomicilio;
    }

    public String getNombreReceptorDomicilio() {
        return nombreReceptorDomicilio;
    }

    public void setNombreReceptorDomicilio(String nombreReceptorDomicilio) {
        this.nombreReceptorDomicilio = nombreReceptorDomicilio;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
