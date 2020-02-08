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
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.CoursesAdapter;
import com.ics.admin.BasicAdmin.Enquiry.ViewEnquiryActivity;
import com.ics.admin.BasicAdmin.Masters.Forclass.AddClassActivity;
import com.ics.admin.CommonJavaClass.AdminProgressdialog;
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

public class ViewCourseActivity extends AppCompatActivity {
    EditText edt_name;
    Spinner class_spiner;
    Button btn_save;
    ArrayList<ClassNAmes> class_names;
    String selected_class , sel_id;
    TextView class_fab;
    LinearLayout coursematerialli;
    RecyclerView getcoursemat;
    com.google.android.material.floatingactionbutton.FloatingActionButton  add_course_fab;
    private CoursesAdapter addCourseActivity;
    ArrayList<ABBCoursemodel> materialsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        class_fab =  findViewById(R.id.class_fab);
        add_course_fab =  findViewById(R.id.add_course_fab);
        getcoursemat= findViewById(R.id.getcoursemat);
        new GETALLCOURSES(new Shared_Preference().getId(this)).execute();

        add_course_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ViewCourseActivity.this , AddCourseActivity.class);
               startActivity(intent);
            }
        });
//        new ABBCourse(Shared_Preference.getId(ViewCourseActivity.this)).execute();
        class_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , AddClassActivity.class);
                startActivity(intent);
            }
        });

    }


    private class GETALLCOURSES extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String batch;

        public GETALLCOURSES(String id) {
            this.id = id;
        }
        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(ViewCourseActivity.this);
            super.onPreExecute();
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
                        Toast.makeText(ViewCourseActivity.this, "Geting Data", Toast.LENGTH_SHORT).show();
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
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewCourseActivity.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        addCourseActivity = new CoursesAdapter(ViewCourseActivity.this , materialsArrayList);
                        getcoursemat.setLayoutManager(layoutManager);
                        getcoursemat.setAdapter(addCourseActivity);


                    }
                    else
                    {
                        Toast.makeText(ViewCourseActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
            adminProgressdialog.EndProgress();
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
