package com.fimestuds.unitrade;

public class Articulo {
    int id;
    String art_name;
    int type;
    String descripcion;
    int costo;
    String imagen;

    public Articulo(){

    }

     Articulo(int id, String art_name, int type, String descripcion, int costo, String imagen) {
        this.id = id;
        this.art_name = art_name;
        this.type= type;
        this.descripcion=descripcion;
        this.costo=costo;
        this.imagen=imagen;
    }

    public String toString() {
        return "Articulo id=" + id + ", Nombre=" + art_name + ", Tipo=" + type + ",Costo=" + costo + "Desc="+ descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
