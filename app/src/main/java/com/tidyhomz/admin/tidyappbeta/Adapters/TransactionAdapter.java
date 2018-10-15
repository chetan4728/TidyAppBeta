package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.UserTransactionModel;
import com.tidyhomz.admin.tidyappbeta.R;

import java.util.ArrayList;

/**
 * Created by Admin on 9/25/2017.
 */

public class TransactionAdapter extends BaseAdapter {
    Context context;
    ArrayList<UserTransactionModel> ArrayData;

    public TransactionAdapter(Context context, ArrayList<UserTransactionModel> ArrayData) {
        this.context = context;
        this.ArrayData = ArrayData;
    }

    @Override
    public int getCount() {
        return ArrayData.size();
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

        UserTransactionModel object = ArrayData.get(position);

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.transaction_list_item,null);
        }

        TextView amount  = (TextView)convertView.findViewById(R.id.amount);
        TextView description  = (TextView)convertView.findViewById(R.id.description);
        TextView date_added  = (TextView)convertView.findViewById(R.id.date_added);
        amount.setText(object.getAmount());
        date_added.setText(object.getDate_added());
        description.setText(object.getDescription());

        return convertView;
    }
}
