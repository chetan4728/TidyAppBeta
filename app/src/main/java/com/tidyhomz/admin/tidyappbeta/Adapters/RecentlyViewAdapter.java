package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.tidyhomz.admin.tidyappbeta.Dataset.RecentlyViewdModel;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Admin on 6/27/2017.
 */

public class RecentlyViewAdapter extends BaseAdapter {

    private  Context context;
    private ArrayList<RecentlyViewdModel> RecentArray;
    public RecentlyViewAdapter(Context context, ArrayList<RecentlyViewdModel> RecentArray) {
        this.context =context;
        this.RecentArray = RecentArray;
    }

    @Override

    public int getCount() {


        return RecentArray.size();
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


        LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = view.inflate(R.layout.recently_view_item, null);
      final  RecentlyViewdModel mobject = RecentArray.get(position);

        TextView product_name = (TextView) convertView.findViewById(R.id.ReName);
        TextView prodcutPrice = (TextView) convertView.findViewById(R.id.prodcutPrice);
        TextView discount = (TextView) convertView.findViewById(R.id.discount);


        TextView specialprodcutPrice = (TextView) convertView.findViewById(R.id.specialprodcutPrice);
        TextView symobi2 = (TextView) convertView.findViewById(R.id.symobi2);
        TextView symobi1 = (TextView) convertView.findViewById(R.id.symobi1);
        final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");
        symobi2.setTypeface(childFont);
        symobi2.setText("`");
        symobi1.setTypeface(childFont);
        symobi1.setText("`");

        if (mobject.getSpecialProductPriice().equalsIgnoreCase("false")) {

            specialprodcutPrice.setText(mobject.getProductPriice());
            prodcutPrice.setVisibility(View.GONE);
        } else {
            prodcutPrice.setVisibility(View.VISIBLE);

            prodcutPrice.setPaintFlags(prodcutPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            specialprodcutPrice.setText(mobject.getSpecialProductPriice());

            discount.setText(mobject.getDiscount());
        }
//        NetworkImageView img = (NetworkImageView) convertView.findViewById(R.id.product_Image);
        String imageUrl = mobject.getProductImage();
        imageUrl = imageUrl.replace(" ", "%20");


        product_name.setText(mobject.getProductName());
        prodcutPrice.setText(mobject.getProductPriice());

        SimpleDraweeView draweeView = (SimpleDraweeView)  convertView.findViewById(R.id.ReImage);
        GenericDraweeHierarchy hierarchy =  GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setPlaceholderImage(R.drawable.placeholder2).build();
        draweeView.setHierarchy(hierarchy);
        draweeView.setImageURI(imageUrl);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).showProductDetail(mobject.getProductId(),mobject.getProductName());
            }
        });

        return convertView;
    }
}
