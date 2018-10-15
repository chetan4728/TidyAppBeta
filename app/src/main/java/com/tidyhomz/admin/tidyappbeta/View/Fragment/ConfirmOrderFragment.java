package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.CartPriceListAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.finalProductAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.CartPriceListModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.ViewCartModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.Utility;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.payu.india.Extras.PayUChecksum;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrderFragment extends Fragment {

    View view;
    String order_id = null,finalPrice = null;
    AppSharedPreferences App;
    ProgressBar progressBar;
    Button confirmfinal;
    private String merchantKey, userCredentials;
    private PaymentParams mPaymentParams;
    private PayuConfig payuConfig;
    private PayUChecksum checksum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_confirm_order, container, false);
        progressBar = new ProgressBar(getActivity());
        App = new AppSharedPreferences(getActivity());
        ClassAPI.set_title.setText("Checkout");

         confirmfinal  =(Button)view.findViewById(R.id.confirmfinal);
         confirmfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        confirmOrder();
        progressBar.show();
        return view;
    }


    public void confirmOrder()
    {


        //Toast.makeText(getActivity(), ""+String.valueOf(Html.fromHtml(getArguments().getString("paymentTitle").toString())), Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.CONFIRMORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressBar.hide();
                       ArrayList<ViewCartModel> finaldataArray = new ArrayList<>();

                        ArrayList<CartPriceListModel> CartPriceListModel = new ArrayList<>();

                        JSONObject finalArray;
                       JSONObject ParseJson = null;
                        try {
                            ParseJson = new JSONObject(response);
                            finalArray =  ParseJson.getJSONObject("result");


                            order_id = finalArray.getString("order_id");


                            for(int i=0;i<finalArray.getJSONArray("totals").length();i++)
                            {
                                JSONObject object = finalArray.getJSONArray("totals").getJSONObject(i);

                                CartPriceListModel get = new CartPriceListModel();
                                get.setName(object.getString("title"));
                                get.setValue(object.getString("text"));
                                CartPriceListModel.add(get);
                                   if(object.getString("title").equals("Total"))
                                   {
                                       finalPrice =object.getString("text");
                                   }




                            }


                            for(int i=0;i<finalArray.getJSONArray("products").length();i++)
                            {
                                JSONObject object = finalArray.getJSONArray("products").getJSONObject(i);

                                ViewCartModel setdata = new ViewCartModel();
                                setdata.setPrduct_id(object.getInt("product_id"));
                                setdata.setCart_id(object.getInt("cart_id"));
                                setdata.setName(object.getString("name"));
                                setdata.setQuantity(object.getString("quantity"));
                                setdata.setPrice(String.valueOf(object.getInt("price")).toString());
                                setdata.setTotal(object.getString("total"));
                                setdata.setModel(object.getString("model"));
                                setdata.setOption(object.getJSONArray("option"));
                                setdata.setHSN(object.getString("hsn"));
                                String image = object.getString("image");
                                image = image.replace(" ","%20");
                                //Log.e("",response);
                               setdata.setImageThumb(image);
                                finaldataArray.add(setdata);

                            }

                            ListView productList = (ListView)view.findViewById(R.id.productList);
                            ListView finalPriceList = (ListView)view.findViewById(R.id.finalPriceList);

                            finalProductAdapter finalProductAdapter = new finalProductAdapter(getActivity(),finaldataArray);

                            CartPriceListAdapter CartPriceListAdapter = new CartPriceListAdapter(getActivity(),CartPriceListModel);



                            productList.setAdapter(finalProductAdapter);
                            Utility.setListViewHeightBasedOnChildren(productList);

                            finalPriceList.setAdapter(CartPriceListAdapter);
                            Utility.setListViewHeightBasedOnChildren(finalPriceList);


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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("address_id", App.pref.getString(App.USERADDRESSID,""));
                params.put("address_method", getArguments().getString("address_method"));
                params.put("payment_method", getArguments().getString("Payment_method"));
                params.put("customer_id", App.pref.getString(App.UserId,""));
                params.put("session_key", App.pref.getString(App.SESSIONKEY,""));
                params.put("coupon", App.pref.getString(App.SETCOUPON,""));
                params.put("wallet", App.pref.getString(App.WalletAmount,""));
                params.put("shpping_Method_cost",getArguments().getString("shpping_Method_cost"));
                params.put("Shpinng_method_tax_id",getArguments().getString("Shpinng_method_tax_id"));
                params.put("Shpinng_method_Title",getArguments().getString("ShippingmethodTitle"));
                params.put("payment_method_Title",String.valueOf(Html.fromHtml(getArguments().getString("paymentTitle").toString())));
                params.put("payment_method_Code",getArguments().getString("PaymentCode"));
                params.put("voucher", App.pref.getString(App.SETGIFT,""));
                params.put("comment", getArguments().getString("comment"));
                params.put("hsn", "");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    public  void submitOrder()
    {

        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.Domain+"index.php?route=android/confirm/payment&key="+ClassAPI.key,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                         if(response.equals("cod"))
                         {
                             progressBar.hide();

                             new AlertDialog.Builder(getActivity())
                                     .setTitle("Congratulations")
                                     .setMessage("Your order has been placed successfully.")
                                     .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                         public void onClick(DialogInterface dialog, int whichButton) {
                                             dialog.dismiss();
                                         }})
                                     .setNegativeButton(android.R.string.no, null).show();

                             success();

                             /*new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                     .setTitleText("Place Order ")
                                     .setContentText("Cash on Delivery")
                                     .setCancelText("Cancel")
                                     .setConfirmText("Confirm")
                                     .showCancelButton(true)
                                     .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                         @Override
                                         public void onClick(SweetAlertDialog sDialog) {
                                             sDialog.cancel();



                                         }
                                     })
                                     .show();*/

                            /* new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                     .setTitleText("Congratulations.")
                                     .setContentText("Your Order Placed Successfully.")
                                     .show();
                             success();*/


                         }

                         else if(response.equals("paytm"))
                         {
                            // checksum();
                             progressBar.hide();

                            /* new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                     .setTitleText("Place Order ")
                                     .setContentText("Pay By Paytm (PG)")
                                     .setCancelText("Cancel")
                                     .setConfirmText("Confirm")
                                     .showCancelButton(true)
                                     .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                         @Override
                                         public void onClick(SweetAlertDialog sDialog) {
                                             sDialog.cancel();

                                             //success();
                                         }
                                     })
                                     .show();*/


                             Bundle bundle = new Bundle();
                             PaymentPaytmFragment PaymentPaytmFragment = new PaymentPaytmFragment();
                             bundle.putString("customer_id",App.pref.getString(App.UserId,""));
                             bundle.putString("total",finalPrice);
                             bundle.putString("order_id",order_id);
                             bundle.putString("mobile",App.pref.getString(App.MobileNo,""));
                             bundle.putString("email",App.pref.getString(App.EmailId,""));
                             //FragmentManager fragmentManager = getFragmentManager();
                             //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                             //  fragmentTransaction.replace(R.id.Fragment_container, PaymentPaytmFragment);
                             PaymentPaytmFragment.setArguments(bundle);
                             // fragmentTransaction.commit();
                             ((MainActivity)getActivity()).replaceFragment(PaymentPaytmFragment);





                         }
                         else if(response.equals("payu"))
                         {

                             progressBar.hide();

                            /* new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                     .setTitleText("Place Order ")
                                     .setContentText("Pay By PayU Biz")
                                     .setCancelText("Cancel")
                                     .setConfirmText("Confirm")
                                     .showCancelButton(true)
                                     .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                         @Override
                                         public void onClick(SweetAlertDialog sDialog) {
                                             sDialog.cancel();



                                         }
                                     })
                                     .show();*/


                             Bundle bundle = new Bundle();
                             PaymentPayuFragment PaymentPayuFragment = new PaymentPayuFragment();
                             bundle.putString("customer_id",App.pref.getString(App.UserId,""));
                             bundle.putString("total",finalPrice);
                             bundle.putString("order_id",order_id);
                             bundle.putString("mobile",App.pref.getString(App.MobileNo,""));
                             bundle.putString("email",App.pref.getString(App.EmailId,""));
                             //  FragmentManager fragmentManager = getFragmentManager();
                             //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                             //  fragmentTransaction.replace(R.id.Fragment_container, PaymentPayuFragment);
                             PaymentPayuFragment.setArguments(bundle);
                             // fragmentTransaction.commit();

                             ((MainActivity)getActivity()).replaceFragment(PaymentPayuFragment);




                         }






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

                params.put("payment_method_Code",getArguments().getString("PaymentCode"));
                params.put("order_id", order_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }







    public void success()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.SUCCESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        OrderSuccessFragment OrderSuccessFragment = new OrderSuccessFragment();
                      //  FragmentManager fragmentManager = getFragmentManager();
                      //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                      //  fragmentTransaction.replace(R.id.Fragment_container, OrderSuccessFragment);
                       // fragmentTransaction.commit();
                        ((MainActivity)getActivity()).replaceFragment(OrderSuccessFragment);
                        ((MainActivity) getActivity()).removeOrderData();


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

                params.put("payment_method_Code",getArguments().getString("PaymentCode"));
                params.put("order_id", order_id);
                params.put("customer_id", App.pref.getString(App.UserId,""));
                params.put("firstname", App.pref.getString(App.FIRSTNAME,""));
                params.put("lastname", App.pref.getString(App.LASTNAME,""));
                params.put("mobile", App.pref.getString(App.MobileNo,""));
                params.put("final_total", finalPrice);
                params.put("wallet", App.pref.getString(App.WalletAmount,""));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);



    }
}
