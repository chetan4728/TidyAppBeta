package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avocarrot.json2view.DynamicView;
import com.avocarrot.json2view.DynamicViewId;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.SpecialBanerAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.special_banner_product;
import com.tidyhomz.admin.tidyappbeta.Helpers.MyGridView;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialFragmentBanner extends Fragment implements View.OnClickListener  {


    JSONObject jsonObject;
    ProgressBar pb;
    private static String[] MOBILE_MODELS = {"iPhone 6","Nexus 6","Moto G","HTC One","Galaxy S5","Sony Xperia Z2","Lumia 830","Galaxy Grand 2"};
    View sampleView;
    FrameLayout offer;
    MyGridView DynamicGridview;
    RelativeLayout item;
    ArrayList<special_banner_product> special_banner_product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ClassAPI.secondarytoolbar .setVisibility(View.GONE);
        sampleView =  inflater.inflate(R.layout.fragment_special_fragment_banner, container, false);
        item = (RelativeLayout)sampleView.findViewById(R.id.test);
         pb = new ProgressBar(getActivity());
       // DynamicGridview = (MyGridView)view.findViewById(R.id.DynamicGridview);
        //GetURLData();
        //GetJSONfile();

        pb.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ClassAPI.SERVERJSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                        try {



                            jsonObject = new JSONObject(response);



                            if (jsonObject != null) {



                                sampleView = DynamicView.createView(getActivity(), jsonObject, SampleViewHolder.class);

                                item.addView(sampleView);
                                GetURLData();

                                pb.hide();



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
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        return sampleView;
    }





    public void GetURLData() {





        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.SpecialOfferBanner,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

                        JSONArray SpecialBannerArray;
                        JSONObject Json = null;
                        special_banner_product = new ArrayList<>();

                        try {
                            Json = new JSONObject(response);
                            SpecialBannerArray = Json.getJSONArray("result");
                            JSONArray ProductList;

                            ProductList = SpecialBannerArray.getJSONObject(0).getJSONArray("product");


                            for(int i=0;i<ProductList.length();i++)
                            {
                                JSONObject jsonObjectdata = ProductList.getJSONObject(i);

                                special_banner_product setdata = new special_banner_product();
                                setdata.setImage(jsonObjectdata.getString("product_image"));
                                setdata.setProduct_id(jsonObjectdata.getInt("product_id"));
                                setdata.setName(jsonObjectdata.getString("product_name"));

                                special_banner_product.add(setdata);
                            }


                             SpecialBanerAdapter sp = new SpecialBanerAdapter(getActivity(),special_banner_product);
                            ((SampleViewHolder) sampleView.getTag()).productList.setAdapter(sp);
                            ((SampleViewHolder) sampleView.getTag()).productList.setNumColumns(2);
                            ((SampleViewHolder) sampleView.getTag()).productList.setVerticalSpacing(10);



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
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    @Override
    public void onClick(View v) {

    }

    static public class SampleViewHolder {
        @DynamicViewId(id = "product1")public View clickableView;

        @DynamicViewId(id = "productList")public MyGridView productList;
        public SampleViewHolder() {
        }
    }
}
