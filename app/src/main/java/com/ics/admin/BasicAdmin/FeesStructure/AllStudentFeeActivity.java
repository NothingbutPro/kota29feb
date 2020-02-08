package com.ics.admin.BasicAdmin.FeesStructure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ics.admin.Adapter.AdminAdapters.AllStudentFeesAdapter;
import com.ics.admin.Adapter.AdminAdapters.Student_Fee_Adapter;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.BasicAdmin.StudentDetails.AllStudentListActivity;
import com.ics.admin.CommonJavaClass.AdminProgressdialog;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.Model.Student_Fee_Details;
import com.ics.admin.Model.Student_Fee_Details_Data;
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
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllStudentFeeActivity extends AppCompatActivity {
    RecyclerView studentfeespin;
    ArrayList<Students> studentsList = new ArrayList<>();
    Student_Fee_Adapter studentAdapter;
    AllStudentFeesAdapter allStudentFeesAdapter;
    SearchView searchbystudent;
    Spinner spin_class,spin_batch;
    ArrayList<ClassNAmes> class_names;
    ArrayList<String> AllBatcheslist = new ArrayList<>();
    private ArrayList<Student_Fee_Details_Data> student_fee_details_list = new ArrayList<>();
    private ArrayList<ABBBatch> batchArrayList = new ArrayList<>();
    private String selected_batch,Student_Name;
    private String sel_batch_id;
    private String selected_class ,sel_id;
    Call<Student_Fee_Details> RegisterCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student_fee);
        studentfeespin = findViewById(R.id.studentfeespin);
        spin_class = findViewById(R.id.spin_class);
        spin_batch = findViewById(R.id.spin_batch);
        searchbystudent = findViewById(R.id.searchbystudent);
        new GETAllClassesxby(new Shared_Preference().getId(this)).execute();
        new GETAllBatchsxby(new Shared_Preference().getId(this)).execute();
        searchbystudent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Student_Name = query;
                GetAllStudentsFees(Student_Name ,sel_id ,sel_batch_id );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //+++++++++++++++++++++++++++++++++++++++++++For class++++++++++++++++++++++++++
        spin_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selected_class = list_class.get(position);
                if(position >0) {
                    position = position - 1;
                    sel_id = class_names.get(position).getUserId();
                    GetAllStudentsFees(Student_Name ,sel_id ,sel_batch_id );
                    Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
     //++++++++++++++++++++++++++++++++++++++++++++++++++For batch +++++++++++++++++++++++++
        spin_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selected_batch = AllBatcheslist.get(position);
                if(position >0) {
                    position = position - 1;
                    sel_batch_id = "" + batchArrayList.get(position).getUserId();
                    GetAllStudentsFees(Student_Name ,sel_id ,sel_batch_id );
                    Log.e("Spinner Selected ", " Items >>> _______" + selected_batch + " --- " + sel_batch_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        new GetSAllStudents(new Shared_Preference().getId(this)).execute();
    }

    private void GetAllStudentsFees(String student_Name, String sel_id, String sel_batch_id) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl("https://ihisaab.in/school_lms/api/").addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
        if(student_Name !=null) {
            RegisterCall = RegApi.STUDENT_FEE_DETAILS_BY_STUDENT_CALL(new Shared_Preference().getId(this), student_Name);
        }else if(sel_id !=null) {
            RegisterCall = RegApi.STUDENT_FEE_DETAILS_BY_CLASS_CALL(new Shared_Preference().getId(this), sel_id);
        }else  if(sel_batch_id !=null) {
            RegisterCall = RegApi.STUDENT_FEE_DETAILS_BY_BATCH_ID_CALL(new Shared_Preference().getId(this), sel_batch_id);
        }
        RegisterCall.enqueue(new Callback<Student_Fee_Details>() {
            @Override
            public void onResponse(Call<Student_Fee_Details> call, Response<Student_Fee_Details> response) {
                Gson gson = new Gson();
                Log.d("attendence string" , ""+gson.toJson(response.body()) );
                Log.d("attendence id" , ""+new Shared_Preference().getId(AllStudentFeeActivity.this) );
                if(response.body().getResponce()) {
                    Toast.makeText(AllStudentFeeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    try {
//                        student_fee_details_list.clear();
                        student_fee_details_list =  response.body().getData();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllStudentFeeActivity.this);
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        studentfeespin.setLayoutManager(linearLayoutManager);
                        allStudentFeesAdapter = new AllStudentFeesAdapter(AllStudentFeeActivity.this ,student_fee_details_list);
                        studentfeespin.setAdapter(allStudentFeesAdapter);
//                        public_community_admin_chat_adapter.notifyDataSetChanged();
//                        getallCommunityMesages(school_id);
                    }catch (Exception e)
                    {
//                        getallCommunityMesages(school_id);
                        e.printStackTrace();
                    }
//                    lovelyProgressDialogs.DismissDialog();

                }else {
                    Toast.makeText(AllStudentFeeActivity.this, "No StudyMaterial found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Student_Fee_Details> call, Throwable t) {

                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(AllStudentFeeActivity.this, "Failed to publish the Query", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private class GETAllClassesxby extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GETAllClassesxby(String id) {
            this.id = id;
            //  this.course=course;
            this.class_id=class_id;
        }

        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(AllStudentFeeActivity.this);
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
                        list_class.add("--Select Class--");
                        for (int k=0 ; k<class_names.size() ; k++){
                            list_class.add(k+1,class_names.get(k).getClass_name());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllStudentFeeActivity.this, android.R.layout.simple_spinner_item
                                ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        spin_class.setAdapter(adapter);

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

    private class GETAllBatchsxby extends AsyncTask<String, Void, String>  {

        String userid;
        // String Faculty_id;
        private Dialog dialog;


        public GETAllBatchsxby(String i) {
            this.userid = i;
        }
        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(AllStudentFeeActivity.this);
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/Batchlist");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", userid);


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
                        Toast.makeText(AllStudentFeeActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        AllBatcheslist.add("---Select Batch--");
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++)
                        {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String userid = jsonObject.getString("id");
                            String Class = jsonObject.getString("Class");
                            String Batch = jsonObject.getString("Batch");
                            String class_id = jsonObject.getString("class_id");
//

                            AllBatcheslist.add(Batch);
                            batchArrayList.add(new ABBBatch(userid,Class,Batch,"Class","Batch" ,class_id));
                        }

//                        for (int k=0 ; k<batchArrayList.size() ; k++){
//                            AllBatcheslist.add(batchArrayList.get(k).getClass());
//                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllStudentFeeActivity.this, android.R.layout.simple_spinner_item
                                ,AllBatcheslist);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        spin_batch.setAdapter(adapter);

                        Log.e("GET CLASS ",">>>>>>>>>>>>>>>>_____________________"+result.toString());
                        Log.e("GET CLASS ","ARRAY LIST SPINNER MAP ____________________"+AllBatcheslist+"\n"+batchArrayList);

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


//    private class GetSAllStudents extends AsyncTask<String, Void, String> {
//        String userid;
//        // String Faculty_id;
//        public  ProgressDialogs progressDialogs;
//        String name; String email; String password; String mobile; String address;
//
//        public GetSAllStudents(String id) {
//            this.userid = id;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressDialogs=  new ProgressDialogs();
//            progressDialogs.ProgressMe((Context) AllStudentFeeActivity.this ,AllStudentFeeActivity.this );
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... arg0) {
//
//            try {
//
//                URL url = new URL("http://ihisaab.in/school_lms/api/view_student");
//
//                JSONObject postDataParams = new JSONObject();
////                postDataParams.put("otp", Mobile_Number);
//                postDataParams.put("user_id", userid);
//
//
//
//                Log.e("postDataParams", postDataParams.toString());
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(15000 /*milliseconds*/);
//                conn.setConnectTimeout(15000 /*milliseconds*/);
//                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(
//                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getPostDataString(postDataParams));
//
//                writer.flush();
//                writer.close();
//                os.close();
//
//                int responseCode = conn.getResponseCode();
//
//                if (responseCode == HttpsURLConnection.HTTP_OK) {
//
//                    BufferedReader in = new BufferedReader(new
//                            InputStreamReader(
//                            conn.getInputStream()));
//
//                    StringBuffer sb = new StringBuffer("");
//                    String line = "";
//
//                    while ((line = in.readLine()) != null) {
//
//                        StringBuffer Ss = sb.append(line);
//                        Log.e("Ss", Ss.toString());
//                        sb.append(line);
//                        break;
//                    }
//
//                    in.close();
//                    return sb.toString();
//
//                } else {
//                    return new String("false : " + responseCode);
//                }
//            } catch (Exception e) {
//                return new String("Exception: " + e.getMessage());
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
////                dialog.dismiss();
//
//                JSONObject jsonObject1 = null;
//                Log.e("PostRegistration", result.toString());
//                try {
//                    progressDialogs.EndMe();
//                    jsonObject1 = new JSONObject(result);
//                    if(jsonObject1.getBoolean("responce")){
//                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
//                        for(int i=0;i<jsonArray.length();i++) {
//                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
//                            String id = jsonObject11.getString("id");
//                            String name = jsonObject11.getString("name");
//                            String mobile = jsonObject11.getString("mobile");
//                            String email = jsonObject11.getString("email");
//                            String Class = jsonObject11.getString("Class");
//                            String Batch = jsonObject11.getString("Batch");
//                            studentsList.add(new Students(id, name, mobile, email, password,Class,Batch,"assigned","feedone_yes"));
//                        }
//                    }else {
//                        Toast.makeText(AllStudentFeeActivity.this, "No students found ,assign them first", Toast.LENGTH_SHORT).show();
//                    }
//                    studentAdapter = new Student_Fee_Adapter(AllStudentFeeActivity.this,studentsList);
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(AllStudentFeeActivity.this );
//                    layoutManager.setOrientation(RecyclerView.VERTICAL);
//                    studentfeespin.setLayoutManager(layoutManager);
//                    studentfeespin.setAdapter(studentAdapter);
//
//
//                } catch (JSONException e) {
//
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//        public String getPostDataString(JSONObject params) throws Exception {
//
//            StringBuilder result = new StringBuilder();
//            boolean first = true;
//
//            Iterator<String> itr = params.keys();
//
//            while (itr.hasNext()) {
//
//                String key = itr.next();
//                Object value = params.get(key);
//
//                if (first)
//                    first = false;
//                else
//                    result.append("&");
//
//                result.append(URLEncoder.encode(key, "UTF-8"));
//                result.append("=");
//                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
//
//            }
//            return result.toString();
//        }
//    }
}
