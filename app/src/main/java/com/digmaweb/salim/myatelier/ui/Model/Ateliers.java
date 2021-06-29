package com.digmaweb.salim.myatelier.ui.Model;

public final class Ateliers {

        private String address ;
        private String name ,  phone , photo_url , lat , att;
        private String rate ;
        private String id ;

    public Ateliers(String address, String name  , String phone , String photo_url , String lat , String att , String rate, String id) {
        this.name = name;
        this.photo_url = photo_url ;
        this.rate = rate;
        this.address = address;
        this.phone = phone;
        this.lat = lat;
        this.att = att;
        this.id = id ;
    }

    public Ateliers() {
        this.name = name;
        this.photo_url = photo_url ;
        this.rate = rate;
        this.address = address;
        this.phone = phone;
        this.lat = lat;
        this.att = att;
        this.id = id ;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
