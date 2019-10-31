package com.fimestuds.unitrade;

public class User {
    private  int id;
    private  String username;
    private   String email;
    private  String pssd;
    private double phone;


    public User(int id, String username, String email,  String pssd, double phone) {
        this.id = id;
        this.email = email;
        this.pssd = pssd;
        this.username = username;
        this.phone= phone;

    }



    public String toString() {
        return "Usuario id=" + id + ", email=" + email + ", contras=" + pssd  + ", Usuario_nom=" + username
                 + ",celular=" + phone ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPssd() {
        return pssd;
    }

    public void setPssd(String pssd) {
        this.pssd = pssd;
    }

    public double getPhone() {
        return phone;
    }

    public void setPhone(double phone) {
        this.phone = phone;
    }
}
