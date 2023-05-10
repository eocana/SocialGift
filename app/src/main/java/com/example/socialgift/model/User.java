package com.example.socialgift.model;

import java.io.Serializable;

public class User implements Serializable {
    private String UUID;
    private String name;
    private String last_name;
    private String image;
    private String email;

    public User() {
        // constructor sin argumentos requerido para la serialización/deserialización
    }

    public User(String UUID, String name, String last_name, String image, String email) {
        this.UUID = UUID;
        this.name = name;
        this.last_name = last_name;
        this.image = image;
        this.email = email;
    }


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
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
}
