package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class _My_Student_Attendence_Model {
    @SerializedName("data")
    @Expose
    private ArrayList<_My_Student_Attendence_Data> data = null;
    @SerializedName("responce")
    @Expose
    private Boolean responce;

    /**
     * No args constructor for use in serialization
     *
     */
    public _My_Student_Attendence_Model() {
    }

    /**
     *
     * @param data
     * @param responce
     */
    public _My_Student_Attendence_Model(ArrayList<_My_Student_Attendence_Data> data, Boolean responce) {
        super();
        this.data = data;
        this.responce = responce;
    }

    public ArrayList<_My_Student_Attendence_Data> getData() {
        return data;
    }

    public void setData(ArrayList<_My_Student_Attendence_Data> data) {
        this.data = data;
    }

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }
}
