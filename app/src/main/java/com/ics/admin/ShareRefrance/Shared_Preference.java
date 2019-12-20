package com.ics.admin.ShareRefrance;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared_Preference {
    public static final String SHARED_PREFERENCE_NAME = "KOTA";
    public static final String ID = "id";
    public static final String IS_LOGIN = "isLogin";
    public static final String NAMEE = "user_fullname";
    public static final String FACULTY_ID = "faculty_id";
    public static final String EMAIL = "user_email";
    public static final String SERVICE = "user_service";
    public static final String SUBSERVICE = "user_subservice";

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

    public static String getNamee(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(NAMEE, "");
    }

    //-------------------------------------
    public static void setEmail(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, value);
        editor.commit();
    }

    public static String getEmail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(EMAIL, "");
    }

    //------------------------------
    public static void setService(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SERVICE, value);
        editor.commit();
    }

    public static String getService(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(SERVICE, "");
    }

    //-----------------------------------
    public static void setSubservice(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SUBSERVICE, value);
        editor.commit();
    }

    public static String getSubservice(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(SUBSERVICE, "");
    }




    public void cleardatetime(){

        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }


}
