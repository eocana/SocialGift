package com.example.socialgift.model;

public class Gift {

    private int id;
    private int wishlist_id;
    private String product_url;
    private int priority;
    private int booked;

    public Gift() {    }
    public Gift(int id, int wishlist_id, String product_url, int priority, int booked) {
        this.id = id;
        this.wishlist_id = wishlist_id;
        this.product_url = product_url;
        this.priority = priority;
        this.booked = booked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWishlistId() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
    }

    public String getProductUrl() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int isBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }
}
