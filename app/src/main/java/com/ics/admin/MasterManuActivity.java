package com.ics.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Adapter.SliderAdapter;
import com.ics.admin.Adapter.SubMenuAdapter;
import com.ics.admin.Adapter.SubPermissionAdapter;
import com.ics.admin.Model.SubMenuPermissions;
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
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MasterManuActivity extends AppCompatActivity {
    SliderView imageSlider;
    private RecyclerView subrec;
    TextView allsubname;
    public static ArrayList<String> Myproducttiles = new ArrayList<>();
    public List<SubMenuPermissions> menuPermisssionList;
    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
    private Context mContext;
    private SubMenuPermissions menuPermisssion;
    ArrayList<SubMenuPermissions> menuPermissionsSubList= new ArrayList<>();
    private SubMenuAdapter menuExpandableAdapter;
    String menuid;
    Shared_Preference shared_preference;
    RecyclerView admin_recyclerView;
    private String submenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_manu);

        shared_preference = new Shared_Preference();
        mContext = this;
        imageSlider = (SliderView) findViewById(R.id.imageSlider);
        allsubname = (TextView) findViewById(R.id.allsubname);
        admin_recyclerView = (RecyclerView) findViewById(R.id.admin_recyclerView);
        SliderAdapter adapter;
        allsubname.setText(getIntent().getStringExtra("ids"));
        new GETALLMYPERMISSIONS(shared_preference.getId(MasterManuActivity.this)).execute();
        adapter = new SliderAdapter(getApplication());
        imageSlider.setSliderAdapter(adapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorVisibility(false);
        //imageSlider.setIndicatorSelectedColor(Color.WHITE);
        //imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(3); //set scroll delay in seconds :
        imageSlider.startAutoCycle();

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplication(),2, LinearLayoutManager.VERTICAL,false);
//
//        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        admin_recyclerView.setLayoutManager(gridLayoutManager);
//        menuExpandableAdapter = new AdminMenuExpandableAdapter(getApplication(), menuPermissionsSubList);
//        admin_recyclerView.setAdapter(menuExpandableAdapter);
//        menuExpandableAdapter.notifyDataSetChanged();
//        admin_recyclerView.requestLayout();
    }

        private class GETALLMYPERMISSIONS  extends AsyncTask<String, Void, String> {
            String Faculty_id;
            String position;
            private Dialog dialog;

            public GETALLMYPERMISSIONS(String faculty_id) {
                this.Faculty_id=faculty_id;

            }

            @Override
            protected String doInBackground(String... arg0) {

                try {

                    URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getsidemenu");

                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("user_id",Faculty_id);
//                postDataParams.put("user_id",Faculty_id);
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
                            Toast.makeText(getApplication(),"You are not registered"+result, Toast.LENGTH_SHORT).show();
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

                                     submenu = jsonArray.getJSONObject(px).getString("submenu");
                                    sUbMenuModel = new SubMenuPermissions(menu_id,menu_name,submenu);

//                                menuPermissionsSubListString.
//                                if(px==0)
                                    if(getIntent().getStringExtra("ids").equals(menu_name)) {
                                        menuPermissionsSubList.add(sUbMenuModel);
                                        if(submenu.contains("Pay Fee")) {
                                            Toast.makeText(mContext, "It contains", Toast.LENGTH_SHORT).show();
                                            menuPermissionsSubList.add(new SubMenuPermissions(menu_id,menu_name,"View Fees") );
                                            menuPermissionsSubList.add(new SubMenuPermissions(menu_id,menu_name,"Student Fee Details") );
//                                            menuPermissionsSubList.add(sUbMenuModel);
                                        }
                                        else
                                            {
//                                            menuPermissionsSubList.add(sUbMenuModel);
                                            Toast.makeText(mContext, "It Does not contains", Toast.LENGTH_SHORT).show();
                                        }

                                        if(submenu.contains("Add Work")) {
                                            menuPermissionsSubList.add(new SubMenuPermissions(menu_id,menu_name,"View Announcements") );

                                        }
                                        try {
                                            if (submenu.contains("Export")) {
                                                Toast.makeText(mContext, "Contain ", Toast.LENGTH_SHORT).show();
                                                menuPermissionsSubList.remove(menuPermissionsSubList.size() -1);
                                                menuPermissionsSubList.remove(menuPermissionsSubList.size() -1);
                                            }
                                        }catch (Exception e)
                                        {
                                            Toast.makeText(mContext, "Doe snot contain "+submenu, Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
//                                        }
                                    }

//                                }

                                }
                            }
                            try {
                                //+++++++++++++++++++++++++++++++For Sequence Add Class,Add Batch, Add Subject, Add Course
                                SubMenuPermissions Add_from;
                                SubMenuPermissions Add_to;
                                //++++++++++++++++Change Class to Batch
                                Add_from = menuPermissionsSubList.get(2);
                                Add_to = menuPermissionsSubList.get(0);
                                menuPermissionsSubList.set(0, Add_from);
                                menuPermissionsSubList.set(2, Add_to);
                                //+++++++++++++++++++++++++++++++++++++++++++++++++Change Subject to Batch
                                Add_from = menuPermissionsSubList.get(2);
                                Add_to = menuPermissionsSubList.get(1);
                                menuPermissionsSubList.set(1, Add_from);
                                menuPermissionsSubList.set(2, Add_to);
                                //++++++++++++++++++++++Change Add COurse with Study material
                                Add_from = menuPermissionsSubList.get(3);
                                Add_to = menuPermissionsSubList.get(4);
                                menuPermissionsSubList.set(4, Add_from);
                                menuPermissionsSubList.set(3, Add_to);
                                menuPermissionsSubList.remove(4);
                         //+++++++++++++++++++Remove Teacehr export++++++++++++++++++++++++


                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(MasterManuActivity.this,2, RecyclerView.VERTICAL,false);
                            gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            admin_recyclerView.setLayoutManager(gridLayoutManager);
                            menuExpandableAdapter = new SubMenuAdapter(MasterManuActivity.this, menuPermissionsSubList);
                            admin_recyclerView.setAdapter(menuExpandableAdapter);
                            menuExpandableAdapter.notifyDataSetChanged();
                            admin_recyclerView.requestLayout();
 //                       for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
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
        }}


