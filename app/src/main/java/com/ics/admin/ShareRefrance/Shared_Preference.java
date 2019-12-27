package com.ics.admin.ShareRefrance;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared_Preference {
    public static final String SHARED_PREFERENCE_NAME = "KOTA";
    public static final String ID = "id";
    public static final String IS_LOGIN = "isLogin";
    public static final String NAMEE = "user_fullname";
    public static final String FACULTY_ID = "faculty_id";
    public static final String COURSE_ID = "course_id";
    public static final String BATCH_ID = "batch_id";
    public static final String CLASS_ID = "class_id";
    public static final String STUDENT_ID = "student_id";
    public static final String SCHOOL_ID = "school_id";

    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor2;


    public static void setId(Context context, String value, String NAMEEs,Boolean login) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID, value);
        editor.putString(NAMEE, NAMEEs);
        editor.putBoolean(IS_LOGIN, login);
        editor.commit();
    }
    public static void setFacultyId(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FACULTY_ID, value);
        editor.commit();
    }

    public static String getId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(ID, "");

    }
    public static String getFacultyId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(FACULTY_ID, "");

    }

    //--------------------------------
    public static void setNamee(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAMEE, value);
        editor.commit();
    }

    public static void setschool_id(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SCHOOL_ID, value);
        editor.commit();
    }

    public static String getNamee(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(NAMEE, "");
    }

    public static String getSchoolId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(SCHOOL_ID, "");
    }

    //-------------------------------------


    //-----------------------------------
    public static void setStudent_info(Context context, String value ,String class_id,String course_id,String batch_id) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(STUDENT_ID, value);
        editor.putString(CLASS_ID, class_id);
        editor.putString(COURSE_ID, course_id);
        editor.putString(BATCH_ID, batch_id);
        editor.commit();
    }
    //+++++++++++++++++++++++++++++++++++++++++
    public static String getStudent_id(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(STUDENT_ID, "");
    }

    public static String getClassId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(CLASS_ID, "");
    }


    public static String getCourseId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(COURSE_ID, "");
    }

    public static String getBatchId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(BATCH_ID, "");
    }




    public void cleardatetime(){

        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }


}
