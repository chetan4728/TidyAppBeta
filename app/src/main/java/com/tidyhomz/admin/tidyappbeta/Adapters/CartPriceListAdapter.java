package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tidyhomz.admin.tidyappbeta.Dataset.CartPriceListModel;
import com.tidyhomz.admin.tidyappbeta.R;

import java.util.ArrayList;

/**
 * Created by Admin on 9/13/2017.
 */

public class CartPriceListAdapter extends BaseAdapter {

    Context context;
    ArrayList<CartPriceListModel> array;

    public CartPriceListAdapter(Context context, ArrayList<CartPriceListModel> array) {
        this.context = context;
        this.array = array;


    }

    @Override
    public int getCount() {
        return array.size();
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

        CartPriceListModel obj = array.get(position);
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_price_list_item,null);
        }
        TextView title = (TextView)convertView.findViewById(R.id.title);
        title.setText(obj.getName());

        LinearLayout highlight = (LinearLayout)convertView.findViewById(R.id.highlight);
        TextView value = (TextView)convertView.findViewById(R.id.value);
        final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");
        value.setTypeface(childFont);
        //Toast.makeText(context, ""+obj.getValue(), Toast.LENGTH_SHORT).show();
        value.setText("`"+obj.getValue());

        if(obj.getName().equals("Total"))
        {
            highlight.setBackgroundColor(Color.parseColor("#333333"));
            value.setTextColor(Color.parseColor("#ffffff"));
            title.setTextColor(Color.parseColor("#ffffff"));

        }

        if(obj.getName().equals("Sub-Total"))
        {
            highlight.setBackgroundColor(Color.parseColor("#ffffff"));
            value.setTextColor(Color.parseColor("#333333"));
            title.setTextColor(Color.parseColor("#333333"));

        }


        return convertView;
    }
}
