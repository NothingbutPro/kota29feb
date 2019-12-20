package com.ics.admin.BasicAdmin.FeesStructure;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.ics.admin.Model.ABBCoursemodel;
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

public class AddFeesActivity extends AppCompatActivity {
    private ArrayList<ClassNAmes> class_names;
    private ArrayList<String> batch_names = new ArrayList<>();
    private Spinner class_spiner_assign,coursespin;
    String selected_batch , sel_batch;
    String selected_class , sel_id;
    Button addfeesave;
    EditText enterfees;
    private ArrayList<ABBCoursemodel> batchArrayList = new ArrayList<ABBCoursemodel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fees);
        enterfees = findViewById(R.id.enterfees);
        addfeesave = findViewById(R.id.addfeesave);
        class_spiner_assign = findViewById(R.id.class_spiner_vid);
        coursespin = findViewById(R.id.coursespin);
        new GETAllClassesx(new Shared_Preference().getId(this)).execute();
        addfeesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SENDFEES(new Shared_Preference().getId(v.getContext()) , enterfees.getText().toString()).execute();
            }
        });
    }

public class GETAllClassesx extends AsyncTask<String, Void, String>
    {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GETAllClassesx(String id) {
        this.id = id;
        //  this.course=course;

    }

        @Override
        protected String doInBackground(String... arg0) {

        try {

            URL url = new URL("http://ihisaab.in/school_lms/Adminapi/classlist");

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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddFeesActivity.this, android.R.layout.simple_spinner_item
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
                            new GETAllBathces(new Shared_Preference().getId(AddFeesActivity.this),sel_id).execute();
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

                    URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getcourse");

                    JSONObject postDataParams = new JSONObject();

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
                            Toast.makeText(AddFeesActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                            batch_names.clear();
                            batchArrayList.clear();
//                        batch_names.clear();
                            coursespin.setAdapter(null);
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                        }else {
                            for(int i=0;i<jsonObject1.getJSONArray("data").length();i++)
                            {
                                JSONObject jsonObject2 = jsonObject1.getJSONArray("data").getJSONObject(i);
                                String id = jsonObject2.getString("id");
                                String Class = jsonObject2.getString("Course");
                                String title = jsonObject2.getString("addedby");
                                String class_id = jsonObject2.getString("class_id");
//                            String pdf_file = jsonObject1.getString("pdf_file");
//                            String id = jsonObject1.getString("id");
//                            String id = jsonObject1.getString("id");
                                batch_names.add(Class);
                                batchArrayList.add(new ABBCoursemodel(id,Class,title,"Course :","Class:",class_id));

                            }

//                            batchArrayList.add(new ABBBatch(userid,Class,Batch,"Class","Batch"));

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddFeesActivity.this, android.R.layout.simple_spinner_item
                                    ,batch_names);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Apply the adapter to the spinner
                            coursespin.setAdapter(adapter);
                            coursespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selected_batch = batch_names.get(position);
                                    sel_batch =""+batchArrayList.get(position).getUserId();
                                    Log.e("Spinner Selected "," Items >>> _______"+selected_class+" --- "+sel_id);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }


                    } catch (JSONException e) {

//                    batch_spin_assign.remo
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

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    }

    private class SENDFEES extends AsyncTask<String, Void, String> {

        String userid;
        String calls_id;
         String feeamount;
        private Dialog dialog;


        public SENDFEES(String i, String feeamount) {
            this.userid = i;
            this.feeamount = feeamount;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/addfee");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("school_id", userid);
                postDataParams.put("class_id", sel_id);
                postDataParams.put("course_id", sel_batch);
                postDataParams.put("fee_amount", feeamount);


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
                    if(jsonObject1.getBoolean("responce")) {
                        Toast.makeText(AddFeesActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddFeesActivity.this ,  ViewAllFeesActivity.class);
                        startActivity(intent);
                        finish();
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

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++

}

