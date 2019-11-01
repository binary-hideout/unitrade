package com.fimestuds.unitrade;

import android.os.Parcel;
import android.os.Parcelable;

public class Articulo implements Parcelable {
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




    protected Articulo(Parcel in) {
        art_name = in.readString();
        type = in.readInt();
        imagen = in.readString();
        costo = in.readInt();
        descripcion = in.readString();


    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(art_name);
        dest.writeInt(type);
        dest.writeString(imagen);
        dest.writeInt(costo);
        dest.writeString(descripcion);

    }
    public static final Creator<Articulo> CREATOR = new Creator<Articulo>() {
        @Override
        public Articulo createFromParcel(Parcel in) {
            return new Articulo(in);
        }

        @Override
        public Articulo[] newArray(int size) {
            return new Articulo[size];
        }
    };

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
