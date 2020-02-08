package com.ics.admin.BasicAdmin.Masters.StudyMaterial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.ics.admin.BasicAdmin.FeesStructure.AddFeesActivity;
import com.ics.admin.CommonJavaClass.AdminProgressdialog;
import com.ics.admin.Model.ClassNAmes;
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
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class EditStudyMaterial extends AppCompatActivity {
    Button select_file,edit_btn;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    public  File MYDOC;
    Spinner editpdfspin;

    private ArrayList<ClassNAmes> class_names = new ArrayList<>();
    EditText alledit;
    int Gallery_view = 2;
    private String selected_class,sel_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_study_material);
        alledit = findViewById(R.id.alledit);
        select_file = findViewById(R.id.select_file);
        editpdfspin = findViewById(R.id.editpdfspin);
        edit_btn = findViewById(R.id.edit_btn);
        new  GetAllCLasses(new Shared_Preference().getId(this)).execute();
        select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( checkAndRequestPermissions())
                {
                    OPENPDFCHOOSER();
                }
            }
        });
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EDITANDUPDATEFILE(getIntent().getStringExtra("id"),getIntent().getStringExtra("class_id"),alledit.getText().toString()).execute();
            }
        });
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

    private void OPENPDFCHOOSER() {
        Intent from_gallery = new Intent();
        from_gallery.setType("application/pdf");
        from_gallery.addCategory(Intent.CATEGORY_OPENABLE);
        from_gallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        from_gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(from_gallery , "Select File"),Gallery_view);
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
//                            selepdftxt.setText("Selected :"+pdfFile.getName());
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
//                            selepdftxt.setText("Selected :"+pdfFile.getName());
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
    private class EDITANDUPDATEFILE extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        String Title;
        String SpinnerId;
        String id, class_id;
        File mydoc;
        String result = "";
        AdminProgressdialog adminProgressdialog;


        public EDITANDUPDATEFILE(String id, String class_id, String Title) {
            this.id = id;
            this.class_id = class_id;
            this.Title = Title;
        }


        @Override
        protected void onPreExecute() {
            adminProgressdialog = new AdminProgressdialog(EditStudyMaterial.this);
            dialog = new ProgressDialog(EditStudyMaterial.this);
            dialog.setMessage("Processing");

            dialog.setCancelable(true);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String r_mob_take = getIntent().getStringExtra("to_the_last_mob");
            String r_e_take = getIntent().getStringExtra("to_the_last_email");




            try {


                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                entity.addPart("title", new StringBody("" + Title));
                entity.addPart("class_id", new StringBody("" + sel_id));
                entity.addPart("id", new StringBody("" +  class_id));
                entity.addPart("pdf_file", new FileBody(MYDOC));

//                    entity.addPart("ret_mobile", new StringBody("" + r_mob));
//                    entity.addPart("reference_referal_code", new StringBody(""+ref_co));

                result = Utilities.postEntityAndFindJson("http://ihisaab.in/school_lms/Adminapi/update_studymaterial", entity);

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
                        Toast.makeText(EditStudyMaterial.this, ""+jsonObject1.getString("data").toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditStudyMaterial.this , StudyMaterialActivity.class);
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
            adminProgressdialog.EndProgress();
        }
    }


    private class GetAllCLasses extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GetAllCLasses(String id) {
            this.id = id;
            //  this.course=course;
        }


        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(EditStudyMaterial.this);
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditStudyMaterial.this, android.R.layout.simple_spinner_item
                                ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        editpdfspin.setAdapter(adapter);
                        editpdfspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
