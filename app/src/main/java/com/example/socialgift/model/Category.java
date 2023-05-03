package com.example.socialgift.model;

public class Category {
    private int id;
    private String name;
    private String description;
    private String photo;
    private int id_parent_category;

    public Category(int id, String name, String description, String photo, int id_parent_category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.id_parent_category = id_parent_category;
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

    public Integer getId_parent_category() {
        return id_parent_category;
    }

    public void setId_parent_category(Integer id_parent_category) {
        this.id_parent_category = id_parent_category;
    }
}
