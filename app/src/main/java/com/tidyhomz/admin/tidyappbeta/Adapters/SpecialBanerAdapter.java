package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tidyhomz.admin.tidyappbeta.Dataset.special_banner_product;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 10/5/2017.
 */

public class SpecialBanerAdapter extends BaseAdapter {

    Context context;
    ArrayList<special_banner_product> special_banner_product;

    public SpecialBanerAdapter(Context context, ArrayList<com.tidyhomz.admin.tidyappbeta.Dataset.special_banner_product> special_banner_product) {
        this.context = context;
        this.special_banner_product = special_banner_product;


    }

    @Override
    public int getCount() {
        return special_banner_product.size();
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

        final special_banner_product getdata = special_banner_product.get(position);
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.special_banner_product_list,null);

        }
        //Log.e("",getdata.getImage());
        ImageView imageview = (ImageView)convertView.findViewById(R.id.imageview);
        Picasso.with(context).load(getdata.getImage()).into(imageview);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) context).showProductDetail(getdata.getProduct_id(),getdata.getName());
            }
        });

        return convertView;
    }
}
