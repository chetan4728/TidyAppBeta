package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.ProductOptionListShow;
import com.tidyhomz.admin.tidyappbeta.Dataset.ViewCartModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.Utility;
import com.tidyhomz.admin.tidyappbeta.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 9/19/2017.
 */

public class finalProductAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<ViewCartModel> RecentArray;
    private ArrayList<ProductOptionListShow> OptionArrray ;

    public finalProductAdapter(Context context, ArrayList<ViewCartModel> recentArray) {
        this.context = context;
        RecentArray = recentArray;

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

        OptionArrray = new ArrayList<>();
        ViewCartModel mobject = RecentArray.get(position);

        if(convertView==null)
        {
            LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.final_product_view_list_item, null);
        }
        final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");

        TextView product_name = (TextView) convertView.findViewById(R.id.productname);
        TextView model = (TextView) convertView.findViewById(R.id.model);
        TextView qunitity = (TextView) convertView.findViewById(R.id.qunitity);
        ListView  optionList = (ListView)convertView.findViewById(R.id.optionList);
        TextView hsn = (TextView) convertView.findViewById(R.id.hsn);

        ImageView productImg = (ImageView) convertView.findViewById(R.id.productImg);
        Picasso.with(context.getApplicationContext())
                .load(mobject.getImageThumb())
                .placeholder(R.drawable.placeholder2)
                .into(productImg);

        for(int i=0;i<mobject.getOption().length();i++)
        {
            ProductOptionListShow productOptionListShow = new ProductOptionListShow();
            try {
                JSONObject obje  = mobject.getOption().getJSONObject(i);
                productOptionListShow.setOption_name(obje.getString("name"));
                productOptionListShow.setOption_text(obje.getString("value"));
                OptionArrray.add(productOptionListShow);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ProductOptionAdapter productOptionAdapter = new ProductOptionAdapter(context,OptionArrray);
        optionList.setAdapter(productOptionAdapter);
        Utility.setListViewHeightBasedOnChildren(optionList);
        product_name.setText(mobject.getName());
        model.setText(mobject.getModel());
        hsn.setText(mobject.getHSN());
        qunitity.setText(String.valueOf(mobject.getQuantity()));


        return convertView;
    }
}
