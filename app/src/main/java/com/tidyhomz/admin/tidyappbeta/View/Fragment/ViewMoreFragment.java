package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.DynamicDesignModuleDataAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.DynamicDesignDataContentModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.MyGridView;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMoreFragment extends Fragment {


    ArrayList<DynamicDesignDataContentModel> dynamicDesignDataContentModelTest;
    DynamicDesignModuleDataAdapter DynamicDesignModuleDataAdapter;
    private JSONArray ProductJson = null;
    MyGridView ViewMoreGrid;
    View view;
    ProgressBar pg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pg = new ProgressBar(getActivity());
        pg.show();
        int module_id = getArguments().getInt("module_id");
        String module_name = getArguments().getString("ModuleName");
        ClassAPI.set_title.setText(module_name);
        view = inflater.inflate(R.layout.fragment_view_more, container, false);
        ViewMoreGrid = (MyGridView)view.findViewById(R.id.ViewMoreGrid);
        String url = ClassAPI.MODULEDATAGET+module_id;
        url=url.replaceAll(" ", "%20");

        VolleyUtils.makeJsonObjectRequest(getActivity(), url, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {

                pg.hide();
               ExtractProductData((String) response);

            }
        });


        return view;
    }


    public void ExtractProductData(String data)
    {

        dynamicDesignDataContentModelTest  = new ArrayList<>();

        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(data);
            ProductJson = jsonObject.getJSONArray("result");
            for(int i=0;i<ProductJson.length();i++) {

                jsonObject = ProductJson.getJSONObject(i);
                DynamicDesignDataContentModel obj = new DynamicDesignDataContentModel();
                obj.setProduct_id(jsonObject.getInt("product_id"));
                obj.setProductName(jsonObject.getString("name"));
                obj.setProductImage(jsonObject.getString("image"));
                obj.setModule_id(jsonObject.getInt("moduleId"));
                dynamicDesignDataContentModelTest.add(obj);
            }


        } catch (JSONException e) {
            e.printStackTrace();


        }

        DynamicDesignModuleDataAdapter = new DynamicDesignModuleDataAdapter(getActivity(),dynamicDesignDataContentModelTest);
        ViewMoreGrid.setAdapter(DynamicDesignModuleDataAdapter);
    }

}
