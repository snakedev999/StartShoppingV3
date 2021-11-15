package com.goldenapps.startshopping.model;

public class ModelRegion {

    private String nombreRegion;
    private String abreviaturaRegion;
    private String numeroRomano;

    public ModelRegion() {
    }

    public ModelRegion(String nombreRegion, String abreviaturaRegion, String numeroRomano) {
        this.nombreRegion = nombreRegion;
        this.abreviaturaRegion = abreviaturaRegion;
        this.numeroRomano = numeroRomano;
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
        return "Nombre region: " + nombreRegion + '\'' +'\n'+
                "Abreviatura region: " + abreviaturaRegion + '\"' +'\n'+
                "Numero romano='" + numeroRomano + '\'' +'\n';
    }
}
