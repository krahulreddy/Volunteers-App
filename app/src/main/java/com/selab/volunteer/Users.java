package com.selab.volunteer;

public class Users {
    String mail;
    String phone;
    String name;
    String url;
    int wallet;


    public Users() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setwallet() {
        this.wallet = 50;

    }

    public void seturl(){
        this.url = "https://mir-s3-cdn-cf.behance.net/project_modules/1400/a6938438650505.598fa5ee0cdf7.jpg";
    }
}
