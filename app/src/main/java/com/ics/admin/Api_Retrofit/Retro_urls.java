package com.ics.admin.Api_Retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Retro_urls {
    String The_Base = "http://ihisaab.in/school_lms/Studentapi/" ;

    @FormUrlEncoded
    @POST("Ragistration")
    Call<String> Register_to_app(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password,
            @Field("passout_year") String passout_year,
            @Field("collage_name") String collage_name,
            @Field("address") String address
//            @Field("profile_image") File profile_image,
//            @Field("sign_image") File sign_image
    );
}
