package com.example.socialgift.model;

public class Gift {

    private String UUID;
    private String id_product;
    private String id_wishlist;
    private int priority;
    private String user_id_booked;

    public Gift(String UUID, String id_product, String id_wishlist, int priority, String user_id_booked) {
        this.UUID = UUID;
        this.id_product = id_product;
        this.id_wishlist = id_wishlist;
        this.priority = priority;
        this.user_id_booked = user_id_booked;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_wishlist() {
        return id_wishlist;
    }

    public void setId_wishlist(String id_wishlist) {
        this.id_wishlist = id_wishlist;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getUser_id_booked() {
        return user_id_booked;
    }

    public void setUser_id_booked(String user_id_booked) {
        this.user_id_booked = user_id_booked;
    }
}
