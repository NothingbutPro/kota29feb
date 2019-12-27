package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Student_Profile_Model {
    @SerializedName("data")
    @Expose
    private _Student_Profile_Model_Data data;
    @SerializedName("responce")
    @Expose
    private Boolean responce;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Profile_Model() {
    }

    /**
     *
     * @param data
     * @param responce
     */
    public _Student_Profile_Model(_Student_Profile_Model_Data data, Boolean responce) {
        super();
        this.data = data;
        this.responce = responce;
    }

    public _Student_Profile_Model_Data getData() {
        return data;
    }

    public void setData(_Student_Profile_Model_Data data) {
        this.data = data;
    }

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }
}
