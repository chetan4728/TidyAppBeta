package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.ProductoptionDropDownDataset;
import com.tidyhomz.admin.tidyappbeta.R;

import java.util.ArrayList;

/**
 * Created by Admin on 8/3/2017.
 */

public class ProductOptionDropDownAdapter extends BaseAdapter implements SpinnerAdapter {


    Context context;
    ArrayList<ProductoptionDropDownDataset> dataset;
    public static View selectedView = null;

    public ProductOptionDropDownAdapter(Context context,ArrayList<ProductoptionDropDownDataset> dataset) {
        this.dataset=dataset;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Object getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductoptionDropDownDataset object = dataset.get(position);

        if(convertView==null) {
            LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.spiner_item, null);
        }

        selectedView = convertView;
        TextView txt = (TextView)convertView.findViewById(R.id.sub);
        TextView id_value = (TextView)convertView.findViewById(R.id.id_value);
        txt.setPadding(16, 16, 0, 16);
        txt.setTextSize(14);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(object.getName());
        String cvale = String.valueOf(object.getProduct_option_value_id());
        id_value.setText(cvale);
        txt.setTextColor(Color.parseColor("#000000"));
        return  convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if(convertView==null) {
            LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.spiner_item, null);
        }
        ProductoptionDropDownDataset object = dataset.get(position);
        TextView txt = (TextView)convertView.findViewById(R.id.sub);
        TextView id_value = (TextView)convertView.findViewById(R.id.id_value);
        txt.setPadding(16, 16, 0, 16);
        selectedView = convertView;
        txt.setTextSize(14);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(object.getName());
        String cvale = String.valueOf(object.getOption_value_id());
        id_value.setText(cvale);
        txt.setTextColor(Color.parseColor("#000000"));
        return  convertView;
    }
}