package com.ics.admin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Student_Fee_Details {
    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private ArrayList<Student_Fee_Details_Data> data = null;
    /**
     *
     * @param data
     * @param responce
     */
    public Student_Fee_Details(Boolean responce, ArrayList<Student_Fee_Details_Data> data) {
        super();
        this.responce = responce;
        this.data = data;
    }

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public ArrayList<Student_Fee_Details_Data> getData() {
        return data;
    }

    public void setData(ArrayList<Student_Fee_Details_Data> data) {
        this.data = data;
    }

}
