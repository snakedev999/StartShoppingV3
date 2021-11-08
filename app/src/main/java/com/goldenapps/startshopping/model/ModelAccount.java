package com.goldenapps.startshopping.model;

public class ModelAccount {

    public String id;
    public String email;
    public String name;
    public String pass;
    public String tipoUsuario;

    public ModelAccount(){

    }

    public ModelAccount(String id) {
        this.id = id;
    }

    public ModelAccount(String id,String email, String name, String pass, String tipoUsuario) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.tipoUsuario = tipoUsuario;
    }
}
