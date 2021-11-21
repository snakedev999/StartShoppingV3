package com.goldenapps.startshopping.model;

public class ModelComuna {

    private String idComuna;
    private String nombreComuna;
    private String idRegionComuna;
    private Boolean b;

    public ModelComuna(){

    }

    public ModelComuna(String idComuna, String nombreComuna, Boolean b) {
        this.idComuna = idComuna;
        this.nombreComuna = nombreComuna;
        this.b = b;
    }

    public ModelComuna(String idRegionComuna, String nombreComuna) {
        this.nombreComuna = nombreComuna;
        this.idRegionComuna = idRegionComuna;
    }

    public String getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(String idComuna) {
        this.idComuna = idComuna;
    }

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreComuna) {
        this.nombreComuna = nombreComuna;
    }

    public String getIdRegionComuna() {
        return idRegionComuna;
    }

    public void setIdRegionComuna(String idRegionComuna) {
        this.idRegionComuna = idRegionComuna;
    }

    @Override
    public String toString() {
        return nombreComuna;
    }
}
