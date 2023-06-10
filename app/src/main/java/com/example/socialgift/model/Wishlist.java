package com.example.socialgift.model;

import java.util.Date;
import java.util.List;

public class Wishlist {
    private int id;
    private Date creation_date;
    private Date end_date;
    private int id_user;

    private String name;

    private String description;

    private List<Gift> gifts;


    public Wishlist() {    }

    public Wishlist(int id, String name, String description,  int id_user, Date created_at) {
        this.id = id;
        this.creation_date = created_at;
        this.id_user = id_user;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creation_date;
    }

    public void setCreationDate(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getEndDate() {
        return end_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }

    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }
}
