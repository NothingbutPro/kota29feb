package com.ics.admin.BasicAdmin.HomeWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.FacultyAdapter;
import com.ics.admin.Adapter.AdminAdapters.StudentAdapter;
import com.ics.admin.BasicAdmin.SelectFacultyActivity;
import com.ics.admin.BasicAdmin.StudentDetails.AssignStudentActivity;
import com.ics.admin.Fragment.FacultyFragment;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.Model.Faculties;
import com.ics.admin.Model.Students;
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
import java.util.Calendar;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class HomeWorkActivity extends AppCompatActivity {
     TextView selectdatae,homework,worktype,dayshome;
     LinearLayout dateli,dtechli;
     public static TextView selectteacher;
     public static String selectteacherStrings ,teacher_id;
     Button give_work_btn;
    String selected_class , sel_id;
    Spinner batch_spin_assign;
    String selected_batch , sel_batch;
    Button viewhomes;
    RecyclerView facultyrec;
    String dates;
    private ArrayList<ClassNAmes> class_names;
    private ArrayList<String> batch_names = new ArrayList<>();
    private Spinner class_spiner_assign;
    private ArrayList<ABBBatch> batchArrayList = new ArrayList<>();
    private ArrayList<Faculties> facultiesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        give_work_btn= findViewById(R.id.give_work_btn);
        dateli= findViewById(R.id.dateli);
        dtechli= findViewById(R.id.dtechli);
        homework= findViewById(R.id.homework);
        worktype= findViewById(R.id.worktype);
        viewhomes= findViewById(R.id.viewhomes);
        dayshome= findViewById(R.id.dayshome);
        selectteacher= findViewById(R.id.selectteacher);
        selectdatae= findViewById(R.id.selectdatae);
        selectteacher= findViewById(R.id.selectteacher);
        batch_spin_assign= findViewById(R.id.class_batch_spinner);
        class_spiner_assign= findViewById(R.id.class_home_spinner);
        new GETCLASSFORHOMe(new Shared_Preference().getId(this)).execute();
        viewhomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext() , ViewWorkActivity.class);
                startActivity(intent);
            }
        });
        give_work_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sel_batch !=null) {
                    if(!batch_spin_assign.getSelectedItem().equals("No Batch Found")) {
                        new POSTHOMEWORK(selected_class, selected_batch, sel_id, sel_batch, dates, selectteacherStrings, homework.getText().toString(), worktype.getText().toString(), dayshome.getText().toString()).execute();
                    }else {
                        Toast.makeText(HomeWorkActivity.this, "Batch selection is wrong", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(HomeWorkActivity.this, "Please select batch", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dtechli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , SelectFacultyActivity.class);
                startActivity(intent);
            }
        });
