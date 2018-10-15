package com.tidyhomz.admin.tidyappbeta.Helpers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Admin on 6/12/2017.
 */

public class VolleyUtils {




    public interface VolleyResponseListener {
        void onError(VolleyError message);
        void onResponse(Object response);
    }




    public static void makeJsonObjectRequest(Context context, String url, final VolleyResponseListener listener) {

        AppSharedPreferences App = new AppSharedPreferences(context);
        String networkStatus = App.pref.getString(App.NETWORKSTATUS, "");







        CacheRequest cacheRequest = new CacheRequest(0, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                try {
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));

                    JSONObject jsonObject = new JSONObject(jsonString);

                    listener.onResponse(jsonObject.toString());

                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                listener.onError(error);
            }
        });



            VolllyRequest.getInstance(((AppCompatActivity) context)).getRequestQueue().getCache().invalidate(url, true);
            VolllyRequest.getInstance(((AppCompatActivity) context)).addToRequestQueue(cacheRequest);



    }


    public static void makeJsonObjectRequestNonCache(Context context, String url, final VolleyResponseListener listener) {

        AppSharedPreferences App = new AppSharedPreferences(context);
        String networkStatus = App.pref.getString(App.NETWORKSTATUS, "");







        CacheRequest cacheRequest = new CacheRequest(0, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                try {
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));

                    JSONObject jsonObject = new JSONObject(jsonString);

                    listener.onResponse(jsonObject.toString());

                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                listener.onError(error);
            }
        });


        cacheRequest.setRetryPolicy(
                new DefaultRetryPolicy(5000,0,1f));
        // cacheRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT.BACKOFF_MULT));
        if(networkStatus.equals("true")) {
            //Toast.makeText(context, "yess", Toast.LENGTH_SHORT).show();
            VolllyRequest.getInstance(((AppCompatActivity) context)).getRequestQueue().getCache().invalidate(url, true);
        }

        VolllyRequest.getInstance(((AppCompatActivity) context)).getRequestQueue().getCache().invalidate(url, true);

        VolllyRequest.getInstance(((AppCompatActivity) context)).addToRequestQueue(cacheRequest);




    }



}
