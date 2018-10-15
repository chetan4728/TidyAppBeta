package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.RelatedProductModel;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Admin on 6/16/2017.
 */

public class RelatedProductAdapter extends BaseAdapter {

    private  Context context;
    private  ArrayList<RelatedProductModel> Arraylist;


    public RelatedProductAdapter(Context context, ArrayList<RelatedProductModel> Arraylist) {
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


        final RelatedProductModel Object = Arraylist.get(position);
        if(convertView==null) {
            LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.related_product_item, null);
        }


        TextView gridText   = (TextView) convertView.findViewById(R.id.layout_one_text);
        String imageUrl = Object.getProductImage();
        imageUrl = imageUrl.replace(" ","%20");
        String name =Object.getProductName();
        name =  name.replace("&amp;","&");
        gridText.setText(name);

        SimpleDraweeView draweeView = (SimpleDraweeView)  convertView.findViewById(R.id.layout_one_image);
        GenericDraweeHierarchy hierarchy =  GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setPlaceholderImage(R.drawable.placeholder2).build();
        draweeView.setHierarchy(hierarchy);
        draweeView.setImageURI(imageUrl);

    convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (context instanceof MainActivity) {
                ((MainActivity) context).showProductDetail(Object.getProduct_id(), Object.getProductName());
            }
        }
    });
        return convertView;
    }
}
