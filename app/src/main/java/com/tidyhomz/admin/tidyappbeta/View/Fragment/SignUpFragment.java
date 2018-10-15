package com.tidyhomz.admin.tidyappbeta.View.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpFragment extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ClassAPI.secondarytoolbar .setVisibility(View.GONE);
          view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ((MainActivity) getActivity()).closeDrag();
        LinearLayout back = (LinearLayout)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).Setback();

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        Button SignUpButton = (Button)view.findViewById(R.id.SignUpButton);
        TextView termsandcondiotion =(TextView)view.findViewById(R.id.termsandcondiotion);
        termsandcondiotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).TermsAndCondtion();
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText SignUpFirstName = (EditText)view.findViewById(R.id.SignUpFirstName);
                EditText SignUpLastName = (EditText)view.findViewById(R.id.SignUpLastName);
                EditText SignUpEmail = (EditText)view.findViewById(R.id.SignUpEmail);
                EditText SignUpMobile = (EditText)view.findViewById(R.id.SignUpMobile);
                EditText SignUpPassword = (EditText)view.findViewById(R.id.SignUpPassword);
                final CheckBox agree  = (CheckBox)view.findViewById(R.id.agree);


                final String firstname = SignUpFirstName.getText().toString().trim();
                final String lastname = SignUpLastName.getText().toString().trim();
                final String mobile = SignUpMobile.getText().toString().trim();
                final String email = SignUpEmail.getText().toString().trim();
                final String password = SignUpPassword.getText().toString().trim();

                int agreevar = 0;
                if(firstname.length()==0)
                {
                    SignUpFirstName.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please enter first name", Toast.LENGTH_SHORT).show();
                }
                else if(lastname.length()==0)
                {
                    SignUpLastName.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please enter last name", Toast.LENGTH_SHORT).show();
                }

                else if(email.length()==0)
                {
                    SignUpEmail.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please enter email ", Toast.LENGTH_SHORT).show();

                }
                else if(emailValidator(email)==false)
                {
                    SignUpEmail.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please enter correct email", Toast.LENGTH_SHORT).show();

                }
                else if(mobile.length()==0)
                {
                    SignUpMobile.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please enter mobile no", Toast.LENGTH_SHORT).show();
                }
                else if(mobile.length() < 10)
                {
                    SignUpMobile.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please enter correct mobile no", Toast.LENGTH_SHORT).show();
                }
                else if (password.length()==0)
                {
                    SignUpPassword.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
                }
                else if(agree.isChecked()==false)
                {
                    agree.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please check terms and conditions", Toast.LENGTH_SHORT).show();
                    agreevar = 0;
                }
                else
                {
                    agreevar = 1;

                    final int finalAgreevar1 = agreevar;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.REGISTER,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    //Log.e("",response);

                                    String messag = response;
                                    if(messag.trim().equals("success"))
                                    {

                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("Congratulations")
                                                .setMessage("Account Created Successfully!")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        dialog.dismiss();
                                                    }})
                                                .setNegativeButton(android.R.string.no, null).show();
                                       // success();
                                       // Toast.makeText(getActivity(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                       /* new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Congratulation!")
                                                .setContentText("Account Created Successfully!")
                                                .show();*/


                                        ((MainActivity) getActivity()).Setback();
                                        getActivity().getSupportFragmentManager().popBackStack();
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(),response, Toast.LENGTH_SHORT).show();
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
                            params.put("firstname",firstname);
                            params.put("lastname",lastname);
                            params.put("telephone", mobile);
                            params.put("email", email);
                            params.put("password",password);
                            params.put("agree", String.valueOf(finalAgreevar1));
                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
                }
            }
        });


        return view;
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
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
}
