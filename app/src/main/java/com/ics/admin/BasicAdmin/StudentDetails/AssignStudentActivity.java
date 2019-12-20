package com.ics.admin.BasicAdmin.StudentDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Interfaces.ProgressDialogs;
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

public class AssignStudentActivity extends AppCompatActivity {
    TextView student_name;
    String selected_class , sel_id;
    Spinner batch_spin_assign;
    String selected_batch , sel_batch;
    Button btn_assign;
    private ArrayList<ClassNAmes> class_names;
    ProgressDialogs  progressDialogs  = new ProgressDialogs();
    private ArrayList<String> batch_names = new ArrayList<>();
    private Spinner class_spiner_assign;
    private ArrayList<ABBBatch> batchArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_student);
        student_name = findViewById(R.id.student_name);
        btn_assign = findViewById(R.id.btn_assign);
        class_spiner_assign = findViewById(R.id.class_spin_assign);
        batch_spin_assign = findViewById(R.id.batch_spin_assign);
        student_name.setText(getIntent().getStringExtra("student_name"));
        progressDialogs.ProgressMe(this, AssignStudentActivity.this);
        new GETAllClasses(new Shared_Preference().getId(this)).execute();
        btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!batch_spin_assign.getSelectedItem().equals("No Batch  Found")) {
                    Toast.makeText(AssignStudentActivity.this, "Processing", Toast.LENGTH_SHORT).show();
                    new AssignTOSTURENT( getIntent().getStringExtra("student_id"), sel_id, sel_batch).execute();
//                    if( getIntent() ==null) {
//                        new AssignTOSTURENT(new Shared_Preference().getId(v.getContext()), sel_id, sel_batch).execute();
//                    }else {
////                        Toast.makeText(AssignStudentActivity.this, "", Toast.LENGTH_SHORT).show();
//
//                    }
                }else {
                    Toast.makeText(AssignStudentActivity.this, "Please Select valid batch", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class GETAllClasses extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GETAllClasses(String id) {
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
                        for (int k=0 ; k<class_names.size() ; k++){
                            list_class.add(class_names.get(k).getClass_name());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AssignStudentActivity.this, android.R.layout.simple_spinner_item
                                ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_spiner_assign.setAdapter(adapter);
                        class_spiner_assign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selected_class = list_class.get(position);
                                sel_id =""+class_names.get(position).getUserId();
                                Log.e("Spinner Selected "," Items >>> _______"+selected_class+" --- "+sel_id);
                                batch_names.clear();
                                batchArrayList.clear();
                                new GETAllBathces(new Shared_Preference().getId(AssignStudentActivity.this),sel_id).execute();
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
    }

    private class GETAllBathces extends AsyncTask<String, Void, String> {

        String userid;
        String calls_id;
        // String Faculty_id;
        private Dialog dialog;


        public GETAllBathces(String i, String sel_id) {
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
                    if(!jsonObject1.getBoolean("responce")){
                        Toast.makeText(AssignStudentActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                        batch_names.clear();
                        batchArrayList.clear();
                        batch_names.add("No Batch  Found");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AssignStudentActivity.this, android.R.layout.simple_spinner_item
                                ,batch_names);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        batch_spin_assign.setAdapter(adapter);
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        batch_names.clear();
                        batchArrayList.clear();

                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String userid = jsonObject.getString("id");
                            String Class = jsonObject.getString("class_id");
                            String Batch = jsonObject.getString("Batch");
                            batch_names.add(Batch);
                            batchArrayList.add(new ABBBatch(userid,Class,Batch,"Class","Batch"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AssignStudentActivity.this, android.R.layout.simple_spinner_item
                                ,batch_names);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        batch_spin_assign.setAdapter(adapter);


                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }
                batch_spin_assign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            if(!batch_spin_assign.getSelectedItem().equals("No Batch  Found")) {
                                selected_batch = batch_names.get(position);
                                sel_batch = "" +batchArrayList.get(position).getUserId();
                            }else {
                                selected_batch = batch_names.get(position);
                            }
                            Log.e("Spinner Selected ", " Items >>> _______" + sel_batch + " --- " + sel_id);
                        }catch (Exception e)
                        {
                            sel_batch =null;
                            Toast.makeText(AssignStudentActivity.this, "No batch found ", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                progressDialogs.EndMe();
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

    private class AssignTOSTURENT extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;
        private String batch_id;


        public AssignTOSTURENT(String id, String sel_id, String sel_batch) {
            this.id = id;
            //  this.course=course;
            this.class_id = sel_id;
            this.batch_id = sel_batch;
        }

        @Override
        protected void onPreExecute() {
            progressDialogs.ProgressMe(AssignStudentActivity.this ,AssignStudentActivity.this);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground (String...arg0){

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/assing_student");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("student_id", id);
                postDataParams.put("class_id", class_id);
                postDataParams.put("batch_id", batch_id);

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
        protected void onPostExecute (String result){
            if (result != null) {
//                dialog.dismiss();
                progressDialogs.EndMe();
                JSONObject jsonObject = null;
                Log.e("PostRegistration ",  result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("responce")) {
                        //    getotp.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(AssignStudentActivity.this , AllStudentListActivity.class);

                        startActivity(intent);
//
                        Toast.makeText(AssignStudentActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString (JSONObject params) throws Exception {

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
