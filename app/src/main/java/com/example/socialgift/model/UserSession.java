package com.example.socialgift.model;

public class UserSession extends User{

    private String token;

    public UserSession() {

    }
      public UserSession(int id, String name, String last_name, String password, String image, String email) {
        super(id, name, last_name, password,image, email);
    }

    public UserSession(User userByEmail) {
        super(userByEmail.getId(), userByEmail.getName(), userByEmail.getLastName(), userByEmail.getPassword(),userByEmail.getImage(), userByEmail.getEmail());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public User getUser() {
        return new User(getId(), getName(), getLastName(), getPassword(),getImage(), getEmail());
    }
}
