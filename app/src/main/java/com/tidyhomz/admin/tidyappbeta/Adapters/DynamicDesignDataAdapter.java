package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.DynamicDesignDataContentModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.DynamicDesignDataModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.MyGridView;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 8/1/2017.
 */

public class DynamicDesignDataAdapter extends BaseAdapter {

    private  Context context;
    private  ArrayList<DynamicDesignDataModel> Arraylist;
    ArrayList<DynamicDesignDataContentModel> dynamicDesignDataContentModel;
    ArrayList<DynamicDesignDataContentModel> dynamicDesignDataContentModelTest;
    DynamicDesignModuleDataAdapter DynamicDesignModuleDataAdapter;

    public DynamicDesignDataAdapter(Context context, ArrayList<DynamicDesignDataModel> Arraylist) {
        this.context = context;
        this.Arraylist = Arraylist;


    }

    @Override
    public int getCount() {
        return Arraylist.size();
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

        dynamicDesignDataContentModelTest = new ArrayList<>();
        final DynamicDesignDataModel Object = Arraylist.get(position);

        if(convertView==null) {
            LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.dynamic_module_container_item, null);
        }

        final MyGridView  containerGrid = (MyGridView)convertView.findViewById(R.id.containerGrid);



        TextView Container_Header = (TextView)convertView.findViewById(R.id.container_heading);
        Container_Header.setText(Object.getModuleName());

        LinearLayout container_view_more = (LinearLayout)convertView.findViewById(R.id.container_view_more);
        container_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, ""+Object.getModule_id(), Toast.LENGTH_SHORT).show();
                ((MainActivity)context).loadMoreProduct(Object.getModuleName(),Object.getModule_id());
            }
        });

        try {
            JSONObject jsonObject =null;
            for(int i=0;i<Object.getModuleData().length();i++) {

                jsonObject = Object.getModuleData().getJSONObject(i);
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






        DynamicDesignModuleDataAdapter = new DynamicDesignModuleDataAdapter(context,dynamicDesignDataContentModelTest);
        containerGrid.setAdapter(DynamicDesignModuleDataAdapter);




        return convertView;
    }
}
