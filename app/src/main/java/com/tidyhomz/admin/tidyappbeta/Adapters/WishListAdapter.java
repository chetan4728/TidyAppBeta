package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Dataset.WishListModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 6/8/2017.
 */

public class WishListAdapter extends  RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

private  ArrayList<WishListModel> wishhListArray;
private Context context;
    private WishListAdapter mListener;
    public static ArrayList<Integer>  UserWhishListRemove;
public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView ProductName,ProductPrice,ProductSpecial,ProductDiscount,ruppeIcon,ruppeIcon2,title;
    public ImageView ProductImage,removeIcon;

    public MyViewHolder(View view) {
        super(view);
        ProductName = (TextView) view.findViewById(R.id.wishlistname);
        ProductSpecial =(TextView)view.findViewById(R.id.specialprodcutPrice);
        ProductPrice = (TextView)view.findViewById(R.id.prodcutPrice);
        ProductDiscount = (TextView)view.findViewById(R.id.discount);
        ruppeIcon =(TextView)view.findViewById(R.id.symobi2);
        ruppeIcon2 = (TextView)view.findViewById(R.id.symobi1);
        ProductImage = (ImageView) view.findViewById(R.id.wishlistlogo);
        removeIcon = (ImageView)view.findViewById(R.id.wishlistremove);

        title = (TextView)view.findViewById(R.id.set_title);

    }


}

    public WishListAdapter(Context context, ArrayList<WishListModel> wishhListArray) {
        this.wishhListArray = wishhListArray;
        this.context = context;
        UserWhishListRemove = new ArrayList<>();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_item_view, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final  WishListModel object = wishhListArray.get(position);

        UserWhishListRemove.add(object.getProduct_id());
        String TitleProduct = object.getProductName();

        TitleProduct =  TitleProduct.replace("&amp;","&");
        ClassAPI.set_title.setText(TitleProduct);
        holder.ProductName.setText(TitleProduct);

        String imageUrl = object.getImage();
        imageUrl = imageUrl.replace(" ","%20");
        holder.ProductSpecial.setText(object.getSpecialPrice());

        if (object.getSpecialPrice().equalsIgnoreCase("false")) {
            holder.ProductPrice.setText(object.getPrice());
            holder.ProductPrice.setVisibility(View.GONE);
        } else {
            holder.ProductPrice.setVisibility(View.VISIBLE);
            holder.ProductPrice.setPaintFlags(holder.ProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.ProductSpecial.setText(object.getSpecialPrice());
            holder.ProductDiscount.setText(object.getDiscount());
        }
        holder.ProductPrice.setText(object.getPrice());
        final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");
        holder.ruppeIcon.setTypeface(childFont);
        holder.ruppeIcon.setText("`");
        holder.ruppeIcon2.setTypeface(childFont);
        holder.ruppeIcon2.setText("`");
        Picasso.with(context.getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder2)
                .into(holder.ProductImage);

        holder.removeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(object);
                removeWishlistProduct(String.valueOf(object.getProduct_id()));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).showProductDetail(object.getProduct_id(),object.getProductName());
                }
            }
        });
        



    }
    public void removeItem(WishListModel infoData) {

        AppSharedPreferences App = new AppSharedPreferences(context);


        int currPosition = wishhListArray.indexOf(infoData);
        wishhListArray.remove(currPosition);
        notifyItemRemoved(currPosition);


               if (context instanceof MainActivity) {

                ((MainActivity)context).loadWishlist();
                   ((MainActivity)context).UserData();

        }


       for(int d=0;d< UserWhishListRemove.size();d++){

            if(infoData.getProduct_id()==UserWhishListRemove.get(d)) {
                UserWhishListRemove.remove(d);

            }
        }


        String t = String.valueOf(UserWhishListRemove.size());
        ClassAPI.itemMessagesBadgeTextView.setText(t);
        if(UserWhishListRemove.size() >0)
        {
            ClassAPI.itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
        }





        App.editor.putInt(App.COUNT, UserWhishListRemove.size());
        for(int i = 0; i<UserWhishListRemove.size(); i++) {
            App.editor.putInt(App.WHISHLIST + i,UserWhishListRemove.get(i));
        }

        App.editor.commit();
    }
    @Override
    public int getItemCount() {

        return wishhListArray.size();


    }

    public void removeWishlistProduct(String Product_id)
    {

        AppSharedPreferences App = new AppSharedPreferences(context);
        String UserId = App.pref.getString(App.UserId,"");
        String URL = ClassAPI.REMOVEWISHLIST+Product_id+"&customer_id="+UserId;

        VolleyUtils.makeJsonObjectRequestNonCache(context, URL, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {

              
                ((MainActivity) context).UserData();
            }
        });
    }
}
