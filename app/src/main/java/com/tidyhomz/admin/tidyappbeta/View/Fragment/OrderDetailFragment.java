package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.OrderHistoryProductDetailAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.OrderDetailProductModel;
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


public class OrderDetailFragment extends Fragment {


    View view;
    int order_id;
    String Customer_id;
    String date_Added;
    JSONArray Jsonarray;
    ListView productDetail;
    ArrayList<OrderDetailProductModel> OrderDetailProductModel;
    OrderHistoryProductDetailAdapter OrderHistoryProductDetailAdapter;
    JSONArray JsonarrayProduct;
    JSONArray ProductPrice;
    TableLayout totalPricetable;
    JSONArray ProductHistory;
    TableLayout historyTable;
    ProgressBar progressBar;
    String Status;
    Button cancelOrder;

    TableRow TableRow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        order_id = getArguments().getInt("order_id");
        ClassAPI.set_title.setText("Order Information");
        date_Added = getArguments().getString("order_date");
        Status= getArguments().getString("status");



        view =  inflater.inflate(R.layout.fragment_order_detail, container, false);

        cancelOrder = (Button)view.findViewById(R.id.cancelOrder);

        if(Status.equals("Processing")||Status.equals("Pending"))
        {
            cancelOrder.setVisibility(View.VISIBLE);

        }


        productDetail = (ListView)view.findViewById(R.id.productDetail);
        AppSharedPreferences App = new AppSharedPreferences(getActivity());
        Customer_id = App.pref.getString(App.UserId,"");
        TextView OrderIdView = (TextView)view.findViewById(R.id.OrderIdView);
        OrderIdView.setText("Order ID: #"+order_id);
        totalPricetable = (TableLayout)view.findViewById(R.id.totalPricetable);
        historyTable = (TableLayout)view.findViewById(R.id.historyTable);
        TextView OrderDateAddedView = (TextView)view.findViewById(R.id.OrderDateAddedView);
        OrderDateAddedView.setText(date_Added);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.ORDERHISTORYDETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                      // Log.e("",response);
                        data(response);

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
                params.put("order_id", String.valueOf(order_id));
                params.put("customer_id",Customer_id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        progressBar = new ProgressBar(getActivity());
        progressBar.show();
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Cancel Order!")
                        .setMessage("Do you really want to cancel the order ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                cancel_order();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();


               /* new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Do you really want to cancel order?")
                        .setCancelText("No")
                        .setConfirmText("Yes")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                                cancel_order();
                            }
                        })
                        .show();*/





            }
        });
        return view;
    }

