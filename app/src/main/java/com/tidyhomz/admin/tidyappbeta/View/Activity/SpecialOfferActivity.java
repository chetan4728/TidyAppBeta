package com.tidyhomz.admin.tidyappbeta.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avocarrot.json2view.DynamicView;
import com.avocarrot.json2view.DynamicViewId;
import com.avocarrot.json2view.MyGridView;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.SpecialBanerAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.special_banner_product;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.SpecialFragmentBanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpecialOfferActivity extends AppCompatActivity {

    JSONObject jsonObject;
    ArrayList<special_banner_product> special_banner_product;
    View sampleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, ClassAPI.SERVERJSON+"sample.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                        try {



                            jsonObject = new JSONObject(response);



                            if (jsonObject != null) {



                                sampleView = DynamicView.createView(getApplicationContext(), jsonObject, SpecialFragmentBanner.SampleViewHolder.class);



                                sampleView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
                                setContentView(sampleView);

                                //((SampleViewHolder) sampleView.getTag()).productList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,MOBILE_MODELS));
                                 GetURLData();





                            } else {
                                Log.e("Json2View", "Could not load valid json file");

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);




    }

    public void GetURLData() {





        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.SpecialOfferBanner,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray SpecialBannerArray;
                        JSONObject Json = null;
                        special_banner_product = new ArrayList<>();

                        try {
                            Json = new JSONObject(response);
                            SpecialBannerArray = Json.getJSONArray("result");
                            JSONArray ProductListArray;

                            ProductListArray = SpecialBannerArray.getJSONObject(0).getJSONArray("product");


                            for(int i=0;i<ProductListArray.length();i++)
                            {
                                JSONObject jsonObjectdata = ProductListArray.getJSONObject(i);

                                special_banner_product setdata = new special_banner_product();
                                setdata.setImage(jsonObjectdata.getString("product_image"));
                                setdata.setProduct_id(jsonObjectdata.getInt("product_id"));
                                setdata.setName(jsonObjectdata.getString("product_name"));
                                special_banner_product.add(setdata);
                            }

                            SpecialBanerAdapter sp = new SpecialBanerAdapter(getApplicationContext(),special_banner_product);
                            ((SpecialFragmentBanner.SampleViewHolder) sampleView.getTag()).productList.setAdapter(sp);
                            ((SpecialFragmentBanner.SampleViewHolder) sampleView.getTag()).productList.setNumColumns(4);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"opps Somthing get's Worng",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    static public class SampleViewHolder {
        @DynamicViewId(id = "product1")public View clickableView;

        @DynamicViewId(id = "productList")public MyGridView productList;
        public SampleViewHolder() {
        }
    }
}
