package com.digmaweb.salim.myatelier.ui.Model;

public class Users {

    private String username ;
    private String email ;
    private String address;
    private String phone ;
    private String password ;
    private int photo ;
    private int id ;

    public Users() {
    }

    public Users(String username, String email, String title, String phone , String password, int photo , int id) {
        this.username = username;
        this.email = email;
        this.address = title;
        this.phone = phone;
        this.photo = photo;
        this.password = password ;
        this.id = id ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

