package com.ics.admin.Api_Retrofit;

import com.ics.admin.Student_main_app._StudentModels._StudentRegistrationModel;
import com.ics.admin.Student_main_app._StudentRegistration;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface StudentApis {
    @FormUrlEncoded
    @POST("Ragistration")
    Call<_StudentRegistrationModel> Register_to_Student(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile") String mobile,
            @Field("address") String address
    );
}
