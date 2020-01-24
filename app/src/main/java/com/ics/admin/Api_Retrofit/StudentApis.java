package com.ics.admin.Api_Retrofit;

import com.ics.admin.Model.Student_Fee_Details;
import com.ics.admin.Student_main_app._StudentModels._My_Student_Attendence_Model;
import com.ics.admin.Student_main_app._StudentModels._StudentRegistrationModel;
import com.ics.admin.Student_main_app._StudentModels._Student_All_Packages;
import com.ics.admin.Student_main_app._StudentModels._Student_All_Packages_data;
import com.ics.admin.Student_main_app._StudentModels._Student_Announcement_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Announcements_Model_Datas;
import com.ics.admin.Student_main_app._StudentModels._Student_Chat_Public_Community;
import com.ics.admin.Student_main_app._StudentModels._Student_Demo_Videos;
import com.ics.admin.Student_main_app._StudentModels._Student_Homeworks_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_PDF_Material_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Profile_Data;
import com.ics.admin.Student_main_app._StudentModels._Student_Profile_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Public_Message;
import com.ics.admin.Student_main_app._StudentModels._Student_Video_Materials_Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface StudentApis {
    @FormUrlEncoded
    @POST("add_student")
    Call<_StudentRegistrationModel> Register_to_Student(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile") String mobile,
            @Field("_st_father_name") String _st_father_name,
            @Field("_st_mothers_name") String _st_mothers_name,
            @Field("_st_parents_mobile") String _st_parents_mobile,
            @Field("address") String address
    );
    @FormUrlEncoded
    @POST("view_attendance")
    Call<_My_Student_Attendence_Model> MY_STUDENT_ATTENDENCE_CALL(
            @Field("student_id") String student_id,
            @Field("att_date") String att_date
    );

    @FormUrlEncoded
    @POST("view_homework")
    Call<_Student_Homeworks_Model> STUDENT_HOMEWORKS_MODEL_CALL(
            @Field("class_id") String class_id,
            @Field("batch_id") String batch_id
    );

    @FormUrlEncoded
    @POST("studymateriallist")
    Call<_Student_PDF_Material_Model> STUDENT_PDF_MATERIAL_MODEL_CALL(
            @Field("user_id") String user_id,
            @Field("class_id") String class_id
    );

    @FormUrlEncoded
    @POST("view_profile")
    Call<_Student_Profile_Model> STUDENT_PROFILE_MODEL_CALL(
            @Field("student_id") String user_id
    );
    @FormUrlEncoded
    @POST("update_student")
    Call<_Student_Profile_Data> STUDENT_PROFILE_DATA_CALL(
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("address") String address,
            @Field("father_name") String father_name,
            @Field("mother_name") String mother_name,
            @Field("parent_mobile") String parent_mobile
    );
//    @FormUrlEncoded
//    @POST("view_video")
//    Call<_Student_Video_Materials_Model> STUDENT_VIDEO_MATERIALS_MODEL_CALL(
//            @Field("user_id") String user_id,
//            @Field("class_id") String class_id,
//            @Field("course_id") String course_id
//    );

    @FormUrlEncoded
    @POST("getpackages")
    Call<_Student_Video_Materials_Model> STUDENT_VIDEO_MATERIALS_MODEL_CALL(
            @Field("search") String search
    );


    @FormUrlEncoded
    @POST("viewallvideo")
    Call<_Student_All_Packages> STUDENT_ALL_PACKAGES_DATA_CALL(
            @Field("video_id") String video_id
    );
    @FormUrlEncoded
    @POST("viewallvideo")
    Call<_Student_Demo_Videos> STUDENT_ALL_DEMO_PACKAGES_DATA_CALL(
            @Field("video_id") String video_id
    );
    @FormUrlEncoded
    @POST("view_announcement")
    Call<_Student_Announcement_Model> STUDENT_ANNOUNCEMENTS_MODEL(
            @Field("class_id") String class_id,
            @Field("batch_id") String batch_id
    );

    @FormUrlEncoded
    @POST("getcommunity")
    Call<_Student_Chat_Public_Community> STUDENT_CHAT_PUBLIC_COMMUNITY_CALL(
            @Field("school_id") String user_id,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("addcommunity")
    Call<_Student_Public_Message> STUDENT_CHAT_PUBLIC_COMMUNITY_CALL(
            @Field("message") String  message,
            @Field("school_id") String school_id,
            @Field("type") String type,
            @Field("user_id") String  user_id
    );

    @FormUrlEncoded
    @POST("checkfeedetail/")
    Call<Student_Fee_Details> STUDENT_FEE_DETAILS_BY_STUDENT_CALL(
            @Field("school_id") String  school_id ,
            @Field("student_name") String student_name
    );

    @FormUrlEncoded
    @POST("checkfeedetail/")
    Call<Student_Fee_Details> STUDENT_FEE_DETAILS_BY_CLASS_CALL(
            @Field("school_id") String  school_id ,
            @Field("class_id") String class_id
    );    @FormUrlEncoded
    @POST("checkfeedetail/")
    Call<Student_Fee_Details> STUDENT_FEE_DETAILS_BY_BATCH_ID_CALL(
            @Field("school_id") String  school_id ,
            @Field("batch_id") String batch_id
    );

}
