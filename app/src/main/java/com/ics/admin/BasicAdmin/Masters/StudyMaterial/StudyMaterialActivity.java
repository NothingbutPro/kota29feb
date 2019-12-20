package com.ics.admin.BasicAdmin.Masters.StudyMaterial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.ics.admin.Adapter.AdminAdapters.StudyMaterialAdapter;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.Model.StudyMaterials;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Utils.Utilities;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.ics.admin.Adapter.AdminAdapters.StudyMaterialAdapter.select_file;

public class  StudyMaterialActivity extends AppCompatActivity {
    RecyclerView studymaterialri;
    StudyMaterialAdapter studyMaterialAdapter;
    public  File MYDOC;
    EditText studyedt_name_s;
    Button studybtn_save_s;
    LinearLayout materialli;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    TextView selepdftxt;
    ImageButton pdfselect;
    int Gallery_view = 2;
    FloatingActionButton materialsubject_fab;
    ArrayList<StudyMaterials> materialsArrayList = new ArrayList<>();
    private ArrayList<ClassNAmes> class_names = new ArrayList<>();
    private Spinner class_spiner;
    String selected_class , sel_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);
        studymaterialri = findViewById(R.id.studymaterialri);
        studyedt_name_s = findViewById(R.id.studyedt_name_s);
        studybtn_save_s = findViewById(R.id.studybtn_save_s);
        selepdftxt = findViewById(R.id.selepdftxt);
        materialli = findViewById(R.id.materialli);
        pdfselect = findViewById(R.id.pdfselect);
        class_spiner = findViewById(R.id.class_spiner);
        new GETALLCLASSES(new Shared_Preference().getId(this)).execute();
        studybtn_save_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!studyedt_name_s.getText().toString().isEmpty() && MYDOC !=null)
                {
                    if(sel_id !=null) {
                        try{
                            new ImageUploadTask(studyedt_name_s.getText().toString(), class_names.get(class_spiner.getSelectedItemPosition()-1).getUserId(), MYDOC).execute();
                        }catch (Exception e)
                        {
                            new ImageUploadTask(studyedt_name_s.getText().toString(), class_names.get(0).getUserId(), MYDOC).execute();
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(StudyMaterialActivity.this, "Please select class first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        select_file.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if( checkAndRequestPermissions())
//                {
//                    OPENPDFCHOOSER();
//                }
//            }
//        });
        pdfselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if( checkAndRequestPermissions())
              {
                 OPENPDFCHOOSER();
              }
            }
        });
        materialsubject_fab = findViewById(R.id.materialsubject_fab);
        materialsubject_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(materialli.getVisibility() == View.VISIBLE)
                {
                    materialli.setVisibility(View.GONE);

                }
                else
                    {
                    materialli.setVisibility(View.VISIBLE);
                }
            }
        });
        new GETALLSTUDYMATERIALS(new Shared_Preference().getId(this)).execute();
    }

    private void OPENPDFCHOOSER() {
        Intent  from_gallery = new Intent();
//        from_gallery.setType("application/pdf");
        from_gallery.setType("*/*");
        from_gallery.addCategory(Intent.CATEGORY_OPENABLE);
        from_gallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        from_gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(from_gallery , "Select File"),Gallery_view);
    }


    //*******************************************************************
    private  boolean checkAndRequestPermissions() {

        int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionStorage1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        // int permissionAnswerCall = ContextCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionStorage1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
//    if (permissionAnswerCall != PackageManager.PERMISSION_GRANTED) {
//        listPermissionsNeeded.add(Manifest.permission.ANSWER_PHONE_CALLS);
//    }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }
    //**********************************************************************


    private class GETALLSTUDYMATERIALS  extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String batch;

        public GETALLSTUDYMATERIALS(String id) {
           this.id = id;
        }


        @Override
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/studymateriallist");

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
                        Toast.makeText(StudyMaterialActivity.this, "Geting Data", Toast.LENGTH_SHORT).show();
                        for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
                        {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String Class = jsonObject1.getString("Class");
                            String title = jsonObject1.getString("title");
                            String class_id = jsonObject1.getString("class_id");

                            String pdf_file = jsonObject1.getString("pdf_file");
//                            String id = jsonObject1.getString("id");
//                            String id = jsonObject1.getString("id");

                            materialsArrayList.add(new StudyMaterials(id,Class,title,pdf_file,class_id));

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(StudyMaterialActivity.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        studyMaterialAdapter = new StudyMaterialAdapter(StudyMaterialActivity.this , materialsArrayList);
                        studymaterialri.setLayoutManager(layoutManager);
                        studymaterialri.setAdapter(studyMaterialAdapter);


                    }
                    else
                    {
                        Toast.makeText(StudyMaterialActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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

    //++++++++++++++++++++++++++++++++++++++++++For PDF+++++++++++++++++++++
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Gallery_view && data != null) {
            try{
//                Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                Uri pickedImage = data.getData();
                File pdfFile = null;
                String[] filePath = { MediaStore.Images.Media.DATA };
                if(Build.VERSION.SDK_INT == Build.VERSION_CODES.N){
                    //     getRealPathFromURI(Single_user_act_TRD.this , pickedImage);


//                  pickedImage =   FileProvider.getUriForFile(Single_user_act_TRD.this, BuildConfig.APPLICATION_ID + ".provider",pdfFile);
                    //   pickedImage =   FileProvider.getUriForFile(Single_user_act_TRD.this, getApplicationContext().getPackageName() + ".provider",pdfFile);

                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.e("Gallery image path is:" , ""+imagePath);

                    pdfFile = new File(imagePath);

                    cursor.close();
                    if (pdfFile!=null) {

                        if(pdfFile.exists()){
                            Log.e("PDF ",""+pdfFile.exists());
                            //   new ImageUploadPDF(pdfFile).execute();
                            MYDOC = pdfFile;
                            selepdftxt.setText("Selected :"+pdfFile.getName());
                            Toast.makeText(this, "Selected "+pdfFile.getName(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                        }
                        //   new ImageCompression().execute(imagePath);
                        //   select_registrationplate.setImageURI(Uri.fromFile(imgFile));
                        //  show(imgFile);
                        // new ImageUploadTask(new File(imagePath)).execute();
                        //   Toast.makeText(ReportSummary.this, "data:=" + imgFile, Toast.LENGTH_LONG).show();
                    }

                }
                else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    File extStore = Environment.getExternalStorageDirectory();
//                    File myFile = new File(extStore.getAbsolutePath() + "/book1/page2.html");
                    String pdf_file_path;
                    Uri uri = data.getData();
                    pdfFile = new File(uri.getPath());//create path from uri
                    final String[] split = pdfFile.getPath().split(":");//split the path.
                    pdf_file_path = split[1];
                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.e("Gallery image path is:" , ""+imagePath);

                    pdfFile = new File(extStore.getAbsolutePath(),pdf_file_path);

                    cursor.close();
                    if (pdfFile!=null) {

                        if (pdfFile.exists()) {
                            Log.e("PDF ", "" + pdfFile.exists());
                            //   new ImageUploadPDF(pdfFile).execute();
                            MYDOC = pdfFile;
                            Toast.makeText(this, "Selected "+pdfFile.getName(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                        }
                    }

//                    File extStore = Environment.getExternalStorageDirectory();
////                    File myFile = new File(extStore.getAbsolutePath() + "/book1/page2.html");
//                    String pdf_file_path;
//                    Uri uri = data.getData();
//                    pdfFile = new File(uri.getPath());//create path from uri
//                    final String[] split = pdfFile.getPath().split(":");//split the path.
//                    pdf_file_path = split[0];
//                    String path = data.uri.toString() // "file:///mnt/sdcard/FileName.mp3"
////                    File file = new File(new URI(path));
//                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
//                    cursor.moveToFirst();
//                    String imagePath = cursor.getString(cursor.getColumnIndex(pdf_file_path));
//                    Log.e("Gallery image path is:" , ""+path);
//                    pdfFile = new File(extStore.getAbsolutePath(),path);
////                    pdfFile = new File(imagePath);
//                    cursor.close();
//                    if (pdfFile!=null) {
//                        if (pdfFile.exists()) {
//                            Log.e("PDF ", "" + pdfFile.exists());
//                            //   new ImageUploadPDF(pdfFile).execute();
//                            MYDOC = pdfFile;
//                            selepdftxt.setText("Selected :"+pdfFile.getName());
//                        }else {
//                            Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
//                        }
//                    }
                } else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                    Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    Log.e("Gallery image path is:" , ""+imagePath);
                    pdfFile = new File(imagePath);
                    if (pdfFile!=null) {

                        if(pdfFile.exists()){
                            Log.e("PDF ",""+pdfFile.exists());
                            selepdftxt.setText("Selected :"+pdfFile.getName());
                            //   new ImageUploadPDF(pdfFile).execute();
                            MYDOC = pdfFile;
                            Toast.makeText(this, "Selected "+pdfFile.getName(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, "Failed to retrieve PDF", Toast.LENGTH_SHORT).show();
                        }
                        //   new ImageCompression().execute(imagePath);
                        //   select_registrationplate.setImageURI(Uri.fromFile(imgFile));
                        //  show(imgFile);
                        // new ImageUploadTask(new File(imagePath)).execute();
                        //   Toast.makeText(ReportSummary.this, "data:=" + imgFile, Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                    }
                }


            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Not getting file from phone please upload valid .pdf", Toast.LENGTH_SHORT).show();
            }

        }
    }
//++++++++++++++++++++++++++++++Enddddddddddddd
    private class GETALLCLASSES extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GETALLCLASSES(String id) {
            this.id = id;
            //  this.course=course;
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
                        for (int i=0 ; i<jarr.length() ; i++)
                        {

                            String cl_id = jarr.getJSONObject(i).getString("id");
                            String cl_name = jarr.getJSONObject(i).getString("Class");
                            class_names.add(new ClassNAmes(cl_id,cl_name));
                        }

                        final ArrayList <String> list_class = new ArrayList<>();
                        list_class.add("--Select Class---");
                        for (int k=0 ; k<class_names.size() ; k++){
                            list_class.add(k+1,class_names.get(k).getClass_name());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudyMaterialActivity.this, android.R.layout.simple_spinner_item
                                ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_spiner.setAdapter(adapter);
                        class_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(position ==0) {
                                    selected_class = list_class.get(position);
                                }else {

                                        selected_class = list_class.get(position);
                                        sel_id = class_names.get(position-1).getUserId();

                                }

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
    //++++++++++++++++
    //-----------------------------------------------------------------------------------------------------------------------------
    class ImageUploadTask extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        String Title;
        String SpinnerId;
        File mydoc;
        String result = "";


        public ImageUploadTask(String Title, String SpinnerId, File mydoc) {
            this.Title = Title;
            this.SpinnerId = SpinnerId;
            this.mydoc = mydoc;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(StudyMaterialActivity.this);
            dialog.setMessage("Processing");

            dialog.setCancelable(true);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {


                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                    entity.addPart("title", new StringBody("" + Title));
                    entity.addPart("class_id", new StringBody("" + SpinnerId));
                    entity.addPart("user_id", new StringBody("" + new Shared_Preference().getId(StudyMaterialActivity.this)));
                    entity.addPart("pdf_file", new FileBody(mydoc));

                     result = Utilities.postEntityAndFindJson("http://ihisaab.in/school_lms/Adminapi/add_studymaterial", entity);

                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            String result1 = result;
            if (result1 != null) {
                dialog.dismiss();
                Log.e("result1", result1);
                try {
                    JSONObject jsonObject1  = new JSONObject(result1);
                    if(jsonObject1.getBoolean("responce")){
                        Toast.makeText(StudyMaterialActivity.this, ""+jsonObject1.getString("data").toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudyMaterialActivity.this , StudyMaterialActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(Single_user_act_TRD.this, "Documents Uploaded Successfully ", Toast.LENGTH_LONG).show();

                //   Intent in=new Intent(MainActivity.this,NextActivity.class);
                //  in.putExtra("doc",doc);
                //     startActivity(in);

            } else {
                dialog.dismiss();
//                Toast.makeText(Single_user_act_TRD.this, "Some Problem", Toast.LENGTH_LONG).show();
            }

        }
    }
}
