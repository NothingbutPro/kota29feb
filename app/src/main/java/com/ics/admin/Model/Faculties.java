package com.ics.admin.Model;

public class Faculties {

    private String userId;

    private String name;

    private String email;

    private String password;

    private String mobile;

    private String address;

    private String status;

    private String type;

    private String addedby;

    private String fromwhere;

    public Faculties(String userId, String name, String email, String password, String mobile, String address, String status, String type, String addedby, String fromwhere) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
        this.status = status;
        this.type = type;
        this.addedby = addedby;
        this.fromwhere = fromwhere;
    }


    public String getFromwhere() {
        return fromwhere;
    }

    public void setFromwhere(String fromwhere) {
        this.fromwhere = fromwhere;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

}
