package com.example.appcall;

public  class Admin {

    public String email;
    public String[] sub_users;


    public Admin(String email, String[] sub_users) {
        this.email= email;
        this.sub_users=sub_users;
    }
    public Admin(String email) {
        this.email= email;
        this.sub_users=null;
    }

}
