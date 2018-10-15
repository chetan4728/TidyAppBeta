package com.tidyhomz.admin.tidyappbeta.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.WishListAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.WishListModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WishListFragment extends Fragment implements View.OnClickListener{

    AppSharedPreferences App;
    RecyclerView WishlistRecyclerView;
    LinearLayout cutometopheader;
    ProgressBar progressBar;
    static ArrayList<Integer> WishlistItem;
    JSONArray ArrivalListData;
    WishListAdapter WishListAdapter;
    public ArrayList<WishListModel> WishListArray;
    public WishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WishlistItem  = new ArrayList<>();
        App = new AppSharedPreferences(getActivity());



        progressBar = new ProgressBar(getActivity());
        progressBar.show();

        int count = App.pref.getInt(App.COUNT, 0);

        for (int i = 0; i < count; i++){
            WishlistItem.add(App.pref.getInt(App.WHISHLIST+i,i));
        }

        if(count > 0) {
            View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
            WishlistRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_Wishlist);


            laodWishlist();
           // cutometopheader = (LinearLayout) view.findViewById(R.id.cutometopheader);
           // cutometopheader.setOnClickListener(this);
           // ClassAPI.MtlistCount = (TextView)view.findViewById(R.id.set_title);
           // ClassAPI.MtlistCount.setText("My Wishlist ("+count+")");
            return view;
        }
        else
        {

            View view2 = inflater.inflate(R.layout.empty_wishlist, container, false);
            //LinearLayout Back =(LinearLayout)view2.findViewById(R.id.cutometopheader);
           // Button Backbtn =(Button)view2.findViewById(R.id.backbutton);
           // Backbtn.setOnClickListener(this);
            //Back.setOnClickListener(this);

            Button backbutton = (Button)view2.findViewById(R.id.backbutton);
            backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int count =  getActivity().getSupportFragmentManager().getBackStackEntryCount();
                    for(int i = 0; i < count; i++) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    ((MainActivity)getActivity()).ShowActionBar();
                    ClassAPI.secondarytoolbar.setVisibility(View.GONE);
                }
            });
            progressBar.hide();
            return view2;
        }


        // Inflate the layout for this fragment

    }


    public void laodWishlist()
    {


        String url = ClassAPI.GET_PRODUCT_WISHLIST+WishlistItem;
        url=url.replaceAll(" ", "%20");


        VolleyUtils.makeJsonObjectRequest(getActivity(), url, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {
                load_data((String) response);
            }
        });



    }

    public void load_data(String response)
    {
        WishListArray = new ArrayList<>();
        JSONObject jsonObject=null;

        try {
            jsonObject = new JSONObject(response);
            ArrivalListData = jsonObject.getJSONArray(ClassAPI.JSON_ARRAY);

            for(int i=0;i<ArrivalListData.length();i++) {

                WishListModel WishListModelModelListObject = new WishListModel();
                JSONObject ImageItem = ArrivalListData.getJSONObject(i);
                WishListModelModelListObject.setProduct_id(ImageItem.getInt("product_id"));
                WishListModelModelListObject.setProductName(ImageItem.getString("name"));
                WishListModelModelListObject.setImage(ImageItem.getString("image"));
                WishListModelModelListObject.setPrice(ImageItem.getString("price"));
                WishListModelModelListObject.setSpecialPrice(ImageItem.getString("special"));
                WishListModelModelListObject.setDiscount(ImageItem.getString("discount"));
                WishListArray.add(WishListModelModelListObject);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setdata();

    }

    public  void setdata()
    {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        WishlistRecyclerView.setLayoutManager(mLayoutManager);

        WishlistRecyclerView.setItemAnimator(new DefaultItemAnimator());

        WishListAdapter = new WishListAdapter(getActivity(),WishListArray);
        WishlistRecyclerView.setAdapter(WishListAdapter);
        progressBar.hide();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

}
