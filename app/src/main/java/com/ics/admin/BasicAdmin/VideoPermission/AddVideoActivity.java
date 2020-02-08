package com.ics.admin.BasicAdmin.VideoPermission;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ics.admin.BasicAdmin.FeesStructure.AddFeesActivity;
import com.ics.admin.CommonJavaClass.AdminProgressdialog;
import com.ics.admin.Model.ABBCoursemodel;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Utils.Utilities;
import com.marcoscg.dialogsheet.DialogSheet;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class AddVideoActivity extends AppCompatActivity {
    Button capturestartbtn,sendbtn;
    CheckBox sharewithlinkbtn;
    ArrayList <String> list_class = new ArrayList<>();
    int Gallery_view = 2;
    LinearLayout linkli;
    private Uri fileUri;
    EditText studyedt_name_sdesv,link_url;
    public static final int MEDIA_TYPE_VIDEO = 3;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static AddVideoActivity ActivityContext =null;
    String selected_batch , sel_batch;
    File mediaFile;
   TextView fliename;
    private ArrayList<ClassNAmes> class_names;
    private ArrayList<String> batch_names = new ArrayList<>();
    private Spinner class_spiner_assign;
    private ArrayList<ABBCoursemodel> batchArrayList = new ArrayList<ABBCoursemodel>();
    private Spinner class_spiner;
    String selected_class , sel_id;
    private Spinner batch_spin_assign;
    private String video_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_video);
        fliename = findViewById(R.id.fliename);
        studyedt_name_sdesv = findViewById(R.id.studyedt_name_sdesv);
        class_spiner_assign = findViewById(R.id.class_spiner_vid);
        linkli = findViewById(R.id.linkli);
        batch_spin_assign = findViewById(R.id.batch_spiner);
        sharewithlinkbtn = findViewById(R.id.sharewithlinkbtn);
//        videoView.setZOrderOnTop(true);
        ActivityContext = this;
        super.onCreate(savedInstanceState);
        capturestartbtn = findViewById(R.id.capturestartbtn);
        sendbtn = findViewById(R.id.sendbtn);
        link_url = findViewById(R.id.link_url);
        sharewithlinkbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    linkli.setVisibility(View.VISIBLE);
                }else {
                    linkli.setVisibility(View.GONE);
                }
            }
        });
