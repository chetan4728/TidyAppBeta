package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Admin on 7/11/2017.
 */

public class ImageListProductDetailAdapter extends PagerAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> ImageList;
    private ArrayList<String> HdImage;
    private int pos = 0;
    private int maxlength = 100;
    public ImageListProductDetailAdapter(Context context,ArrayList<String> ImageList, ArrayList<String> HdImage) {

        this.context = context;
        this.ImageList = ImageList;
        this.HdImage = HdImage;
    }

    @Override
    public int getCount() {
        return ImageList.size();
    }



    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.product_detail_image_view, container, false);
        SimpleDraweeView im_slider = (SimpleDraweeView) view.findViewById(R.id.imageView);
        GenericDraweeHierarchy hierarchy =  GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setPlaceholderImage(R.drawable.placeholder2).build();
        im_slider.setHierarchy(hierarchy);
        im_slider.setImageURI(ImageList.get(position));



        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //this will log the page number that was click



            }
        });

        if(position!=pos) {
            if (pos >= ImageList.size() - 1) {
                pos = 0;
            } else {
                pos++;
            }
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) context).loadProductImageZoom(HdImage.get(position));

            }
        });


        return view;

    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        {
            return view == object;
        }
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
