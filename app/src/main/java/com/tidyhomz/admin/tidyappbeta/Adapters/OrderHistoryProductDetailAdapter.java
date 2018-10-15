package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Dataset.OrderDetailProductModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.OrderDetailFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 9/6/2017.
 */

public class OrderHistoryProductDetailAdapter extends BaseAdapter {

    ArrayList<OrderDetailProductModel> array;
    Fragment fragment;
    Context context;
    String status;
    public OrderHistoryProductDetailAdapter(Context context, ArrayList<OrderDetailProductModel> array, Fragment fragment, String status) {
        this.context = context;
        this.array = array;
        this.fragment= fragment;
        this.status = status;

    }

    @Override
    public int getCount() {
        return array.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OrderDetailProductModel getobject = array.get(position);

        if(convertView==null)
        {
            LayoutInflater view = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.order_detail_product_info_item,null);
        }

        TextView productName = (TextView)convertView.findViewById(R.id.productName);
        TextView productModel = (TextView)convertView.findViewById(R.id.productModel);
        TextView productqunitity = (TextView)convertView.findViewById(R.id.productqunitity);
        TextView prodcutPrice = (TextView)convertView.findViewById(R.id.productprice);
        TextView productTotal = (TextView)convertView.findViewById(R.id.productTotal);
        final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");
        productTotal.setTypeface(childFont);
        prodcutPrice.setTypeface(childFont);
        productName.setText("Product Name : "+getobject.getName());
        productModel.setText("Model : "+getobject.getModel());
        productqunitity.setText("Quantity : "+getobject.getQuantity());
        prodcutPrice.setText("Price : "+"`"+getobject.getPrice());
        productTotal.setText("Total : "+"`"+getobject.getTotal());



        Button reorder = (Button)convertView.findViewById(R.id.reorder);
        reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppSharedPreferences App = new AppSharedPreferences(context);
                final String UserId = App.pref.getString(App.UserId,"");
                final  String Session = App.pref.getString(App.SESSIONKEY,"");


                StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.PLACEREORDER,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                                ((MainActivity) context).UserData();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {


                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){

                        Map<String,String> params = new HashMap<String, String>();
                        params.put("customer_id",UserId);
                        params.put("order_id",getobject.getOrder_id());
                        params.put("order_product_id", String.valueOf(getobject.getOrder_product_id()));
                        params.put("Session",Session);
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        });

        Button returnorder = (Button)convertView.findViewById(R.id.returnorder);
        returnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderDetailFragment) fragment).return_order(Integer.parseInt(getobject.getOrder_id()),getobject.getProduct_id());
            }
        });


        if(status.equals("Pending"))
        {
            returnorder.setVisibility(View.GONE);
            reorder.setVisibility(View.GONE);

        }
        if(status.equals("Complete"))
        {
            returnorder.setVisibility(View.VISIBLE);
        }
        else
        {
            returnorder.setVisibility(View.GONE);
        }
        return convertView;
    }
}
