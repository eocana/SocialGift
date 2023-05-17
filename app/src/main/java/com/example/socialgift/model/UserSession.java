package com.example.socialgift.model;

public class UserSession extends User{

    private String token;

    public UserSession() {

    }
/*    public UserSession(String UUID, String name, String last_name, String image, String email) {
        super(UUID, name, last_name, image, email);
    }*/

    public UserSession(User userByEmail) {
        super(userByEmail.getUUID(), userByEmail.getName(), userByEmail.getLastName(), userByEmail.getImage(), userByEmail.getEmail());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
