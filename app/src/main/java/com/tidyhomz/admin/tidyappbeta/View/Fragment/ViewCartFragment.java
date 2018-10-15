package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.Log;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.CartPriceListAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.ViewCartAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.CartPriceListModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.ViewCartModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.Utility;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//import cn.pedant.SweetAlert.SweetAlertDialog;


public class ViewCartFragment extends Fragment {



    JSONArray ViewCartArray;
    JSONArray TotalDetail;
    JSONArray TaxDetail;
    ArrayList<ViewCartModel> Viewcartmodel;
    ListView listcartview;
    ViewCartAdapter ViewCartAdapter;
    ProgressBar pg;
    CartPriceListAdapter CartPriceListAdapter;
    TableLayout taxes;
    TableRow taxesRow;
    RelativeLayout cartFragment;
    ArrayList<CartPriceListModel> CartPriceListModel;
    Button appycpn,applygift,RemoveCoupn,removegift;
    EditText editcoupcode,getgiftcode;
    String finalCheckprice,WalletPricess;
    TextView textlimit;
    TextView proceedPrice;
     TextView cupnresponse,giftyresponse;
     LinearLayout wallet_layout;

            CheckBox walletcash ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pg = new ProgressBar(getActivity());
        pg.show();
        ClassAPI.set_title.setText("My Cart");
        final AppSharedPreferences app = new AppSharedPreferences(getActivity());
        int CARTVIEWCOUNT = app.pref.getInt(app.CARTCOUNT, 0);

        AppSharedPreferences appp = new AppSharedPreferences(getActivity());
        appp.editor.putString(appp.WalletAmount,"0");


