package com.ics.admin.Model;

public class Students {
    public String id;
    public String name;
    public String mobile;
    public String email;
    public String password;
    public String present_or_not;
    public String Batch;
    public String Class;
    public String Assigned;
    public String Feedone;
    public String getFeedone() {
        return Feedone;
    }

    public void setFeedone(String feedone) {
        Feedone = feedone;
    }


    public String getAssigned() {
        return Assigned;
    }

    public void setAssigned(String assigned) {
        Assigned = assigned;
    }

    public String getBatch() {
        return Batch;
    }
    public void setBatch(String batch) {
        Batch = batch;
    }
    public String getClassx() {
        return this.Class;
    }

    public void setClass(String aClass) {
        Class = aClass;
    }

    public String getPresent_or_not() {
        return present_or_not;
    }

    public void setPresent_or_not(String present_or_not) {
        this.present_or_not = present_or_not;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Students(String id, String name, String mobile, String email, String password, String Class, String Batch, String not_assigned, String feedone) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.Class = Class;
        this.Batch = Batch;
        this.Assigned = not_assigned;
        this.Feedone = feedone;

    }
    public Students(String id, String name, String mobile, String email, String password ,String present_or_not) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.present_or_not = present_or_not;
    }
}
