package com.fimestuds.unitrade;

public class Articulo {
    int id;
    String art_name;
    int type;

    public Articulo(int id, String art_name, int type) {
        this.id = id;
        this.art_name = art_name;
        this.type= type;
    }

    public String toString() {
        return "Articulo id=" + id + ", Nombre=" + art_name + ", Tipo=" + type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArt_name() {
        return art_name;
    }

    public void setArt_name(String art_name) {
        this.art_name = art_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
