package com.goldenapps.startshopping.model;

public class ModelTallaProducto {

    private String idTallaProducto;
    private String idProducto;
    private String talla;

    public ModelTallaProducto(){

    }

    public ModelTallaProducto(String idProducto, String talla) {
        this.idProducto = idProducto;
        this.talla = talla;
    }

    public ModelTallaProducto(String idTallaProducto, String idProducto, String talla) {
        this.idTallaProducto = idTallaProducto;
        this.idProducto = idProducto;
        this.talla = talla;
    }

    public String getIdTallaProducto() {
        return idTallaProducto;
    }

    public void setIdTallaProducto(String idTallaProducto) {
        this.idTallaProducto = idTallaProducto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    @Override
    public String toString() {
        return talla;
    }
}
