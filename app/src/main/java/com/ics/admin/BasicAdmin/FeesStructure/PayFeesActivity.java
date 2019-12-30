package com.ics.admin.BasicAdmin.FeesStructure;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
public class PayFeesActivity extends AppCompatActivity {
    Spinner class_fee_id,batch_fee_id,student_fee_id;
    LinearLayout nonemiselecli,emiselecli;
    private ArrayList<ClassNAmes> class_names;
    TextView student_name;
    private ArrayList<ABBCoursemodel> stud_class_names = new ArrayList<>();
    private ArrayList<String> batch_names = new ArrayList<>();
    private ArrayList<String> stud_spinner_class_names = new ArrayList<>();
    String selected_batch , sel_batch;
    String selected_class , sel_id;
    String selected_student_id_class , sel_student,  sel_courses;
    Button submitfee;
    Boolean feesemiornot;
    int Payed_or_not,payed_id;
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++If NOt payed at all++++++++++++++++++++++++++++++++++++++++
    EditText feeamount,payamtwithoutemi,installs;
    private Spinner paybyspinwithout,paymodes;
    //+++++++++++++++++++++++++++++++++++EMI FIELDS+++++++++++++++++++++++++++++++++++++++++++++++++++++
    EditText emimnthstr,emimonthamt,payinamt;
    //++++++++++++++++++++++++++++++++++++++++
    private ArrayList<ABBCoursemodel> batchArrayList = new ArrayList<ABBCoursemodel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fees);
        class_fee_id = findViewById(R.id.class_fee_id);
        batch_fee_id = findViewById(R.id.batch_fee_id);
        student_fee_id = findViewById(R.id.student_fee_id);
        student_name = findViewById(R.id.student_name);
        nonemiselecli = findViewById(R.id.nonemiselecli);
        emiselecli = findViewById(R.id.emiselecli);
        submitfee = findViewById(R.id.submitfee);
        installs = findViewById(R.id.installs);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++If NOt payed at all++++++++++++++++++++++++++++++++++++++++
        feeamount = findViewById(R.id.feeamount);
        paybyspinwithout = findViewById(R.id.paybyspinwithout);
        paymodes = findViewById(R.id.paymodes);
        // +++++++++++++++++++++++  paying with EMI
        payamtwithoutemi = findViewById(R.id.payamtwithoutemi);
        emimnthstr = findViewById(R.id.emimnthstr);
        emimonthamt = findViewById(R.id.emimonthamt);
        payinamt = findViewById(R.id.payinamt);
