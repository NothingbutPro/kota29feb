package com.ics.admin.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.AdminMenuExpandableAdapter;
import com.ics.admin.Adapter.SliderAdapter;
import com.ics.admin.BasicAdmin.AdminActivity;
import com.ics.admin.Model.MenuPermisssion;
import com.ics.admin.Model.SubMenuPermissions;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class CommunityFragment extends Fragment {
    SliderView imageSlider;
    private SwipeRefreshLayout swipeContainer;
 public List<String> _MenuPermisssionslistDataHeader = new ArrayList<>();
    ArrayList<SubMenuPermissions> menuPermissionsSubList= new ArrayList<>();
    ArrayList<String> menuPermissionsSubListString= new ArrayList<>();
    public ArrayList<MenuPermisssion> menuPermisssionheaderListStrings;
    ArrayList<String>menuPermisssionheaderList = new ArrayList<>();
    public HashMap<SubMenuPermissions, List<SubMenuPermissions>> _ListHashMaplistDataChild = new HashMap<>();
    private AdminMenuExpandableAdapter menuExpandableAdapter;
    RecyclerView admin_recyclerView;
    Shared_Preference shared_preference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_community, container, false);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                // To keep animation for 4 seconds
                menuPermissionsSubList.clear();
                new GETALLMYPERMISSIONS(shared_preference.getId(getActivity())).execute();

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeContainer.setRefreshing(false);
                    }
                }, 4000); // Delay in millis
            }
        });
        imageSlider=(SliderView)view.findViewById(R.id.imageSlider);
        admin_recyclerView=(RecyclerView)view.findViewById(R.id.admin_recyclerView);
        shared_preference=new Shared_Preference();
        menuPermissionsSubList.clear();
        new GETALLMYPERMISSIONS(shared_preference.getId(getActivity())).execute();
        SliderAdapter adapter;
        adapter = new SliderAdapter(getActivity());
        imageSlider.setSliderAdapter(adapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorVisibility(false);
        //imageSlider.setIndicatorSelectedColor(Color.WHITE);
        //imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(3); //set scroll delay in seconds :
        imageSlider.startAutoCycle();

        return view;
    }
    private class GETALLMYPERMISSIONS extends AsyncTask<String, Void, String> {
        String Faculty_id;
        private Dialog dialog;

        public GETALLMYPERMISSIONS(String Faculty_id) {
            this.Faculty_id = Faculty_id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getsidemenu");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("user_id", OTPActivity.Faculty_id);
                postDataParams.put("user_id",Faculty_id );
//                postDataParams.put("teacher_id", "4");


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

                    jsonObject = new JSONObject(result);
                    if(!jsonObject.getBoolean("responce")){
//                       Intent intent = new Intent(OTPActivity.this , LoginActivity.class);
//                       startActivity(intent);
                        Toast.makeText(getActivity(),"You are not registerd"+result, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String menu_name =null;
                        SubMenuPermissions sUbMenuModel = null;
                        for(int i=0;i<jsonObject.getJSONObject("data").length();i++)
                        {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("a"+i);

                            for (int px=0;px<jsonArray.length();px++)
                            {
                                String menu_id = jsonArray.getJSONObject(px).getString("menu_id");
                                menu_name = jsonArray.getJSONObject(px).getString("menu_name");
                                String submenu = jsonArray.getJSONObject(px).getString("submenu");
                                sUbMenuModel = new SubMenuPermissions(menu_id,menu_name,submenu);

//                                menuPermissionsSubListString.
                                if(px==0) {
                                    menuPermissionsSubList.add(sUbMenuModel);
//                                    _MenuPermisssionslistDataHeader.add(menu_name);

                                }

                            }
//                            _ListHashMaplistDataChild.put(sUbMenuModel,menuPermissionsSubList);

                        }
//                        Log.e("full hash map",""+_ListHashMaplistDataChild.entrySet());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);

                        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        admin_recyclerView.setLayoutManager(gridLayoutManager);
                        menuExpandableAdapter = new AdminMenuExpandableAdapter(getActivity(), menuPermissionsSubList);
                        admin_recyclerView.setAdapter(menuExpandableAdapter);
                        menuExpandableAdapter.notifyDataSetChanged();
                        admin_recyclerView.requestLayout();
//                        for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
//                        {
//                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
//                            String  permission_id =jsonObject1.getString("permission_id");
//                            String  menu_id =jsonObject1.getString("menu_id");
//                            String  menu_name =jsonObject1.getString("menu_name");
//                            String  status =jsonObject1.getString("status");
//                            if(i<5)
//                            {
//                                MenuItem fact1 = navigationView.findViewById(R.id.nav_home);
//                                fact1.setTitle(""+menu_name);
//
//                            }
////                            menuPermisssionList.add(new MenuPermisssion(permission_id,menu_id,menu_name,status));
////sdf
//                        }
                    }


                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Timed out from server ", Toast.LENGTH_SHORT).show();
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

    private class Verifyotp extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        private Dialog dialog;


        public Verifyotp(String toString, String s) {

            this.Mobile_Number = toString;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getmenulist");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id", Mobile_Number);
//                postDataParams.put("mobile", mobile.getText().toString());


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

                    jsonObject = new JSONObject(result);
                    if(!jsonObject.getBoolean("responce")){
//                        getotp.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        String type = jsonObject.getJSONObject("data").getString("type");
                        String Faculty_id = jsonObject.getJSONObject("data").getString("type");
                        if(type.equals("1")) {
                            Intent intent1 = new Intent(getActivity(), AdminActivity.class);
                            startActivity(intent1);
                           // finish();
                        }else {
                            Intent intent1 = new Intent(getActivity(), AdminActivity.class);
                            startActivity(intent1);
                            //finish();
                        }
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

//+++++++++++++++++++++++++++++++++++++++++++++++Default static
//public static class  DELETStuff extends AsyncTask<String, Void, String> {
//    String user_id;
//    String newclassname;
//    private Dialog dialog;
//
//    Activity activity;
//    String urls;
////    public void DeleteDialog(Activity activity) {
////
////        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
////        builder1.setMessage("Write your message here.");
////        builder1.setCancelable(true);
////
////        builder1.setPositiveButton(
////                "Yes",
////                new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        dialog.cancel();
//////                        del_or_not = 1;
////                    }
////                });
////
////        builder1.setNegativeButton(
////                "No",
////                new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        dialog.cancel();
//////                        del_or_not = 2;
////                    }
////                });
////
////        AlertDialog alert11 = builder1.create();
////        alert11.show();
////
////    }
//
//    public DELETStuff(String Del_ids ,Activity activity, String user_id, String url)
//    {
//        this.user_id = user_id;
//        this.activity = activity;
//        this.urls = url;
//        this.newclassname = Del_ids;
//
//    }
//
//
//    @Override
//    protected String doInBackground(String... arg0) {
//
//        try {
//
//            URL url = new URL(urls);
//
//            JSONObject postDataParams = new JSONObject();
////                postDataParams.put("otp", Mobile_Number);
//            postDataParams.put(newclassname, user_id);
//            Log.e("postDataParams", postDataParams.toString());
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(15000 /*milliseconds*/);
//            conn.setConnectTimeout(15000 /*milliseconds*/);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getPostDataString(postDataParams));
//
//            writer.flush();
//            writer.close();
//            os.close();
//
//            int responseCode = conn.getResponseCode();
//
//            if (responseCode == HttpsURLConnection.HTTP_OK) {
//
//                BufferedReader in = new BufferedReader(new
//                        InputStreamReader(
//                        conn.getInputStream()));
//
//                StringBuffer sb = new StringBuffer("");
//                String line = "";
//
//                while ((line = in.readLine()) != null) {
//
//                    StringBuffer Ss = sb.append(line);
//                    Log.e("Ss", Ss.toString());
//                    sb.append(line);
//                    break;
//                }
//
//                in.close();
//                return sb.toString();
//
//            } else {
//                return new String("false : " + responseCode);
//            }
//        } catch (Exception e) {
//            return new String("Exception: " + e.getMessage());
//        }
//
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        if (result != null) {
////                dialog.dismiss();
//
//            JSONObject jsonObject1 = null;
//            Log.e("PostRegistration", result.toString());
//            try {
//
//                jsonObject1 = new JSONObject(result);
//                if(jsonObject1.getBoolean("responce")){
//                    Intent intent = new Intent(activity , activity.getClass());
//                    activity.startActivity(intent);
//                    activity.finish();
////                        Toast.makeText(ClassViewActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
////                        getotp.setVisibility(View.VISIBLE);
////                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
//                }else {
//
//
//                }
//
//
//            } catch (JSONException e) {
//
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    public String getPostDataString(JSONObject params) throws Exception {
//
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//
//        Iterator<String> itr = params.keys();
//
//        while (itr.hasNext()) {
//
//            String key = itr.next();
//            Object value = params.get(key);
//
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(key, "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
//
//        }
//        return result.toString();
//    }
//}

    //++++++++++++++
}
