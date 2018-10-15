package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.payu.india.Payu.PayuConstants;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolllyRequest;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class ForgetPasswordFragment extends Fragment {


        EditText forgetEmail;
        Button ResetButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ClassAPI.secondarytoolbar .setVisibility(View.GONE);
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        ((MainActivity) getActivity()).closeDrag();
        LinearLayout back = (LinearLayout)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).Setback();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        forgetEmail = (EditText)view.findViewById(R.id.forgetEmail);
        ResetButton = (Button)view.findViewById(R.id.ResetButton);
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserEmail = forgetEmail.getText().toString().trim();

                if(UserEmail.matches(""))
                {
                    forgetEmail.startAnimation(shakeError());


                }
                else if(UserEmail.isEmpty())
                {
                    Toast.makeText(getContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(emailValidator(UserEmail)==false)
                {
                    forgetEmail.startAnimation(shakeError());

                }
                else
                {
                    forgetPassword();

                }



            }
        });



        return view;
    }


    public boolean emailValidator(String email)
    {

        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void forgetPassword()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.FORGETPASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Reset Password!")
                                .setMessage("The link has been sent to you email account ")
                                .setIcon(android.R.drawable.ic_dialog_email)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();
                       /* new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Reset Password")
                                .setContentText("Please check your email to reset!")
                                .show();*/


                        ((MainActivity) getActivity()).Setback();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("email", forgetEmail.getText().toString());
                return params;
            }
        };

        VolllyRequest.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }
}