//        sharewithlinkbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                link_url.setVisibility(View.VISIBLE);
//            }
//        });
        new  GETAllClasses(new Shared_Preference().getId(this)).execute();
        capturestartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkAndRequestPermissions()) {
//                    link_url.setVisibility(View.GONE);
                    DialogSheet dialogSheet = new DialogSheet(v.getContext());
                    dialogSheet.setTitle("Choose one")
                            .setColoredNavigationBar(true)
                            .setCancelable(true)
                            .setBackgroundColor(Color.WHITE) // Your custom background color
                            .setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
                            .show();
                    dialogSheet.setView(R.layout._ask_for_how_to_upload);
                    View inflatedView = dialogSheet.getInflatedView();
                    LinearLayout uploadfromsd = inflatedView.findViewById(R.id.uploadfromsd);
                    LinearLayout recordfromli = inflatedView.findViewById(R.id.recordfromli);
                    uploadfromsd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSheet.dismiss();
                            OpenCHooser();
                        }
                    });
                    recordfromli.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSheet.dismiss();
                            startcrecording();
                        }
                    });

                }
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bMap = ThumbnailUtils.createVideoThumbnail(mediaFile.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                    File filesDir = v.getContext().getFilesDir();
                    File imageFile = new File(filesDir, mediaFile.getName() + ".jpg");
                    OutputStream os;
                    try {
                        os = new FileOutputStream(imageFile);
                        bMap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();
                        //++++++++++++++++++++++++++++++++++++++++Get Video timewa+++++++++
                        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                            //use one of overloaded setDataSource() functions to set your data source
                        retriever.setDataSource(AddVideoActivity.this, Uri.fromFile(mediaFile));
                        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                        long timeInMillisec = Long.parseLong(time);
                        retriever.release();
                        video_time = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(timeInMillisec));
                        if(!studyedt_name_sdesv.getText().toString().isEmpty()) {
                            if(class_spiner_assign.getSelectedItemPosition() !=0) {
                                if(batch_spin_assign.getSelectedItemPosition() !=0) {
                                    new OKSENDVIDEO(mediaFile.getName(), sel_batch, sel_id, mediaFile, imageFile).execute();
                                }else {
                                    Toast.makeText(AddVideoActivity.this, "Please select course", Toast.LENGTH_SHORT).show();
                                }
                            }else {

                                Toast.makeText(AddVideoActivity.this, "Please select class", Toast.LENGTH_SHORT).show();
                                class_spiner_assign.callOnClick();
                            }
                        }else {
                            studyedt_name_sdesv.setError("Title can not be empty");
                            Toast.makeText(AddVideoActivity.this, "Title can not be empty", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(AddVideoActivity.this, "No Video found", Toast.LENGTH_SHORT).show();
                        Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                    }

                }catch (Exception e)
                {
                    Toast.makeText(AddVideoActivity.this, "Please select a video", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
//                startcrecording();
            }
        });


    }

    private void OpenCHooser() {
        Intent from_gallery = new Intent();
        from_gallery.setType("video/*");
        from_gallery.addCategory(Intent.CATEGORY_OPENABLE);
        from_gallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        from_gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(from_gallery , "Select File"),Gallery_view);
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

    private void startcrecording() {
        // create new Intentwith with Standard Intent action that can be
        // sent to have the camera application capture an video and return it.
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        // create a file to save the video
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

        // set the image file name
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // set the video image quality to high
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        // start the Video Capture Intent
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
//        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
//        }
    }
    /** Create a file Uri for saving an image or video */
    private Uri getOutputMediaFileUri(int type){

        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(int type){

        // Check that the SDCard is mounted
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraVideo");


        // Create the storage directory(MyCameraVideo) if it does not exist
        if (! mediaStorageDir.exists()){
//            mediaStorageDir.mkdirs();
            if (! mediaStorageDir.mkdirs()){

//                output.setText("Failed to create directory MyCameraVideo.");

                Toast.makeText(ActivityContext, "Failed to create directory MyCameraVideo.",
                        Toast.LENGTH_LONG).show();

                Log.d("MyCameraVideo", "Failed to create directory MyCameraVideo.");

                return null;
            }
        }


        // Create a media file name

        // For unique file name appending current timeStamp with file name
        java.util.Date date= new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());



        if(type == MEDIA_TYPE_VIDEO) {

            // For unique video file name appending current timeStamp with file name
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
            Toast.makeText(ActivityContext, ""+mediaFile.getName(),
                    Toast.LENGTH_LONG).show();
            fliename.setText(mediaFile.getName());
//            videoView.setVideoPath(mediaFile.getAbsolutePath());
        } else {
//            fliename.setText("No video file selected");
            return null;
        }

        return mediaFile;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (requestCode == Gallery_view) {
                Intent data = intent;
                try {
//                Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                    Uri pickedImage = data.getData();
                    File pdfFile = null;
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                        //     getRealPathFromURI(Single_user_act_TRD.this , pickedImage);


//                  pickedImage =   FileProvider.getUriForFile(Single_user_act_TRD.this, BuildConfig.APPLICATION_ID + ".provider",pdfFile);
                        //   pickedImage =   FileProvider.getUriForFile(Single_user_act_TRD.this, getApplicationContext().getPackageName() + ".provider",pdfFile);

                        Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                        cursor.moveToFirst();
                        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                        Log.e("Gallery image path is:", "" + imagePath);

                        pdfFile = new File(imagePath);

                        cursor.close();
                        if (pdfFile != null) {

                            if (pdfFile.exists()) {
                                Log.e("PDF ", "" + pdfFile.exists());
                                //   new ImageUploadPDF(pdfFile).execute();
                                mediaFile = pdfFile;
                                fliename.setText(mediaFile.getName());
//                            selepdftxt.setText("Selected :"+pdfFile.getName());
                                Toast.makeText(this, "Selected " + pdfFile.getName(), Toast.LENGTH_SHORT).show();
                            } else {
//                                fliename.setText("No video file selected");
                                Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                            }
                            //   new ImageCompression().execute(imagePath);
                            //   select_registrationplate.setImageURI(Uri.fromFile(imgFile));
                            //  show(imgFile);
                            // new ImageUploadTask(new File(imagePath)).execute();
                            //   Toast.makeText(ReportSummary.this, "data:=" + imgFile, Toast.LENGTH_LONG).show();
                        }

                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
                        Log.e("Gallery image path is:", "" + imagePath);

                        pdfFile = new File(extStore.getAbsolutePath(), pdf_file_path);

                        cursor.close();
                        if (pdfFile != null) {

                            if (pdfFile.exists()) {
                                Log.e("PDF ", "" + pdfFile.exists());
                                //   new ImageUploadPDF(pdfFile).execute();
                                mediaFile = pdfFile;
                                fliename.setText(mediaFile.getName());
                                Toast.makeText(this, "Selected " + pdfFile.getName(), Toast.LENGTH_SHORT).show();
                            } else {
//                                fliename.setText("No video file selected");
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
                    } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                        Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                        cursor.moveToFirst();
                        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                        Log.e("Gallery image path is:", "" + imagePath);
                        pdfFile = new File(imagePath);
                        if (pdfFile != null) {

                            if (pdfFile.exists()) {
                                Log.e("PDF ", "" + pdfFile.exists());
//                            selepdftxt.setText("Selected :"+pdfFile.getName());
                                //   new ImageUploadPDF(pdfFile).execute();
                                mediaFile = pdfFile;
                                fliename.setText(mediaFile.getName());
                                Toast.makeText(this, "Selected " + pdfFile.getName(), Toast.LENGTH_SHORT).show();
                            } else {
                                fliename.setText("No video file selected");
                                Toast.makeText(this, "Failed to retrieve PDF", Toast.LENGTH_SHORT).show();
                            }
                            //   new ImageCompression().execute(imagePath);
                            //   select_registrationplate.setImageURI(Uri.fromFile(imgFile));
                            //  show(imgFile);
                            // new ImageUploadTask(new File(imagePath)).execute();
                            //   Toast.makeText(ReportSummary.this, "data:=" + imgFile, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Please upload from SD card only", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Not getting file from phone please upload valid .pdf", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("Video File : ", "" + intent.getData());

                // Video captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Video saved to: " + intent.getData(), Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == RESULT_CANCELED) {


            Log.e("Video File : ", "Cancleded");

            // User cancelled the video capture
//            Toast.makeText(this, "User cancelled the video capture.",
//                    Toast.LENGTH_LONG).show();
            fliename.setText("No video file selected");

        } else {
            fliename.setText("No video file selected");
            Log.e("Video File : ", "failed");


            // Video capture failed, advise user
            Toast.makeText(this, "Video capture failed.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private class OKSENDVIDEO extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        String Title;
        String batch_id,class_id;
        String result = "";
        File file;
        File bMap;
        AdminProgressdialog adminProgressdialog;

        public OKSENDVIDEO(String name, String sel_batch, String sel_id, File mediaFile, File bMap) {
            this.Title = name;
            this.batch_id = sel_batch;
            this.class_id = sel_id;
            this.Title = name;
            this.file = mediaFile;
            this.bMap = bMap;
        }

        @Override
        protected void onPreExecute() {
            adminProgressdialog = new AdminProgressdialog(AddVideoActivity.this);
            super.onPreExecute();
        }

//        @Override
//        protected void onPreExecute() {
//            dialog = new ProgressDialog(AddVideoActivity.this);
//            dialog.setMessage("Processing");
//
//            dialog.setCancelable(true);
//            dialog.show();
//            dialog.setCanceledOnTouchOutside(false);
//            super.onPreExecute();
//        }

        @Override
        protected String doInBackground(Void... params) {

            String r_mob_take = getIntent().getStringExtra("to_the_last_mob");
            String r_e_take = getIntent().getStringExtra("to_the_last_email");
            try {
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                entity.addPart("title", new StringBody("" + Title));
                entity.addPart("class_id", new StringBody("" + class_id));
                entity.addPart("course_id", new StringBody("" + batch_id));
                entity.addPart("video_time", new StringBody("" + video_time));
                entity.addPart("video_url", new StringBody("" + link_url.getText().toString()));
                entity.addPart("description", new StringBody("" + studyedt_name_sdesv.getText().toString()));
                entity.addPart("school_id", new StringBody("" + new Shared_Preference().getId(AddVideoActivity.this)));
                entity.addPart("video", new FileBody(mediaFile));
                entity.addPart("video_image", new FileBody(bMap));
//                entity.addPart("video_image", new FileBody(mydoc));

//                    entity.addPart("ret_mobile", new StringBody("" + r_mob));
//                    entity.addPart("reference_referal_code", new StringBody(""+ref_co));

                result = Utilities.postEntityAndFindJson("http://ihisaab.in/school_lms/api/add_video", entity);

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
//                dialog.dismiss();
                Log.e("result1", result1);
                try {
                    JSONObject jsonObject1  = new JSONObject(result1);
                    if(jsonObject1.getBoolean("responce")){
                        Toast.makeText(AddVideoActivity.this, "Video Added Successfully", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(AddVideoActivity.this, ""+jsonObject1.getString("data").toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddVideoActivity.this , VideosViewActivity.class);
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
//                dialog.dismiss();
//                Toast.makeText(Single_user_act_TRD.this, "Some Problem", Toast.LENGTH_LONG).show();
            }
            adminProgressdialog.EndProgress();
        }
    }

    private class GETAllClasses extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GETAllClasses(String id) {
            this.id = id;
            //  this.course=course;
            this.class_id=class_id;
        }

        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(AddVideoActivity.this);
            super.onPreExecute();
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

                        list_class.add("--Select Class--");
                        for (int k=0 ; k<class_names.size() ; k++){
                            list_class.add(class_names.get(k).getClass_name());
                        }

                    }
                    else
                    {
                        list_class.add("--Select Class--");

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddVideoActivity.this, android.R.layout.simple_spinner_item
                            ,list_class);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    class_spiner_assign.setAdapter(adapter);
                    class_spiner_assign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position ==0) {
//                                selected_class = list_class.get(position);
                                sel_id = "";
                                Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                batch_names.clear();
                                batchArrayList.clear();
                                new GETAllBathces(new Shared_Preference().getId(AddVideoActivity.this), sel_id).execute();
                            }else {
                                selected_class = list_class.get(position);
                                sel_id = class_names.get(position-1).getUserId();
                                Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                batch_names.clear();
                                batchArrayList.clear();
                                new GETAllBathces(new Shared_Preference().getId(AddVideoActivity.this), sel_id).execute();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    Log.e("GET CLASS ",">>>>>>>>>>>>>>>>_____________________"+result.toString());
                    Log.e("GET CLASS ","ARRAY LIST SPINNER MAP ____________________"+class_names+"\n"+list_class);


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


        public GETAllBathces(String i, String sel_id) {
            this.userid = i;
            this.calls_id = sel_id;
        }
        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(AddVideoActivity.this);
            super.onPreExecute();
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
                    batch_names.add("--Select Course--");
                    jsonObject1 = new JSONObject(result);
                    if(!jsonObject1.getBoolean("responce")){
                        Toast.makeText(AddVideoActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                        batch_names.clear();
                        batchArrayList.clear();
//                        batch_names.clear();
                        batch_spin_assign.setAdapter(null);
                        batch_names.add("--Select Course--");
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
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddVideoActivity.this, android.R.layout.simple_spinner_item
                            ,batch_names);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    batch_spin_assign.setAdapter(adapter);
                    batch_spin_assign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(position !=0) {
                                    selected_batch = batch_names.get(position-1);
                                    sel_batch = "" + batchArrayList.get(position-1).getUserId();
                                    Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {

//                    batch_spin_assign.remo
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

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}