//++++++++++++++++++++
        feeamount.setFocusable(false);
        //++++++++++++++++++++++++++++++++++++++++++++++++++++FIRSTTTTTTTTTTTTTT CALL CLASS+++++++++++++++++++++++++
        new GETAllClassesx(new Shared_Preference().getId(this)).execute();
        //+++++++++++++++++++++++++++++++++++++++++++++++++
        installs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Integer.valueOf(installs.getText().toString()) <Integer.valueOf(feeamount.getText().toString())) {
                    float actualpayamount = Float.parseFloat(feeamount.getText().toString()) / Float.valueOf(installs.getText().toString());
                    payinamt.setText(String.valueOf(actualpayamount));
                    emimonthamt.setText(String.valueOf(actualpayamount));
                    emimnthstr.setText(installs.getText().toString());
                }else {
                    Toast.makeText(PayFeesActivity.this, "Wrong instalments ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++ALL EMIS+++++++++++++++++++++++++++++++++++++++++++++
        paybyspinwithout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {

                        feesemiornot = true;
                        nonemiselecli.setVisibility(View.GONE);
                        emiselecli.setVisibility(View.VISIBLE);

                }else  {
                if(Payed_or_not ==0) {
                    feesemiornot = false;
                    nonemiselecli.setVisibility(View.VISIBLE);
                    emiselecli.setVisibility(View.GONE);
                }else {
                    paybyspinwithout.setSelection(0);
                    Toast.makeText(PayFeesActivity.this, "You can only pay with Emi", Toast.LENGTH_LONG).show();
                }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(PayFeesActivity.this, " paybyspinwithout Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++

        submitfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emiselecli.getVisibility() == View.VISIBLE)
                {
                    if(Payed_or_not ==0 )
                    {
                        if(feesemiornot == true) {
                            new PAYWITHEMI(new Shared_Preference().getId(v.getContext()), sel_id ,sel_batch,selected_student_id_class,feeamount.getText().toString(),paybyspinwithout.getSelectedItem().toString(),paymodes.getSelectedItem().toString() ,emimonthamt.getText().toString() ,emimnthstr.getText().toString()).execute();
                        }else if(feesemiornot ==false) {
                            new SSENDMONEYWITHOUTEMI(new Shared_Preference().getId(v.getContext()), sel_id ,sel_batch,selected_student_id_class,feeamount.getText().toString(),paybyspinwithout.getSelectedItem().toString(),paymodes.getSelectedItem().toString()).execute();
                        }
                    }else {
                            new JUSTPAYTHEINSTALMETN(payed_id,paymodes.getSelectedItem().toString()).execute();

//                        new PAYWITHEMI(new Shared_Preference().getId(v.getContext()), sel_id ,sel_batch,selected_student_id_class,feeamount.getText().toString(),paybyspinwithout.getSelectedItem().toString(),paymodes.getSelectedItem().toString() ,emimonthamt.getText().toString() ,emimnthstr.getText().toString()).execute();
                    }
                    Toast.makeText(PayFeesActivity.this, "paying with emi now", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PayFeesActivity.this, "paying without emi now", Toast.LENGTH_SHORT).show();
                    new SSENDMONEYWITHOUTEMI(new Shared_Preference().getId(v.getContext()), sel_id ,sel_batch,selected_student_id_class,feeamount.getText().toString(),paybyspinwithout.getSelectedItem().toString(),paymodes.getSelectedItem().toString()).execute();
                }
            }
        });
    }
    //+++++++++ +++++++++++++++++++++select classes adll++++++++++++++++++++++++++++++++++++

    public class GETAllClassesx extends AsyncTask<String, Void, String> {

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
                Log.e("PostRegistration ", "classlist" + result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("responce")) {
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                        JSONArray jarr = jsonObject.getJSONArray("data");
                        class_names = new ArrayList<>();
                        for (int i = 0; i < jarr.length(); i++) {

                            String cl_id = jarr.getJSONObject(i).getString("id");
                            String cl_name = jarr.getJSONObject(i).getString("Class");
                            class_names.add(new ClassNAmes(cl_id, cl_name));
                        }

                        final ArrayList<String> list_class = new ArrayList<>();
                        for (int k = 0; k < class_names.size(); k++) {
                            list_class.add(class_names.get(k).getClass_name());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PayFeesActivity.this, android.R.layout.simple_spinner_item
                                , list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_fee_id.setAdapter(adapter);
                        class_fee_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selected_class = list_class.get(position);
                                sel_id = "" + class_names.get(position).getUserId();
                                Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                batch_names.clear();
                                batchArrayList.clear();
                                new GETAllBathces(new Shared_Preference().getId(PayFeesActivity.this), sel_id).execute();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        Log.e("GET CLASS ", ">>>>>>>>>>>>>>>>_____________________" + result.toString());
                        Log.e("GET CLASS ", "ARRAY LIST SPINNER MAP ____________________" + class_names + "\n" + list_class);

                    } else {

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
                    Log.e("PostRegistration" , "getcourse "+result.toString());
                    try {

                        jsonObject1 = new JSONObject(result);
                        if (!jsonObject1.getBoolean("responce")) {
                            Toast.makeText(PayFeesActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                            batch_names.clear();
                            batchArrayList.clear();
//                        batch_names.clear();
                            batch_fee_id.setAdapter(null);
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < jsonObject1.getJSONArray("data").length(); i++) {
                                JSONObject jsonObject2 = jsonObject1.getJSONArray("data").getJSONObject(i);
                                String id = jsonObject2.getString("id");
                                String Class = jsonObject2.getString("Course");
                                String title = jsonObject2.getString("addedby");
                                String class_id = jsonObject2.getString("class_id");
//                                String class_id = jsonObject2.getString("class_id");
//                            String pdf_file = jsonObject1.getString("pdf_file");
//                            String id = jsonObject1.getString("id");
//                            String id = jsonObject1.getString("id");
                                batch_names.add(Class);
                                batchArrayList.add(new ABBCoursemodel(id, Class, title, "Course :", "Class:",class_id));

                            }

//                            batchArrayList.add(new ABBBatch(userid,Class,Batch,"Class","Batch"));

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(PayFeesActivity.this, android.R.layout.simple_spinner_item
                                    , batch_names);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Apply the adapter to the spinner
                            batch_fee_id.setAdapter(adapter);

                            batch_fee_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selected_batch = batch_names.get(position);
                                    sel_batch = "" + batchArrayList.get(position).getUserId();
                                    Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_batch);
                                    new GETALLSTUDENTSBYCOurse(new Shared_Preference().getId(PayFeesActivity.this), sel_id, sel_batch).execute();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    Toast.makeText(PayFeesActivity.this, "batch_fee_id nothing selected", Toast.LENGTH_SHORT).show();
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

            private class GETALLSTUDENTSBYCOurse extends AsyncTask<String, Void, String> {

                String userid;
                String calls_id;
                String sel_course;
                private Dialog dialog;


                public GETALLSTUDENTSBYCOurse(String id, String sel_id, String sel_course) {
                    this.userid = id;
                    this.calls_id = sel_id;
                    this.sel_course = sel_course;
                }


                @Override
                protected String doInBackground(String... arg0) {

                    try {

                        URL url = new URL("http://ihisaab.in/school_lms/api/getstudentbyclass");

                        JSONObject postDataParams = new JSONObject();

                        postDataParams.put("school_id", userid);
                        postDataParams.put("class_id", calls_id);
                        postDataParams.put("course_id", sel_course);


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
                            if (!jsonObject1.getBoolean("responce")) {
                                Toast.makeText(PayFeesActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                                stud_spinner_class_names.clear();
                                stud_class_names.clear();
//                        batch_names.clear();
                                student_fee_id.setAdapter(null);

                            } else {
                                stud_spinner_class_names.clear();
                                stud_class_names.clear();
//                        batch_names.clear();
                                student_fee_id.setAdapter(null);
                                feeamount.setText(jsonObject1.getString("fee"));
                                payamtwithoutemi.setText(jsonObject1.getString("fee"));
                                payamtwithoutemi.setFocusable(false);
                                for (int i = 0; i < jsonObject1.getJSONArray("data").length(); i++) {
                                    JSONObject jsonObject2 = jsonObject1.getJSONArray("data").getJSONObject(i);
                                    String id = jsonObject2.getString("id");
                                    String name = jsonObject2.getString("name");
                                    stud_spinner_class_names.add(name);
                                    stud_class_names.add(new ABBCoursemodel(id, name));

                                }

//                            batchArrayList.add(new ABBBatch(userid,Class,Batch,"Class","Batch"));

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PayFeesActivity.this, android.R.layout.simple_spinner_item
                                        , stud_spinner_class_names);

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Apply the adapter to the spinner
                                student_fee_id.setAdapter(adapter);
                                student_fee_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selected_batch = stud_spinner_class_names.get(position);
                                        selected_student_id_class = "" + stud_class_names.get(position).getStud_id();
                                        Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + selected_student_id_class);
                                        installs.setFocusable(true);
                                        new CheckForStudents(new Shared_Preference().getId(PayFeesActivity.this),sel_course,sel_id,selected_student_id_class).execute();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        Toast.makeText(PayFeesActivity.this, "student_fee_id nothing selected", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }


                        } catch (JSONException e) {

//                    batch_spin_assign.remo
                            e.printStackTrace();
                        }

                    }
                }
            }

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        }
        //++++++++++++++++++++++++++++

    }

    private class CheckForStudents extends AsyncTask<String, Void, String> {
        String id;
        String sel_course;
        String sel_id;
        String selected_student_id_class;

        public CheckForStudents(String id, String sel_course, String sel_id, String selected_student_id_class) {
            this.id = id;
            this.sel_course = sel_course;
            this.sel_id = sel_id;
            this.selected_student_id_class = selected_student_id_class;

        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/checkpayment");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("school_id", id);
                postDataParams.put("class_id", sel_id);
                postDataParams.put("course_id", sel_course);
                postDataParams.put("student_id", selected_student_id_class);


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
                Log.e("PostRegistration", "checkpayment"+result.toString());
                try {

                    jsonObject1 = new JSONObject(result);
                    if (jsonObject1.getBoolean("responce")) {
                        Toast.makeText(PayFeesActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        emiselecli.setVisibility(View.VISIBLE);
//                        nonemiselecli.setVisibility(View.GONE);
                        Payed_or_not =1;

                        payed_id = jsonObject1.getJSONObject("data").getInt("id");
                        emimonthamt.setText(jsonObject1.getJSONObject("data").getString("amount"));
                        emimonthamt.setFocusable(false);
                        emimnthstr.setFocusable(false);
                        payinamt.setFocusable(false);
                        installs.setText(jsonObject1.getJSONObject("data").getString("instalment_no"));
                        installs.setFocusable(false);
                        emimnthstr.setText(jsonObject1.getJSONObject("data").getString("month"));
                        payinamt.setText(jsonObject1.getJSONObject("data").getString("amount"));
                    }
                    else
                        {
                            if(jsonObject1.getString("error").equals("Due amount")) {
                                Payed_or_not = 0;
//                                nonemiselecli.setVisibility(View.GONE);
//                                emiselecli.setVisibility(View.GONE);
                                emimonthamt.setFocusable(true);
                                emimnthstr.setFocusable(true);
                                payinamt.setFocusable(true);
                                installs.setFocusable(true);
                            }else {
                                Payed_or_not = 0;
//                                nonemiselecli.setVisibility(View.GONE);
//                                emiselecli.setVisibility(View.GONE);
                                emimonthamt.setFocusable(true);
                                emimnthstr.setFocusable(true);
                                payinamt.setFocusable(true);
                                installs.setFocusable(true);
//                                try {
//                                    student_fee_id.setSelection(student_fee_id.getSelectedItemPosition()+1);
//                                }catch (Exception e)
//                                {
//                                    e.printStackTrace();
//                                }
                                feeamount.setText("");
                                new AlertDialog.Builder(PayFeesActivity.this)
                                        .setTitle("Alert")
                                        .setMessage("You Have already paid for this course,Please select course again!")

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton("Ok Got It", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Continue with delete operation
                                                try {
                                                    student_fee_id.setSelection(student_fee_id.getSelectedItemPosition() + 1);
                                                }catch (Exception e)
                                                {
                                                    Toast.makeText(PayFeesActivity.this, "All Student are paid", Toast.LENGTH_SHORT).show();
                                                }
                                                dialog.dismiss();
                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton(android.R.string.no, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
//                                Toast.makeText(PayFeesActivity.this, "You Have already paid for this course", Toast.LENGTH_LONG).show();
                            }
                        Log.d("results" , "found"+result);
//

                    }
                } catch (JSONException e) {

//                    batch_spin_assign.remo
                    e.printStackTrace();
                }

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

    private class SSENDMONEYWITHOUTEMI extends AsyncTask<String, Void, String> {
        String id;
        String sel_course;
        String sel_id;
        String selected_student_id_class;
        String feeamount;
        String paybyspinwithoutemi;
        String paymodes;


        public SSENDMONEYWITHOUTEMI(String id, String sel_id, String sel_course, String selected_student_id_class, String feeamount, String paybyspinwithoutemi, String paymodes) {
            this.id = id;
            this.sel_course = sel_course;
            this.sel_id = sel_id;
            this.selected_student_id_class = selected_student_id_class;
            this.feeamount = feeamount;
            this.paybyspinwithoutemi = paybyspinwithoutemi;
            this.paymodes = paymodes;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/payfee");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("school_id", id);
                postDataParams.put("class_id", sel_id);
                postDataParams.put("course_id", sel_course);
                postDataParams.put("student_id", selected_student_id_class);
                postDataParams.put("total_amount", feeamount);
                postDataParams.put("total_payamount", feeamount);
                postDataParams.put("payby", paybyspinwithoutemi);
                postDataParams.put("paymode", paymodes);
                postDataParams.put("staff_id", "0");
//                postDataParams.put("emi_month_amt", "0");


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
                    if (jsonObject1.getString("payby") !=null) {
                        Toast.makeText(PayFeesActivity.this, "You paid with "+jsonObject1.getString("payby"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PayFeesActivity.this , AllStudentFeeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(PayFeesActivity.this, "Payment failed", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(PayFeesActivity.this , AllStudentFeeActivity.class);
//                        startActivity(intent);
//                        finish();
                        Log.d("results" , "found"+result);
//

                    }


                } catch (JSONException e) {

//                    batch_spin_assign.remo
                    e.printStackTrace();
                }

            }
        }

    }

    private class JUSTPAYTHEINSTALMETN extends AsyncTask<String, Void, String> {
        int payed_id;
        String selectedItem;


        public JUSTPAYTHEINSTALMETN(int payed_id, String selectedItem) {
            this.payed_id = payed_id;
            this.selectedItem = selectedItem;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/payemifee");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("id", payed_id);
                postDataParams.put("paymode", selectedItem);

//                postDataParams.put("emi_month_amt", "0");


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
                    if (jsonObject1.getBoolean("responce")) {
                        Toast.makeText(PayFeesActivity.this, "You paid with EMI", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(PayFeesActivity.this, "Payment failed", Toast.LENGTH_SHORT).show();
                        Log.d("results" , "found"+result);
                        finish();
                    }


                } catch (JSONException e) {

//                    batch_spin_assign.remo
                    e.printStackTrace();
                }

            }
        }

    }

    private class PAYWITHEMI extends AsyncTask<String, Void, String> {

        String id;
        String sel_course;
        String sel_id;
        String selected_student_id_class;
        String feeamount;
        String paybyspinwithoutemi;
        String paymodes;
        String amount;
        String months;



        public PAYWITHEMI(String id, String sel_id, String sel_course, String selected_student_id_class, String feeamount, String paybyspinwithoutemi, String paymodes, String amount, String months) {
            this.id = id;
            this.sel_course = sel_course;
            this.sel_id = sel_id;
            this.selected_student_id_class = selected_student_id_class;
            this.feeamount = feeamount;
            this.paybyspinwithoutemi = paybyspinwithoutemi;
            this.paymodes = paymodes;
            this.amount = amount;
            this.months = months;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/payfee");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("school_id", id);
                postDataParams.put("class_id", sel_id);
                postDataParams.put("course_id", sel_course);
                postDataParams.put("student_id", selected_student_id_class);
                postDataParams.put("total_amount", feeamount);
                postDataParams.put("total_payamount", feeamount);
                postDataParams.put("payby", paybyspinwithoutemi);
                postDataParams.put("paymode", paymodes);
                postDataParams.put("staff_id", "0");
                postDataParams.put("emi_month_amt", amount);
                postDataParams.put("emi_month", months);
//                postDataParams.put("emi_month_amt", "0");


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
                    if (jsonObject1.getBoolean("responce")) {
                        Intent intent = new Intent(PayFeesActivity.this , AllStudentFeeActivity.class);
                        startActivity(intent);
                        Toast.makeText(PayFeesActivity.this, "You paid with EMI", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(PayFeesActivity.this, "Payment failed", Toast.LENGTH_SHORT).show();
                        Log.d("results" , "found"+result);
//

                    }


                } catch (JSONException e) {

//                    batch_spin_assign.remo
                    e.printStackTrace();
                }

            }
        }

    }
}
