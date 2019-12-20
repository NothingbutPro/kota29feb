package com.ics.admin.Model;

public class ClassNAmes {

    private String userId;

    private String class_name;


    public ClassNAmes(String userId, String class_name) {
        this.userId = userId;
        this.class_name = class_name;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }


}
