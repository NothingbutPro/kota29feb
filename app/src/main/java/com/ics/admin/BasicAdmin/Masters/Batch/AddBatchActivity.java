package com.ics.admin.BasicAdmin.Masters.Batch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.ics.admin.Adapter.AdminAdapters.BatchAdapter;
import com.ics.admin.CommonJavaClass.ProgressDialogs;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.taishi.flipprogressdialog.FlipProgressDialog;

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

public class AddBatchActivity extends AppCompatActivity {
    RecyclerView recyclerView1;
    com.github.clans.fab.FloatingActionButton fab;
    Shared_Preference shared_preference;
    LinearLayout batchli;
    ArrayList<ABBBatch> batchArrayList = new ArrayList<>();
    //For adding batch
    EditText proedt_name_s;
    Spinner pro_class_spiner;
    ArrayList<ClassNAmes> class_names;
    Button pro_btn_save_batch;
    String selected_class , sel_id;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batch);

        shared_preference=new Shared_Preference();

        TextView fab=findViewById(R.id.batchfab);
        recyclerView1=(RecyclerView)findViewById(R.id.recyclerView1);
        batchli= findViewById(R.id.batchli);
        proedt_name_s= findViewById(R.id.proedt_name_s);
        pro_class_spiner= findViewById(R.id.pro_class_spiner);
        pro_btn_save_batch= findViewById(R.id.pro_btn_save_batch);

      new GetAllBatch(Shared_Preference.getId(AddBatchActivity.this)).execute();
      //new GETSPINNERDATA(Shared_Preference.getId(AddBatchActivity.this)).execute();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(batchli.getVisibility() == View.VISIBLE)
//                {
//                    batchli.setVisibility(View.GONE);
//                }else {
//                    batchli.setVisibility(View.VISIBLE);
//                }
                Intent intent=new Intent(AddBatchActivity.this, AddStudentActivity.class);
                startActivity(intent);
                finish();

            }
        });

