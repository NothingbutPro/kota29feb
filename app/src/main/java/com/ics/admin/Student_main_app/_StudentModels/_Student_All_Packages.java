package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class _Student_All_Packages {
    @SerializedName("data")
    @Expose
    private ArrayList<_Student_All_Packages_data> data;
    @SerializedName("responce")
    @Expose
    private Boolean responce;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_All_Packages() {
    }

    /**
     *
     * @param data
     * @param responce
     */
    public _Student_All_Packages(ArrayList<_Student_All_Packages_data> data, Boolean responce) {
        super();
        this.data = data;
        this.responce = responce;
    }

    public ArrayList<_Student_All_Packages_data> getData() {
        return data;
    }

    public void setData(ArrayList<_Student_All_Packages_data> data) {
        this.data = data;
    }

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }
}
