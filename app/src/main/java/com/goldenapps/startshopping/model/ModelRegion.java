package com.goldenapps.startshopping.model;

public class ModelRegion {

    private String idRegion;
    private String nombreRegion;
    private String abreviaturaRegion;
    private String numeroRomano;
    private Boolean b;

   public ModelRegion(){

   }

    public ModelRegion(String idRegion, String nombreRegion, String numeroRomano,Boolean b) {
        this.idRegion = idRegion;
        this.nombreRegion = nombreRegion;
        this.numeroRomano = numeroRomano;
        this.b = b;
    }

    public ModelRegion(String nombreRegion, String abreviaturaRegion, String numeroRomano) {
        this.nombreRegion = nombreRegion;
        this.abreviaturaRegion = abreviaturaRegion;
        this.numeroRomano = numeroRomano;
    }

    public String getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(String idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    public String getAbreviaturaRegion() {
        return abreviaturaRegion;
    }

    public void setAbreviaturaRegion(String abreviaturaRegion) {
        this.abreviaturaRegion = abreviaturaRegion;
    }

    public String getNumeroRomano() {
        return numeroRomano;
    }

    public void setNumeroRomano(String numeroRomano) {
        this.numeroRomano = numeroRomano;
    }

    @Override
    public String toString() {
        return nombreRegion +" "+numeroRomano;
    }
}
