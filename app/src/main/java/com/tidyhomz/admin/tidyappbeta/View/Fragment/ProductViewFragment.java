package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.ProductGridViewAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.ProductGridViewModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProductViewFragment extends  Fragment implements View.OnClickListener {



private ProductGridViewAdapter ProductGridViewAdapter;
private ArrayList<ProductGridViewModel> ProductGridList ;
private JSONArray ProductJson = null;

private Context context;
private GridView GridviewList;
private int  CurrentPageLimit = 1;
private String paramCatId;
private ProgressBar progressBar;
private RecyclerView rcviewList;
private ListView ProductListview;
private String  grid = "Grid";
private  String List ="List";
        AlertDialog levelDialog;
        AppSharedPreferences App;



@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_view, container, false);

        App = new AppSharedPreferences(getActivity());
        prepareListData();



        context = ((AppCompatActivity)getActivity());
        GridviewList = (GridView) view.findViewById(R.id.gridViewProduct);
        ProductListview = (ListView)view.findViewById(R.id.ProductListview);


        final ToggleButton togglebutton = (ToggleButton)view.findViewById(R.id.toggleList);
        final LinearLayout gridviewcontainer  = (LinearLayout)view.findViewById(R.id.gridviewcontainer);
        togglebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(togglebutton.isChecked()) {
                               ProductListview.setVisibility(View.VISIBLE);
                               gridviewcontainer.setVisibility(View.INVISIBLE);
                               ProductGridViewAdapter = new ProductGridViewAdapter(context,ProductGridList,List);
                               ProductListview.setAdapter(ProductGridViewAdapter);


                        }
                        else {
                                ProductListview.setVisibility(View.GONE);
                                gridviewcontainer.setVisibility(View.VISIBLE);
                                ProductGridViewAdapter = new ProductGridViewAdapter(context, ProductGridList, grid);
                                GridviewList.setAdapter(ProductGridViewAdapter);
                        }

                }

        });



        LinearLayout Sort = (LinearLayout)view.findViewById(R.id.SortView);
        Sort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        sort();
                }
        });

        LinearLayout filters  =(LinearLayout)view.findViewById(R.id.filterList);

        filters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        filter();

                }
        });





        return view;

        }


@Override
public void onClick(View v) {
        int id = v.getId();





        }

public void prepareListData() {


        paramCatId = getArguments().getString("paramCatId");
        progressBar = new ProgressBar(getActivity());
        progressBar.show();
        String URL = null;
        String filter = App.pref.getString(App.FILTER,"");
        if(filter!="")
        {
                 URL= ClassAPI.TAG_LAOD_PRODUCT+paramCatId+"&"+filter;

        }
        else
        {
                URL= ClassAPI.TAG_LAOD_PRODUCT+paramCatId;

        }


        VolleyUtils.makeJsonObjectRequestNonCache(getActivity(), URL, new VolleyUtils.VolleyResponseListener() {
                @Override
                public void onError(VolleyError message) {

                        }

                @Override
                public void onResponse(Object response) {
                        ExtractProductData((String) response);

                        progressBar.hide();
                        }
                        });

        }

public void ExtractProductData(String data)
        {




        ProductGridList  = new ArrayList<>();
        JSONObject jsonObject=null;

        try {
        jsonObject = new JSONObject(data);
        ProductJson = jsonObject.getJSONArray(ClassAPI.PRODUCT_DATA);



        for(int k=0;k<ProductJson.length();k++) {

        ProductGridViewModel ModelObject = new ProductGridViewModel();
        JSONObject PrductItem = ProductJson.getJSONObject(k);
                String name = PrductItem.getString("name");
                name = name.replace("&amp;","&");
                ModelObject.setProductName(name);
                ModelObject.setProductImage(PrductItem.getString("image"));
                ModelObject.setProductDiscount(PrductItem.getString("discount"));
                ModelObject.setProductId(PrductItem.getInt("product_id"));
                ModelObject.setProductRating(PrductItem.getString("rating"));
                ModelObject.setProductPrice(PrductItem.getString("price"));
                ModelObject.setProductSpecial(PrductItem.getString("special"));
                ModelObject.setProductStock(PrductItem.getString("stock_status"));
                ModelObject.setQuntity(PrductItem.getInt("quantity"));
                ProductGridList.add(ModelObject);

        }




        } catch (JSONException e) {
        e.printStackTrace();
        }

        initGridList();
        }




        public  void initGridList()

        {

                ProductGridViewAdapter = new ProductGridViewAdapter(context,ProductGridList,grid);
                GridviewList.setAdapter(ProductGridViewAdapter);
                ProductGridViewAdapter = new ProductGridViewAdapter(context,ProductGridList,List);
                ProductListview.setAdapter(ProductGridViewAdapter);

        }

        private static int selectedDatePosition = 0;

                        private  void filter()
                        {

                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(getActivity());
                                builder.setTitle("Filter By");

                                final String[] myBooks =
                                        {" Price (Low > High) "," Price (High > Low) "};

                                final String[]  choiceData =
                                        {"sort=p.price&order=ASC","sort=p.price&order=DESC"};
                                builder.setSingleChoiceItems(
                                        myBooks,
                                        selected,
                                        new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                        //set to buffKey instead of selected
                                                        //(when cancel not save to selected)
                                                        buffKey = which;
                                                }
                                        })
                                        .setCancelable(false)
                                        .setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener()
                                                {
                                                        @Override
                                                        public void onClick(DialogInterface dialog,
                                                                            int which) {



                                                                String q = choiceData[buffKey];
                                                                App.editor.putString(App.FILTER,q);
                                                                App.editor.commit();
                                                                prepareListData();
                                                                selected = buffKey;
                                                        }














                                                }
                                        )
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener()
                                                {
                                                        @Override
                                                        public void onClick(DialogInterface dialog,
                                                                            int which) {

                                                        }
                                                }
                                        );

                                AlertDialog alert = builder.create();
                                alert.show();
                        }
        private int selected = 0;
        private int buffKey = 0; // add buffer value
        private void sort() {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setTitle("Sort By");

                final String[] myBooks =
                        {" Name (A - Z) "," Name (Z - A) "," Price (Low > High) "," Price (High > Low) "," Rating (Highest)"," Rating (Lowest)"};

                final String[]  choiceData =
                        {"sort=pd.name&order=ASC","sort=pd.name&order=DESC","sort=p.price&order=ASC","sort=p.price&order=DESC","sort=rating&order=DESC","sort=rating&order=ASC"};
                builder.setSingleChoiceItems(
                        myBooks,
                        selected,
                        new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        //set to buffKey instead of selected
                                        //(when cancel not save to selected)
                                        buffKey = which;
                                }
                        })
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener()
                                {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {



                                               String q = choiceData[buffKey];
                                                App.editor.putString(App.FILTER,q);
                                                App.editor.commit();
                                                prepareListData();
                                                selected = buffKey;
                                        }














                                }
                        )
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener()
                                {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                        }
                                }
                        );

                AlertDialog alert = builder.create();
                alert.show();
        }



        }
