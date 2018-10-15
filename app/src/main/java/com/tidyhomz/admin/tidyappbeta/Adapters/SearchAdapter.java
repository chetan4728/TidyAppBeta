package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.ModelSearch;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by Admin on 6/1/2017.
 */

public class SearchAdapter extends BaseAdapter {

    Context context;
    ArrayList<ModelSearch> filterList;
    View view;

    public SearchAdapter(Context context, ArrayList<ModelSearch> filterList, View view)
    {
       this.context = context;
        this.filterList = filterList;
        this.view = view;


    }

    @Override
    public int getCount() {


        return this.filterList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filterList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.search_listview_item, null);

        }
        final ModelSearch obj = filterList.get(position);

        TextView tx = (TextView)convertView.findViewById(R.id.sr_text);
        ImageView serchImage = (ImageView)convertView.findViewById(R.id.serchImage);
        tx.setText(obj.getProductname());
        String Image = obj.getProductImage();


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) context).showProductDetail(obj.getProductid(),obj.getProductname());
                InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }
        });



        return convertView;
    }




}
