package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Dataset.HorizontalcatModel;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Admin on 5/18/2017.
 */

public class HorizontalCategoryAdapter extends RecyclerView.Adapter<HorizontalCategoryAdapter.MyViewHolder> {

    private  ArrayList<HorizontalcatModel> SliderCatList;
    private Context context;
    ImageLoader ImageLoader;
     SimpleDraweeView imgview;

    public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtView;


    public MyViewHolder(View view) {
        super(view);
        txtView = (TextView) view.findViewById(R.id.txtView);
        imgview = (SimpleDraweeView)view.findViewById(R.id.ProductImage);

    }
}


    public HorizontalCategoryAdapter(Context context, ArrayList<HorizontalcatModel> SliderCatList) {
        this.SliderCatList = SliderCatList;
        this.context = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_category_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
          final HorizontalcatModel SlidcatPos = SliderCatList.get(position);

        holder.txtView.setText(SlidcatPos.getCatName());
        String Imagename = SlidcatPos.getImageIcon();

        //Picasso.with(context).load("http://android.tidyhomz.com/image/catalog/cms/home1/" +
                //Imagename + ".jpg").into(holder.imgview);

        String imageUrl = ClassAPI.Domain+"image/catalog/android_images/top_menu/" +
                Imagename + ".png";
        imgview.setImageURI(imageUrl);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (context instanceof MainActivity) {


                    ((MainActivity) context).loadCategoryProduct(SlidcatPos.getCategoryId(),SlidcatPos.getCatName());
                }

            }
        });


    }

    @Override
    public int getItemCount() {

        return SliderCatList.size();


    }
}






