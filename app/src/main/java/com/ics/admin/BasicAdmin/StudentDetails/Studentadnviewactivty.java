package com.ics.admin.BasicAdmin.StudentDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class Studentadnviewactivty extends AppCompatActivity {
    LinearLayout stdli;
    FloatingActionButton addstdfab;
    EditText nameed,emailtxt,passworded,mobileed,addressed,edt_father_name,edt_m_name,edt_parent_number;
    Button addstudentsbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentadnviewactivty);
        initializevviews();

        addstdfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stdli.getVisibility() == View.GONE)
                {
                    stdli.setVisibility(View.VISIBLE);
                }else {
                    stdli.setVisibility(View.GONE);
                }
            }
        });
        addstudentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddStudentsFOrAmin(new Shared_Preference().getId(v.getContext()) ,nameed.getText().toString() , emailtxt.getText().toString(),
                        passworded.getText().toString() , mobileed.getText().toString(),addressed.getText().toString()
                ,edt_father_name.getText().toString() ,edt_m_name.getText().toString() , edt_parent_number.getText().toString()).execute();
            }
        });
    }


    private void initializevviews() {
        //++++++++++++fab++++++++++++
        addstdfab = findViewById(R.id.addstdfab);
        //+++++++++++++++++++++++
        //+======+++Buttom
        addstudentsbtn = findViewById(R.id.addstudentsbtn);
        //+++++++++++++++++
        nameed = findViewById(R.id.nameed);
        edt_father_name = findViewById(R.id.edt_father_name);
        edt_m_name = findViewById(R.id.edt_m_name);
        edt_parent_number = findViewById(R.id.edt_parent_number);
        emailtxt = findViewById(R.id.emailtxt);
        passworded = findViewById(R.id.passworded);
        mobileed = findViewById(R.id.mobileed);
        addressed = findViewById(R.id.addressed);
        //++++++++++++Linear layout++++++++++++++++++++
        stdli = findViewById(R.id.stdli);
        //+++++++++++++++++++++++++++++++++++

    }


    private class AddStudentsFOrAmin  extends AsyncTask<String, Void, String> {
        String userid;
        // String Faculty_id;
        private Dialog dialog;
        String name; String email; String password; String mobile; String address,father_name,mother_name,parent_number;
        public AddStudentsFOrAmin(String id, String name, String email, String password, String mobile, String address, String father_name, String mother_name, String parent_number) {
            this.userid = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.mobile = mobile;
            this.address = address;
            this.father_name = father_name;
            this.mother_name = mother_name;
            this.parent_number = parent_number;
        }



        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/add_student");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("school_id", userid);
                postDataParams.put("name", name);
                postDataParams.put("email", email);
                postDataParams.put("password", password);
                postDataParams.put("mobile", mobile);
                postDataParams.put("address", address);
                postDataParams.put("father_name", father_name);
                postDataParams.put("mother_name", mother_name);
                postDataParams.put("parent_mobile", parent_number);


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

                JSONObject jsonObject1 = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject1 = new JSONObject(result);
                    if(jsonObject1.getBoolean("responce")){
                        String name = jsonObject1.getString("name");
                        String student_id = jsonObject1.getString("data");
                        Intent intent = new Intent(Studentadnviewactivty.this,AssignStudentActivity.class);
                        intent.putExtra("student_name",name);
                        intent.putExtra("student_id",student_id);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(Studentadnviewactivty.this, "Email and Mobile numebr must be unique", Toast.LENGTH_SHORT).show();
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
