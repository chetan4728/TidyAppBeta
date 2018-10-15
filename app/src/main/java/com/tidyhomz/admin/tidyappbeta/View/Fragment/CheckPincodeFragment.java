package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.GPSTracker;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONObject;


public class CheckPincodeFragment extends Fragment {


    Context mContext;
    LocationManager locationManager;
    EditText pincode;
    JSONObject GetPincodeArray;
    GPSTracker gps;
     LinearLayout containerme;
     View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();

        view = inflater.inflate(R.layout.fragment_check_pincode, container, false);
        gps = new GPSTracker(mContext, getActivity());
        ((MainActivity) getActivity()).closeDrag();
        pincode  = (EditText)view.findViewById(R.id.pincode_check);
        Button btn = (Button)view.findViewById(R.id.submitpincode);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPincode(String.valueOf(pincode.getText()));
            }
        });


        final LinearLayout autopincode = (LinearLayout) view.findViewById(R.id.getlocationauto);
        containerme = (LinearLayout) view.findViewById(R.id.container);


        autopincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                } else {



                    // Check if GPS enabled
                    if (gps.canGetLocation()) {

                        AppSharedPreferences app = new AppSharedPreferences(getActivity());
                        if(gps.getPostalCode(getActivity())==null)
                        {
                            Snackbar snackbar = Snackbar
                                    .make(containerme, "Invalid Pincode !!! Enter Manually...", Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }


                        else {


                            checkPincode(gps.getPostalCode(getActivity()));


                        }

                    } else {

                        gps.showSettingsAlert();


                    }
                }


            }
        });

        return view;
    }

    public void checkPincode(String pincode)
    {



         VolleyUtils.makeJsonObjectRequest(getActivity(), ClassAPI.CHECKPINCODE+pincode, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {



                    JSONObject Json = null;
                    try {
                        Json = new JSONObject((String) response);
                        //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

                        GetPincodeArray = Json.getJSONObject("result");
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        String pin_pincode = GetPincodeArray.getString("pin_pincode");
                        String city = GetPincodeArray.getString("pin_city");
                       // String cod = GetPincodeArray.getString("pin_cod");
                        //String online = GetPincodeArray.getString("pin_prepaid");
                        //String reverse = GetPincodeArray.getString("pin_reverse");
                        AppSharedPreferences app = new AppSharedPreferences(getActivity());
                        app.editor.putString(app.PINCODE, "DELIVERED TO " + city + " " + pin_pincode);
                        app.editor.commit();

                        ((MainActivity) getActivity()).Setback();
                        getActivity().getSupportFragmentManager().popBackStack();

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }



        });

    }

}
