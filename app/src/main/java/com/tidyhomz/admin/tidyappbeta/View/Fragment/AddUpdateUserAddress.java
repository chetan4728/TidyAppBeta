package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.AddressSpinnerAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.AddressSpinnerCountryAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.addressCountrySpinner;
import com.tidyhomz.admin.tidyappbeta.Dataset.addressSpinner;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class AddUpdateUserAddress extends Fragment {


    ArrayList<addressSpinner> AddressSpiinerModel;
    ArrayList<addressCountrySpinner> addressCountryArray;

    ProgressBar progressBar;
    String customer_id;
    View view;
    TextInputLayout input_layout_fname,input_layout_lname,input_layout_mobile,input_layout_address1,input_layout_postcode,input_layout_city,layout_spinner_state;
    EditText UserFirstName,UserLasttName,addressLine1,postcode,City,UserMobile,company,addressLine2;
    private JSONArray CountryArray;
    private JSONArray StateArray;
    RadioButton radioButton;
    JSONArray EditData;
    Spinner countrydp,statedp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ClassAPI.set_title.setText("New Address");

        //Toast.makeText(getActivity(), ""+getArguments().getString("Flag"), Toast.LENGTH_SHORT).show();

        AppSharedPreferences App = new AppSharedPreferences(getActivity());
        progressBar = new ProgressBar(getActivity());
          customer_id = App.pref.getString(App.UserId,"");






        view =  inflater.inflate(R.layout.fragment_add_update_user_address, container, false);
        countrydp  = (Spinner)view.findViewById(R.id.countrydp);
        statedp = (Spinner)view.findViewById(R.id.statedp);

        countrydp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


                TextView id_value = (TextView)arg0.findViewById(R.id.id_value);
                TextView value = (TextView)arg0.findViewById(R.id.value);
                countrydp.setTag(id_value.getText());


                if(value.getText().equals("Select Country")) {

                }
                else
                {
                    if(getArguments().getString("Flag").equals("Edit"))
                    {


                    }
                    else {

                    }
                }

            }



            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        statedp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


                TextView id_value = (TextView)arg0.findViewById(R.id.id_value);
                statedp.setTag(id_value.getText());


            }



            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });




         input_layout_fname = (TextInputLayout)view. findViewById(R.id.input_layout_fname);
         input_layout_lname = (TextInputLayout)view.findViewById(R.id.input_layout_lname);
         input_layout_mobile = (TextInputLayout)view.findViewById(R.id.input_layout_mobile);
         input_layout_address1 = (TextInputLayout)view.findViewById(R.id.input_layout_address1);
         input_layout_postcode  = (TextInputLayout)view.findViewById(R.id.input_layout_postcode);
         input_layout_city = (TextInputLayout)view.findViewById(R.id.input_layout_city);

        layout_spinner_state= (TextInputLayout)view.findViewById(R.id.layout_spinner_state);

         UserFirstName = (EditText) view.findViewById(R.id.UserFirstName);
         UserLasttName = (EditText) view.findViewById(R.id.UserLasttName);
         addressLine1 = (EditText) view.findViewById(R.id.addressLine1);
         postcode = (EditText) view.findViewById(R.id.postcode);
         addressLine2 = (EditText)view.findViewById(R.id.addressLine2);
         City = (EditText) view.findViewById(R.id.City);
         UserMobile = (EditText) view.findViewById(R.id.UserMobile);
         company = (EditText) view.findViewById(R.id.company);









         UserFirstName.addTextChangedListener(new MyTextWatcher(UserFirstName));
         UserLasttName.addTextChangedListener(new MyTextWatcher(UserLasttName));
         addressLine1.addTextChangedListener(new MyTextWatcher(addressLine1));
         UserMobile.addTextChangedListener(new MyTextWatcher(UserMobile));
         postcode.addTextChangedListener(new MyTextWatcher(postcode));
         City.addTextChangedListener(new MyTextWatcher(City));


        Button submitaddress = (Button)view.findViewById(R.id.submitAdd);
        submitaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        if(getArguments().getString("Flag").equals("Edit"))
        {
            get_edit();

        }



        postcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    progressBar.show();
                    getAvaiablePincode(postcode.getText());

                }
            }
        });

              return view;


        //AddressSpinnerAdapter AddressSpinnerAdapter = new AddressSpinnerAdapter(getActivity(),AddressDatasetModel);

    }




    public  void getCountry(final int pin_country)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETCOUNTRY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        getdata(response,pin_country);


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
    public void getdata(String response, int pin_country)
    {

        addressCountryArray = new ArrayList<>();
        String CountryName = null;
        JSONObject json = null;
        try {
            json = new JSONObject(response);


            CountryArray = json.getJSONArray("country");




            for(int j=0; j < CountryArray.length();j++) {
                JSONObject object = CountryArray.getJSONObject(j);
                addressCountrySpinner   getdata  = new  addressCountrySpinner();
                getdata.setCountry_id(object.getInt("country_id"));
                if(object.getInt("country_id")==pin_country)
                {
                    CountryName= object.getString("name");
                }
                getdata.setCountry_name(object.getString("name"));
                addressCountryArray.add(getdata);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }



        AddressSpinnerCountryAdapter AddressSpinnerCountryAdapter = new AddressSpinnerCountryAdapter(getActivity(),addressCountryArray);
        countrydp.setAdapter(AddressSpinnerCountryAdapter);

        for(int i = 0; i < countrydp.getCount(); i++){
            if(countrydp.getItemAtPosition(i).toString().equals(CountryName)){
                countrydp.setSelection(i);
                break;
            }
        }

    }


    public void getAvaiablePincode(Editable text)
    {
        final  String Textdd = String.valueOf(text);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETPINICODEVERIFY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.hide();





                            try {
                                JSONObject json = new JSONObject(response);

                                City.setText(json.getString("pin_city"));

                                getCountry(json.getInt("pin_country"));
                                get_state(String.valueOf(json.getInt("pin_country")), String.valueOf(json.getInt("pin_state")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




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
                params.put("postcode", String.valueOf(Textdd));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void get_state(final String country_id, final String state_name)
    {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETSTATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        get_state_data(response,state_name);



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
                params.put("Country_id",country_id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


        public void get_state_data(String response,String State_id)
        {
            AddressSpiinerModel = new ArrayList<>();

            String CityName = null;
            JSONObject json = null;
            int state_id = 0;
            try {
                json = new JSONObject(response);


                StateArray = json.getJSONArray("result");
                    if(!State_id.isEmpty()) {
                         state_id = Integer.parseInt(State_id.toString());
                    }

                for(int j=0; j < StateArray.length();j++) {
                    JSONObject object = StateArray.getJSONObject(j);
                    addressSpinner   getdata  = new  addressSpinner();
                    getdata.setState_id(object.getInt("state_id"));
                    if(!State_id.isEmpty()) {
                        if(object.getInt("state_id")==state_id)
                        {
                            CityName = object.getString("name");

                        }
                    }
                    getdata.setState_name(object.getString("name"));
                    AddressSpiinerModel.add(getdata);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }



            AddressSpinnerAdapter AddressSpinnerAdapter = new AddressSpinnerAdapter(getActivity(),AddressSpiinerModel);
            statedp.setAdapter(AddressSpinnerAdapter);


            for(int i = 0; i < statedp.getCount(); i++){


                if(statedp.getItemAtPosition(i).toString().equals(CityName)){
                    statedp.setSelection(i);
                    break;
                }



            }


        }

    public void get_edit()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETEDITADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);

                            EditData = json.getJSONArray("result");
                            JSONObject object = EditData.getJSONObject(0);
                            UserFirstName.setText(object.getString("firstname"));
                            UserLasttName.setText(object.getString("lastname"));
                            UserMobile.setText(object.getString("telephone"));
                            company.setText(object.getString("company"));
                            addressLine1.setText(object.getString("address_1"));
                            addressLine2.setText(object.getString("address_2"));
                            postcode.setText(object.getString("postcode"));
                            City.setText(object.getString("city"));

                            getAvaiablePincode(postcode.getText());
                            RadioButton yesbutton =(RadioButton)view.findViewById(R.id.default_yes);
                            RadioButton nobutton =(RadioButton)view.findViewById(R.id.default_no);



                            if(object.getInt("default_address")==1)
                            {
                                yesbutton.setChecked(true);
                                nobutton.setChecked(false);
                            }
                            else
                            {
                                nobutton.setChecked(true);
                                yesbutton.setChecked(false);
                            }




                            progressBar.hide();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
                params.put("address_id",String.valueOf(getArguments().getInt("Address_id")));
                params.put("customer_id",String.valueOf(customer_id));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    private void submitForm() {

            validatelName();
            validatelName();
            validateMobile();
            validateAddress();
            validatePostcode();
            validateCity();
            validateState();

            if (!validateName())
            {
                return;
            }

            if (!validatelName())
            {
                return;
            }
            if (!validateMobile())
            {
                return;
            }
            if (!validateAddress())
            {
                return;
            }
            if (!validatePostcode())
            {
                return;
            }

            if (!validateCity())
            {
                return;
            }

             submit_form();



    }



    public void submit_form()
    {
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.select_address);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();

        final RadioButton defaltaddress = (RadioButton)view.findViewById(radioButtonID);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.SUBMITREG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        UserAddressFragment UserAddressFragment = new UserAddressFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.Fragment_container, UserAddressFragment);
                        fragmentTransaction.commit();
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
                params.put("firstname",UserFirstName.getText().toString());
                params.put("lastname",UserLasttName.getText().toString());
                params.put("telephone",UserMobile.getText().toString());
                params.put("company",company.getText().toString());
                params.put("address_1",addressLine1.getText().toString());
                params.put("address_2",addressLine2.getText().toString());
                params.put("postcode",postcode.getText().toString());
                params.put("city",City.getText().toString());
                params.put("zone_id", String.valueOf(statedp.getTag()));
                params.put("country_id", String.valueOf(countrydp.getTag()));
                params.put("default", String.valueOf(defaltaddress.getTag()));
                params.put("Flag",getArguments().getString("Flag"));
                params.put("address_id", String.valueOf(getArguments().getInt("Address_id")));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private boolean validateName() {

        if (UserFirstName.getText().toString().trim().isEmpty()) {
            input_layout_fname.setError(getString(R.string.error_name));
            requestFocus(UserFirstName);
            return false;
        }


        else {
            input_layout_fname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatelName() {

        if (UserLasttName.getText().toString().trim().isEmpty()) {
            input_layout_lname.setError(getString(R.string.error_lnane));
            requestFocus(UserLasttName);
            return false;
        }


        else {
            input_layout_lname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {

        if (UserMobile.getText().toString().trim().isEmpty() ) {
            input_layout_mobile.setError(getString(R.string.error_mobile));
            requestFocus(UserMobile);
            return false;
        }
        else if (UserMobile.getText().length() < 10 ) {
            input_layout_mobile.setError(getString(R.string.error_mobile2));
            requestFocus(UserMobile);
            return false;
        }


        else {
            input_layout_mobile.setErrorEnabled(false);
        }

        return true;
    }

    public  boolean validateState()
    {

        if (statedp.getSelectedItemPosition()==0) {
            layout_spinner_state.setError(getString(R.string.error_state));
            requestFocus(statedp);
            return false;
        }


        else {
            layout_spinner_state.setErrorEnabled(false);
        }

        return true;

    }


    private boolean validatePostcode() {

        if (postcode.getText().toString().trim().isEmpty() ) {
            input_layout_postcode.setError(getString(R.string.error_postcode));
            requestFocus(postcode);
            return false;
        }
        else if (postcode.getText().length() < 6 ) {
            input_layout_postcode.setError(getString(R.string.error_postcode2));
            requestFocus(postcode);
            return false;
        }


        else {
            input_layout_postcode.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (addressLine1.getText().toString().trim().isEmpty()) {
            input_layout_address1.setError(getString(R.string.error_address1));
            requestFocus(addressLine1);
            return false;
        }


        else {
            input_layout_address1.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCity() {

        if (City.getText().toString().trim().isEmpty()) {
            input_layout_city.setError(getString(R.string.error_city));
            requestFocus(City);
            return false;
        }


        else {
             input_layout_city.setErrorEnabled(false);
        }

        return true;
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

     private class MyTextWatcher implements TextWatcher{

         private View view;

         private MyTextWatcher(View view) {
             this.view = view;
         }
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {

         }

         @Override
         public void afterTextChanged(Editable s) {
             switch (view.getId()) {
                 case R.id.UserFirstName:
                     validateName();
                     break;

                 case R.id.UserLasttName:
                     validatelName();
                     break;

                 case R.id.UserMobile:
                     validateMobile();
                     break;

                 case R.id.addressLine1:
                     validateAddress();
                     break;

                 case R.id.postcode:
                     validatePostcode();
                     break;

                 case R.id.City:
                     validateCity();
                     break;

             }
         }
     }

    public abstract class TextChangedListener<T> implements TextWatcher {
        private T target;

        public TextChangedListener(T target) {
            this.target = target;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            this.onTextChanged(target, s);
        }

        public abstract void onTextChanged(T target, Editable s);
    }
}




