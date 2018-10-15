package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.WalletTransactionAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.WalletTransactionModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.Utility;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {

View view;
    TabLayout allTabs;
    RelativeLayout  walletcash;
    ProgressBar progressBar;
    LinearLayout referal,refferbtn;
    AppSharedPreferences App;
    TextView  TextOfreferDesc;
    TextView refferCode;
    TextView TextOfrefer,walletcashTotal;
    TextView shareFriend;
    ListView wallet_transaction;
    EditText refferedit;
    Button refferapply;
    ArrayList<WalletTransactionModel> datalist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ClassAPI.set_title.setText("My Wallet");
        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        allTabs = (TabLayout)view.findViewById(R.id.tabs);
        allTabs.addTab(allTabs.newTab().setText("REFER & EARN"));
        allTabs.addTab(allTabs.newTab().setText("WALLET CASH"),true);
        walletcash = (RelativeLayout)view.findViewById(R.id.walletcash);
        walletcash = (RelativeLayout)view.findViewById(R.id.walletcash);
        referal = (LinearLayout)view.findViewById(R.id.referal);
        wallet_transaction = (ListView)view.findViewById(R.id.wallet_transaction);
        App = new AppSharedPreferences(getActivity());
        final Typeface childFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Rupee_Foradian.ttf");
        progressBar = new ProgressBar(getActivity());
        TextOfrefer = (TextView)view.findViewById(R.id.TextOfrefer);
        walletcashTotal  = (TextView)view.findViewById(R.id.walletcashTotal);
        walletcashTotal.setTypeface(childFont);
        refferbtn = (LinearLayout)view.findViewById(R.id.refferbtn);
        TextOfreferDesc  = (TextView)view.findViewById(R.id.TextOfreferDesc);
        TextOfrefer.setTypeface(childFont);
        shareFriend = (TextView)view.findViewById(R.id.shareFriend);
        TextOfreferDesc.setTypeface(childFont);
        refferedit =(EditText)view.findViewById(R.id.refferedit);
        refferapply = (Button) view.findViewById(R.id.refferapply);
        bindWidgetsWithAnEvent();
        referEearn();
        fetchTransaction();
        TextView terms = (TextView)view.findViewById(R.id.terms);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermsAndCondiotionsWallet TermsAndCondiotionsWallet = new TermsAndCondiotionsWallet();
                //FragmentManager fragmentManager = getFragmentManager();
               // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
              //  fragmentTransaction.replace(R.id.Fragment_container, TermsAndCondiotionsWallet);
               // fragmentTransaction.addToBackStack(null);
               // fragmentTransaction.commit();
                ((MainActivity)getActivity()).replaceFragment(TermsAndCondiotionsWallet);
            }
        });
        shareFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String appPackageName = getActivity().getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "" + refferCode.getText());
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);
            }
        });
        refferapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyRefferCode();
            }
        });
        progressBar.show();
        return view;
    }


    private void applyRefferCode()
    {
        final String reffercode = String.valueOf(refferedit.getText());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.ApplyRefferCode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                        referEearn();
                        fetchTransaction();


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
                params.put("customer_id",App.pref.getString(App.UserId,""));
                params.put("refferCode",reffercode);
                params.put("firstname",App.pref.getString(App.FIRSTNAME,""));
                params.put("lastname",App.pref.getString(App.LASTNAME,""));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    private void bindWidgetsWithAnEvent()
    {
        allTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());


            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {



            }
        });
    }
    private void setCurrentTabFragment(int tabPosition)
    {

        if(tabPosition==0)
        {
            walletcash.setVisibility(View.GONE);
            referal.setVisibility(View.VISIBLE);

        }
        else if(tabPosition==1)
        {
            referal.setVisibility(View.GONE);
            walletcash.setVisibility(View.VISIBLE);

        }





    }

    public void referEearn()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.REFERCODE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONArray REFERARRAY = null;
                        JSONArray REFERARRAYDATA = null;
                        JSONArray REFERARRAYOFFER = null;
                        try {
                            JSONObject Json = new JSONObject(response);
                            REFERARRAY = Json.getJSONArray("result");
                            REFERARRAYOFFER = REFERARRAY.getJSONObject(0).getJSONArray("referoffer");
                            REFERARRAYDATA = REFERARRAY.getJSONObject(0).getJSONArray("referCode");


                             refferCode = (TextView)view.findViewById(R.id.refferCode);
                            walletcashTotal.setText("`"+REFERARRAYDATA.getJSONObject(0).getString("total_wallet_amount"));

                           refferCode.setText("REFERRAL CODE: "+REFERARRAYDATA.getJSONObject(0).getString("referal_code"));
                           TextOfrefer.setText("Refer Friends & Earn Upto"+" `"+REFERARRAYOFFER.getJSONObject(0).getString("max_earning"));
                           TextOfreferDesc.setText("Refer a freind and earn `"+REFERARRAYOFFER.getJSONObject(0).getString("offer_amount_sender")+". Your friend earns `"+REFERARRAYOFFER.getJSONObject(0).getString("offer_amount_reciever")+" when they use your refral code");
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
                params.put("customer_id",App.pref.getString(App.UserId,""));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void fetchTransaction()
    {
        datalist = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETWALLETTRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.hide();
                        JSONArray data;
                        try {
                            JSONObject Json  =  new JSONObject(response);
                            data = Json.getJSONArray("result");
                            for(int i=0;i<data.length();i++)
                            {
                                JSONObject jsonObject = data.getJSONObject(i);
                                WalletTransactionModel setdata = new WalletTransactionModel();
                                setdata.setRefferal_id(jsonObject.getInt("refferal_id"));
                                setdata.setAfter_refral_amount(jsonObject.getInt("after_refral_amount"));
                                setdata.setTitle(jsonObject.getString("title"));
                                setdata.setDesc_detail(jsonObject.getString("Desc_detail"));
                                setdata.setStatus(jsonObject.getInt("status"));
                                setdata.setDate(jsonObject.getString("after_refral_date"));
                                setdata.setRefeByName(jsonObject.getString("refferd_by_name"));
                                setdata.setCustomer_id(jsonObject.getInt("customer_id"));
                                datalist.add(setdata);

                                if(jsonObject.getInt("referal_status")==1)
                                {
                                    refferbtn.setVisibility(View.GONE);
                                }


                            }

                            WalletTransactionAdapter walletTransactionAdapter = new WalletTransactionAdapter(getActivity(),datalist);
                            wallet_transaction.setAdapter(walletTransactionAdapter);
                            Utility.setListViewHeightBasedOnChildren(wallet_transaction);
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
                params.put("customer_id",App.pref.getString(App.UserId,""));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
