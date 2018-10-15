package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.shipping_method_model;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Admin on 9/15/2017.
 */

public class ShippingMethodAdapter extends BaseAdapter  {

    Context context;
    ArrayList<shipping_method_model> arraylidt;

    private int selectedPosition = 0;
    RadioButton title_id;
    TextView taxDetail;
    public ShippingMethodAdapter(Context context, ArrayList<shipping_method_model> arraylidt) {
        this.context = context;
        this.arraylidt = arraylidt;

    }

    @Override
    public int getCount() {
        return arraylidt.size();
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


        shipping_method_model get = arraylidt.get(position);
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.shiping_method_item,null);



        }



        try {


             title_id = (RadioButton)convertView.findViewById(R.id.title_id);
            title_id.setText(get.getMmethod().getJSONObject(get.getCode()).getString("title"));
            TextView namevalue = (TextView)convertView.findViewById(R.id.namevalue);
            final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");
            namevalue.setTypeface(childFont);

            if (position == selectedPosition) {
               title_id.setChecked(true);
                 taxDetail = (TextView)convertView.findViewById(R.id.taxDetail);
                taxDetail.setText(get.getMmethod().getJSONObject(get.getCode()).getString("cost"));
                taxDetail.setTag(get.getMmethod().getJSONObject(get.getCode()).getString("tax_class_id"));
                title_id.setTag(get.getMmethod().getJSONObject(get.getCode()).getString("code"));
            } else title_id.setChecked(false);
            title_id.setOnClickListener(onStateChangedListener(title_id, position,get.getMmethod().getJSONObject(get.getCode()).getString("code"),get));
            namevalue.setText("-  "+"`"+get.getMmethod().getJSONObject(get.getCode()).getString("text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }





        return convertView;
    }
    private View.OnClickListener onStateChangedListener(final RadioButton checkBox, final int position, final String tag, final shipping_method_model get) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    selectedPosition = position;
                    checkBox.setTag(tag);
                    try {
                        taxDetail.setText(get.getMmethod().getJSONObject(get.getCode()).getString("cost"));
                        taxDetail.setTag(get.getMmethod().getJSONObject(get.getCode()).getString("tax_class_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    selectedPosition = -1;
                }
                notifyDataSetChanged();
            }
        };
    }

    private String Spacer(String number){
        StringBuilder strB = new StringBuilder();
        strB.append(number);
        int Three = 0;

        for(int i=number.length();i>0;i--){
            Three++;
            if(Three == 3){
                strB.insert(i-1, ",");
                Three = 0;
            }
        }
        return strB.toString();
    }



}
