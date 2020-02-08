package com.ics.admin.BasicAdmin.Masters.Batch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ics.admin.CommonJavaClass.AdminProgressdialog;
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

public class AddStudentActivity extends AppCompatActivity {
 EditText edt_name;
 Spinner class_spiner;
 ArrayList<ClassNAmes> class_names;
 Button btn_save;
 String selected_class , sel_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        edt_name = (EditText) findViewById(R.id.edt_name_s);
        btn_save = (Button) findViewById(R.id.btn_save_batch);
        class_spiner=(Spinner)findViewById(R.id.class_spiner);

        new GetClassSpinner(Shared_Preference.getId(AddStudentActivity.this)).execute();


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sel_id != null) {
                    new AddBatch(Shared_Preference.getId(AddStudentActivity.this), sel_id, edt_name.getText().toString()).execute();
                }else {
                    Toast.makeText(AddStudentActivity.this, "Please select class", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    class AddBatch extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String batch;
        AdminProgressdialog adminProgressdialog;
        public AddBatch(String id, String sel_id, String class_spiner) {
            this.id = id;
            this.class_id=sel_id;
            this.batch=class_spiner;
        }

        @Override
        protected void onPreExecute() {
//            Activity host = (Activity) view.getContext();
            adminProgressdialog = new AdminProgressdialog(AddStudentActivity.this);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/add_Batch");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("user_id", id);
                postDataParams.put("class_id",class_id);
                postDataParams.put("batch",batch);
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
                adminProgressdialog.EndProgress();
                try {

                    Log.e("AddBatch ",">>>>>>>>>>>>>>>>_____________________"+result);
                    jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("responce")){
                        Toast.makeText(AddStudentActivity.this, "Add Batch Successfull", Toast.LENGTH_SHORT).show();
                        adminProgressdialog.EndProgress();
                        Intent intent=new Intent(AddStudentActivity.this, AddBatchActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        adminProgressdialog.EndProgress();
                        Toast.makeText(AddStudentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(AddStudentActivity.this , AdminActivity.class);
//                        startActivity(intent);
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
               ///////////////////  Class Spinner List

    private class GetClassSpinner extends AsyncTask<String, Void, String> {
        AdminProgressdialog adminProgressdialog;
        String id;

        public GetClassSpinner(String id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            adminProgressdialog = new AdminProgressdialog(AddStudentActivity.this);
            super.onPreExecute();
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
                    if(jsonObject.getBoolean("responce")){
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                        JSONArray jarr = jsonObject.getJSONArray("data");
                        class_names = new ArrayList<>();
                        for (int i=0 ; i<jarr.length() ; i++){

                            String cl_id = jarr.getJSONObject(i).getString("id");
                            String cl_name = jarr.getJSONObject(i).getString("Class");
                            class_names.add(new ClassNAmes(cl_id,cl_name));
                        }

                        final ArrayList <String> list_class = new ArrayList<>();
                        list_class.add("---Select class---");
                        for (int k=0 ; k<class_names.size() ; k++){

                            list_class.add( class_names.get(k).getClass_name());

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_spinner_item
                            ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_spiner.setAdapter(adapter);
                        class_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(position ==0) {
                                    selected_class = list_class.get(position);
//                                    sel_id = "" + class_names.get(position).getUserId();
                                    Toast.makeText(AddStudentActivity.this, "Please select class", Toast.LENGTH_SHORT).show();
                                }else {
                                    try {
                                        selected_class = list_class.get(position);
                                        sel_id = "" + class_names.get(position-1).getUserId();
                                    }catch (Exception e)
                                    {
                                        selected_class = list_class.get(0);
                                        sel_id = "" + class_names.get(0).getUserId();
                                        e.printStackTrace();
                                    }
                                }
                                Log.e("Spinner Selected "," Items >>> _______"+selected_class+" --- "+sel_id);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        Log.e("GET CLASS ",">>>>>>>>>>>>>>>>_____________________"+result.toString());
                        Log.e("GET CLASS ","ARRAY LIST SPINNER MAP ____________________"+class_names+"\n"+list_class);
                        adminProgressdialog.EndProgress();
                    }
                    else
                    {
                        final ArrayList <String> list_class = new ArrayList<>();
                        list_class.add("---Select class---");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_spinner_item
                                ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_spiner.setAdapter(adapter);
                        adminProgressdialog.EndProgress();
                    }


                } catch (JSONException e) {
                    adminProgressdialog.EndProgress();
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

