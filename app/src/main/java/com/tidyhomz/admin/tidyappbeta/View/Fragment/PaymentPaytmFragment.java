package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

//import cn.pedant.SweetAlert.SweetAlertDialog;


public class PaymentPaytmFragment extends Fragment {


   View view;
    String Order_id;
    AppSharedPreferences App;
    LinearLayout error_message;
    float finalwallet = 0;
    int randomNo = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        view =  inflater.inflate(R.layout.fragment_payment_paytm, container, false);
        error_message  = (LinearLayout) view.findViewById(R.id.error_message);
        ClassAPI.set_title.setText("Paytm Payment");

        Random r = new Random();
        randomNo = r.nextInt(1000+1);
        Order_id = getArguments().getString("order_id")+"R"+randomNo;
        App = new AppSharedPreferences(getActivity());





        try {
            finalwallet = Float.parseFloat(String.valueOf(getNumberByString(getArguments().getString("total"))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getContext(), ""+finalwallet, Toast.LENGTH_SHORT).show();

        checksum();
        return view;
    }
    @Override
    public void onStart(){
        super.onStart();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }





    public static double getNumberByString(String s) throws ParseException {
        return NumberFormat.getInstance(Locale.getDefault()).parse(s).doubleValue();
    }

/* Passing data To server for Response */
	public  void checksum()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.PayrmChecksum,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                      //  Log.i("paytmRequest",ClassAPI.PayrmChecksum);
                       // Log.i("paytmResponse",response);

                        JSONObject  JSON = null;
                        JSONArray data;

                        try {
                            JSON = new JSONObject(response);
                            data = JSON.getJSONArray("CHECKSUMHASH");
                            //Toast.makeText(getActivity(), ""+data.getJSONObject(0).getString("CHECKSUMHASH"), Toast.LENGTH_SHORT).show();
                          if(data!=null) {
                              onStartTransaction(data.getJSONObject(0).getString("CHECKSUMHASH"));
                          }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), "Something went wrong!"+error, Toast.LENGTH_SHORT).show();
                       /* new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something went wrong!"+error)
                                .show();*/
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {



                Map<String, String> params = new HashMap<String, String>();
                params.put("MID" , App.pref.getString(App.PAYTM_MID,""));
                params.put("ORDER_ID" ,Order_id);
                params.put("CUST_ID" , App.pref.getString(App.UserId,""));
                params.put("INDUSTRY_TYPE_ID" , App.pref.getString(App.PAYTM_INDUSTRY_TYPE_ID,""));
                params.put("CHANNEL_ID" , "WAP");
                params.put("TXN_AMOUNT" , String.valueOf(finalwallet));
                params.put("WEBSITE" , App.pref.getString(App.PAYTM_WEBSITE,""));
                params.put("EMAIL",  App.pref.getString(App.EmailId,""));
                params.put("MOBILE_NO", App.pref.getString(App.MobileNo,""));
                params.put("CALLBACK_URL" , App.pref.getString(App.PAYTM_CALLBACK_URL,""));
                //Log.e("", String.valueOf(params));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



    public void onStartTransaction(String checksumhash) {

      //PaytmPGService Service = PaytmPGService.getStagingService(); /*Test */
        PaytmPGService Service = PaytmPGService.getProductionService(); /*production */

        //Kindly create complete Map and checksum on your server side and then put it here in paramMap.

        Random r = new Random(System.currentTimeMillis());
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID" ,  App.pref.getString(App.PAYTM_MID,""));
        paramMap.put("ORDER_ID" , Order_id);
        paramMap.put("CUST_ID" , App.pref.getString(App.UserId,""));
        paramMap.put("INDUSTRY_TYPE_ID" , App.pref.getString(App.PAYTM_INDUSTRY_TYPE_ID,""));
        paramMap.put("CHANNEL_ID" , "WAP");

        paramMap.put("TXN_AMOUNT" , String.valueOf(finalwallet));
        paramMap.put("WEBSITE" , App.pref.getString(App.PAYTM_WEBSITE,""));
        paramMap.put("EMAIL",  App.pref.getString(App.EmailId,""));
        paramMap.put("MOBILE_NO", App.pref.getString(App.MobileNo,""));
        paramMap.put("CALLBACK_URL" , App.pref.getString(App.PAYTM_CALLBACK_URL,""));

        paramMap.put("CHECKSUMHASH" , checksumhash);
        PaytmOrder Order = new PaytmOrder(paramMap);

        Service.initialize(Order, null);

        Service.startPaymentTransaction(getActivity(), true, true,
                new PaytmPaymentTransactionCallback() {

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {

                       // Log.i("main1",""+inErrorMessage);
                       // Toast.makeText(getActivity(), ""+inErrorMessage, Toast.LENGTH_SHORT).show();

                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {


                   //Log.i("main2",""+inResponse);
                        String STATUS = inResponse.getString("STATUS");
                        String RESPMSG = inResponse.getString("RESPMSG");

                          //Toast.makeText(getActivity(), ""+RESPMSG, Toast.LENGTH_SHORT).show();



                        if(STATUS.equals("TXN_SUCCESS")) {



                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Congratulations")
                                    .setMessage("Your order has been placed successfully.")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {
                                           dialog.dismiss();
                                        }})
                                    .setNegativeButton(android.R.string.no, null).show();
                            success();


                        }
                        else
                        {


                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Opps!")
                                    .setMessage("Something went wrong! "+RESPMSG)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            ConfirmOrderFragment cn  = new ConfirmOrderFragment();
                                            ((MainActivity)getActivity()).replaceFragment(cn);
                                        }})
                                    .setNegativeButton(android.R.string.no, null).show();

                           /* new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Something went wrong! "+RESPMSG)
                                    .setConfirmText("Try Again")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();

                                            ConfirmOrderFragment cn  = new ConfirmOrderFragment();
                                            ((MainActivity)getActivity()).replaceFragment(cn);
                                        }
                                    })
                                    .show();*/


                        }
                    }

