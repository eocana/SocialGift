package com.example.socialgift.model;

import java.util.UUID;

public class User {
    private UUID UUID;
    private String name;
    private String last_name;
    private String image;
    private String email;


    public User(java.util.UUID UUID, String name, String last_name, String image, String email) {
        this.UUID = UUID;
        this.name = name;
        this.last_name = last_name;
        this.image = image;
        this.email = email;
    }



    public java.util.UUID getUUID() {
        return UUID;
    }

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
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
