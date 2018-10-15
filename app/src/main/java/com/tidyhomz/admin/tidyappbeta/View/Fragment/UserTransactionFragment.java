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
import com.tidyhomz.admin.tidyappbeta.Adapters.TransactionAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.UserTransactionModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserTransactionFragment extends Fragment {


View view;
ListView UserTransaction;
    ProgressBar progressBar;
    ArrayList<UserTransactionModel> UserTransactionModel;
    AppSharedPreferences app;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user_transaction, container, false);
        UserTransaction = (ListView)view.findViewById(R.id.UserTransaction);
        app = new AppSharedPreferences(getActivity());
        getTransaction();
        progressBar = new ProgressBar(getActivity());
        progressBar.show();
        return  view;



    }



    public void getTransaction()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                        ExtractTransaction(response);

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
                params.put("customer_id",app.pref.getString(app.UserId,""));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void ExtractTransaction(String response)
    {
        UserTransactionModel = new ArrayList<>();
        JSONObject json = null;
        try {
            json = new JSONObject(response);

            JSONArray Jsonarray = json.getJSONArray("result");

            TextView totalTransaction = (TextView)view.findViewById(R.id.totalTransaction);
            totalTransaction.setText("Total Transaction "+Jsonarray.length());
            for(int i=0;i<Jsonarray.length();i++)
            {
                UserTransactionModel object =  new UserTransactionModel();
                JSONObject jsonobject = Jsonarray.getJSONObject(i);
                object.setAmount(jsonobject.getString("amount"));
                object.setDate_added(jsonobject.getString("description"));
                object.setDescription(jsonobject.getString("date_added"));
                UserTransactionModel.add(object);
            }

            TransactionAdapter  TransactionAdapter = new TransactionAdapter(getActivity(),UserTransactionModel);
            UserTransaction.setAdapter(TransactionAdapter);
            progressBar.hide();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
