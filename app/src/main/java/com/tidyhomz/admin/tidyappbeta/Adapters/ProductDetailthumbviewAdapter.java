package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.ProductDetailFragment;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Admin on 5/18/2017.
 */

public class ProductDetailthumbviewAdapter extends RecyclerView.Adapter<ProductDetailthumbviewAdapter.MyViewHolder> {

    private  ArrayList<String> thumbList;
    private Context context;
     ImageLoader ImageLoader;
     public  SimpleDraweeView imgview;
    private  int i;

    public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtView;


    public MyViewHolder(View view) {
        super(view);
        imgview = (SimpleDraweeView)view.findViewById(R.id.thumbview);

    }
}


    public ProductDetailthumbviewAdapter(Context context, ArrayList<String> thumbList, int i) {
        this.thumbList = thumbList;
        this.context = context;
        this.i=i;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_detail_thumb_view_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


      if(i==position) {

          int color = context.getResources().getColor(R.color.colorPrimaryDark);
          RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
          roundingParams.setBorder(color, 4.0f);
          imgview.getHierarchy().setRoundingParams(roundingParams);

      }
      else
      {
          int color = context.getResources().getColor(R.color.dialogplus_black_overlay);
          RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
          roundingParams.setBorder(color, 4.0f);
          imgview.getHierarchy().setRoundingParams(roundingParams);
      }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ProductDetailFragment.sendData(position);


            }
        });

        imgview.setImageURI(thumbList.get(position));

    }

    @Override
    public int getItemCount() {

        return thumbList.size();


    }
}






