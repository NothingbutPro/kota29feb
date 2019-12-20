package com.ics.admin.BasicAdmin;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ics.admin.R;
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

public class AddFacultyActivity extends AppCompatActivity {
    EditText edt_name,edt_mobile,edt_email,edt_password,edt_address;
    Button btn_save;
    Shared_Preference shared_preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        edt_name=(EditText)findViewById(R.id.edt_name);
        edt_mobile=(EditText)findViewById(R.id.edt_mobile);
        edt_email=(EditText)findViewById(R.id.edt_email);
        edt_password=(EditText)findViewById(R.id.ed_password_fact);
        btn_save=(Button) findViewById(R.id.btn_save);

         shared_preference=new Shared_Preference();

           btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddFaculty(edt_name.getText().toString(),edt_mobile.getText().toString(),edt_email.getText().toString(),edt_password.getText().toString()).execute();

                if (validate()) {
                    if (validate()){
                        Intent intent=new Intent(AddFacultyActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(AddFacultyActivity.this, "Registation Successfull", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    }

                } else {
                    Toast.makeText(AddFacultyActivity.this, "Please Fill Data Correctly", Toast.LENGTH_SHORT).show();
                }
            }
             public  boolean isEmail(String email){
                 return android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
             }

             public boolean validCellPhone(String number)
             {
                 return  !TextUtils.isEmpty(number) && (number.length()==10) && android.util.Patterns.PHONE.matcher(number).matches();
             }

             private boolean validate() {
                 String name = edt_name.getText().toString().trim();
                 String mobile = edt_mobile.getText().toString().trim();
                 String mail = edt_email.getText().toString().trim();
                 String password = edt_password.getText().toString().trim();

                 if (name.matches("") && mobile.matches("") && mail.matches("") && password.matches(""))
                 {
                     edt_name.setError("Please Enter Name");
                     return false;
                 }
                 else if (mobile.matches(""))
                 {
                     edt_mobile.setError("Please Enter Mobile Number");
                     return false;
                 }
                 else if (!isEmail(mail))
                 {
                     edt_email.setError("Please Enter Email");
                     return false;
                 }
                 else if (password.matches(""))
                 {
                     edt_password.setError("Please Enter Pasword");
                     return false;
                 }


                 else
                 {
                     return  true;
                 }

             }

         });

        edt_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 10) {
                    // new RegGetOtp(s.toString()).execute();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private class AddFaculty extends AsyncTask<String, Void, String> {
        String Name;
        String Phone_Number;
        String Email;
        String Password;
        String Address;
        String Faculty_id;
        private Dialog dialog;


        public AddFaculty(String name, String phone_number, String email,String Password) {
            this.Name = name;
            this.Phone_Number =phone_number ;
            this.Email = email;
            this.Password=Password;

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/registration");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("name", Name);
//                postDataParams.put("name", Faculty_id);
                postDataParams.put("mobile", Phone_Number);
                postDataParams.put("email", Email);
                postDataParams.put("password", Password);
                postDataParams.put("address", Address);
                postDataParams.put("user_id", new Shared_Preference().getId(AddFacultyActivity.this));

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
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                    }
                    else
                    {
                        Toast.makeText(AddFacultyActivity.this, "Faculty Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddFacultyActivity.this , AdminActivity.class);
                        startActivity(intent);
                        finish();
//                        JSONObject massage=jsonObject.getJSONObject("massage");
//
////                        String mobile=jsonObject.getString("mobile");
//
////                        Toast.makeText(getApplication(),"Sorry You are not Registerd"+name, Toast.LENGTH_SHORT).show();
//
//                        //Intent intent=new Intent(RegistrationActivity.this, HomePageActivity.class);
//                        //startActivity(intent);
//                        //finish();
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