//        selectteacher.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//
////                final Dialog dialog = new Dialog(v.getContext());
////                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                dialog.setCancelable(true);
////                dialog.setContentView(R.layout.facultydialog);
////                 facultyrec = dialog.findViewById(R.id.facultyrecx);
////                facultiesArrayList.clear();
////                new GETSALLFACULTIES(new Shared_Preference().getId(v.getContext())).execute();
////                selectteacher.addTextChangedListener(new TextWatcher() {
////                    @Override
////                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////                    }
////
////                    @Override
////                    public void onTextChanged(CharSequence s, int start, int before, int count) {
////                        dialog.dismiss();
////                    }
////
////                    @Override
////                    public void afterTextChanged(Editable s) {
////
////                    }
////                });
//////                selectteacher.setText(faculty_name);
////
//////                facultyrec.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//////                    @Override
//////                    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//////                        return false;
//////                    }
//////
//////                    @Override
//////                    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//////                        dialog.dismiss();
//////
//////                    }
//////
//////                    @Override
//////                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//////
//////                    }
//////                });
////                dialog.show();
//            }
//        });
        dateli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int  mDay   = c.get(Calendar.DAY_OF_MONTH);
                //launch datepicker modal
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Log.d("LOG_APP", "DATE SELECTED "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                dates = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                selectdatae.setText(String.valueOf(selectdatae.getText().toString()+": "+ dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                                //PUT YOUR LOGING HERE
                                //UNCOMMENT THIS LINE TO CALL TIMEPICKER
                                //openTimePicker();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

//        selectdatae.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get Current Date
//                final Calendar c = Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR);
//                int mMonth = c.get(Calendar.MONTH);
//                int  mDay   = c.get(Calendar.DAY_OF_MONTH);
//                //launch datepicker modal
//                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                Log.d("LOG_APP", "DATE SELECTED "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                dates = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
//                                selectdatae.setText(String.valueOf(selectdatae.getText().toString()+": "+ dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
//                                //PUT YOUR LOGING HERE
//                                //UNCOMMENT THIS LINE TO CALL TIMEPICKER
//                                //openTimePicker();
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });

    }

    private class GETCLASSFORHOMe extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GETCLASSFORHOMe(String id) {
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeWorkActivity.this, android.R.layout.simple_spinner_item
                                ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_spiner_assign.setAdapter(adapter);
                        class_spiner_assign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(position ==0) {
                                    selected_class = list_class.get(position);

                                }else {
                                    try {
                                        selected_class = list_class.get(position);
                                        sel_id = "" + class_names.get(position-1).getUserId();
                                        new GETAllBathces(new Shared_Preference().getId(HomeWorkActivity.this),sel_id).execute();
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

                    if(!jsonObject1.getBoolean("responce"))
                    {
                        Toast.makeText(HomeWorkActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                        batch_names.clear();
                        batchArrayList.clear();
                        batch_spin_assign.setAdapter(null);
                        batch_names.add("No Batch Found");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeWorkActivity.this, android.R.layout.simple_spinner_item
                                ,batch_names);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        batch_spin_assign.setAdapter(adapter);
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeWorkActivity.this, android.R.layout.simple_spinner_item
                                ,batch_names);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        batch_spin_assign.setAdapter(adapter);
                        batch_spin_assign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private class GETSALLFACULTIES extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        String Faculty_id;
        private Dialog dialog;


        public GETSALLFACULTIES(String Faculty_id) {
            this.Faculty_id =Faculty_id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getallteacher");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", Faculty_id);


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
                        Toast.makeText(HomeWorkActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String userid = jsonObject.getString("user_id");
                            String name = jsonObject.getString("name");
                            String email = jsonObject.getString("email");
                            String password = jsonObject.getString("password");
                            String mobile = jsonObject.getString("mobile");
                            String address = jsonObject.getString("address");
                            String status = jsonObject.getString("status");
                            String type = jsonObject.getString("type");
                            String addedby = jsonObject.getString("addedby");
                            String fromwhere = "ForHomwwork";

                            facultiesArrayList.add(new Faculties(userid,name,email,password,mobile,address,status,type,addedby ,fromwhere));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeWorkActivity.this);
                        facultyrec.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

                        FacultyAdapter facultyAdapter = new FacultyAdapter(HomeWorkActivity.this,facultiesArrayList);
                        facultyrec.setAdapter(facultyAdapter); // set the Adapter to RecyclerView
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

    private class POSTHOMEWORK extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        String Faculty_id;
        private Dialog dialog;
        private String class_id;
        private String batch_id;
        private String work_date;
        private String school_id;
        private String teachers_id;
        private String homeworkstr;
        private String work_type;
        private String daysforwok;

        public POSTHOMEWORK(String selected_class, String selected_batch, String sel_id, String sel_batch, String dates, String selectteacherStrings, String home, String work, String days) {
            this.class_id = sel_id;
            this.batch_id = sel_batch;
            this.work_date = dates;
            this.homeworkstr = dates;
            this.homeworkstr = home;
            this.work_type = work;
            this.daysforwok = days;
//            this.teacher_id = teacher_id;

        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/add_homework");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("class_id", class_id);
                postDataParams.put("batch_id", batch_id);
                postDataParams.put("work_date", work_date);
                postDataParams.put("school_id", new Shared_Preference().getId(HomeWorkActivity.this));
                postDataParams.put("teacher_id", HomeWorkActivity.teacher_id);
                postDataParams.put("homework", homeworkstr);
                postDataParams.put("work_type", work_type);
                postDataParams.put("daysforwok", daysforwok);


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
                    if(jsonObject1.getBoolean("responce"))
                    {
                        Intent intent = new Intent(HomeWorkActivity.this ,ViewWorkActivity.class);
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
}