public  void cancel_order()
{
    StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.CANCELORDER,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();

                    getActivity().getSupportFragmentManager().popBackStack();
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
            params.put("order_id", String.valueOf(order_id));
            params.put("customer_id",Customer_id);
            return params;
        }

    };

    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
    requestQueue.add(stringRequest);
}
    public void data(String response)
    {

        JSONObject json = null;
        try {
            json = new JSONObject(response);
            Jsonarray = json.getJSONArray("result");


            TextView paymentMethod = (TextView)view.findViewById(R.id.paymentMethod);
            TextView shippongtMethod = (TextView)view.findViewById(R.id.shippongtMethod);
            TextView Paymentaddresss = (TextView)view.findViewById(R.id.Paymentaddresss);
            TextView Shippingaddresss = (TextView)view.findViewById(R.id.Shippingaddresss);
            shippongtMethod.setText(Jsonarray.getJSONObject(0).getString("shipping_method"));


            String[] splitStr = Jsonarray.getJSONObject(0).getString("payment_method").trim().split("&amp;");

                paymentMethod.setText(splitStr[0]);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Paymentaddresss.setText(Html.fromHtml(Jsonarray.getJSONObject(0).getString("payment_address"),Html.FROM_HTML_MODE_LEGACY));
            } else {
                Paymentaddresss.setText(Html.fromHtml(Jsonarray.getJSONObject(0).getString("payment_address")));
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Shippingaddresss.setText(Html.fromHtml(Jsonarray.getJSONObject(0).getString("shipping_address"),Html.FROM_HTML_MODE_LEGACY));
            } else {
                Shippingaddresss.setText(Html.fromHtml(Jsonarray.getJSONObject(0).getString("shipping_address")));
            }

            JsonarrayProduct = Jsonarray.getJSONObject(0).getJSONArray("products");

            OrderDetailProductModel = new ArrayList<>();
            for(int j=0 ;j < JsonarrayProduct.length();j++)
            {

                OrderDetailProductModel setdata = new OrderDetailProductModel();
                JSONObject object = JsonarrayProduct.getJSONObject(j);
                setdata.setName(object.getString("name"));
                setdata.setProduct_id(object.getInt("product_id"));
                setdata.setOrder_id(object.getString("order_id"));
                setdata.setModel(object.getString("model"));
                setdata.setOption(object.getJSONArray("option"));
                setdata.setPrice(object.getString("price"));
                setdata.setQuantity(object.getString("quantity"));
                setdata.setTotal(object.getString("total"));
                setdata.setOrder_product_id(object.getString("order_product_id"));
                OrderDetailProductModel.add(setdata);

            }
            ProductPrice = Jsonarray.getJSONObject(0).getJSONArray("totals");
            ProductHistory = Jsonarray.getJSONObject(0).getJSONArray("histories");


            for(int k =0 ;k<ProductPrice.length();k++)
            {
                JSONObject object = ProductPrice.getJSONObject(k);

                TableRow = new TableRow(getActivity());
                TableRow.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                TextView Title = new TextView(getActivity());
                String attribute = object.getString("title").replaceFirst("\\s++$", "");
                Title.setText(attribute);
                Title.setTextColor(Color.parseColor("#616161"));
                Title.setGravity(Gravity.LEFT);
                Title.setTextSize(15);
                Title.setPadding(0,20,30,10);
                TableRow.addView(Title);


                TextView amount = new TextView(getActivity());
                final Typeface childFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Rupee_Foradian.ttf");
                amount.setTypeface(childFont);
                String value = "`"+object.getString("text").replaceFirst("\\s++$", "");
                amount.setText(value);
                amount.setTextColor(Color.parseColor("#616161"));
                amount.setGravity(Gravity.LEFT);
                amount.setTextSize(15);
                amount.setPadding(0,20,30,10);
                TableRow.addView(amount);




                totalPricetable.addView(TableRow, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                totalPricetable.setColumnShrinkable(1,true);

            }



            TableRow = new TableRow(getActivity());
            TableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            TableRow.setWeightSum(1);
            TableRow.setLayoutParams(new TableRow.LayoutParams(android.widget.TableRow.LayoutParams.WRAP_CONTENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
            TableRow.setWeightSum(3);
            TableRow.LayoutParams lp1;

            TableRow.setBackgroundResource(R.color.cardview_shadow_start_color);
            TableRow.setPadding(10,10,10,10);

            lp1 = new TableRow.LayoutParams(0, android.widget.TableRow.LayoutParams.WRAP_CONTENT, 1);
            TextView Title1 = new TextView(getActivity());
            Title1.setLayoutParams(lp1);
            Title1.setPadding(10,10,0,0);
            String attribute1 = "Date Added";
            Title1.setText(attribute1);
            Title1.setTextColor(Color.parseColor("#616161"));
            Title1.setGravity(Gravity.LEFT);
            Title1.setTextSize(15);
            TableRow.addView(Title1);

            TextView status1 = new TextView(getActivity());
            status1.setLayoutParams(lp1);
            String statusdata1 = "Status";
            status1.setText(statusdata1);
            status1.setPadding(0,10,0,0);
            status1.setTextColor(Color.parseColor("#616161"));
            status1.setGravity(Gravity.LEFT);
            status1.setTextSize(15);
            TableRow.addView(status1);

            TextView coment1 = new TextView(getActivity());
            String comentdata1 = "Comment";
            coment1.setText(comentdata1);
            coment1.setLayoutParams(lp1);
            coment1.setPadding(0,10,0,0);
            coment1.setTextColor(Color.parseColor("#616161"));
            coment1.setGravity(Gravity.LEFT);
            coment1.setTextSize(15);
            TableRow.addView(coment1);

            historyTable.addView(TableRow, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            historyTable.setColumnShrinkable(1,true);

            for(int m = 0; m < ProductHistory.length();m++)
            {
                JSONObject object = ProductHistory.getJSONObject(m);

                TableRow = new TableRow(getActivity());
                TableRow.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                TableRow.setWeightSum(1);
                TableRow.setLayoutParams(new TableRow.LayoutParams(android.widget.TableRow.LayoutParams.WRAP_CONTENT, android.widget.TableRow.LayoutParams.WRAP_CONTENT));
                TableRow.setWeightSum(3);
                TableRow.LayoutParams lp;
                lp = new TableRow.LayoutParams(0, android.widget.TableRow.LayoutParams.WRAP_CONTENT, 1);
                TextView Title = new TextView(getActivity());
                Title.setLayoutParams(lp);
                Title.setPadding(10,10,0,0);
                String attribute = object.getString("date_added").replaceFirst("\\s++$", "");
                Title.setText(attribute);
                Title.setTextColor(Color.parseColor("#616161"));
                Title.setGravity(Gravity.LEFT);
                Title.setTextSize(15);
                TableRow.addView(Title);

                TextView status = new TextView(getActivity());
                status.setLayoutParams(lp);
                String statusdata = object.getString("status").replaceFirst("\\s++$", "");
                status.setText(statusdata);
                status.setPadding(0,10,0,0);
                status.setTextColor(Color.parseColor("#616161"));
                status.setGravity(Gravity.LEFT);
                status.setTextSize(15);
                TableRow.addView(status);

                TextView coment = new TextView(getActivity());
                String comentdata = object.getString("comment").replaceFirst("\\s++$", "");
                coment.setText(comentdata);
                coment.setLayoutParams(lp);
                coment.setPadding(0,10,0,0);
                coment.setTextColor(Color.parseColor("#616161"));
                coment.setGravity(Gravity.LEFT);
                coment.setTextSize(15);
                TableRow.addView(coment);

                historyTable.addView(TableRow, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                historyTable.setColumnShrinkable(1,true);
            }



            OrderHistoryProductDetailAdapter OrderHistoryProductDetailAdapter = new OrderHistoryProductDetailAdapter(getActivity(),OrderDetailProductModel,OrderDetailFragment.this,Status);
            productDetail.setAdapter(OrderHistoryProductDetailAdapter);
            Utility.setListViewHeightBasedOnChildren(productDetail);
            progressBar.hide();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void  return_order(int order_id,int product_id)
    {
        Bundle bundle = new Bundle();
        bundle.putString("order_id", String.valueOf(order_id));
        bundle.putString("product_id", String.valueOf(product_id));
        ProductOrderReturnFragment ProductOrderReturnFragment = new ProductOrderReturnFragment();
      //  FragmentManager fragmentManager = getFragmentManager();
       // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
       // fragmentTransaction.replace(R.id.Fragment_container, ProductOrderReturnFragment);
      ProductOrderReturnFragment.setArguments(bundle);
       // fragmentTransaction.commit();
        ((MainActivity)getActivity()).replaceFragment(ProductOrderReturnFragment);
    }

}
