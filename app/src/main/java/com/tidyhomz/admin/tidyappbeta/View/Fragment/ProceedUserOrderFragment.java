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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payu.india.Payu.PayuConstants;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.PaymentMethodAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.ShippingMethodAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.payment_method_model;
import com.tidyhomz.admin.tidyappbeta.Dataset.shipping_method_model;
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

//import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProceedUserOrderFragment extends Fragment {


    View view;
    ExpandableListView checkoutOption;
    ListView ShippingMethodList;
    ListView paymentmethod;
    AppSharedPreferences App;
    ScrollView mainScroll;
    ProgressBar pg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        ClassAPI.set_title.setText("Delivery");
        pg   = new ProgressBar(getActivity());

        // Inflate the layout for this fragment

        view =  inflater.inflate(R.layout.fragment_proceed_user_order, container, false);
        mainScroll = (ScrollView)view.findViewById(R.id.mainScroll);
        TextView paymentName = (TextView)view.findViewById(R.id.paymentName);
        TextView paymentAddress = (TextView)view.findViewById(R.id.paymentAddress);
        TextView paymentmobile = (TextView)view.findViewById(R.id.paymentmobile);

        TextView terms = (TextView)view.findViewById(R.id.terms);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TermsAndConditionFragmnet td = new TermsAndConditionFragmnet();
                ((MainActivity)getActivity()).replaceFragment(td);
            }
        });

        App = new AppSharedPreferences(getActivity());
        paymentName.setText(App.pref.getString(App.USERADDRESSNAME,""));
        paymentAddress.setText(App.pref.getString(App.USERADDRESS,""));
        paymentmobile.setText(App.pref.getString(App.USERADDRESSMOBILE,""));

        if(App.pref.getString(App.USERADDRESSID,"").equals(""))
        {
            TextView addresswarning = (TextView)view.findViewById(R.id.addresswarning);
            addresswarning.setVisibility(View.VISIBLE);



            new AlertDialog.Builder(getActivity())
                    .setTitle("Opps!")
                    .setMessage("Please add your address")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Get me there", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            UserAddressFragment UserAddressFragment = new UserAddressFragment();
                            ((MainActivity)getActivity()).replaceFragment(UserAddressFragment);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            /*new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops")
                    .setContentText("Please Select Address..")
                    .setConfirmText("Get me there")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            UserAddressFragment UserAddressFragment = new UserAddressFragment();
                            ((MainActivity)getActivity()).replaceFragment(UserAddressFragment);

                        }
                    })
                    .show();*/
        }
        else
        {
            TextView addresswarning = (TextView)view.findViewById(R.id.addresswarning);
            LinearLayout addContainer = (LinearLayout)view.findViewById(R.id.addContainer);
            addContainer.setVisibility(View.VISIBLE);
            addresswarning.setVisibility(View.GONE);

        }

        getShippingMethod();
        getPayment_method();



        Button changeaddress = (Button)view.findViewById(R.id.changeaddress);
        changeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAddressFragment UserAddressFragment = new UserAddressFragment();
              //  FragmentManager fragmentManager = getFragmentManager();
                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               // fragmentTransaction.replace(R.id.Fragment_container, UserAddressFragment);
               // fragmentTransaction.addToBackStack(null);
               // fragmentTransaction.commit();

                ((MainActivity)getActivity()).replaceFragment(UserAddressFragment);
            }
        });



        Button submit_order =(Button)view.findViewById(R.id.submit_order);
        submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                RadioButton confiramtion = (RadioButton)view.findViewById(R.id.confiramtion);
                if(confiramtion.isChecked()) {
                    proceedUserOrder();
                }
                else
                {
                    Toast.makeText(getActivity(), "You must agree to the Terms Conditions!", Toast.LENGTH_SHORT).show();


                }
            }
        });


        pg.show();
        mainScroll.setVisibility(View.GONE);
        return view;
    }



    public void getPayment_method()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETPAYMENTMETHOD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       // Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                        //Log.e("",response);

                        ArrayList<payment_method_model> payment_method_model = new ArrayList<>();
                        JSONArray PaymentArray;
                        JSONArray PaymentMethod;
                        JSONObject ParseJson = null;
                        try {
                            ParseJson = new JSONObject(response);




                            PaymentArray =  ParseJson.getJSONArray("result");


                            TextView text_payment_method = (TextView)view.findViewById(R.id.text_payment_method);
                            text_payment_method.setText(PaymentArray.getJSONObject(0).getString("text_payment_method"));

                            TextView Usercoment = (TextView)view.findViewById(R.id.comment);
                            Usercoment.setText(PaymentArray.getJSONObject(0).getString("text_comments"));
                            PaymentMethod = PaymentArray.getJSONObject(0).getJSONArray("payment_methods");


                             paymentmethod =(ListView)view.findViewById(R.id.paymentmethod);



                            try {
                                for(int i=0;i<PaymentMethod.length();i++)
                                {
                                    payment_method_model set = new payment_method_model();
                                    JSONObject Jobject = PaymentMethod.getJSONObject(i);
                                    set.setTitle(Jobject.getString("title"));
                                    set.setCode(Jobject.getString("code"));
                                    set.setOrder(Jobject.getString("sort_order"));
                                    payment_method_model.add(set);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            PaymentMethodAdapter PaymentMethodAdapter = new PaymentMethodAdapter(getActivity(),payment_method_model);
                            paymentmethod.setAdapter(PaymentMethodAdapter);
                            Utility.setListViewHeightBasedOnChildren(paymentmethod);

                            pg.hide();
                            mainScroll.setVisibility(View.VISIBLE);
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
                params.put("customer_id", App.pref.getString(App.UserId,""));
                params.put("session_key", App.pref.getString(App.SESSIONKEY,""));
                params.put("country_id", App.pref.getString(App.USERADDRESSCOUNTREY,""));
                params.put("zone_id", App.pref.getString(App.USERADDRESSZHONE,""));
                params.put("coupon", App.pref.getString(App.SETCOUPON,""));
                params.put("voucher", App.pref.getString(App.SETGIFT,""));
                params.put("wallet", App.pref.getString(App.WalletAmount,""));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    public  void getShippingMethod()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETSHIPPINGMETHOD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                       // Log.e("",response);

                     ArrayList<shipping_method_model> shipping_method_model = new ArrayList<>();
                        JSONArray ShippingArray;
                        JSONArray ShippingMethod;
                        JSONObject Json = null;
                        try {
                            Json = new JSONObject(response);
                            ShippingArray = Json.getJSONArray("result");

                            TextView text_shipping_method = (TextView)view.findViewById(R.id.text_shipping_method);
                            TextView commentText = (TextView)view.findViewById(R.id.comment);
                            text_shipping_method.setText(ShippingArray.getJSONObject(0).getString("text_shipping_method"));
                            commentText.setText(ShippingArray.getJSONObject(0).getString("text_comments"));
                            ShippingMethod = ShippingArray.getJSONObject(0).getJSONArray("method");

                             ShippingMethodList =(ListView)view.findViewById(R.id.ShippingMethodList);




                            for(int i=0; i <ShippingMethod.length();i++)
                            {
                                JSONObject Jobject = ShippingMethod.getJSONObject(i);
                                shipping_method_model obj = new shipping_method_model();
                                obj.setMmethod(Jobject.getJSONObject("quote"));
                                obj.setName(Jobject.getString("title"));
                                obj.setCode(Jobject.getString("code"));
                                shipping_method_model.add(obj);
                            }


                            ShippingMethodAdapter ShippingMethodAdapter = new ShippingMethodAdapter(getActivity(),shipping_method_model);
                            ShippingMethodList.setAdapter(ShippingMethodAdapter);
                            Utility.setListViewHeightBasedOnChildren(ShippingMethodList);
                            pg.hide();
                            mainScroll.setVisibility(View.VISIBLE);


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
                params.put("customer_id", App.pref.getString(App.UserId,""));
                params.put("session_key", App.pref.getString(App.SESSIONKEY,""));
                params.put("country_id", App.pref.getString(App.USERADDRESSCOUNTREY,""));
                params.put("zone_id", App.pref.getString(App.USERADDRESSZHONE,""));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void proceedUserOrder() {

        if (App.pref.getString(App.USERADDRESS, "").isEmpty()) {

            Toast.makeText(getActivity(), "Pleaase Select address", Toast.LENGTH_SHORT).show();

            /*new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops")
                    .setContentText("Please Select Address..")
                    .setConfirmText("Get me there")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            UserAddressFragment UserAddressFragment = new UserAddressFragment();
                            ((MainActivity)getActivity()).replaceFragment(UserAddressFragment);

                        }
                    })
                    .show();*/

        } else {
            String Address_method = null;
            String Payment_method = null;
            String shpping_Method_cost = null;
            String Shpinng_method_tax_id = null;
            String Shipping_method_Title = null;
            String comment = null;

            String paymentTitle = null;
            String PaymentCode = null;

            for (int j = 0; j < ShippingMethodList.getChildCount(); j++) {

                View listItem = ShippingMethodList.getChildAt(j);
                final RadioButton title_id = (RadioButton) listItem.findViewById(R.id.title_id);
                final TextView taxDetail = (TextView) listItem.findViewById(R.id.taxDetail);
                TextView getcomment = (TextView) view.findViewById(R.id.getcomment);
                comment = String.valueOf(getcomment.getText());


                if (title_id.isChecked()) {
                    Address_method = String.valueOf(title_id.getTag());
                    shpping_Method_cost = String.valueOf(taxDetail.getText());
                    Shpinng_method_tax_id = String.valueOf(taxDetail.getTag());
                    Shipping_method_Title = String.valueOf(title_id.getText());
                }
            }

            for (int j = 0; j < paymentmethod.getChildCount(); j++) {

                View listItem = paymentmethod.getChildAt(j);


                final RadioButton paymentType = (RadioButton) listItem.findViewById(R.id.paymentType);
                if (paymentType.isChecked()) {
                    Payment_method = String.valueOf(paymentType.getTag());
                    TextView aymentdata = (TextView) listItem.findViewById(R.id.paymentdata);
                    paymentTitle = String.valueOf(aymentdata.getText());
                    PaymentCode = String.valueOf(aymentdata.getTag());

                }
            }


            ConfirmOrderFragment ConfirmOrderFragment = new ConfirmOrderFragment();
            Bundle bundle = new Bundle();
            bundle.putString("address_method", Address_method);
            bundle.putString("Payment_method", Payment_method);
            bundle.putString("shpping_Method_cost", shpping_Method_cost);
            bundle.putString("Shpinng_method_tax_id", Shpinng_method_tax_id);
            bundle.putString("ShippingmethodTitle", Shipping_method_Title);
            bundle.putString("paymentTitle", paymentTitle);
            bundle.putString("PaymentCode", PaymentCode);
            bundle.putString("comment", comment);
            //FragmentManager fragmentManager = getFragmentManager();
            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.replace(R.id.Fragment_container, ConfirmOrderFragment);
            ConfirmOrderFragment.setArguments(bundle);
            //fragmentTransaction.addToBackStack(null);
           // fragmentTransaction.commit();
            ((MainActivity)getActivity()).replaceFragment(ConfirmOrderFragment);
        }
    }

}
