package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.NotificationAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.NotificationModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserNotifications extends Fragment {


   View view;
   ListView notificationList;
   ArrayList<NotificationModel> ArrayList;
   int Notification_id;
    JSONArray ReciveData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        LoadNotification();

        Notification_id = getArguments().getInt("notification_id");
        ClassAPI.set_title.setText("Notifications");
        view =  inflater.inflate(R.layout.fragment_user_notifications, container, false);
        notificationList = (ListView)view.findViewById(R.id.notificationList);


        return  view;
    }


    public void LoadNotification()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETNOTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        extractNotification(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"opps Somthing get's Worng",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                AppSharedPreferences app = new AppSharedPreferences(getContext());
                Map<String,String> params = new HashMap<String, String>();
                params.put("customer_id",app.pref.getString(app.UserId,""));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public  void extractNotification(String response)
    {
        ArrayList = new ArrayList<>();
        JSONObject Json = null;

        try {
            Json =  new JSONObject(response);

            ReciveData = Json.getJSONArray("result");

            TextView notificationcount = (TextView) view.findViewById(R.id.notificationcount);
            notificationcount.setText("You have Total "+ReciveData.length()+" Notifications");


                for (int i = 0; i < ReciveData.length(); i++) {

                  NotificationModel setdata = new NotificationModel();

                    JSONObject getItem = ReciveData.getJSONObject(i);
                    setdata.setNotification_id(getItem.getInt("notification_id"));
                    setdata.setNotification_title(getItem.getString("notification_title"));
                    setdata.setNotification_desc(getItem.getString("notification_desc"));
                    setdata.setNotification_image(getItem.getString("notification_image"));
                    setdata.setNotification_type(getItem.getString("notification_type"));
                    setdata.setNotification_type_id(getItem.getString("notification_type_id"));
                    setdata.setNotificatin_data(getItem.getString("date"));
                    setdata.setNotification_flag(getItem.getInt("check_flag"));
                    setdata.setNotificatin_type_name(getItem.getString("notification_type_name"));

                    ArrayList.add(setdata);
                }

            NotificationAdapter NotificationAdapter = new NotificationAdapter(getActivity(),ArrayList,Notification_id);
            notificationList.setAdapter(NotificationAdapter);

        } catch (JSONException e) {

            e.printStackTrace();
        }


    }

}
