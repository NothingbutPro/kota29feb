package com.ics.admin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Activities_by_Parag.ui.Faculty_Dashoard;
import com.ics.admin.BasicAdmin.AdminActivity;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.StudentDashboardActivity;

import com.ics.admin.Student_main_app._StudentRegistration;
import com.marcoscg.dialogsheet.DialogSheet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OTPActivity  extends AppCompatActivity {
    EditText getotp, mobile;
    Button getotpbtn,getotps;
    public static int Admin_id;
   // public static String Faculty_id;
    String who = "none";
    Shared_Preference shared_preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getotp = findViewById(R.id.getotp);
        mobile = findViewById(R.id.mobile);
        getotpbtn = findViewById(R.id.getotpbtn);
        getotps = findViewById(R.id.getotps);
        getotp.setVisibility(View.GONE);
        getotps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobile.getText().toString().length() ==10) {
                    new GetOtp(mobile.getText().toString()).execute();
                }else {
                    Toast.makeText(OTPActivity.this, "Mobile number is too short", Toast.LENGTH_SHORT).show();
                    getotpbtn.setVisibility(View.VISIBLE);
                    getotp.setVisibility(View.GONE);

                }
            }
        });
        getotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getotpbtn.getText().toString().equals("Login")) {
                    if(who.equals("none")) {
                        Toast.makeText(OTPActivity.this, ""+who, Toast.LENGTH_SHORT).show();
                        new Verifyotp(getotp.getText().toString(), mobile.getText().toString()).execute();
                    }else {
                        Toast.makeText(OTPActivity.this, ""+who, Toast.LENGTH_SHORT).show();
                        new VerifyotpforStudent(getotp.getText().toString(), mobile.getText().toString()).execute();
                    }
                }
                else {

                }
            }
        });

    }

    private class GetOtp extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        private Dialog dialog;

        public GetOtp(String toString) {
            this.Mobile_Number = toString;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(OTPActivity.this);
            dialog.show();
            dialog.setTitle("Please wait getting OTP");
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/sendotp");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("mobile", Mobile_Number);


                Log.e("postDataParams", "Adminapi/sendotp"+postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();
                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if(!jsonObject.getBoolean("responce")){
                        cancel(true);
                        who = "student";
                        new GETOPTPSTUDENT(Mobile_Number).execute();
//                        ShowDialognow(Mobile_Number);
//                       Intent intent = new Intent(OTPActivity.this , LoginActivity.class);
//                       startActivity(intent);
//                        Toast.makeText(getApplication(),"You are not registerd", Toast.LENGTH_SHORT).show();
                    }else {
                        who = "none";
                        getotp.setVisibility(View.VISIBLE);
                        String otp= jsonObject.getString("data");
                        getotp.setHint("Enter Otp here");
                        getotpbtn.setText("Login");
                        getotpbtn.setVisibility(View.VISIBLE);
                        getotps.setText("Resent Otp");
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    private void ShowDialognow(String mobile_Number) {
//        new FancyGifDialog.Builder(OTPActivity.this).
        DialogSheet dialogSheet = new DialogSheet(this);
        dialogSheet.setTitle("Register as")
                .setColoredNavigationBar(true)
                .setCancelable(true)
                .setBackgroundColor(Color.WHITE) // Your custom background color
                .setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
                .show();
        dialogSheet.setView(R.layout._student_app_ask_dialog);
//         Access dialog custom inflated view
        View inflatedView = dialogSheet.getInflatedView();
        TextView parenttxt = inflatedView.findViewById(R.id.parenttxt);
        TextView studenttxt = inflatedView.findViewById(R.id.studenttxt);
        parenttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(OTPActivity.this , _StudentRegistration.class);
                startActivity(intent);
//                Toast.makeText(OTPActivity.this, "You have chosen ", Toast.LENGTH_SHORT).show();
                dialogSheet.dismiss();
//                who = "student";
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    RegisterDialogshow();
//                }
//                new GETOPTPSTUDENT(mobile_Number).execute();
            }
        });
        studenttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(OTPActivity.this , _StudentRegistration.class);
                startActivity(intent);
//                Toast.makeText(OTPActivity.this, " chose 1 ", Toast.LENGTH_SHORT).show();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    RegisterDialogshow();
//                }
//                dialogSheet.dismiss();
//                who = "student";
//                new GETOPTPSTUDENT(mobile_Number).execute();
            }
        });
