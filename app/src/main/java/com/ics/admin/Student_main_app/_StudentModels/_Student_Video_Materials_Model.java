package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class _Student_Video_Materials_Model {
    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private ArrayList<_Student_Video_Materials_Model_Data> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Video_Materials_Model() {
    }

    /**
     *
     * @param data
     * @param responce
     */
    public _Student_Video_Materials_Model(Boolean responce, ArrayList<_Student_Video_Materials_Model_Data> data) {
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

    public ArrayList<_Student_Video_Materials_Model_Data> getData() {
        return data;
    }

    public void setData(ArrayList<_Student_Video_Materials_Model_Data> data) {
        this.data = data;
    }
}
