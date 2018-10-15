package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.SearchAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.SearchHistroryAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.ModelSearch;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    ArrayList<ModelSearch> SerachArray;
    SearchHistroryAdapter SearchHistroryAdapter;
    private JSONArray SearchListData;
    private SearchAdapter SearchAdapter;
    private ArrayList<ModelSearch> ArrayList;
    ProgressBar progressBar;
    ListView ls,historyls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ClassAPI.set_title.setText("Serach Product");
        SearchView  serachLayout = (SearchView)view.findViewById(R.id.serachLayout);
         ls = (ListView)view.findViewById(R.id.list_view);
        historyls = (ListView)view.findViewById(R.id.list_view_histroy);
        serachLayout.setQueryHint("Search Product & More");
        progressBar = new ProgressBar(getActivity());

         serachLayout.setIconified(false);
        ((MainActivity)getActivity()).bothhidebar();





        VolleyUtils.makeJsonObjectRequest(getActivity(),ClassAPI.Domain + "index.php?route=android/api/serachSuggestion&key=4310031881", new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {

                getserachSuggestion(response);



            }
        });




        serachLayout.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BestSellersData(newText);
                return false;
            }
        });

        progressBar.show();

        return view;


    }



    public  void  getserachSuggestion(Object response)
    {

        ArrayList  =  new ArrayList<>();
        JSONObject jsonObject=null;

        try {
            jsonObject = new JSONObject(String.valueOf(response));
            SearchListData = jsonObject.getJSONArray("result");

            for(int i=0;i<SearchListData.length();i++) {
                JSONObject serchgetdata = SearchListData.getJSONObject(i);
                ModelSearch object = new ModelSearch();
                String name = serchgetdata.getString("name");
                int id= serchgetdata.getInt("product_id");
                name = name.replace("&amp;","&");
                String image = serchgetdata.getString("image");


                object.setProductImage(image);
                object.setProductname(name);
                object.setProductid(id);

                ArrayList.add(object);

            }

            SearchHistroryAdapter = new SearchHistroryAdapter(((AppCompatActivity)getActivity()),ArrayList,getView());
            historyls.setAdapter(SearchHistroryAdapter);
            progressBar.hide();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void BestSellersData(final String newText)
    {

        if(newText.length() >= 3) {
            ls.setVisibility(View.VISIBLE);

            VolleyUtils.makeJsonObjectRequest(getActivity(),ClassAPI.Domain + "index.php?route=android/api/getSerchProduct&key=4310031881&filter=" + newText, new VolleyUtils.VolleyResponseListener() {
                @Override
                public void onError(VolleyError message) {

                }

                @Override
                public void onResponse(Object response) {
                    ExtractSearch((String) response);
                }
            });


        }
        else
        {
            ls.setVisibility(View.GONE);
        }

    }

    public void ExtractSearch(String data)
    {

        SerachArray = new ArrayList<>();
        JSONObject jsonObject=null;

        try {
            jsonObject = new JSONObject(data);
            SearchListData = jsonObject.getJSONArray(ClassAPI.PRODUCT_DATA);

            for(int i=0;i<SearchListData.length();i++) {
                JSONObject serchgetdata = SearchListData.getJSONObject(i);
                ModelSearch object = new ModelSearch();
                String name = serchgetdata.getString("name");
                int id= serchgetdata.getInt("product_id");
                name = name.replace("&amp;","&");
                String image = serchgetdata.getString("image");


                object.setProductImage(image);
                object.setProductname(name);
                object.setProductid(id);
                SerachArray.add(object);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        GetExtract();
    }



    public void  GetExtract()
    {
        SearchAdapter = new SearchAdapter(((AppCompatActivity)getActivity()),SerachArray,getView());
        ls.setAdapter(SearchAdapter);
    }



}
