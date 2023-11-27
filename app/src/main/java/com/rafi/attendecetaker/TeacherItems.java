package com.rafi.attendecetaker;

import android.graphics.Bitmap;

public class TeacherItems {
    private String name;
    private String number;
    private String email;
    private String password;
    private Bitmap image;

    public TeacherItems(String name, String number, String email, String password, Bitmap image) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Bitmap getImage() {return image;}

    public void setImage(Bitmap image) {this.image = image;}
}
