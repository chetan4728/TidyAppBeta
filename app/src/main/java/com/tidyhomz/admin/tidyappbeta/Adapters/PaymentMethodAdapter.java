package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.payment_method_model;
import com.tidyhomz.admin.tidyappbeta.R;

import java.util.ArrayList;

/**
 * Created by Admin on 9/16/2017.
 */

public class PaymentMethodAdapter extends BaseAdapter {

    Context context;
    ArrayList<payment_method_model> arrayList;
    private int selectedPosition = 0;
    TextView paymentdata;
    public PaymentMethodAdapter(Context context, ArrayList<payment_method_model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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

        payment_method_model obj = arrayList.get(position);

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.payment_list_item,null);


        }

        RadioButton paymentType = (RadioButton)convertView.findViewById(R.id.paymentType);

        String[] splitStr = obj.getTitle().trim().split("&nbsp;");
        paymentType.setText(splitStr[0]);
        paymentType.setTag(obj.getCode());

        if (position == selectedPosition) {
            paymentType.setChecked(true);
            paymentdata = (TextView)convertView.findViewById(R.id.paymentdata);
            String title = obj.getTitle();
            String code = obj.getCode();
            paymentdata.setText(title);
            paymentdata.setTag(code);
        } else paymentType.setChecked(false);
        paymentType.setOnClickListener(onStateChangedListener(paymentType, position,obj));


        return convertView;
    }
    private View.OnClickListener onStateChangedListener(final RadioButton checkBox, final int position,final  payment_method_model obj) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    selectedPosition = position;
                    paymentdata.setText(obj.getTitle());
                    paymentdata.setTag(obj.getCode());
                } else {
                    selectedPosition = -1;
                }
                notifyDataSetChanged();
            }
        };
    }
}
