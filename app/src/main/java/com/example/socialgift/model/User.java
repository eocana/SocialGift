package com.example.socialgift.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String last_name;

    private String password;
    private String image;
    private String email;

    public User() {
        // constructor sin argumentos requerido para la serialización/deserialización
    }

    public User(int id, String name, String last_name, String password,String image, String email) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.password = password;
        this.image = image;
        this.email = email;
    }

    public User(int id, String name, String lastName, String email, String image) {
        this.id = id;
        this.name = name;
        this.last_name = lastName;
        this.email = email;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