        if (CARTVIEWCOUNT > 0) {

            final View view = inflater.inflate(R.layout.fragment_view_cart, container, false);
            proceedPrice =(TextView)view.findViewById(R.id.proceedPrice);
            listcartview = (ListView) view.findViewById(R.id.listcartview);
            LoadCart();
            cartFragment = (RelativeLayout) view.findViewById(R.id.cartFragment);
            cartFragment.setVisibility(View.GONE);

            appycpn = (Button)view.findViewById(R.id.appycpn);
            editcoupcode = (EditText)view.findViewById(R.id.editcoupcode);
            RemoveCoupn = (Button)view.findViewById(R.id.RemoveCoupn);
            removegift = (Button)view.findViewById(R.id.removegift);
            applygift = (Button)view.findViewById(R.id.applygift);
            getgiftcode = (EditText)view.findViewById(R.id.getgiftcode);
            cupnresponse = (TextView)view.findViewById(R.id.cupnresponse);
            giftyresponse = (TextView)view.findViewById(R.id.giftyresponse);
            walletcash = (CheckBox)view.findViewById(R.id.walletcash);
            wallet_layout = (LinearLayout)view.findViewById(R.id.wallet_layout);
            if(!app.pref.getString(app.SETCOUPON,"").isEmpty())
            {
                RemoveCoupn.setVisibility(View.VISIBLE);
                appycpn.setVisibility(View.GONE);
                editcoupcode.setVisibility(View.GONE);
                cupnresponse.setText("You applied "+app.pref.getString(app.SETCOUPON,""));
                cupnresponse.setVisibility(View.VISIBLE);
            }
            else
            {
                RemoveCoupn.setVisibility(View.GONE);
                appycpn.setVisibility(View.VISIBLE);
                editcoupcode.setVisibility(View.VISIBLE);
                cupnresponse.setVisibility(View.GONE);
            }


            if(!app.pref.getString(app.SETGIFT,"").isEmpty())
            {
                removegift.setVisibility(View.VISIBLE);
                applygift.setVisibility(View.GONE);
                getgiftcode.setVisibility(View.GONE);
                giftyresponse.setText("You applied "+app.pref.getString(app.SETGIFT,""));
                giftyresponse.setVisibility(View.VISIBLE);
            }
            else
            {
                removegift.setVisibility(View.GONE);
                applygift.setVisibility(View.VISIBLE);
                getgiftcode.setVisibility(View.VISIBLE);
                giftyresponse.setVisibility(View.GONE);
            }



            RemoveCoupn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    app.editor.putString(app.SETCOUPON,"");
                    app.editor.commit();
                    RemoveCoupn.setVisibility(View.GONE);
                    appycpn.setVisibility(View.VISIBLE);
                    editcoupcode.setVisibility(View.VISIBLE);
                    cupnresponse.setVisibility(View.GONE);
                    LoadCart();
                }
            });
            removegift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    app.editor.putString(app.SETGIFT,"");
                    app.editor.commit();
                    removegift.setVisibility(View.GONE);
                    applygift.setVisibility(View.VISIBLE);
                    getgiftcode.setVisibility(View.VISIBLE);
                    giftyresponse.setVisibility(View.GONE);
                    LoadCart();
                }
            });
            appycpn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    final AppSharedPreferences App = new AppSharedPreferences(getActivity());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.APPLYCPN,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();


                                    App.editor.putString(App.SETCOUPON, String.valueOf(editcoupcode.getText()));
                                    if(response.equals("empty")) {
                                        cupnresponse.setText(String.valueOf("WARNING: PLEASE ENTER A COUPON CODE!"));
                                        cupnresponse.setBackgroundColor(Color.parseColor("#f2dede"));
                                        cupnresponse.setTextColor(Color.parseColor("#a94442"));
                                        cupnresponse.setVisibility(View.VISIBLE);
                                        appycpn.setVisibility(View.GONE);
                                        editcoupcode.setVisibility(View.GONE);

                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        appycpn.setVisibility(View.VISIBLE);
                                                        editcoupcode.setVisibility(View.VISIBLE);
                                                        cupnresponse.setVisibility(View.GONE);
                                                    }
                                                },
                                                2500);
                                    }
                                    else  if(response.equals("error"))
                                    {
                                        cupnresponse.setText(String.valueOf("WARNING: COUPON IS EITHER INVALID, EXPIRED OR REACHED ITS USAGE LIMIT!"));
                                        cupnresponse.setBackgroundColor(Color.parseColor("#f2dede"));
                                        cupnresponse.setTextColor(Color.parseColor("#a94442"));
                                        cupnresponse.setVisibility(View.VISIBLE);
                                        appycpn.setVisibility(View.GONE);
                                        editcoupcode.setVisibility(View.GONE);

                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        appycpn.setVisibility(View.VISIBLE);
                                                        editcoupcode.setVisibility(View.VISIBLE);
                                                        cupnresponse.setVisibility(View.GONE);
                                                    }
                                                },
                                                2500);
                                    }
                                    else
                                    {
                                        cupnresponse.setText("You applied "+editcoupcode.getText());
                                        cupnresponse.setVisibility(View.VISIBLE);
                                        cupnresponse.setBackgroundColor(Color.parseColor("#dff0d8"));
                                        cupnresponse.setTextColor(Color.parseColor("#3c763d"));
                                        appycpn.setVisibility(View.GONE);
                                        RemoveCoupn.setVisibility(View.VISIBLE);
                                        editcoupcode.setVisibility(View.GONE);
                                        App.editor.commit();


                                        LoadCart();
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
                            params.put("coupon", String.valueOf(editcoupcode.getText().toString().trim()));
                            params.put("customer_id", String.valueOf(App.pref.getString(App.UserId,"")));
                            params.put("session_id", String.valueOf(App.pref.getString(App.SESSIONKEY,"")));
                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);


                }
            });

            Button proceed =(Button)view.findViewById(R.id.proceed);
            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();


                    bundle.putString("Flag","Add");

                    ProceedUserOrderFragment ProceedUserOrderFragment = new ProceedUserOrderFragment();

                    ((MainActivity)getActivity()).replaceFragment(ProceedUserOrderFragment);

                    /*FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.Fragment_container, ProceedUserOrderFragment);
                    fragmentTransaction.addToBackStack(null);
                    ProceedUserOrderFragment.setArguments(bundle);
                    fragmentTransaction.commit();*/
                }
            });

            applygift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AppSharedPreferences App = new AppSharedPreferences(getActivity());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.APPLYGIFT,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {







                                    if(response.equals("empty")) {
                                        giftyresponse.setText(String.valueOf("WARNING: PLEASE ENTER A GIFT CERTIFICATE CODE!"));
                                        giftyresponse.setBackgroundColor(Color.parseColor("#f2dede"));
                                        giftyresponse.setTextColor(Color.parseColor("#a94442"));
                                        giftyresponse.setVisibility(View.VISIBLE);
                                        applygift.setVisibility(View.GONE);
                                        getgiftcode.setVisibility(View.GONE);
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        applygift.setVisibility(View.VISIBLE);
                                                        getgiftcode.setVisibility(View.VISIBLE);
                                                        giftyresponse.setVisibility(View.GONE);
                                                    }
                                                },
                                                2500);
                                    }
                                    else  if(response.equals("error"))
                                    {
                                        giftyresponse.setText(String.valueOf("WARNING: GIFT CERTIFICATE IS EITHER INVALID OR THE BALANCE HAS BEEN USED UP!"));
                                        giftyresponse.setBackgroundColor(Color.parseColor("#f2dede"));
                                        giftyresponse.setTextColor(Color.parseColor("#a94442"));
                                        giftyresponse.setVisibility(View.VISIBLE);
                                        applygift.setVisibility(View.GONE);
                                        getgiftcode.setVisibility(View.GONE);
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        applygift.setVisibility(View.VISIBLE);
                                                        getgiftcode.setVisibility(View.VISIBLE);
                                                        giftyresponse.setVisibility(View.GONE);
                                                    }
                                                },
                                                2500);
                                    }
                                    else
                                    {
                                        AppSharedPreferences App = new AppSharedPreferences(getActivity());
                                        App.editor.putString(App.SETGIFT, String.valueOf(getgiftcode.getText()));
                                        App.editor.commit();
                                        LoadCart();
                                        giftyresponse.setText(String.valueOf("Success: Your gift certificate discount has been applied!"));
                                        giftyresponse.setVisibility(View.VISIBLE);
                                        applygift.setVisibility(View.GONE);
                                        getgiftcode.setVisibility(View.GONE);

                                        giftyresponse.setBackgroundColor(Color.parseColor("#dff0d8"));
                                        giftyresponse.setTextColor(Color.parseColor("#3c763d"));
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
                            params.put("voucher", String.valueOf(getgiftcode.getText().toString().trim()));
                            params.put("customer_id", String.valueOf(App.pref.getString(App.UserId,"")));
                            params.put("session_id", String.valueOf(App.pref.getString(App.SESSIONKEY,"")));
                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);



                }
            });
             textlimit = (TextView)view.findViewById(R.id.textlimit);
            final AppSharedPreferences App = new AppSharedPreferences(getActivity());








            walletcash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    float checkcartprice = 0;

                    String wallet_status = String.valueOf(walletcash.getTag());

                        if (wallet_status.equals("1"))
                        {
                            try {
                                checkcartprice = Float.parseFloat(String.valueOf(getNumberByString(finalCheckprice.toString())));
                                // Toast.makeText(getActivity(), ""+getNumberByString(finalCheckprice.toString()), Toast.LENGTH_SHORT).show();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        //Toast.makeText(getActivity(), ""+checkcartprice, Toast.LENGTH_SHORT).show();

                        int WalletPricessd = Integer.parseInt(WalletPricess.toString());
                        String voucherStatus = App.pref.getString(App.SETCOUPON, "");


                        if ((WalletPricessd != 0) && (checkcartprice >= 1000) && (voucherStatus.isEmpty())) {
                            if (isChecked) {


                                StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.CheckWalletCash,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {


                                                LinearLayout myLayout = (LinearLayout) view.findViewById(R.id.offersection);
                                                float finalwallet = 0;
                                                try {
                                                    finalwallet = Float.parseFloat(String.valueOf(getNumberByString(response)));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }

                                                finalwallet = finalwallet;

                                                myLayout.setVisibility(View.GONE); // Or whatever you want to do with the view.
                                                App.editor.putString(App.WalletAmount, String.valueOf(finalwallet));
                                                App.editor.commit();

                                                LoadCart();

                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getActivity(), "opps Somthing get's Worng", Toast.LENGTH_LONG).show();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("customer_id", App.pref.getString(App.UserId, ""));
                                        params.put("wallet_amount", String.valueOf(walletcash.getText()));
                                        return params;
                                    }

                                };

                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                requestQueue.add(stringRequest);

                            }

                            if (isChecked == false) {

                                LinearLayout myLayout = (LinearLayout) view.findViewById(R.id.offersection);

                                myLayout.setVisibility(View.VISIBLE); // Or whatever you want to do with the view.
                                walletcash.setChecked(false);
                                App.editor.putString(App.WalletAmount, "0");
                                App.editor.commit();
                                LoadCart();


                            }
                        } else if (WalletPricessd == 0) {
                            walletcash.setChecked(false);
                      // Or whatever you want to do with the view.
                            walletcash.setChecked(false);
                            App.editor.putString(App.WalletAmount, "0");
                            App.editor.commit();
                            LoadCart();
                            Toast.makeText(getActivity(), "Sorry there is 0 amount in your wallet.", Toast.LENGTH_SHORT).show();
                       /* new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops")
                                .setContentText("Sorry there is 0 amount in your wallet.")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();

                                    }
                                })
                                .show();*/

                        } else {
                            walletcash.setChecked(false);
                            App.editor.putString(App.WalletAmount, "0");
                            App.editor.commit();
                            LoadCart();
                            walletcash.setChecked(false);
                            Toast.makeText(getActivity(), "Wallet cash can be used on cart value above Rs.1000", Toast.LENGTH_SHORT).show();
                        /*new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops")
                                .setContentText("Wallet cash can be used on cart value above Rs.1000")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();

                                    }
                                })
                                .show();*/


                        }
                    }
                    else
                    {
                        walletcash.setChecked(false);
                        App.editor.putString(App.WalletAmount, "0");
                        App.editor.commit();
                        LoadCart();
                        walletcash.setChecked(false);
                        Toast.makeText(getActivity(), "Sorry!! You already applied for maximum discount", Toast.LENGTH_SHORT).show();
                    }
                }

            });




            referEearn();
            TextView terms = (TextView)view.findViewById(R.id.termsandcond);
            terms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TermsAndCondiotionsWallet TermsAndCondiotionsWallet = new TermsAndCondiotionsWallet();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.Fragment_container, TermsAndCondiotionsWallet);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            return view;
        }
        else
        {
            pg.hide();
            View view = inflater.inflate(R.layout.empty_cart, container, false);
            getActivity().getSupportFragmentManager().popBackStack("ViewCartFragment",0);
            Button backbutton = (Button)view.findViewById(R.id.backbutton);
            backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int count =  getActivity().getSupportFragmentManager().getBackStackEntryCount();
                    for(int i = 0; i < count; i++) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    ((MainActivity)getActivity()).ShowActionBar();
                    ClassAPI.secondarytoolbar.setVisibility(View.GONE);
                }
            });


            return view;

        }
    }


    public static double getNumberByString(String s) throws ParseException {
        return NumberFormat.getInstance(Locale.getDefault()).parse(s).doubleValue();
    }
    public void referEearn()
    {
        final Typeface childFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Rupee_Foradian.ttf");

        textlimit.setTypeface(childFont);
        textlimit.setText("You can use 50% wallet amount above `1000 cart amount");

        final AppSharedPreferences App = new AppSharedPreferences(getActivity());
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


                            WalletPricess  = REFERARRAYDATA.getJSONObject(0).getString("total_wallet_amount");
                            String wallet_status = REFERARRAYDATA.getJSONObject(0).getString("status");

                            walletcash.setText("`"+REFERARRAYDATA.getJSONObject(0).getString("total_wallet_amount"));
                            walletcash.setTag(wallet_status);
                            walletcash.setTypeface(childFont);

                    if(wallet_status.equals("1")) {

                        if (App.pref.getString(App.WalletAmount, "").equals("0") == false && App.pref.getString(App.WalletAmount, "").isEmpty() == false) {
                            // Toast.makeText(getActivity(), ""+App.pref.getString(App.WalletAmount,""), Toast.LENGTH_SHORT).show();
                            float checkcartprice = 0;

                            try {
                                checkcartprice = Float.parseFloat(String.valueOf(getNumberByString(finalCheckprice.toString())));
                                // Toast.makeText(getActivity(), ""+getNumberByString(finalCheckprice.toString()), Toast.LENGTH_SHORT).show();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            final AppSharedPreferences Appp = new AppSharedPreferences(getActivity());
                            int WalletPricessd = Integer.parseInt(WalletPricess.toString());
                            String voucherStatus = Appp.pref.getString(Appp.SETCOUPON, "");


                            if ((WalletPricessd != 0) && (checkcartprice >= 1000) && (voucherStatus.isEmpty())) {
                                walletcash.setChecked(true);
                            } else {
                                walletcash.setChecked(false);
                            }
                        } else {
                            walletcash.setChecked(false);
                        }
                    }
                    else
                    {
                        wallet_layout.setVisibility(View.GONE);
                    }

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


    public void LoadCart(){

        AppSharedPreferences app = new AppSharedPreferences(getActivity());

        String customer_id = app.pref.getString(app.UserId,"");
        String session_key = app.pref.getString(app.SESSIONKEY,"");
        String coupon = app.pref.getString(app.SETCOUPON,"");
        String voucher = app.pref.getString(app.SETGIFT,"");



        String WalletAmount  = String.valueOf(app.pref.getString(app.WalletAmount,""));
      Log.i("",ClassAPI.VIEWCART+customer_id+"&session_key="+session_key+"&coupon="+coupon+"&voucher="+voucher+"&wallet="+WalletAmount);
        VolleyUtils.makeJsonObjectRequestNonCache(getActivity(), ClassAPI.VIEWCART+customer_id+"&session_key="+session_key+"&coupon="+coupon+"&voucher="+voucher+"&wallet="+WalletAmount, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {
                Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object response) {
                cartFragment.setVisibility(View.VISIBLE);
              // Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
               extractDatacart((String) response);

               pg.hide();
            }

        });




    }




    public void extractDatacart(String data)

    {
      //  Log.i("",data);

        Viewcartmodel = new ArrayList<>();
        CartPriceListModel = new ArrayList<>();

        JSONObject json = null;

        try {

            json = new JSONObject(data);

            ViewCartArray = json.getJSONArray(ClassAPI.JSON_ARRAY);


            TotalDetail = json.getJSONArray("Total");





            AppSharedPreferences App = new AppSharedPreferences(getActivity());
            App.editor.commit();

            for(int i=0;i<ViewCartArray.length();i++)

            {


                JSONObject object = ViewCartArray.getJSONObject(i);
                ViewCartModel dataset = new ViewCartModel();
                String image = object.getString("thumb");
                image = image.replace(" ","%20");
                dataset.setImageThumb(image);
                dataset.setName(object.getString("name"));
                dataset.setPrice(object.getString("price"));
                dataset.setQuantity(object.getString("quantity"));
                dataset.setTotal(object.getString("total"));
                dataset.setCart_id(object.getInt("cart_id"));
                dataset.setPrduct_id(object.getInt("product_id"));
                Viewcartmodel.add(dataset);


            }
            ClassAPI.set_title.setText("My Cart ("+ViewCartArray.length()+")");




            ListView priceList = (ListView)getView().findViewById(R.id.priceList);

            for(int j=0;j<TotalDetail.length();j++) {
                JSONObject object = TotalDetail.getJSONObject(j);

                CartPriceListModel get = new CartPriceListModel();
                get.setName(object.getString("title"));
                get.setValue(object.getString("text"));
                CartPriceListModel.add(get);
                if(object.getString("title").equals("Total")) {
                    final Typeface childFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Rupee_Foradian.ttf");
                    proceedPrice.setTypeface(childFont);
                    finalCheckprice = object.getString("text");
                    proceedPrice.setText("`"+object.getString("text"));

                }

            }

            //Toast.makeText(getActivity(), ""+finalCheckprice.toString(), Toast.LENGTH_SHORT).show();

            CartPriceListAdapter CartPriceListAdapter = new CartPriceListAdapter(getActivity(),CartPriceListModel);
            priceList.setAdapter(CartPriceListAdapter);
            Utility.setListViewHeightBasedOnChildren(priceList);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        LinearLayout gotoprice = (LinearLayout)getView().findViewById(R.id.gotoprice);
        gotoprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        ViewCartAdapter  = new ViewCartAdapter(getActivity(),Viewcartmodel,ViewCartFragment.this);
        listcartview.setAdapter(ViewCartAdapter);

        Utility.setListViewHeightBasedOnChildren(listcartview);



    }







    public  void removeCartProduct(int cart_id)
    {
        final AppSharedPreferences app = new AppSharedPreferences(getActivity());

        String customer_id = app.pref.getString(app.UserId,"");
        String session_key = app.pref.getString(app.SESSIONKEY,"");





        final int count = app.pref.getInt(app.CARTVIEWCOUNT,0);

        String setcount = String.valueOf(count);
        ClassAPI.cartViewCount.setText(setcount);

        VolleyUtils.makeJsonObjectRequestNonCache(getActivity(), ClassAPI.REMOVECART+cart_id+"&customer_id="+customer_id+"&session_key="+session_key+"&wallet=0", new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {
                Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object response) {




            }

        });


    }

}
