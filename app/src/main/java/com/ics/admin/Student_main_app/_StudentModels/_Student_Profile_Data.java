package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Student_Profile_Data {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
    @SerializedName("parent_mobile")
    @Expose
    private String parentMobile;
    @SerializedName("responce")
    @Expose
    private Boolean responce;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Profile_Data() {
    }

    /**
     *
     * @param fatherName
     * @param address
     * @param responce
     * @param name
     * @param motherName
     * @param parentMobile
     * @param email
     */
    public _Student_Profile_Data(String name, String email, String address, String fatherName, String motherName, String parentMobile, Boolean responce) {
        super();
        this.name = name;
        this.email = email;
        this.address = address;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.parentMobile = parentMobile;
        this.responce = responce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }
}
