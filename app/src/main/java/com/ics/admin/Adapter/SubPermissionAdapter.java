package com.ics.admin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.ics.admin.Model.SubMenuPermissions;
import com.ics.admin.R;

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

import androidx.recyclerview.widget.RecyclerView;

public class SubPermissionAdapter extends RecyclerView.Adapter<SubPermissionAdapter.MyViewHolder> {
    public static ArrayList<String> Myproducttiles = new ArrayList<>();
    public List<SubMenuPermissions> menuPermisssionList;
    int pos_try;
    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
    private Context mContext;
    private SubMenuPermissions menuPermisssion;
    private RecyclerView subrec;

    public SubPermissionAdapter(Context context, List<SubMenuPermissions> menuPermisssion) {
        mContext = context;
        this.menuPermisssionList = menuPermisssion;
        setHasStableIds(true);
    }

    @Override
    public SubPermissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subpermission, parent, false);
        return new SubPermissionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubPermissionAdapter.MyViewHolder holder, final int position) {
        menuPermisssion = menuPermisssionList.get(position);
        this.pos_try = position;
        holder.namestff.setText(menuPermisssionList.get(position).getMenu_id());
        if(menuPermisssion.getStatus().equals("null"))
        {
            holder.permtoggel.setChecked(false);
        }else {
            holder.permtoggel.setChecked(true);
        }
        holder.permtoggel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    new PostPermission("2",menuPermisssionList.get(position).getSub_id(), "1").execute();
                }else {
                    new PostPermission("2",menuPermisssionList.get(position).getSub_id(), "whatever").execute();
                }
            }
        });
//        holder.emailstff.setText(menuPermisssion.getEmail());
//        holder.stffid.setText(menuPermisssion.getDesignation());
//        Glide.with(mContext).load("http://neareststore.in/uploads/staff/" + addNews141.getImage()).addListener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                Toast.makeText(mContext, "Failed to load image", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                Toast.makeText(mContext, "Image laod success", Toast.LENGTH_SHORT).show();
//                return false;
//            }

//        searching_manufacturers_data = menuPermisssionList.get(pos_try);
//        Log.e("Position","is "+pos_try);
//        document = searching_manufacturers_data.getBrandName();

        StrictMode.setVmPolicy(builder.build());

    }

    @Override
    public int getItemCount() {
        return menuPermisssionList.size();
    }

    @Override
    public long getItemId(int position) {
//        return super.getItemId(position);
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ToggleButton permtoggel;
        TextView namestff, emailstff, stffid;


        public MyViewHolder(View itemView) {
            super(itemView);
            //   mname = (TextView) itemView.findViewById(R.id.mname);
            // manu_img =  itemView.findViewById(R.id.manu_img);
            namestff = itemView.findViewById(R.id.names);
            permtoggel = itemView.findViewById(R.id.permtoggel);

            //     adddes =  itemView.findViewById(R.id.adddes);


        }
    }

    private class SubMITPermissions   extends AsyncTask<String, Void, String> {
        String menu_id;
        int user_id;
        String menu_name;
        int position;

        public SubMITPermissions( String menu_name, String menu_id, int user_id) {
            this.menu_id = menu_id;
            this.menu_name = menu_name;
            this.user_id = user_id;

        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getsubmenulist");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("user_id", ((Activity)mContext).getIntent().getStringExtra("teacher_id"));
                postDataParams.put("menu_id", menu_id);

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
                    if (!jsonObject.getBoolean("responce")) {
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                    } else {
                        int i = 0;
                        for (; i < jsonObject.getJSONArray("data").length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
                            String permission_id = jsonObject1.getString("permission_id");
                            String submenu = jsonObject1.getString("submenu");
//                            String menu_name = jsonObject1.getString("menu_name");
                            Log.e("submenu name",""+submenu);
//                            menuPermissionsSubList.add(position , new SubMenuPermissions(permission_id, submenu));

                        }
//
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

    private class PostPermission extends AsyncTask<String, Void, String> {
        String menu_id;
        String user_id;
        String menu_name;
        String isChecked;

        public PostPermission(String user_id, String menu_id, String isChecked) {
            this.isChecked=isChecked;
            this.user_id=user_id;
            this.menu_id=menu_id;
        }

//        public PostPermission( String menu_name, String menu_id, int user_id) {
//            this.menu_id = menu_id;
//            this.menu_name = menu_name;
//            this.user_id = user_id;
//
//        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/add_submenupermission");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("user_id", ((Activity)mContext).getIntent().getStringExtra("teacher_id"));
                postDataParams.put("sub_id", menu_id);
                postDataParams.put("status", isChecked);

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
                    if (!jsonObject.getBoolean("responce")) {
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                    } else {
                        int i = 0;
//                        notifyDataSetChanged();
//
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