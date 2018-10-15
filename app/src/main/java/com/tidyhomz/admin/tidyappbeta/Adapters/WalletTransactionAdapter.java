package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.WalletTransactionModel;
import com.tidyhomz.admin.tidyappbeta.R;

import java.util.ArrayList;

/**
 * Created by Admin on 10/23/2017.
 */

public class WalletTransactionAdapter extends BaseAdapter  {

    Context context;
    ArrayList<WalletTransactionModel> Array;

    public WalletTransactionAdapter(Context context, ArrayList<WalletTransactionModel> array) {
        this.context = context;
        this.Array = array;
    }

    @Override
    public int getCount() {
        return Array.size();
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

        WalletTransactionModel getdata = Array.get(position);

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.wallet_transaction_list,null);
        }
        final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/Rupee_Foradian.ttf");
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView desc = (TextView)convertView.findViewById(R.id.desc);
        desc.setTypeface(childFont);
        TextView date = (TextView)convertView.findViewById(R.id.date);

        title.setText(getdata.getTitle()+" "+getdata.getRefeByName());
        desc.setText(getdata.getDesc_detail()+" `"+getdata.getAfter_refral_amount());
        date.setText("Added on: "+getdata.getDate());


        return convertView;
    }
}
