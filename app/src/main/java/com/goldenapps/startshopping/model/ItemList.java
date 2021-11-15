package com.goldenapps.startshopping.model;

import java.io.Serializable;

public class ItemList implements Serializable {
    private String NameCat;
    private int imgResource;

    public ItemList(String NameCat, int imgResource) {
        this.NameCat = NameCat;
        this.imgResource = imgResource;
    }

    public String getTitulo() {
        return NameCat;
    }

    public int getImgResource() {
        return imgResource;
    }
}
