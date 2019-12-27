package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

import java.util.ArrayList;

public class _Student_Announcement_Model {

    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private ArrayList<_Student_Announcements_Model_Datas> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Announcement_Model() {
    }

    /**
     *
     * @param data
     * @param responce
     */
    public _Student_Announcement_Model(Boolean responce, ArrayList<_Student_Announcements_Model_Datas> data) {
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

    public ArrayList<_Student_Announcements_Model_Datas> getData() {
        return data;
    }

    public void setData(ArrayList<_Student_Announcements_Model_Datas> data) {
        this.data = data;
    }
}
