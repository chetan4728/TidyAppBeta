package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.MainMenuListAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.MainMenuListModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MenuFragment extends Fragment {



    private ListView MenuListview;
    private ArrayList<MainMenuListModel> MainMenuListModel;
    private MainMenuListAdapter MainMenuListAdapter;
    private  JSONArray MenuListArray = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        MenuListview =(ListView)view.findViewById(R.id.MenuListview);
        sendRequest();
        View headerview =  ((LayoutInflater)(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.nav_header, null, false);
        View footerview =  ((LayoutInflater)(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.nav_footer, null, false);
        headerview.setMinimumHeight(100);

         MenuListview.addHeaderView(headerview);
         MenuListview.addFooterView(footerview);

        Button btlogin =(Button)headerview.findViewById(R.id.loginme);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).login();
            }
        });

        AppSharedPreferences app = new AppSharedPreferences(getActivity());
        String Login_status  = app.pref.getString(app.LOGINSTATUS,"");
        String Full_name  = app.pref.getString(app.FullName,"");
        if(Login_status.toString().toString().trim().equals("1")) {

            Button sdfsd = (Button) headerview.findViewById(R.id.loginme);
            TextView SessionUsername = (TextView) headerview.findViewById(R.id.SessionUsername);
            SessionUsername.setText("Welcome "+Full_name);
            sdfsd.setVisibility(View.GONE);
        }

        TextView versiond = (TextView)view.findViewById(R.id.version);

        PackageManager manager = getActivity().getPackageManager();
        PackageInfo info = null;

        try {
            info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            versiond.setText("Version "+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        LinearLayout cartfooter  = (LinearLayout) footerview.findViewById(R.id.cartfooter);
        LinearLayout WishListView  = (LinearLayout) footerview.findViewById(R.id.WishListView);
        LinearLayout myacount = (LinearLayout)footerview.findViewById(R.id.myacount);
        LinearLayout orderList = (LinearLayout)footerview.findViewById(R.id.orderList);
        LinearLayout notification = (LinearLayout)footerview.findViewById(R.id.notfi);
        LinearLayout About = (LinearLayout)footerview.findViewById(R.id.About);
        LinearLayout TermsConditions = (LinearLayout)footerview.findViewById(R.id.TermsConditions);
        LinearLayout cancel = (LinearLayout)footerview.findViewById(R.id.cancel);
        LinearLayout Policy = (LinearLayout)footerview.findViewById(R.id.Policy);
        LinearLayout help_center = (LinearLayout)footerview.findViewById(R.id.help_center);
        LinearLayout rateUs = (LinearLayout)footerview.findViewById(R.id.rateUs);


        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).rateUs();
            }
        });




        Policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).Privacy();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).ReturnPolicy();
            }
        });

        TermsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).TermsAndCondtion();

            }
        });
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).Aboutus();

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).LoadNotification();

            }
        });

        myacount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).MyAccount();

            }
        });
        cartfooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).loadCartList();
            }
        });
        WishListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).loadWishlist();
            }
        });
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).My_orders();

            }
        });

        help_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).supportChat();
            }
        });


        return view;
    }
    public void sendRequest(){



        VolleyUtils.makeJsonObjectRequest(getActivity(), ClassAPI.TAG_CAT, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {
                ExtractData((String) response);
            }

        });




    }

    public  void ExtractData(String data)
    {
        MainMenuListModel = new ArrayList<>();
        JSONObject Json = null;
        try {
            Json = new JSONObject(data);
            MenuListArray = Json.getJSONArray(ClassAPI.JSON_ARRAY);

            for(int i=0;i<MenuListArray.length();i++)
            {
                Json = MenuListArray.getJSONObject(i);
                MainMenuListModel obj = new MainMenuListModel();
                obj.setProductId(Json.getInt("category_id"));
                obj.setProductName(Json.getString("name"));
                obj.setProductImage(Json.getString("menuIcon"));
                MainMenuListModel.add(obj);
            }


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        MainMenuListAdapter MainMenuListAdapter = new MainMenuListAdapter(getActivity(),MainMenuListModel);
        MenuListview.setAdapter(MainMenuListAdapter);

    }

}
