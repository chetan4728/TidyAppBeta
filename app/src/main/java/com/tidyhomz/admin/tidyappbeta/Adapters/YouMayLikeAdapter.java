package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.tidyhomz.admin.tidyappbeta.Dataset.YouMayLikeModel;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Admin on 6/21/2017.
 */

public class YouMayLikeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<YouMayLikeModel> ArrayList;

    public YouMayLikeAdapter(Context context, ArrayList<YouMayLikeModel> ArrayList) {

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

        if(convertView==null) {
            LayoutInflater View = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = View.inflate(R.layout.you_may_like_item,null);

        }

       final YouMayLikeModel object = ArrayList.get(position);


        SimpleDraweeView Image = (SimpleDraweeView) convertView.findViewById(R.id.You_two_image);
        TextView gridText   = (TextView) convertView.findViewById(R.id.You_layout_two_text);
        String imageUrl = object.getProductImage();
        imageUrl = imageUrl.replace(" ","%20");
        GenericDraweeHierarchy hierarchy =  GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setPlaceholderImage(R.drawable.placeholder2).build();
        Image.setHierarchy(hierarchy);
        Image.setImageURI(imageUrl);
        Image.setImageURI(imageUrl);

        gridText.setText(object.getProductName());


convertView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        ((MainActivity) context).showProductDetail(object.getProduct_id(),object.getProductName());
    }
});


        return convertView;
    }
}
