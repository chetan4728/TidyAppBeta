package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.UserRatingAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.UserReviewModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Review_RatingFragment extends Fragment {



    ListView reviewList;
    UserRatingAdapter UserRatingAdapter;
    ArrayList<UserReviewModel> UserRating;
    String product_id;
    String Product_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment






        UserRating = new ArrayList<>();
        final String ReviewDataset = getArguments().getString("ReviewDataset");
          product_id = getArguments().getString("Product_id");
        Product_name = getArguments().getString("ProductTitle");

        try {
            JSONArray result = new JSONArray(ReviewDataset);
            for(int i=0;i<result.length();i++) {
                UserReviewModel object = new UserReviewModel();

                JSONObject json_data = result.getJSONObject(i);
                object.setReview(json_data.getString("text"));
                object.setUserrating(json_data.getString("rating"));
                object.setReviewerName(json_data.getString("author"));
                object.setDateon(json_data.getString("date_added"));
                UserRating.add(object);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final View view = inflater.inflate(R.layout.fragment_review__rating, container, false);



Button add_review = (Button)view.findViewById(R.id.add_review);
        add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View add_review_layout =  ((LayoutInflater)(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.add_review_layout, null, false);
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Add Review");
                // this is set the view from XML inside AlertDialog
                alert.setView(add_review_layout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                            }



                });
                final AlertDialog dialog = alert.create();
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Boolean wantToCloseDialog = false;

                        final EditText UserName= (EditText)add_review_layout.findViewById(R.id.UserName);
                        final EditText Userreview= (EditText)add_review_layout.findViewById(R.id.Userreview);
                        final RatingBar Userrating = (RatingBar)add_review_layout.findViewById(R.id.Userrating);
                        AppSharedPreferences app = new AppSharedPreferences(getActivity());
                        final String UserId  = app.pref.getString(app.UserId,"");
                        final String EmailId  = app.pref.getString(app.EmailId,"");



                        if(Userreview.getText().length()==0)
                        {
                            Toast.makeText(getActivity(), "Please Enter Reivew", Toast.LENGTH_SHORT).show();
                        }
                        else if(Userrating.getRating() ==0)
                        {
                            Toast.makeText(getActivity(), "Please Give Rating", Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.SAVERATING,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();


                                            if(response.equals("success")) {

                                                Toast.makeText(getActivity(), "Your Review Added Successfully", Toast.LENGTH_SHORT).show();
                                                UserName.setText("");
                                                Userreview.setText("");
                                                Userrating.setRating(0);
                                                dialog.dismiss();

                                            }
                                            else
                                            {
                                                Toast.makeText(getActivity(),"opps Somthing get's Worng",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(),""+error,Toast.LENGTH_LONG).show();
                                        }
                                    }){
                                @Override
                                protected Map<String,String> getParams(){
                                    Map<String,String> params = new HashMap<String, String>();
                                    params.put("name",UserName.getText().toString().trim());
                                    params.put("text",Userreview.getText().toString().trim());
                                    params.put("rating", String.valueOf(Userrating.getRating()));
                                    params.put("product_id", String.valueOf(product_id));
                                    params.put("session_user_id",UserId);
                                    params.put("session_email",EmailId);
                                    return params;
                                }

                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                            requestQueue.add(stringRequest);

                        }



                        if(wantToCloseDialog)
                            dialog.dismiss();
                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                    }
                });

                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Boolean wantToCloseDialog = true;
                        //Do stuff, possibly set wantToCloseDialog to true then...
                        if(wantToCloseDialog)
                            dialog.dismiss();
                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                    }
                });
            }
        });




        reviewList = (ListView)view.findViewById(R.id.reviewList);
        UserRatingAdapter UserRatingAdapter = new UserRatingAdapter(getActivity(),UserRating);
        reviewList.setAdapter(UserRatingAdapter);


        return view;
    }

}
