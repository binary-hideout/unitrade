package com.fimestuds.unitrade;

import android.os.Parcel;
import android.os.Parcelable;

public class Articulo implements Parcelable {
    int id;
    String user_id;
    String art_name;
    int type;
    String descripcion;
    String costo;
    String imagen;
    String celular;
    int rating;


     Articulo(int id, String user_id, String art_name, int type, String descripcion, String costo,
              String imagen, String celular, int rating ) {
        this.id = id;
        this.user_id = user_id;
        this.art_name = art_name;
        this.type= type;
        this.descripcion=descripcion;
        this.costo=costo;
        this.imagen=imagen;
        this.celular=celular;
        this.rating=rating;
    }

    public Articulo(){
    }



    protected Articulo(Parcel in) {
         user_id = in.readString();
        art_name = in.readString();
        type = in.readInt();
        imagen = in.readString();
        costo = in.readString();
        descripcion = in.readString();
        celular=in.readString();
        rating=in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(art_name);
        dest.writeString(user_id);
        dest.writeInt(type);
        dest.writeString(imagen);
        dest.writeString(costo);
        dest.writeString(descripcion);
        dest.writeString(celular);
        dest.writeInt(rating);

    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
