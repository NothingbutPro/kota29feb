package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class _Student_Homeworks_Model {
    @SerializedName("data")
    @Expose
    private ArrayList<_Student_Homeworks_Model_Data> data = null;
    @SerializedName("responce")
    @Expose
    private Boolean responce;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Homeworks_Model() {
    }

    /**
     *
     * @param data
     * @param responce
     */
    public _Student_Homeworks_Model(ArrayList<_Student_Homeworks_Model_Data> data, Boolean responce) {
        super();
        this.data = data;
        this.responce = responce;
    }

    public ArrayList<_Student_Homeworks_Model_Data> getData() {
        return data;
    }

    public void setData(ArrayList<_Student_Homeworks_Model_Data> data) {
        this.data = data;
    }

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }
}
