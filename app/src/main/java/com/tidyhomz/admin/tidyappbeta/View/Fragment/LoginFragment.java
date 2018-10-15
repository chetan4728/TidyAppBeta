package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.API.EndPoints;
import com.tidyhomz.admin.tidyappbeta.API.SharedPrefManager;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolllyRequest;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class LoginFragment extends Fragment {

    EditText txtEmail,txtPassword;
    Button  btnLogin,btnSignup;
    TextView resetPassword;
    JSONArray LoginArray;
    ProgressBar pg;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ClassAPI.secondarytoolbar .setVisibility(View.GONE);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        txtEmail = (EditText)view.findViewById(R.id.LoginEmail);
        txtPassword = (EditText)view.findViewById(R.id.LoginPassword);
        btnLogin  = (Button) view.findViewById(R.id.LoginButton);
        btnSignup = (Button)view.findViewById(R.id.UserSignup);
        resetPassword = (TextView)view.findViewById(R.id.resetPassword);
        ((MainActivity) getActivity()).closeDrag();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });

        LinearLayout back = (LinearLayout)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).Setback();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserEmail = txtEmail.getText().toString().trim();
                String UserPass = txtPassword.getText().toString().trim();



                if(UserEmail.matches(""))
                {

                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();


                }
                else if(emailValidator(UserEmail)==false)
                {
                    txtEmail.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please enter correct email", Toast.LENGTH_SHORT).show();
                }

                else if(UserPass.matches(""))
                {
                    txtPassword.startAnimation(shakeError());
                    Toast.makeText(getActivity(), "Please enter correct Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pg = new ProgressBar(getActivity());
                    pg.show();
                    RequestLogin(UserEmail,UserPass);
                }
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUp();

            }
        });
        return view ;
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


    public void RequestLogin(String userEmail, String userPass){

        VolleyUtils.makeJsonObjectRequest(getActivity(), ClassAPI.CHECKLOGIN+"&email="+userEmail+"&password="+userPass, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {


            }

            @Override
            public void onResponse(Object response) {

                pg.hide();



                LoginData(String.valueOf(response));

            }

        });




    }

    public void LoginData(String data)
    {
        JSONObject Json = null;


        try {
            Json = new JSONObject(data);
            LoginArray = Json.getJSONArray("result");
            //View headerview =  ((LayoutInflater)(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.nav_header, null, false);
            //Toast.makeText(getActivity(), ""+LoginArray, Toast.LENGTH_SHORT).show();
            //Button btlogin =(Button)headerview.findViewById(R.id.loginme);
            //btlogin.setVisibility(View.GONE);

        if(LoginArray.getJSONObject(0).getString("success").equals("false"))
        {
            Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            /*new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Invalid Credentials")
                    .show();*/
        }
        else {

            //TextView SessionUsername =(TextView)headerview.findViewById(R.id.SessionUsername);
            //SessionUsername.setText("admin");

            AppSharedPreferences app = new AppSharedPreferences(getActivity());
            String firstname = LoginArray.getJSONObject(0).getString("firstname");
            String lastname = LoginArray.getJSONObject(0).getString("lastname");
            String mobile = LoginArray.getJSONObject(0).getString("telephone");
            String FullName = firstname+" "+lastname;
            app.editor.putString(app.FullName, FullName);
            app.editor.putString(app.FIRSTNAME, firstname);
            app.editor.putString(app.LASTNAME, lastname);
            app.editor.putString(app.MobileNo, mobile);
            app.editor.putString(app.LOGINSTATUS, "1");
            app.editor.putString(app.UserId, LoginArray.getJSONObject(0).getString("customer_id"));
            app.editor.putString(app.EmailId, LoginArray.getJSONObject(0).getString("email"));
            app.editor.putString(app.SESSIONKEY, LoginArray.getJSONObject(0).getString("session_key"));




            app.editor.commit();
            String email = LoginArray.getJSONObject(0).getString("email");
            sendTokenToServer(email);
            getActivity().getSupportFragmentManager().popBackStack();
            ((MainActivity) getActivity()).Setback();
            ((MainActivity) getActivity()).UserData();

            FragmentTransaction dx = getActivity().getSupportFragmentManager().beginTransaction();
            dx.add(R.id.main_menu, new MenuFragment(), "Menu");
            dx.commit();


        }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void SignUp()
    {
        SignUpFragment  SignUpFragment = new SignUpFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Fragment_container, SignUpFragment);
        fragmentTransaction.addToBackStack("LoginFragment");
        fragmentTransaction.commit();
    }

    public void Reset()
    {
        ForgetPasswordFragment  ForgetPasswordFragment = new ForgetPasswordFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Fragment_container, ForgetPasswordFragment);
        fragmentTransaction.addToBackStack("LoginFragment");
        fragmentTransaction.commit();
    }

    private void sendTokenToServer(final String email) {

        final String token = SharedPrefManager.getInstance(getActivity()).getDeviceToken();
        //Toast.makeText(getActivity(), ""+token, Toast.LENGTH_SHORT).show();

        if (token == null) {

            Toast.makeText(getActivity(), "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                           // Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("email", email);
                params.put("token", token);
                return params;
            }
        };

        VolllyRequest.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
