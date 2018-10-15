package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.ProductOptionListShow;
import com.tidyhomz.admin.tidyappbeta.R;

import java.util.ArrayList;

/**
 * Created by Admin on 11/7/2017.
 */

public class ProductOptionAdapter extends BaseAdapter {

    Context context;
    ArrayList<ProductOptionListShow> list;

    public ProductOptionAdapter(Context context, ArrayList<ProductOptionListShow> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
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

        ProductOptionListShow getdata = list.get(position);
        if(convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.product_option_show,null);

        }
        TextView option_name = (TextView)convertView.findViewById(R.id.option_name);
        option_name.setText(getdata.getOption_name());
        TextView option_value = (TextView)convertView.findViewById(R.id.option_value);
        option_value.setText(getdata.getOption_text());

        return convertView;

    }
}
