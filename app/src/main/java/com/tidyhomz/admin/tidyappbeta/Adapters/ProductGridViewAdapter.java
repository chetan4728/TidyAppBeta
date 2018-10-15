package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Dataset.ProductGridViewModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Admin on 5/29/2017.
 */

public class ProductGridViewAdapter extends BaseAdapter  {
    public static ArrayList<Integer>  UserWhishList;
    public ArrayList<ProductGridViewModel> ProductGridListArray;
    public  String GetParam;
    private  View view;

    AppSharedPreferences App;
    private Context context;
    ImageLoader ImageLoader;


    public ProductGridViewAdapter(Context context, ArrayList<ProductGridViewModel> ProductGridListArray,String GetParam)
    {
        this.context= context;
        this.ProductGridListArray = ProductGridListArray;
        UserWhishList = new ArrayList<>();
        this.GetParam = GetParam;
        App = new AppSharedPreferences(context);

       // Toast.makeText(context,ProductGridListArray.size()+" Products", Toast.LENGTH_LONG).show();




        int count = App.pref.getInt(App.COUNT, 0);



        if(count > 0) {

            for (int m = 0; m < count; m++) {

                    UserWhishList.add(App.pref.getInt(App.WHISHLIST + m, m));


            }
        }

    }



    @Override
    public int getCount() {
        return this.ProductGridListArray.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {




        final ProductGridViewModel mobject = ProductGridListArray.get(position);
        if(convertView==null) {


            App.editor.putString(App.PRODUCTLISTSTYLE,GetParam);
            App.editor.commit();

            String v = App.pref.getString(App.PRODUCTLISTSTYLE, "");
            if(v.equals("Grid"))
            {
                view  = ((LayoutInflater) (context).getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.product_gridview_item, null, false);
            }
            else
            {
                view  = ((LayoutInflater) (context).getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.product_listview_item, null, false);

            }

            convertView = view;

        }

        TextView product_name = (TextView) convertView.findViewById(R.id.prodcutName);
        TextView prodcutPrice = (TextView) convertView.findViewById(R.id.prodcutPrice);
        TextView discount = (TextView) convertView.findViewById(R.id.discount);
        TextView dicount = (TextView) convertView.findViewById(R.id.dicount);
        ImageView wishimg=(ImageView) convertView.findViewWithTag("wishlist");
        RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.pop_ratingbar);
        ratingBar.setRating(Float.parseFloat(mobject.getProductRating()));


        wishimg.setId(mobject.getProductId());
        wishimg.setImageResource(R.drawable.heart_icon);

        if(!UserWhishList.isEmpty()){

            for(int j=0;j< UserWhishList.size();j++){
                if(mobject.getProductId()== UserWhishList.get(j)){
                    wishimg.setImageResource(R.drawable.heartclick);

                }
            }
        }





        TextView specialprodcutPrice = (TextView) convertView.findViewById(R.id.specialprodcutPrice);
        TextView symobi2 = (TextView) convertView.findViewById(R.id.symobi2);
        TextView symobi1 = (TextView) convertView.findViewById(R.id.symobi1);
        final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");
        symobi2.setTypeface(childFont);
        symobi2.setText("`");
        symobi1.setTypeface(childFont);
        symobi1.setText("`");



        if (mobject.getProductSpecial().equalsIgnoreCase("false")) {
            specialprodcutPrice.setText(mobject.getProductPrice());
            prodcutPrice.setVisibility(View.GONE);
            symobi1.setText("");
            dicount.setVisibility(View.GONE);

        } else {
            prodcutPrice.setVisibility(View.VISIBLE);
            prodcutPrice.setPaintFlags(prodcutPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            specialprodcutPrice.setText(mobject.getProductSpecial());
            discount.setText(mobject.getProductDiscount());
            if(mobject.getQuntity()==0)
            {
                if(mobject.getProductStock().equals("Coming Soon")) {
                    dicount.setText(mobject.getProductStock());
                    dicount.setBackgroundColor(Color.parseColor("#5bc975"));
                }
                else if(mobject.getProductStock().equals("Out Of Stock"))
                {
                    dicount.setText(mobject.getProductStock());
                    dicount.setBackgroundColor(Color.parseColor("#f15d58"));
                }
            }
            else
            {
                dicount.setBackgroundColor(Color.parseColor("#5bc975"));
                dicount.setText(mobject.getProductDiscount());
            }


        }
//        NetworkImageView img = (NetworkImageView) convertView.findViewById(R.id.product_Image);


        product_name.setText(mobject.getProductName());
        prodcutPrice.setText(mobject.getProductPrice());
        String imageUrl = mobject.getProductImage();
        imageUrl = imageUrl.replace(" ", "%20");
        SimpleDraweeView draweeView = (SimpleDraweeView)  convertView.findViewById(R.id.product_Image);
        GenericDraweeHierarchy hierarchy =  GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setPlaceholderImage(R.drawable.placeholder2).build();
        draweeView.setHierarchy(hierarchy);
        draweeView.setImageURI(imageUrl);
        draweeView.setImageURI(imageUrl);

        wishimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                ImageView test=(ImageView) v.findViewById(v.getId());

                if(UserWhishList.contains(v.getId()))
                {

                    test.setImageResource(R.drawable.heart_icon);
                    for(int d=0;d< UserWhishList.size();d++){

                        if(v.getId()==UserWhishList.get(d)) {
                            UserWhishList.remove(d);

                            Toast.makeText(context, "Product removed from wishlist", Toast.LENGTH_SHORT).show();
                            removeWishlistProduct(String.valueOf(v.getId()));
                            notifyDataSetChanged();
                        }
                    }
                }
                else {
                    test.setImageResource(R.drawable.heartclick);
                    addWishlistServer(String.valueOf(v.getId()));
                     UserWhishList.add(v.getId());
                    Toast.makeText(context, "Product added to wishlist", Toast.LENGTH_SHORT).show();

                    notifyDataSetChanged();
                }



                App.editor.putInt(App.COUNT, UserWhishList.size());


                for(int i = 0; i<UserWhishList.size(); i++) {


                    App.editor.putInt(App.WHISHLIST + i,UserWhishList.get(i));
                }


                App.editor.commit();


                String t = String.valueOf(UserWhishList.size());
                ClassAPI.itemMessagesBadgeTextView.setText(t);
                ClassAPI.CommanWishlistCount.setText(t);
                if(UserWhishList.size() >0)
                {
                    ClassAPI.itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
                    ClassAPI.CommanWishlistCount.setVisibility(View.VISIBLE);
                }
                else
                {
                    ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
                    ClassAPI.CommanWishlistCount.setVisibility(View.GONE);
                }




            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = String.valueOf(mobject.getProductId());
                if (context instanceof MainActivity) {
                    ((MainActivity) context).showProductDetail(Integer.parseInt(id),mobject.getProductName());
                }

            }
        });
        return convertView;

    }


    public void addWishlistServer(String Product_id)
    {

        String UserId = App.pref.getString(App.UserId,"");
        String URL = ClassAPI.ADDWISHLIST+Product_id+"&customer_id="+UserId;
        VolleyUtils.makeJsonObjectRequestNonCache(context, URL, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {


            }
        });
    }


    public void removeWishlistProduct(String Product_id)
    {


        String UserId = App.pref.getString(App.UserId,"");
        String URL = ClassAPI.REMOVEWISHLIST+Product_id+"&customer_id="+UserId;

        VolleyUtils.makeJsonObjectRequestNonCache(context, URL, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {


            }
        });
    }

}
