package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Student_Public_Message {
    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("massage")
    @Expose
    private _Student_Public_Message_Data massage;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Public_Message() {
    }

    /**
     *
     * @param responce
     * @param massage
     */
    public _Student_Public_Message(Boolean responce, _Student_Public_Message_Data massage) {
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

    public _Student_Public_Message_Data getMassage() {
        return massage;
    }

    public void setMassage(_Student_Public_Message_Data massage) {
        this.massage = massage;
    }
}
