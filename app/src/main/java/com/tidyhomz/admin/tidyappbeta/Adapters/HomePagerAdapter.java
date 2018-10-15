package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tidyhomz.admin.tidyappbeta.Dataset.HomeSliderModel;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 5/16/2017.
 */

public class HomePagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    Activity activity;
    private ArrayList<HomeSliderModel> SliderModelList;
    private int pos = 0;
    private int maxlength = 100;


    public HomePagerAdapter(Activity activity, ArrayList<HomeSliderModel> SliderModelList) {
        this.activity = activity;
        this.SliderModelList = SliderModelList;


    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        final HomeSliderModel SliderModelPos = SliderModelList.get(pos);
        View view = layoutInflater.inflate(R.layout.home_slider_layout, container, false);

        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);
 String imageUrl  =SliderModelPos.getImageUrl();
        imageUrl = imageUrl.replace(" ","%20");
        Picasso.with(activity.getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder2)
                .into(im_slider);


        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //this will log the page number that was click


                if (SliderModelPos.getSliderType().equals("category_id")) {


                    if (activity instanceof MainActivity) {

                        ((MainActivity) activity).loadCategoryProduct(SliderModelPos.getSliderId(), "Special Product");
                    }
                }

                else if(SliderModelPos.getSliderType().equals("product_id"))
                {
                    if (activity instanceof MainActivity) {
                        ((MainActivity) activity).showProductDetail(Integer.parseInt(SliderModelPos.getSliderId()),"Special Product");
                    }
                }
                else if(SliderModelPos.getLink()!=null && SliderModelPos.getSliderId().equals("0"))
                {
                  if(SliderModelPos.getLink()=="" || SliderModelPos.getLink().equals("") || SliderModelPos.getLink().isEmpty())
                  {
                      ((MainActivity) activity).home();
                  }
                  else
                  {
                      ((MainActivity) activity).LoadSpecialBannerFragment();
                  }


                }
                else
                {
                    ((MainActivity) activity).home();
                }
            }
        });

if(position!=pos) {
    if (pos >= SliderModelList.size() - 1) {
        pos = 0;
    } else {
        pos++;
    }
}


        return view;

    }


    @Override
    public int getCount() {
       return maxlength;
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
