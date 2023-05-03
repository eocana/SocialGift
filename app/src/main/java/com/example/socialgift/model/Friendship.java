package com.example.socialgift.model;

import java.util.Date;

public class Friendship {
    private String id_user_from;
    private String id_user_to;
    private Date send_at;
    private String status;

    public Friendship() {}

    public Friendship(String id_user_from, String id_user_to, Date send_at, String status) {
        this.id_user_from = id_user_from;
        this.id_user_to = id_user_to;
        this.send_at = send_at;
        this.status = status;
    }

    public String getId_user_from() {
        return id_user_from;
    }

    public void setId_user_from(String id_user_from) {
        this.id_user_from = id_user_from;
    }

    public String getId_user_to() {
        return id_user_to;
    }

    public void setId_user_to(String id_user_to) {
        this.id_user_to = id_user_to;
    }

    public Date getSend_at() {
        return send_at;
    }

    public void setSend_at(Date send_at) {
        this.send_at = send_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
