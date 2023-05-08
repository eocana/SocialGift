package com.example.socialgift.model;

import java.util.Date;

public class Wishlist {
    private String UUID;
    private Date created_at;
    private Date finished_at;
    private String id_user;
    private String name;

    public Wishlist(String UUID, Date created_at, Date finished_at, String id_user, String name) {
        this.UUID = UUID;
        this.created_at = created_at;
        this.finished_at = finished_at;
        this.id_user = id_user;
        this.name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(Date finished_at) {
        this.finished_at = finished_at;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
