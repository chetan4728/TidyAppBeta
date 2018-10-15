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

import java.util.HashMap;
import java.util.Map;

//import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserChangePassword extends Fragment {


    View view;
    TextInputLayout layout_password,layout_confirm;
    EditText Password,confirm_password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user_change_password, container, false);
        Password= (EditText)view.findViewById(R.id.Password);
        confirm_password = (EditText)view.findViewById(R.id.confirm_password);
        Password.addTextChangedListener(new UserChangePassword.MyTextWatcher(Password));
        confirm_password.addTextChangedListener(new UserChangePassword.MyTextWatcher(confirm_password));
        layout_password = (TextInputLayout)view.findViewById(R.id.layout_password);
        layout_confirm = (TextInputLayout)view.findViewById(R.id.layout_confirm);

        Button update_password = (Button)view.findViewById(R.id.update_passowrd);
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validatePassword();
                validateConfirmPassword();



                if (!validatePassword())
                {
                    return;
                }

                if (!validateConfirmPassword())
                {
                    return;
                }

                update_btn();
            }
        });


        return view;
    }


    public void update_btn()
    {
        AppSharedPreferences App = new AppSharedPreferences(getActivity());
        final String customer_id = App.pref.getString(App.UserId,"");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.UPDATEPASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                       /* new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText(response)
                                .show();*/

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
                params.put("password", String.valueOf(Password.getText()));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    private boolean validatePassword() {

        if (Password.getText().toString().trim().isEmpty()) {
            layout_password.setError(getString(R.string.error_password));
            requestFocus(Password);
            return false;
        }


        else {
            layout_password.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmPassword() {

        if (confirm_password.getText().toString().trim().isEmpty()) {
            layout_confirm.setError(getString(R.string.error_confirm_password));
            requestFocus(confirm_password);
            return false;
        }
        else if(!confirm_password.getText().toString().trim().equals(Password.getText().toString().trim()))
        {
            layout_confirm.setError(getString(R.string.error_password_missmatch));
            requestFocus(confirm_password);
            return false;

        }


        else {
            layout_confirm.setErrorEnabled(false);
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
                case R.id.Password:
                    validatePassword();
                    break;

                case R.id.confirm_password:
                    validateConfirmPassword();
                    break;



            }
        }
    }

}
