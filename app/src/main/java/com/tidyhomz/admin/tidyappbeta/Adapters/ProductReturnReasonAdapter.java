package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Dataset.return_reasons_model;
import com.tidyhomz.admin.tidyappbeta.R;

import java.util.ArrayList;

/**
 * Created by Admin on 9/9/2017.
 */

public class ProductReturnReasonAdapter  extends BaseAdapter {


    ArrayList<return_reasons_model> return_reasons_model;
    Context context;
    private RadioButton selected = null;
    public ProductReturnReasonAdapter(Context context,ArrayList<return_reasons_model> return_reasons_model) {
        this.context =context;
        this.return_reasons_model = return_reasons_model;
    }


    @Override
    public int getCount() {
        return return_reasons_model.size();
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

        return_reasons_model obj  = return_reasons_model.get(position);
        if(convertView==null)
        {
            LayoutInflater view = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.return_resaon_item,null);
        }
        final RadioButton optionreason = (RadioButton)convertView.findViewById(R.id.optionreason);
        optionreason.setText(obj.getName());
        optionreason.setTag(obj.getReson_id());




        optionreason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selected != null) {
                    selected.setChecked(false);
                }
                optionreason.setChecked(true);
                selected = optionreason;

                ClassAPI.ProductResasonFlag.setText(String.valueOf(optionreason));


            }
        });



        return convertView;
    }
}
