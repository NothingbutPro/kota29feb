package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class _Student_Chat_Public_Community {
    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("massage")
    @Expose
    private ArrayList<_Student_Chat_Public_Community_Data> massage = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Chat_Public_Community() {
    }

    /**
     *
     * @param responce
     * @param massage
     */
    public _Student_Chat_Public_Community(Boolean responce, ArrayList<_Student_Chat_Public_Community_Data> massage) {
        super();
        this.responce = responce;
        this.massage = massage;
    }

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public ArrayList<_Student_Chat_Public_Community_Data> getMassage() {
        return massage;
    }

    public void setMassage(ArrayList<_Student_Chat_Public_Community_Data> massage) {
        this.massage = massage;
    }

}
