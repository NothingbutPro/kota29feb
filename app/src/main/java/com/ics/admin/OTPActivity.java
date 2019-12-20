package com.ics.admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ics.admin.Activities_by_Parag.ui.Faculty_Dashoard;
import com.ics.admin.BasicAdmin.AdminActivity;
import com.ics.admin.ShareRefrance.Shared_Preference;

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

import androidx.appcompat.app.AppCompatActivity;

public class OTPActivity  extends AppCompatActivity {

    EditText getotp, mobile;
    Button getotpbtn,getotps;
    public static int Admin_id;
   // public static String Faculty_id;
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
                    new Verifyotp(getotp.getText().toString(),mobile.getText().toString()).execute();
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
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/sendotp");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("mobile", Mobile_Number);


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
                    if(!jsonObject.getBoolean("responce")){
//                       Intent intent = new Intent(OTPActivity.this , LoginActivity.class);
//                       startActivity(intent);
                        Toast.makeText(getApplication(),"You are not registerd", Toast.LENGTH_SHORT).show();
                    }else {
                        getotp.setVisibility(View.VISIBLE);
                        String otp= jsonObject.getString("data");
                        getotp.setText(otp);
                        getotpbtn.setText("Login");
                        getotpbtn.setVisibility(View.VISIBLE);
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
                    if(!jsonObject.getBoolean("responce")){
                        getotp.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        shared_preference=new Shared_Preference();
                        String type = jsonObject.getJSONObject("data").getString("type");
                        String Admin_id = jsonObject.getJSONObject("data").getString("user_id");
                        if(type.equals("1")) {

                            shared_preference.setId(OTPActivity.this,Admin_id,"Admin",true);
                            Intent intent = new Intent(OTPActivity.this, AdminActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            String Faculty_id = jsonObject.getJSONObject("data").getString("addedby");
                            shared_preference.setId(OTPActivity.this,Faculty_id,"Faculty",true);
                            shared_preference.setFacultyId(OTPActivity.this,Admin_id);
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
    }

