package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.ProductReturnReasonAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.return_reasons_model;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.Utility;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProductOrderReturnFragment extends Fragment {


     View view;
    String order_id;
    String customer_id;
    String product_id;
    JSONArray ArrayData;
    JSONArray OrderDetail;
    ArrayList<return_reasons_model> dataarray;
    JSONArray Prodcut_detail;
    JSONArray option_array;
    Button submit_return_detail;
    public TextView checkID;
    ListView resaon;
    EditText Userreview;
   RadioGroup selectreutrunFlag;
    ProgressBar progressBar;
    
    ProductReturnReasonAdapter ProductReturnReasonAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_product_order_return, container, false);
        ClassAPI.ProductResasonFlag.setText(String.valueOf(0));
        progressBar = new ProgressBar(getActivity());
        progressBar.show();
          order_id = getArguments().getString("order_id");
         product_id = getArguments().getString("product_id");
         resaon = (ListView)view.findViewById(R.id.resons);
        final AppSharedPreferences App = new AppSharedPreferences(getActivity());
        customer_id = App.pref.getString(App.UserId,"");
        selectreutrunFlag = (RadioGroup)view.findViewById(R.id.selectreutrunFlag);
        Userreview = (EditText)view.findViewById(R.id.Userreview);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.RETURNORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      // Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                        //Log.e("",response);
                        data(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(),"opps Somthing get's Worng",Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("order_id", String.valueOf(order_id));
                params.put("product_id", String.valueOf(product_id));
                params.put("customer_id",customer_id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        submit_return_detail =(Button)view.findViewById(R.id.submit_return_detail);









        submit_return_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ClassAPI.ProductResasonFlag.getText().equals("0"))
                {
                    Toast.makeText(getActivity(), "You must select a return product reason!", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int j = 0; j < resaon.getChildCount(); j++) {

                        View listItem = resaon.getChildAt(j);
                        final RadioButton optionreason = (RadioButton) listItem.findViewById(R.id.optionreason);

                        if (optionreason.isChecked()) {


                            int checked =  selectreutrunFlag.getCheckedRadioButtonId();
                            final RadioButton radioflag = (RadioButton)view.findViewById(checked);
                            final TextView userName = (TextView)view.findViewById(R.id.userName);
                            final TextView Useremail = (TextView)view.findViewById(R.id.Useremail);
                            final TextView Mobile = (TextView)view.findViewById(R.id.Mobile);
                            final TextView orderId = (TextView)view.findViewById(R.id.orderId);
                            final TextView orderDate = (TextView)view.findViewById(R.id.orderDate);
                            final TextView productname = (TextView)view.findViewById(R.id.productname);
                            final TextView productcode = (TextView)view.findViewById(R.id.productcode);
                            final TextView productQuantity = (TextView)view.findViewById(R.id.productQuantity);



                            StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.ADDRETURN,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();


                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            Toast.makeText(getActivity(),"opps Somthing get's Worng",Toast.LENGTH_LONG).show();
                                        }
                                    })
                            {
                                @Override
                                protected Map<String,String> getParams(){
                                    Map<String,String> params = new HashMap<String, String>();
                                    params.put("order_id", String.valueOf(order_id));
                                    params.put("product_id", String.valueOf(product_id));
                                    params.put("return_reason_id", String.valueOf(optionreason.getTag()));
                                    params.put("opened", String.valueOf(radioflag.getTag()));
                                    params.put("comment", String.valueOf(Userreview.getText()));
                                    params.put("customer_id",customer_id);
                                    params.put("firstname",App.pref.getString(App.FIRSTNAME,""));
                                    params.put("lastname",App.pref.getString(App.LASTNAME,""));
                                    params.put("email", String.valueOf(Useremail.getText()));
                                    params.put("telephone", String.valueOf(Mobile.getText()));
                                    params.put("product", String.valueOf(productname.getText()));
                                    params.put("model", String.valueOf(productcode.getText()));
                                    params.put("quantity", String.valueOf(productQuantity.getText()));
                                    params.put("date_ordered",String.valueOf(orderDate.getText()));

                                    return params;
                                }

                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                            requestQueue.add(stringRequest);


                            //text.getTag();


                        } 


                    }
                }


            }
        });

        return  view;

    }

    public void data(String response)
    {
        JSONObject json = null;
        try {
            json = new JSONObject(response);
            ArrayData = json.getJSONArray("result");
            OrderDetail = ArrayData.getJSONObject(0).getJSONArray("order_detail");

            TextView userName = (TextView)view.findViewById(R.id.userName);
            userName.setText(OrderDetail.getJSONObject(0).getString("firstname")+" "+OrderDetail.getJSONObject(0).getString("lastname"));
            TextView Useremail = (TextView)view.findViewById(R.id.Useremail);
            Useremail.setText(OrderDetail.getJSONObject(0).getString("email"));

            TextView Mobile = (TextView)view.findViewById(R.id.Mobile);
            Mobile.setText(OrderDetail.getJSONObject(0).getString("telephone"));

            TextView orderId = (TextView)view.findViewById(R.id.orderId);
            orderId.setText(OrderDetail.getJSONObject(0).getString("order_id"));

            TextView orderDate = (TextView)view.findViewById(R.id.orderDate);
            orderDate.setText(OrderDetail.getJSONObject(0).getString("date_ordered"));

            Prodcut_detail = ArrayData.getJSONObject(0).getJSONArray("product_detail");

            TextView productname = (TextView)view.findViewById(R.id.productname);
            productname.setText(Prodcut_detail.getJSONObject(0).getString("name"));

            TextView productQuantity = (TextView)view.findViewById(R.id.productQuantity);
            productQuantity.setText(Prodcut_detail.getJSONObject(0).getString("quantity"));

            TextView productcode = (TextView)view.findViewById(R.id.productcode);
            productcode.setText(Prodcut_detail.getJSONObject(0).getString("model"));

            option_array =  ArrayData.getJSONObject(0).getJSONArray("return_reasons");


            dataarray = new ArrayList<>();
            for(int k=0;k<option_array.length();k++)
            {
                return_reasons_model ubj = new return_reasons_model();
                JSONObject object = option_array.getJSONObject(k);

                ubj.setName(object.getString("name"));
                ubj.setReson_id(object.getString("return_reason_id"));
                dataarray.add(ubj);


            }
            ProductReturnReasonAdapter  ProductReturnReasonAdapter = new ProductReturnReasonAdapter(getActivity(),dataarray);
            resaon.setAdapter(ProductReturnReasonAdapter);
            Utility.setListViewHeightBasedOnChildren(resaon);

            progressBar.hide();



            } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
