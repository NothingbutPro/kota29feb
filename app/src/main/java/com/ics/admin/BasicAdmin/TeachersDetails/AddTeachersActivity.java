package com.ics.admin.BasicAdmin.TeachersDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ics.admin.BasicAdmin.AddFacultyActivity;
import com.ics.admin.BasicAdmin.AdminActivity;
import com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class AddTeachersActivity extends AppCompatActivity {
    EditText edt_name,edt_mobile,edt_email,edt_password,edt_address;
    Button btn_save;
    Spinner class_staff_spin,staff_batch_spin,edt_type_spin;
    String selected_class , sel_id;
    LinearLayout main_staff_id_li;
    String type;
    private ArrayList<ClassNAmes> class_names;
    String selected_batch , sel_batch;
    Shared_Preference shared_preference;
    private ArrayList<String> batch_names = new ArrayList<>();
    private ArrayList<ABBBatch> batchArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);
        edt_name=(EditText)findViewById(R.id.edt_name);
        class_staff_spin=(Spinner) findViewById(R.id.class_staff_spin);
        staff_batch_spin=(Spinner) findViewById(R.id.staff_batch_spin);
        edt_type_spin=(Spinner) findViewById(R.id.edt_type_spin);
        edt_mobile=(EditText)findViewById(R.id.edt_mobile);
        edt_email=(EditText)findViewById(R.id.edt_email);
        edt_password=(EditText)findViewById(R.id.ed_password_fact);
        btn_save=(Button) findViewById(R.id.btn_save);
        main_staff_id_li=(LinearLayout) findViewById(R.id.main_staff_id_li);
        shared_preference=new Shared_Preference();
        edt_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    main_staff_id_li.setVisibility(View.VISIBLE);
                    type = "2";
                    new GETCLASSFORHOMe_Staff(new Shared_Preference().getId(AddTeachersActivity.this)).execute();
                }else {
                    sel_id ="";
                    type = "3";
                    sel_batch="";
                    main_staff_id_li.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                        new AddFaculty(edt_name.getText().toString(),edt_mobile.getText().toString(),edt_email.getText().toString(),edt_password.getText().toString()).execute();
                } else {
                    Toast.makeText(AddTeachersActivity.this, "Please Fill Data Correctly", Toast.LENGTH_SHORT).show();
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
                if (name.matches("") && mobile.matches(""))
                {
                    edt_name.setError("Please Enter Name");
                    return false;
                }
                else if (mobile.matches(""))
                {
                    edt_mobile.setError("Please Enter Mobile Number");
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
                postDataParams.put("class_id", sel_id);
                postDataParams.put("batch_id", sel_batch);
                postDataParams.put("email", Email);
                postDataParams.put("password", Password);
                postDataParams.put("address", Address);
                postDataParams.put("type", type);
                postDataParams.put("user_id", new Shared_Preference().getId(AddTeachersActivity.this));

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
                        Toast.makeText(AddTeachersActivity.this, "Faculty Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddTeachersActivity.this , ViewTeachersActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                    {
                        Toast.makeText(AddTeachersActivity.this, "Duplicate Number or Email id", Toast.LENGTH_SHORT).show();
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

    private class GETCLASSFORHOMe_Staff extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GETCLASSFORHOMe_Staff(String id) {
            this.id = id;
            //  this.course=course;
            this.class_id=class_id;
        }



        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getclass");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("user_id", id);

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
                Log.e("PostRegistration ", "for get class  "+result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("responce"))
                    {
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                        JSONArray jarr = jsonObject.getJSONArray("data");
                        class_names = new ArrayList<>();
                        for (int i=0 ; i<jarr.length() ; i++)
                        {

                            String cl_id = jarr.getJSONObject(i).getString("id");
                            String cl_name = jarr.getJSONObject(i).getString("Class");
                            class_names.add(new ClassNAmes(cl_id,cl_name));
                        }

                        final ArrayList <String> list_class = new ArrayList<>();
                        list_class.add("--Select Class--");
                        for (int k=0 ; k<class_names.size() ; k++){

                            list_class.add(k+1 ,class_names.get(k).getClass_name());

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddTeachersActivity.this, android.R.layout.simple_spinner_item
                                ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_staff_spin.setAdapter(adapter);
                        class_staff_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(position ==0) {
                                    selected_class = list_class.get(position);
                                    new GETAllBathces_Add_Staff(new Shared_Preference().getId(AddTeachersActivity.this),sel_id).execute();
                                }else {
                                    try {
                                        selected_class = list_class.get(position);
                                        sel_id = "" + class_names.get(position-1).getUserId();
                                        new GETAllBathces_Add_Staff(new Shared_Preference().getId(AddTeachersActivity.this),sel_id).execute();
                                    }catch (Exception e)
                                    {

//                                        selected_class = list_class.get(position);
//                                        sel_id = "" + class_names.get(position-1).getUserId();
//                                        new GETAllBathces(new Shared_Preference().getId(HomeWorkActivity.this),sel_id).execute();
                                        e.printStackTrace();
                                    }
                                    Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                }
                                batch_names.clear();
                                batchArrayList.clear();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        Log.e("GET CLASS ",">>>>>>>>>>>>>>>>_____________________"+result.toString());
                        Log.e("GET CLASS ","ARRAY LIST SPINNER MAP ____________________"+class_names+"\n"+list_class);

                    }
                    else
                    {

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

        private class GETAllBathces_Add_Staff extends AsyncTask<String, Void, String> {

            String userid;
            String calls_id;
            // String Faculty_id;
            private Dialog dialog;


            public GETAllBathces_Add_Staff(String i, String sel_id) {
                this.userid = i;
                this.calls_id = sel_id;
            }

            @Override
            protected String doInBackground(String... arg0) {

                try {

                    URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getbatch");

                    JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                    postDataParams.put("user_id", userid);
                    postDataParams.put("class_id", calls_id);


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

                        if(!jsonObject1.getBoolean("responce"))
                        {
                            Toast.makeText(AddTeachersActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                            batch_names.clear();
                            batchArrayList.clear();
                            staff_batch_spin.setAdapter(null);
                            batch_names.add("--Select Batch--");
//                            batch_names.add("No Batch Found");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddTeachersActivity.this, android.R.layout.simple_spinner_item
                                    ,batch_names);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Apply the adapter to the spinner
                            staff_batch_spin.setAdapter(adapter);
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                        }else {
                            batch_names.add("--Select Batch--");
                            for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
                                JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                                String userid = jsonObject.getString("id");
                                String Class = jsonObject.getString("class_id");
                                String Batch = jsonObject.getString("Batch");


                                batch_names.add(Batch);
                                batchArrayList.add(new ABBBatch(userid,Class,Batch,"Class","Batch"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddTeachersActivity.this, android.R.layout.simple_spinner_item
                                    ,batch_names);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Apply the adapter to the spinner
                            staff_batch_spin.setAdapter(adapter);
                            staff_batch_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(position==0) {
                                        selected_batch = batch_names.get(position);
                                    }else {
                                        try {

                                            selected_batch = batch_names.get(position);
                                            sel_batch = "" + batchArrayList.get(position-1).getUserId();
                                            Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                        }catch (Exception e)
                                        {
//                                        selected_batch = batch_names.get(position);
//                                        sel_batch = "" + batchArrayList.get(position-1).getUserId();
                                            Log.e("Spinner batch Selected ", " Items >>> _______"+ selected_batch+ selected_class + " --- " + sel_id);
                                            e.printStackTrace();
                                        }
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

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
}
