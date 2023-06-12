package com.example.socialgift.model;

public class Category {
    private int id;
    private String name;
    private String description;
    private String photo;
    private int categoryParentId;

    public Category() {    }
    public Category(int id, String name, String description, String photo, int categoryParentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.categoryParentId = categoryParentId;
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

    public Integer getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(Integer categoryParentId) {
        this.categoryParentId = categoryParentId;
    }
}
