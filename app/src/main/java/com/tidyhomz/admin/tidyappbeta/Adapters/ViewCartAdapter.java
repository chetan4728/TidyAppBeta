package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Dataset.ViewCartModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.ViewCartFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Admin on 7/22/2017.
 */

public class ViewCartAdapter extends BaseAdapter {

    Context context;
    ArrayList<ViewCartModel> ViewcartModel;
    android.support.v4.app.Fragment fragment;
    ArrayList UsercartList;
    public static ArrayList<Integer>  MoveToWishList;

    TextView qunitity;
    AppSharedPreferences App;
    int count;
    public ViewCartAdapter(Context context, ArrayList<ViewCartModel> ViewcartModel, android.support.v4.app.Fragment fragment) {
        this.context = context;
        this.ViewcartModel = ViewcartModel;
       this.fragment = fragment;
        UsercartList = new ArrayList<>();
        MoveToWishList = new ArrayList<>();
        App = new AppSharedPreferences(context);

        // Toast.makeText(context,ProductGridListArray.size()+" Products", Toast.LENGTH_LONG).show();

        if(ViewcartModel.size() > 0) {

            for (int m = 0; m < ViewcartModel.size(); m++) {

                UsercartList.add(App.pref.getInt(App.WHISHLIST + m, m));


            }
        }


        count = App.pref.getInt(App.COUNT, 0);
        int  counttt =  App.pref.getInt(App.CARTVIEWCOUNT, 0);


        App.editor.putInt(App.CARTVIEWCOUNT,ViewcartModel.size());
        App.editor.commit();


        if(count > 0) {

            for (int m = 0; m < count; m++) {

                MoveToWishList.add(App.pref.getInt(App.WHISHLIST + m, m));


            }
        }
    }

    @Override
    public int getCount() {
        return ViewcartModel.size();
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

        if(convertView==null) {


                LayoutInflater infalter = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = infalter.inflate(R.layout.view_cart_layout_item, null);


        }



        final  ViewCartModel object = ViewcartModel.get(position);

        SimpleDraweeView productImage = (SimpleDraweeView)convertView.findViewById(R.id.productImage);
        productImage.setImageURI(object.getImageThumb());

        TextView productname = (TextView)convertView.findViewById(R.id.productname);
        TextView sy = (TextView)convertView.findViewById(R.id.symobi1);
        TextView sytt = (TextView)convertView.findViewById(R.id.symobi2);
        TextView prodcutPrice = (TextView)convertView.findViewById(R.id.prodcutPrice);
        qunitity = (TextView)convertView.findViewById(R.id.qunitity);
        final TextView TotalPrice = (TextView)convertView.findViewById(R.id.TotalPrice);
        TextView removeCartProduct = (TextView)convertView.findViewById(R.id.removeCartProduct);
        TextView movetowishlist = (TextView)convertView.findViewById(R.id.movetowishlist);
        String TitleProduct = object.getName();

        TitleProduct =  TitleProduct.replace("&amp;","&");
        productname.setText(TitleProduct);
        removeCartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                        ((ViewCartFragment) fragment).removeCartProduct(object.getCart_id());

                        AppSharedPreferences app = new AppSharedPreferences(context);
                        int CARTVIEWCOUNT = app.pref.getInt(app.CARTCOUNT, 0);

                        for(int d=0;d< UsercartList.size();d++){


                            UsercartList.remove(d);


                        }











                                Toast.makeText(context, "Product removed from cart successfully", Toast.LENGTH_SHORT).show();
                                if(UsercartList.size()==0) {
                                    App.editor.remove("CARTCOUNT");
                                    App.editor.commit();
                                    // ((MainActivity) context).emptycart();

                                }
                                notifyDataSetChanged();
                                ((MainActivity) context).removeproduct();
                                ((MainActivity) context).UserData();




            }
        });

        TextView minuscart = (TextView)convertView.findViewById(R.id.minuscart);
        TextView addQuntitycart = (TextView)convertView.findViewById(R.id.addcart);

        minuscart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int update = Integer.parseInt(qunitity.getText().toString().trim());
                if(update!=1)
                {
                    AppSharedPreferences app = new AppSharedPreferences(context);
                    app.editor.putString(app.WalletAmount,"0");
                    app.editor.commit();
                    update -= 1;
                }

               // Toast.makeText(context, ""+update, Toast.LENGTH_SHORT).show();
                addMoreCartProduct(object.getCart_id(),update,object.getPrduct_id());
            }
        });
        addQuntitycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int update = Integer.parseInt(qunitity.getText().toString().trim());
                update +=1;
              //  Toast.makeText(context, ""+update, Toast.LENGTH_SHORT).show();
                addMoreCartProduct(object.getCart_id(),update,object.getPrduct_id());
            }
        });
        movetowishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               AppSharedPreferences Sessiondata = new AppSharedPreferences(context);
                MoveToWishList.add(object.getPrduct_id());
                addWishlistServer(String.valueOf(object.getPrduct_id()));
                App.editor.putInt(App.COUNT, MoveToWishList.size());


                for(int i = 0; i<MoveToWishList.size(); i++) {


                    App.editor.putInt(App.WHISHLIST + i,MoveToWishList.get(i));
                }


                App.editor.commit();


                String t = String.valueOf(MoveToWishList.size());
                ClassAPI.itemMessagesBadgeTextView.setText(t);
                if(MoveToWishList.size() >0)
                {
                    ClassAPI.itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
                }
                else
                {
                    ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
                }
               ((ViewCartFragment) fragment).removeCartProduct(object.getCart_id());


             notifyDataSetChanged();

            }
        });

        final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");
        sy.setTypeface(childFont);
        sy.setText("`");
        sytt.setTypeface(childFont);
        sytt.setText("`");

        String unit_price = object.getPrice();
        int unit_getQuantity = Integer.parseInt(object.getQuantity());

        float tunitprice=0;

        try {
            tunitprice = Float.parseFloat(String.valueOf(getNumberByString(unit_price.toString())));
            // Toast.makeText(getActivity(), ""+getNumberByString(finalCheckprice.toString()), Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        float Total_price = tunitprice*unit_getQuantity;
        String finalTotal = String.valueOf(Total_price);

        TotalPrice.setText(finalTotal);

          String p = String.valueOf(object.getPrice());
          prodcutPrice.setText(p);
        qunitity.setText(object.getQuantity());

        return convertView;
    }

    public static float getNumberByString(String s) throws ParseException {
        return NumberFormat.getNumberInstance(Locale.getDefault()).parse(s).floatValue();
    }

    public void addWishlistServer(String Product_id)
    {

        String UserId = App.pref.getString(App.UserId,"");
        String URLdata = ClassAPI.ADDWISHLIST+Product_id+"&customer_id="+UserId;
        //Log.e("",URL);
        VolleyUtils.makeJsonObjectRequestNonCache(context, URLdata, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {
                Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object response) {


                ((MainActivity) context).UserData();

            }
        });
    }



    public void addMoreCartProduct(int cart_id, int cartindex,int product_id)
    {


        String URL = ClassAPI.UPDATEQUNITIY+cart_id+"&quntity="+cartindex+"&product_id="+product_id;



        VolleyUtils.makeJsonObjectRequestNonCache(context, URL, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {


                try {
                    JSONObject json = new JSONObject(String.valueOf(response));
                    json.getBoolean("Success");
                  if(json.getBoolean("Success"))
                  {
                      ((ViewCartFragment) fragment).LoadCart();
                  }
                  else
                  {
                      Toast.makeText(context, "Item out of stock", Toast.LENGTH_SHORT).show();
                  }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}
