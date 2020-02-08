package com.ics.admin.BasicAdmin.HomeWork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.FacultyAdapter;
import com.ics.admin.Adapter.AdminAdapters.StudentAdapter;
import com.ics.admin.BasicAdmin.Enquiry.ViewEnquiryActivity;
import com.ics.admin.BasicAdmin.FeesStructure.AddFeesActivity;
import com.ics.admin.BasicAdmin.Masters.StudyMaterial.EditStudyMaterial;
import com.ics.admin.BasicAdmin.Masters.StudyMaterial.StudyMaterialActivity;
import com.ics.admin.BasicAdmin.SelectFacultyActivity;
import com.ics.admin.BasicAdmin.StudentDetails.AssignStudentActivity;
import com.ics.admin.CommonJavaClass.AdminProgressdialog;
import com.ics.admin.Fragment.FacultyFragment;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.Model.Faculties;
import com.ics.admin.Model.Students;
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
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HomeWorkActivity extends AppCompatActivity {
     TextView selectdatae,homework;
      EditText dayshome;
     LinearLayout dateli,dtechli;
     public static TextView selectteacher;
     public static String selectteacherStrings ,teacher_id;
     Button give_work_btn;
    String selected_class , sel_id;
    Spinner batch_spin_assign,worktype;
    String selected_batch , sel_batch;
    Button viewhomes,upload_pdf;
    RecyclerView facultyrec;
    String dates;
    //++++++++++++++++++++++++++++For PDF Upload
    int Gallery_view = 2;
    public  File MYDOC;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    //++++++++++++++++++++++++++
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
        upload_pdf= findViewById(R.id.upload_pdf);
        viewhomes= findViewById(R.id.viewhomes);
        dayshome= findViewById(R.id.dayshome);
        selectteacher= findViewById(R.id.selectteacher);
        selectdatae= findViewById(R.id.selectdatae);
        selectteacher= findViewById(R.id.selectteacher);
        batch_spin_assign= findViewById(R.id.class_batch_spinner);
        class_spiner_assign= findViewById(R.id.class_home_spinner);
        new GETCLASSFORHOMe(new Shared_Preference().getId(this)).execute();
        dayshome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int  mDay   = c.get(Calendar.DAY_OF_MONTH);
                //launch datepicker modal
                Toast.makeText(HomeWorkActivity.this, "Getting sub", Toast.LENGTH_SHORT).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                selectdatae.setText("");
                                dayshome.setText("");
                                Log.d("LOG_APP", "DATE SELECTED "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                dates = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
//                                selectdatae.setText(String.valueOf(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                                dayshome.setText(String.valueOf(dates));
                                //PUT YOUR LOGING HERE
                                //UNCOMMENT THIS LINE TO CALL TIMEPICKER
                                //openTimePicker();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkAndRequestPermissions()) {
                    getOpenFileChooserPdf();
                }
            }
        });
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
                        if(MYDOC ==null) {
                            new POSTHOMEWORK(selected_class, selected_batch, sel_id, sel_batch, dates, selectteacherStrings, homework.getText().toString(), worktype.getSelectedItem().toString(), dayshome.getText().toString()).execute();
                        }else {
                            new POSTWITHPDF(selected_class, selected_batch, sel_id, sel_batch, dates, selectteacherStrings, homework.getText().toString(), worktype.getSelectedItem().toString(), dayshome.getText().toString()).execute();
                        }
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
                                selectdatae.setText("");
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

    private void getOpenFileChooserPdf() {
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

    private boolean checkAndRequestPermissions() {
        int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionStorage1 = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int permissionStorage3 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionStorage4 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // int permissionAnswerCall = ContextCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }if (permissionStorage1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (permissionStorage3 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }if (permissionStorage4 != PackageManager.PERMISSION_GRANTED) {
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

    private class GETCLASSFORHOMe extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;
        AdminProgressdialog adminProgressdialog;
        public GETCLASSFORHOMe(String id) {
            this.id = id;
            //  this.course=course;
            this.class_id=class_id;
        }

        @Override
        protected void onPreExecute() {
            adminProgressdialog  = new AdminProgressdialog(HomeWorkActivity.this);
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
                                    new GETAllBathces(new Shared_Preference().getId(HomeWorkActivity.this),"").execute();
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
                        adminProgressdialog.EndProgress();
                    }
                    else
                    {
                        adminProgressdialog.EndProgress();
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

    private class GETAllBathces extends AsyncTask<String, Void, String> {

        String userid;
        String calls_id;
        // String Faculty_id;
        private Dialog dialog;
        AdminProgressdialog adminProgressdialog;


        public GETAllBathces(String i, String sel_id) {
            this.userid = i;
            this.calls_id = sel_id;
        }

        @Override
        protected void onPreExecute() {
            adminProgressdialog = new AdminProgressdialog(HomeWorkActivity.this);
            super.onPreExecute();
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
                        batch_names.add("--Select Batch--");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeWorkActivity.this, android.R.layout.simple_spinner_item
                                ,batch_names);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        batch_spin_assign.setAdapter(adapter);
                        adminProgressdialog.EndProgress();
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

                        adminProgressdialog.EndProgress();
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

                } catch (JSONException e) {
                    adminProgressdialog.EndProgress();
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
        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(HomeWorkActivity.this);
            super.onPreExecute();
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
                        adminProgressdialog.EndProgress();
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
                        adminProgressdialog.EndProgress();
                    }


                } catch (JSONException e) {
                    adminProgressdialog.EndProgress();
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

        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(HomeWorkActivity.this);
            super.onPreExecute();
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
                        adminProgressdialog.EndProgress();
                        Intent intent = new Intent(HomeWorkActivity.this ,ViewWorkActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    adminProgressdialog.EndProgress();
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

    private class POSTWITHPDF extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        String Title;
        String SpinnerId;
        String id, class_id;
        File mydoc;
        String result = "";
        String Mobile_Number;
        String Faculty_id;
        private String batch_id;
        private String work_date;
        private String school_id;
        private String teachers_id;
        private String homeworkstr;
        private String work_type;
        private String daysforwok;

        public POSTWITHPDF(String selected_class, String selected_batch, String sel_id, String sel_batch, String dates, String selectteacherStrings, String home, String work, String days) {
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
        protected void onPreExecute() {
            dialog = new ProgressDialog(HomeWorkActivity.this);
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

                entity.addPart("class_id", new StringBody("" + class_id));
                entity.addPart("batch_id", new StringBody("" + batch_id));
                entity.addPart("work_date", new StringBody("" +  work_date));
                entity.addPart("school_id", new StringBody("" +  school_id));
                entity.addPart("teacher_id", new StringBody("" +  teacher_id));
                entity.addPart("homework", new StringBody("" +  homework.getText().toString()));
                entity.addPart("work_type", new StringBody("" +  work_type));
                entity.addPart("daysforwok", new StringBody("" +  daysforwok));
                if(MYDOC !=null) {
                    entity.addPart("pdf_file", new FileBody(MYDOC));
                }else {
                    entity.addPart("pdf_file", new StringBody(""));
                }
//                    entity.addPart("ret_mobile", new StringBody("" + r_mob));
//                    entity.addPart("reference_referal_code", new StringBody(""+ref_co));

                result = Utilities.postEntityAndFindJson("http://ihisaab.in/school_lms/api/add_homework", entity);

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
//                        Toast.makeText(HomeWorkActivity.this, ""+jsonObject1.getString("data").toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeWorkActivity.this , StudyMaterialActivity.class);
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
