package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class userProfilefragment extends Fragment {


   View view;

    String customer_id;
    TextView showname;
    EditText editfirstname,editLastname,editEmail,editMobilename;
    TextInputLayout input_layout_firstname,input_layout_lastname,input_layout_mobile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user_profilefragment, container, false);

        AppSharedPreferences App = new AppSharedPreferences(getActivity());

        String Fullname = App.pref.getString(App.FullName,"");
        final String FIRSTNAME = App.pref.getString(App.FIRSTNAME,"");
        final String LASTNAME = App.pref.getString(App.LASTNAME,"");
        String EmailId = App.pref.getString(App.EmailId,"");
        final String MobileNo = App.pref.getString(App.MobileNo,"");
        customer_id = App.pref.getString(App.UserId,"");
        showname = (TextView)view.findViewById(R.id.showname);


        editfirstname = (EditText) view.findViewById(R.id.editfirstname);
        editLastname = (EditText)view.findViewById(R.id.editLastname);
        editEmail = (EditText)view.findViewById(R.id.editEmail);
        editMobilename = (EditText)view.findViewById(R.id.editMobilename);

        editfirstname.addTextChangedListener(new userProfilefragment.MyTextWatcher(editfirstname));
        editLastname.addTextChangedListener(new userProfilefragment.MyTextWatcher(editLastname));
        editMobilename.addTextChangedListener(new userProfilefragment.MyTextWatcher(editMobilename));

        input_layout_firstname = (TextInputLayout)view. findViewById(R.id.input_layout_firstname);
        input_layout_lastname = (TextInputLayout)view.findViewById(R.id.input_layout_lastname);
        input_layout_mobile = (TextInputLayout)view.findViewById(R.id.input_layout_mobile);




        get_user_detail();

        Button update_detail = (Button)view.findViewById(R.id.update_detail);
        update_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validatelName();
                validatelName();
                validateMobile();


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

                update_detail();
            }
        });

        return view;
    }

    public  void update_detail()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.UPDATEPROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        /*new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText(response)
                                .show();*/
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                        get_user_detail();
                        AppSharedPreferences app = new AppSharedPreferences(getActivity());
                        app.editor.putString(app.FullName, editfirstname.getText()+" "+editLastname.getText());
                        app.editor.putString(app.FIRSTNAME, String.valueOf(editfirstname.getText()));
                        app.editor.putString(app.LASTNAME, String.valueOf(editLastname.getText()));
                        app.editor.putString(app.MobileNo, String.valueOf(editMobilename.getText()));
                        app.editor.commit();
                        ((MainActivity) getActivity()).Setback();
                        getActivity().getSupportFragmentManager().popBackStack();

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
                params.put("firstname", String.valueOf(editfirstname.getText()));
                params.put("lastname", String.valueOf(editLastname.getText()));
                params.put("telephone", String.valueOf(editMobilename.getText()));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    public void get_user_detail()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETPROFILEDETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       JSONArray profiledata;

                        JSONObject json = null;
                        try {
                            json = new JSONObject(String.valueOf(response));
                            profiledata = json.getJSONArray("result");


                            editfirstname.setText(profiledata.getJSONObject(0).getString("firstname"));
                            editLastname.setText(profiledata.getJSONObject(0).getString("lastname"));
                            editEmail.setText(profiledata.getJSONObject(0).getString("email"));
                            editMobilename.setText(profiledata.getJSONObject(0).getString("mobile"));
                            showname.setText(profiledata.getJSONObject(0).getString("firstname")+" "+profiledata.getJSONObject(0).getString("lastname"));




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
                params.put("customer_id",customer_id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private boolean validateName() {

        if (editfirstname.getText().toString().trim().isEmpty()) {
            input_layout_firstname.setError(getString(R.string.error_name));
            requestFocus(editfirstname);
            return false;
        }


        else {
            input_layout_firstname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatelName() {

        if (editLastname.getText().toString().trim().isEmpty()) {
            input_layout_lastname.setError(getString(R.string.error_lnane));
            requestFocus(editLastname);
            return false;
        }


        else {
            input_layout_lastname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {

        if (editMobilename.getText().toString().trim().isEmpty() ) {
            input_layout_mobile.setError(getString(R.string.error_mobile));
            requestFocus(editMobilename);
            return false;
        }
        else if (editMobilename.getText().length() < 10 ) {
            input_layout_mobile.setError(getString(R.string.error_mobile2));
            requestFocus(editMobilename);
            return false;
        }


        else {
            input_layout_mobile.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

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
                case R.id.editfirstname:
                    validateName();
                    break;

                case R.id.editLastname:
                    validatelName();
                    break;

                case R.id.editMobilename:
                    validateMobile();
                    break;



            }
        }
    }
}