                    @Override
                    public void networkNotAvailable() {
                        // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        //Log.i("main4",""+inErrorMessage);
                       // Toast.makeText(getActivity(), inErrorMessage, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {
                       // Toast.makeText(getActivity(), inErrorMessage, Toast.LENGTH_SHORT).show();
                        //Log.i("main3",""+inErrorMessage);
                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        // TODO Auto-generated method stub


                        new AlertDialog.Builder(getActivity())
                                .setTitle("Opps!")
                                .setMessage("Something went wrong! Transaction process stopped by User")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        ConfirmOrderFragment cn  = new ConfirmOrderFragment();
                                        ((MainActivity)getActivity()).replaceFragment(cn);
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();


                       /* new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something went wrong! Transaction process stopped by User")
                                .setConfirmText("Try Again")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();

                                        ConfirmOrderFragment cn  = new ConfirmOrderFragment();
                                        ((MainActivity)getActivity()).replaceFragment(cn);
                                    }
                                })
                                .show();*/


                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {


                        new AlertDialog.Builder(getActivity())
                                .setTitle("Opps!")
                                .setMessage("Something went wrong! Transaction process stopped by User")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        ConfirmOrderFragment cn  = new ConfirmOrderFragment();
                                        ((MainActivity)getActivity()).replaceFragment(cn);
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();

                        /*new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something went wrong! Transaction Process Stoped By User")
                                .setConfirmText("Try Again")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();

                                        ConfirmOrderFragment cn  = new ConfirmOrderFragment();
                                        ((MainActivity)getActivity()).replaceFragment(cn);
                                    }
                                })
                                .show();*/

                    }

                });
    }
    public void success()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.SUCCESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        AppSharedPreferences App = new AppSharedPreferences(getActivity());
                        App.editor.remove("SETCOUPON");
                        App.editor.remove("SETGIFT");
                        App.editor.remove("COUNT");
                        App.editor.commit();

                        ((MainActivity) getActivity()).UserData();

                        OrderSuccessFragment OrderSuccessFragment = new OrderSuccessFragment();
                        ((MainActivity)getActivity()).replaceFragment(OrderSuccessFragment);



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

                params.put("payment_method_Code","paytm");
                params.put("order_id", getArguments().getString("order_id"));
                params.put("customer_id", App.pref.getString(App.UserId,""));
                params.put("firstname", App.pref.getString(App.FIRSTNAME,""));
                params.put("lastname", App.pref.getString(App.LASTNAME,""));
                params.put("mobile", App.pref.getString(App.MobileNo,""));
                params.put("final_total", getArguments().getString("total"));
                params.put("wallet", App.pref.getString(App.WalletAmount,""));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);



    }
}
