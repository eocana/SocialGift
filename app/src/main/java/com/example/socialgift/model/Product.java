package com.example.socialgift.model;

public class Product {
    private String UUID;
    private String name;
    private String description;
    private String photo;
    private String price;
    private String id_category;
    private String link;

    public Product(String UUID, String name, String description, String photo, String price, String id_category, String link) {
        this.UUID = UUID;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.id_category = id_category;
        this.link = link;
    }

    // getters and setters

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
