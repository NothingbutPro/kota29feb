package com.ics.admin.Student_main_app._StudentModels;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class _StudentRegistrationModel {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("responce")
        @Expose
        private Boolean responce;
        @SerializedName("data")
        @Expose
        private Integer data;

        /**
         * No args constructor for use in serialization
         *
         */
        public _StudentRegistrationModel() {
        }

        /**
         *
         * @param password
         * @param address
         * @param data
         * @param responce
         * @param name
         * @param mobile
         * @param email
         */
        public _StudentRegistrationModel(String name, String email, String password, String mobile, String address, Boolean responce, Integer data) {
            super();
            this.name = name;
            this.email = email;
            this.password = password;
            this.mobile = mobile;
            this.address = address;
            this.responce = responce;
            this.data = data;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Boolean getResponce() {
            return responce;
        }

        public void setResponce(Boolean responce) {
            this.responce = responce;
        }

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }


}