//        pro_btn_save_batch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             //   new ADDBAtch( new  Shared_Preference().getId(AddBatchActivity.this).toString(),class_names.get(pro_class_spiner.getSelectedItemPosition()).getUserId().toString() ,proedt_name_s.getText().toString()).execute();
//            }
//        });
    }
    private class GetAllBatch extends AsyncTask<String, Void, String> {
//        ProgressDialogs progressDialogs = new ProgressDialogs();
       String userid;
        FlipProgressDialog flipProgressDialog;
       // String Faculty_id;
        private Dialog dialog;


        public GetAllBatch(String i) {
            this.userid = i;
        }

        @Override
        protected void onPreExecute() {
//            flipProgressDialog=   progressDialogs.SetProgress();
//            flipProgressDialog.show(getFragmentManager() , "");
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
                        Toast.makeText(AddBatchActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++)
                        {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String userid = jsonObject.getString("id");
                            String Class = jsonObject.getString("Class");
                            String Batch = jsonObject.getString("Batch");
                            String class_id = jsonObject.getString("class_id");
//
                            batchArrayList.add(new ABBBatch(userid,Class,Batch,"Class","Batch" ,class_id));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
                        recyclerView1.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

                        BatchAdapter batchAdapter = new BatchAdapter(AddBatchActivity.this,batchArrayList);
                        recyclerView1.setAdapter(batchAdapter); // set the Adapter to RecyclerView

                    }
//                    flipProgressDialog.dismiss();

                } catch (JSONException e) {
                    flipProgressDialog.dismiss();
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

//    private class GETSPINNERDATA  extends AsyncTask<String, Void, String> {
//
//        String id;
//
//        public GETSPINNERDATA(String id) {
//            this.id = id;
//        }
//
//
//        @Override
//        protected String doInBackground(String... arg0) {
//
//
//            try {
//
//                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getclass");
//
//                JSONObject postDataParams = new JSONObject();
//
//                postDataParams.put("user_id", id);
//
//                Log.e("postDataParams", postDataParams.toString());
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(15000 /*milliseconds*/);
//                conn.setConnectTimeout(15000 /*milliseconds*/);
//                conn.setRequestMethod("POST");
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
//                JSONObject jsonObject = null;
//                Log.e("PostRegistration ", "for get class  "+result.toString());
//                try {
//
//                    jsonObject = new JSONObject(result);
//                    if(jsonObject.getBoolean("responce")){
//                        //    getotp.setVisibility(View.VISIBLE);
////                        Intent
//                        JSONArray jarr = jsonObject.getJSONArray("data");
//                        class_names = new ArrayList<>();
//                        for (int i=0 ; i<jarr.length() ; i++){
//
//                            String cl_id = jarr.getJSONObject(i).getString("id");
//                            String cl_name = jarr.getJSONObject(i).getString("Class");
//                            class_names.add(new ClassNAmes(cl_id,cl_name));
//                        }
//
//                        final ArrayList <String> list_class = new ArrayList<>();
//                        for (int k=0 ; k<class_names.size() ; k++){
//                            list_class.add(class_names.get(k).getClass_name());
//                        }
//
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddBatchActivity.this, android.R.layout.simple_spinner_item
//                                ,list_class);
//
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        // Apply the adapter to the spinner
//                        pro_class_spiner.setAdapter(adapter);
//                        pro_class_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                selected_class = list_class.get(position);
//                                sel_id =""+class_names.get(position).getUserId();
//                                Log.e("Spinner Selected "," Items >>> _______"+selected_class+" --- "+sel_id);
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parent) {
//
//                            }
//                        });
//                        Log.e("GET CLASS ",">>>>>>>>>>>>>>>>_____________________"+result.toString());
//                        Log.e("GET CLASS ","ARRAY LIST SPINNER MAP ____________________"+class_names+"\n"+list_class);
//
//                    }
//                    else
//                    {
////                        Toast.makeText(AddStudentActivity.this, "Faculty Added", Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(AddStudentActivity.this , AdminActivity.class);
////                        startActivity(intent);
////                        JSONObject massage=jsonObject.getJSONObject("massage");
////
//////                        String mobile=jsonObject.getString("mobile");
////
//////                        Toast.makeText(getApplication(),"Sorry You are not Registerd"+name, Toast.LENGTH_SHORT).show();
////
////                        //Intent intent=new Intent(RegistrationActivity.this, HomePageActivity.class);
////                        //startActivity(intent);
////                        //finish();
//                    }
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

//    private class ADDBAtch   extends AsyncTask<String, Void, String> {
//
//        String user_id;
//        String spin_id;
//        String batch;
//
//        public ADDBAtch(String user_id, String spin_id, String batch) {
//        this.user_id =user_id;
//        this.spin_id =spin_id;
//        this.batch =batch;
//        }
//
//
//        @Override
//        protected String doInBackground(String... arg0) {
//
//
//            try {
//
//                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/add_Batch");
//
//                JSONObject postDataParams = new JSONObject();
//
//                postDataParams.put("user_id", user_id);
//                postDataParams.put("class_id",spin_id);
//                postDataParams.put("batch",batch);
//                Log.e("postDataParams", postDataParams.toString());
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(15000 /*milliseconds*/);
//                conn.setConnectTimeout(15000 /*milliseconds*/);
//                conn.setRequestMethod("POST");
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
//                JSONObject jsonObject = null;
//                Log.e("PostRegistration", result.toString());
//                try {
//
//                    Log.e("AddBatch ",">>>>>>>>>>>>>>>>_____________________"+result);
//                    jsonObject = new JSONObject(result);
//                    if(jsonObject.getBoolean("responce")){
//                        Toast.makeText(AddBatchActivity.this, "Add Batch Successfull", Toast.LENGTH_SHORT).show();
//
//                        Intent intent=new Intent(AddBatchActivity.this,AddBatchActivity.class);
//                        startActivity(intent);
//
//                    }
//                    else
//                    {
////                        Toast.makeText(AddStudentActivity.this, "Faculty Added", Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(AddStudentActivity.this , AdminActivity.class);
////                        startActivity(intent);
////                        JSONObject massage=jsonObject.getJSONObject("massage");
////
//////                        String mobile=jsonObject.getString("mobile");
////
//////                        Toast.makeText(getApplication(),"Sorry You are not Registerd"+name, Toast.LENGTH_SHORT).show();
////
////                        //Intent intent=new Intent(RegistrationActivity.this, HomePageActivity.class);
////                        //startActivity(intent);
////                        //finish();
//                    }
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
