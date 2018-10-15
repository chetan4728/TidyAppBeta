package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tidyhomz.admin.tidyappbeta.Dataset.BannerDatasetModel;
import com.tidyhomz.admin.tidyappbeta.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 9/26/2017.
 */

public class BannerAdapterTop extends BaseAdapter {
    Context context;
    ArrayList<BannerDatasetModel> ArrayListdata;
    String bannerPostion;

    public BannerAdapterTop(Context context, ArrayList<BannerDatasetModel> arrayListdata, String top) {
        this.context = context;
        this.ArrayListdata = arrayListdata;
        this.bannerPostion = top;

    }

    @Override
    public int getCount() {
        return ArrayListdata.size();
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
        BannerDatasetModel get = ArrayListdata.get(position);

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.banner_images_list,null);
        }
           ImageView bannerImage = (ImageView) convertView.findViewById(R.id.bannerImage);



            if(get.getBanner_position().equals(bannerPostion)) {

                Picasso.with(context).load(get.getBanner_image()).into(bannerImage);

            }

        return convertView;
    }
}
