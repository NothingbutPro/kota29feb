package com.ics.admin.Activities_by_Parag.ui;

import androidx.annotation.NonNull;

public class Batch {
    private String userId;

    private String Batch_Class;

    private String Batch;

    public Batch(String userid, String name, String email, String password, String mobile, String address, String userId, String batch_Class, String batch) {
        this.userId = userId;
        Batch_Class = batch_Class;
        Batch = batch;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBatch_Class() {
        return Batch_Class;
    }

    public void setBatch_Class(String batch_Class) {
        Batch_Class = batch_Class;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }
}