//        String [] strings = {"Parent" , "Student"};
//        new LovelyChoiceDialog(this )
//                .setTopColorRes(R.color.red)
//                .setTitle("Can't find You")
//                .setIcon(R.drawable.asr_logo)
//                .setMessage("Please choose")
//                .setItems(strings, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
//                    @Override
//                    public void onItemSelected(int position, String item) {
//                        if(position ==0)
//                        {
//                            Toast.makeText(OTPActivity.this, "You have chosen ", Toast.LENGTH_SHORT).show();
//                            new GETOPTPSTUDENT(mobile_Number).execute();
//                        }else {
//                            Toast.makeText(OTPActivity.this, " chose 1 ", Toast.LENGTH_SHORT).show();
//                            new GETOPTPSTUDENT(mobile_Number).execute();
//                        }
//                    }
//                })
//                .show();
    }

    private class Verifyotp extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        private Dialog dialog;


        public Verifyotp(String toString, String s) {

            this.Mobile_Number = toString;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/login");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("mobile", mobile.getText().toString());


                Log.e("postDataParams", "admin_login"+postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
//                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if(!jsonObject.getBoolean("responce")){
                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(OTPActivity.this , StudentDashboardActivity.class);
//                        startActivity(intent);
//                        finish();
                    }else {
                        shared_preference=new Shared_Preference();
                        String type = jsonObject.getJSONObject("data").getString("type");
                        String Admin_id = jsonObject.getJSONObject("data").getString("user_id");
                        if(type.equals("1")) {

                            shared_preference.setId(OTPActivity.this,Admin_id,"Admin",true);
                            String fact_name = jsonObject.getJSONObject("data").getString("name");
                            shared_preference.setFactName(OTPActivity.this ,fact_name );
                            Intent intent = new Intent(OTPActivity.this, AdminActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(type.equals("2")) {
                            String Faculty_id = jsonObject.getJSONObject("data").getString("addedby");
                            String fact_name = jsonObject.getJSONObject("data").getString("name");

                            shared_preference.setId(OTPActivity.this,Faculty_id,"Faculty",true);
                            shared_preference.setFacultyId(OTPActivity.this,Admin_id);
                            shared_preference.setFactName(OTPActivity.this ,fact_name );
                            Intent intent1 = new Intent(OTPActivity.this, Faculty_Dashoard.class);
                            startActivity(intent1);
                            finish();
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    private class GETOPTPSTUDENT extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        private Dialog dialog;

        public GETOPTPSTUDENT(String mobile_Number) {
            this.Mobile_Number = mobile_Number;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Studentapi/sendotp");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("mobile", Mobile_Number);


                Log.e("postDataParams", "Studentapi/sendotp"+postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
//                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if(!jsonObject.getBoolean("responce")){
                        cancel(true);
//                       Intent intent = new Intent(OTPActivity.this , LoginActivity.class);
//                       startActivity(intent);
//                        Toast.makeText(getApplication(),"You are not registerd", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if(jsonObject.getString("error").equals("Your account currently inactive"))
                            {
                                //Not active
                                UserNotActivedialog();
                            }else {
                                ShowDialognow(Mobile_Number);

                            }
                        }else {
                            Toast.makeText(OTPActivity.this, "Your version is too low", Toast.LENGTH_SHORT).show();
                        }
//       String [] strings = {"Cancel" , "Register now"};
//        new LovelyChoiceDialog(OTPActivity.this )
//                .setTopColorRes(R.color.red)
//                .setTitle("Do you want to Register")
//                .setIcon(R.drawable.asr_logo)
//                .setMessage("Please choose")
//                .setItems(strings, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
//                    @Override
//                    public void onItemSelected(int position, String item) {
//                        if(position ==0)
//                        {
//                            Toast.makeText(OTPActivity.this, "You have Cancel ", Toast.LENGTH_SHORT).show();
//
//                        }else {
//                            Toast.makeText(OTPActivity.this, " Regsiter 1 ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .show();
                    }else {
                        getotp.setVisibility(View.VISIBLE);
                        String otp= jsonObject.getString("data");
                        getotp.setHint("Enter Otp Here");
                        getotpbtn.setText("Login");
                        getotps.setText("Otp Sent Successfully");
                        getotpbtn.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

        private void UserNotActivedialog() {
            new SweetAlertDialog(OTPActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("You are currently inactive!")
                     .setContentText("Please contact to admin")
                    .setConfirmText("Ok")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismiss();
                        }
                    })
                    .show();
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    private void RegisterDialogshow() {
        // Simple Material Dialog
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Can't find you!Do you want to register")
                .setCancelText("Not Now")
                .setConfirmText("Register Now")
                .showCancelButton(true).
                setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance, keep widget user state, reset them if you need
                        sDialog.dismiss();
//                        Toast.makeText(OTPActivity.this, "You have ", Toast.LENGTH_SHORT).show();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        Intent intent  = new Intent(OTPActivity.this , _StudentRegistration.class);
                        startActivity(intent);
//                        Toast.makeText(OTPActivity.this, "ok registering now", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }

    private class VerifyotpforStudent extends AsyncTask<String, Void, String>{
        String Mobile_Number;
        private Dialog dialog;


        public VerifyotpforStudent(String toString, String s) {

            this.Mobile_Number = toString;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Studentapi/login");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("mobile", mobile.getText().toString());


                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
//                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("responce")){
                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"wrong OTP", Toast.LENGTH_SHORT).show();
                        shared_preference.setschool_id(OTPActivity.this ,jsonObject.getJSONObject("data").getString("school_id"));
                        shared_preference.setStudent_info(OTPActivity.this ,jsonObject.getJSONObject("data").getString("id"),jsonObject.getJSONObject("data").getString("class_id"),jsonObject.getJSONObject("data").getString("course_id"),jsonObject.getJSONObject("data").getString("batch_id"));
                        Intent intent = new Intent(OTPActivity.this , StudentDashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(OTPActivity.this, "Failed", Toast.LENGTH_SHORT).show();

//                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }
}

