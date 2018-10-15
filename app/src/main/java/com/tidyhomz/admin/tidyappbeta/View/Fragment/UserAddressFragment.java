package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.UserAddressAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.AddressDatasetModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.addressSpinner;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserAddressFragment extends Fragment {


  View view;
    private ArrayList<AddressDatasetModel> AddressDatasetModel;
    private  ArrayList<addressSpinner>  AddressSpiinerModel;
    private JSONArray AddressArray;
    private JSONArray CountryArray;
    ProgressBar progressBar;
    private JSONArray StateArray;
    private UserAddressAdapter UserAddressAdapter;
    ListView Address;
    String customer_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppSharedPreferences App = new AppSharedPreferences(getActivity());
        customer_id = App.pref.getString(App.UserId,"");
        view  =  inflater.inflate(R.layout.fragment_user_address, container, false);

        Button add_address = (Button)view.findViewById(R.id.add_new);

        progressBar = new ProgressBar(getActivity());
        ClassAPI.set_title.setText("Address Book");



        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("Flag","Add");
                AddUpdateUserAddress AddUpdateUserAddress = new AddUpdateUserAddress();
                //FragmentManager fragmentManager = getFragmentManager();
             //   FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
              //  fragmentTransaction.replace(R.id.Fragment_container, AddUpdateUserAddress);
             //   fragmentTransaction.addToBackStack(null);
                AddUpdateUserAddress.setArguments(bundle);
              //  fragmentTransaction.commit();

                ((MainActivity)getActivity()).replaceFragment(AddUpdateUserAddress);

            }
        });

        Address = (ListView)view.findViewById(R.id.AddressList);

        getaddresList();
        progressBar.show();
        return  view;
    }

    public  void editAdress(int address_id)
    {
        Bundle bundle = new Bundle();
        bundle.putString("Flag","Edit");
        bundle.putInt("Address_id",address_id);
        AddUpdateUserAddress AddUpdateUserAddress = new AddUpdateUserAddress();
      //  FragmentManager fragmentManager = getFragmentManager();
      //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      //  fragmentTransaction.replace(R.id.Fragment_container, AddUpdateUserAddress);
      //  fragmentTransaction.addToBackStack(null);
        AddUpdateUserAddress.setArguments(bundle);
      //  fragmentTransaction.commit();
        ((MainActivity)getActivity()).replaceFragment(AddUpdateUserAddress);
    }

    public void getaddresList()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        getdata(response);

                      // Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();


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
                Map<String,String> params = new HashMap<String, String>();
                params.put("customer_id",customer_id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void update_default_address(final int address_id)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.UPDATEDEFAULTADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        getaddresList();

                        Toast.makeText(getActivity(), "Default Address Set Succssfully", Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"opps Somthing get's Worng",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("customer_id", customer_id);
                params.put("address_id", String.valueOf(address_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        }
    public  void getdata(String response)
    {
        //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

        AddressDatasetModel = new ArrayList<>();

        JSONObject  json = null;
        try {
            json = new JSONObject(response);

            AddressArray = json.getJSONArray("result");
            for(int i=0; i < AddressArray.length();i++)
            {
                JSONObject object = AddressArray.getJSONObject(i);
                AddressDatasetModel  setdata = new AddressDatasetModel();

                setdata.setAddress_id(object.getInt("address_id"));
                setdata.setCustomer_id(object.getInt("customer_id"));
                setdata.setFirstname(object.getString("firstname"));
                setdata.setLastname(object.getString("lastname"));
                setdata.setTelephone(object.getString("telephone"));
                setdata.setCompany(object.getString("company"));
                setdata.setAddress_1(object.getString("address_1"));
                setdata.setAddress_2(object.getString("address_2"));
                setdata.setCity(object.getString("city"));
                setdata.setPostcode(object.getString("postcode"));
                setdata.setCountry_id(object.getString("country_id"));
                setdata.setZone_id(object.getString("zone_id"));
                setdata.setCustom_field(object.getString("custom_field"));
                setdata.setCountry(object.getString("country"));
                setdata.setState(object.getString("state"));
                setdata.setDefault_yes(object.getInt("default_address"));
                AddressDatasetModel.add(setdata);

            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        UserAddressAdapter  UserAddressAdapter = new UserAddressAdapter(getActivity(),AddressDatasetModel,UserAddressFragment.this);
        Address.setAdapter(UserAddressAdapter);
        progressBar.hide();

    }


}
