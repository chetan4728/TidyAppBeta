package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Dataset.MainMenuListModel;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Admin on 4/28/2017.
 */

public class MainMenuListAdapter extends BaseAdapter {
    private ArrayList<MainMenuListModel> ArrayList;
    private Activity context;




    public MainMenuListAdapter(Activity context, ArrayList<MainMenuListModel> ArrayList) {
        this.context = context;
        this.ArrayList = ArrayList;

    }

    @Override
    public int getCount() {
        return ArrayList.size();
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

        final  MainMenuListModel Obj = ArrayList.get(position);

            LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.main_menu_list_item, null);


        TextView  textViewId = (TextView) convertView.findViewById(R.id.textViewId);
        TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);
        ImageView menuicon = (ImageView) convertView.findViewById(R.id.menu_icon);

       // Log.e("",ClassAPI.Domain+"image/catalog/android_images/drawable/" + Obj.getProductImage() + ".png");
        Picasso.with(context).load(ClassAPI.Domain+"image/catalog/android_images/drawable/" + Obj.getProductImage() + ".png").into(menuicon);

        String Cat_id = String.valueOf(Obj.getProductId());
        String name = String.valueOf(Obj.getProductName());
        textViewId.setText(Cat_id);
        textViewName.setText(name);


        // Toast.makeText(context, ""+catName[position], Toast.LENGTH_SHORT).show();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (context instanceof MainActivity) {
                    ((MainActivity) context).getCatId(Obj.getProductId(),Obj.getProductName());
                }

                // Do what you want here...

            }
        });

        return convertView;
    }


}