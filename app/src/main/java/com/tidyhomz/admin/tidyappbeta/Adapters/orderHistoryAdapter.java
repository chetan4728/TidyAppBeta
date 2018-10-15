package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.OrderHistoryModel;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.UserOrderHistoryFragment;

import java.util.ArrayList;

/**
 * Created by Admin on 9/4/2017.
 */

public class orderHistoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<OrderHistoryModel> Arralistdata;
    Fragment fragment;

    public orderHistoryAdapter(Context context, ArrayList<OrderHistoryModel> Arralistdata, Fragment fragment) {
        this.context =context;
        this.Arralistdata = Arralistdata;
        this.fragment = fragment;

    }

    @Override
    public int getCount() {
        return Arralistdata.size();
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

        final OrderHistoryModel getdata = Arralistdata.get(position);

        if(convertView ==null)
        {
            LayoutInflater view = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.order_history_list_item,null);
        }

        TextView order_id = (TextView)convertView.findViewById(R.id.order_id);
        final String orderid = String.valueOf(getdata.getOrder_id());
        order_id.setText("Order ID : #"+orderid);

        TextView customer_name = (TextView)convertView.findViewById(R.id.customer_name);
        customer_name.setText(getdata.getName());


        TextView product_number = (TextView)convertView.findViewById(R.id.product_number);
        product_number.setText("No. of Products : "+getdata.getProducts());

        TextView product_Status = (TextView)convertView.findViewById(R.id.product_Status);
        product_Status.setText("Status : "+getdata.getStatus());

        final TextView product_date = (TextView)convertView.findViewById(R.id.product_date);
        product_date.setText("Date Added : "+getdata.getDate_added());
        TextView total = (TextView)convertView.findViewById(R.id.total);

        final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");
        total.setTypeface(childFont);
        total.setText("Total : " + "`"+getdata.getTotal());

        TextView MoreDetail = (TextView)convertView.findViewById(R.id.view_detail);
        MoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((UserOrderHistoryFragment) fragment).load_Detail_order(getdata.getOrder_id(),product_date.getText().toString(),getdata.getStatus());

            }
        });



        return convertView;
    }
}
