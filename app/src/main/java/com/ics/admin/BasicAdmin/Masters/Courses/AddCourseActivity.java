package com.ics.admin.BasicAdmin.Masters.Courses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.ics.admin.Adapter.AdminAdapters.CoursesAdapter;
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

public class AddCourseActivity extends AppCompatActivity {
    EditText edt_name;
    Spinner class_spiner;
    Button btn_save;
    ArrayList<ClassNAmes> class_names;
    String selected_class , sel_id;
    FloatingActionButton course_fab;
    LinearLayout coursematerialli;
    RecyclerView getcoursemat;
    private CoursesAdapter addCourseActivity;
    ArrayList<ABBCoursemodel> materialsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        edt_name = (EditText) findViewById(R.id.edt_name_course);
        course_fab =  findViewById(R.id.course_fab);
        btn_save = (Button) findViewById(R.id.btn_save_course);
        coursematerialli =  findViewById(R.id.coursematerialli);
        class_spiner=(Spinner)findViewById(R.id.class_spiner_course);
        getcoursemat= findViewById(R.id.getcoursemat);
        new GETALLCOURSES(new Shared_Preference().getId(this)).execute();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        class_spiner.setAdapter(adapter);
        new ABBCourse(Shared_Preference.getId(AddCourseActivity.this)).execute();
        course_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coursematerialli.getVisibility() == View.VISIBLE)
                {
                    coursematerialli.setVisibility(View.GONE);
                }else {
                    coursematerialli.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edt_name.getText().toString().isEmpty()){
                    new ADDCOURSE(edt_name.getText().toString() ,class_names.get(class_spiner.getSelectedItemPosition()).getUserId() ).execute();
                }
               // new AddStudentActivity.AddBatch(Shared_Preference.getId(AddCourseActivity.this),sel_id,edt_name.getText().toString()).execute();
            }
        });
    }
    private class ABBCourse extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public ABBCourse(String id) {
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCourseActivity.this, android.R.layout.simple_spinner_item
                                ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_spiner.setAdapter(adapter);
                        class_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selected_class = list_class.get(position);
                                sel_id =""+class_names.get(position).getUserId();
                                Log.e("Spinner Selected "," Items >>> _______"+selected_class+" --- "+sel_id);
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
//                        Toast.makeText(AddStudentActivity.this, "Faculty Added", Toast.LENGTH_SHORT).show();
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

    private class GETALLCOURSES extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String batch;

        public GETALLCOURSES(String id) {
            this.id = id;
        }


        @Override
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/Courselist");

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
                Log.e("PostRegistration", result.toString());
                try {

                    Log.e("AddBatch ",">>>>>>>>>>>>>>>>_____________________"+result);
                    jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("responce")){
                        Toast.makeText(AddCourseActivity.this, "Geting Data", Toast.LENGTH_SHORT).show();
                        for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
                        {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String Class = jsonObject1.getString("Class");
                            String title = jsonObject1.getString("Course");
                            String class_id = jsonObject1.getString("class_id");
//                            String pdf_file = jsonObject1.getString("pdf_file");
//                            String id = jsonObject1.getString("id");
//                            String id = jsonObject1.getString("id");

                            materialsArrayList.add(new ABBCoursemodel(id,Class,title,"Course :","Class:",class_id ));


                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(AddCourseActivity.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        addCourseActivity = new CoursesAdapter(AddCourseActivity.this , materialsArrayList);
                        getcoursemat.setLayoutManager(layoutManager);
                        getcoursemat.setAdapter(addCourseActivity);


                    }
                    else
                    {
                        Toast.makeText(AddCourseActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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

    private class ADDCOURSE extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String Coursename;

        public ADDCOURSE(String Coursename, String classNAmes) {
            this.Coursename = Coursename;
            this.class_id = classNAmes;
        }


        @Override
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/add_Course");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("user_id", new Shared_Preference().getId(AddCourseActivity.this));
                postDataParams.put("course", Coursename);
                postDataParams.put("class_id", class_id);

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

//                    Log.e("AddBatch ",">>>>>>>>>>>>>>>>_____________________"+result);
                    jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("responce")) {
                        Intent intent = new Intent(AddCourseActivity.this , AddCourseActivity.class);
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
