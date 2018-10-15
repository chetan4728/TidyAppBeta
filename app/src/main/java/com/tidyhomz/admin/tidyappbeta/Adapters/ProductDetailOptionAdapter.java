package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Dataset.ProductOptionDataset;
import com.tidyhomz.admin.tidyappbeta.Dataset.ProductoptionDropDownDataset;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/3/2017.
 */

public class ProductDetailOptionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProductOptionDataset> ProductOptionDataset;
    List<String> testlist ;
    ProductDetailOptionAdapter ProductDetailOptionAdapter;
    List<Integer> Values;
    private  ArrayList<ProductoptionDropDownDataset> ProductoptionDropDownDataset;
    ProductOptionDropDownAdapter ProductOptionDropDownAdapter;
    ListView optiondata;
    int count;
    EditText optionusername,otionid;
    public Spinner optionSpinner;
    ArrayList<String> validation = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();
    RelativeLayout optionImgsrd;
    public  View P;
    public  boolean FlagSet = false;
    int flag; LinearLayout option_container;

    public ProductDetailOptionAdapter(Context context, ArrayList<ProductOptionDataset> ProductOptionDataset, ListView optiondata, RelativeLayout optionImgsrd) {

        this.context= context;
        this.ProductOptionDataset = ProductOptionDataset;
        this.optiondata= optiondata;
        this.optionImgsrd= optionImgsrd;

        for (int i = 0; i < ProductOptionDataset.size(); i++) {
            final ProductOptionDataset getobject = ProductOptionDataset.get(i);
            validation.add(getobject.getName());
        }
    }

    @Override
    public int getCount() {
        return ProductOptionDataset.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ProductoptionDropDownDataset = new ArrayList<>();
        Values = new ArrayList<Integer>();

        final ProductOptionDataset getobject = ProductOptionDataset.get(position);



        if(convertView==null) {
            LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.product_detail_option_item, null);




        }



     option_container = (LinearLayout)convertView.findViewById(R.id.optioncontainer);




        TextView optionTitle = (TextView)convertView.findViewById(R.id.productOptiontitle);
        optionTitle.setText(getobject.getName());

        optionSpinner = (Spinner)convertView.findViewById(R.id.optionspinner);




        if(getobject.getType().equals("select"))
        {


            try {
                JSONObject jsonObject = null;


                for(int i=0;i<getobject.getProduct_option_value().length();i++) {
                    ProductoptionDropDownDataset  obb = new ProductoptionDropDownDataset();

                    jsonObject = getobject.getProduct_option_value().getJSONObject(i);
                    obb.setName(jsonObject.getString("name"));
                    obb.setProduct_option_value_id(jsonObject.getInt("product_option_value_id"));

                   obb.setSpinner_type(jsonObject.getString("spinner_type"));
                    obb.setFont_color(jsonObject.getString("color_code"));
                    obb.setFont_type(jsonObject.getString("font_type"));
                    obb.setOption_value_id(jsonObject.getInt("option_value_id"));


                    ProductoptionDropDownDataset.add(obb);

                }


            } catch (JSONException e) {
                e.printStackTrace();


            }
            ProductOptionDropDownAdapter ProductOptionDropDownAdapter = new ProductOptionDropDownAdapter(context,ProductoptionDropDownDataset);
            optionSpinner.setAdapter(ProductOptionDropDownAdapter);






        }
        else if(getobject.getType().equals("text"))
        {
            optionusername =(EditText)convertView.findViewById(R.id.optionusername);
            optionusername.setVisibility(View.VISIBLE);
            optionSpinner.setVisibility(View.GONE);
            int maxLengthofEditText = 13;
            optionusername.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthofEditText)});
            optionusername.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    ClassAPI.option_name_for_laptop.setText(s);
                }
            });

        }
        else
        {
            optionusername =(EditText)convertView.findViewById(R.id.optionusername);
            optionusername.setVisibility(View.VISIBLE);
            optionSpinner.setVisibility(View.GONE);
            int maxLengthofEditText = 1000;
            optionusername.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthofEditText)});
        }


        final View finalConvertView = convertView;
        optionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int pos, long id) {



                ProductoptionDropDownDataset mSelected = (ProductoptionDropDownDataset) parentView.getItemAtPosition(pos);


                if(mSelected.getSpinner_type().toString().trim().equals("font_color"))
                {
                    String color = mSelected.getFont_color();
                    ClassAPI.option_name_for_laptop.setTextColor(Color.parseColor(color));

                }
                else  if(mSelected.getSpinner_type().toString().trim().equals("font_type"))
                {
                    String font = mSelected.getFont_type();
                    final Typeface childFont = Typeface.createFromAsset(context.getAssets(), "fonts/"+font+".ttf");
                     ClassAPI.option_name_for_laptop.setTypeface(childFont);
                }


                if(mSelected.getProduct_option_value_id() >0) {


                    if(mSelected.getName().equals("Yes")&&getobject.getCustomerequired().equals("TopRequired"))
                    {

                        optionImgsrd.setVisibility(View.VISIBLE);
                        for (int i = 0; i < optiondata.getChildCount(); i++) {


                            final ProductOptionDataset getobject = ProductOptionDataset.get(i);


                           /* if(getobject.getCustome_required_flag().equals("CheckSelectionFag")) {

                            }
                            else
                            {
                                if (validation.contains(getobject.getParenttitle())) {
                                    View listItem = optiondata.getChildAt(i);
                                    listItem.setVisibility(View.VISIBLE);
                                }
                            }*/




                        }



                    }
                    else if(mSelected.getName().equals("No")&&getobject.getCustomerequired().equals("TopRequired"))
                    {
                        optionImgsrd.setVisibility(View.GONE);
                        for (int i = 0; i < optiondata.getChildCount(); i++) {


                            final ProductOptionDataset getobject = ProductOptionDataset.get(i);


                            /*if(getobject.getCustome_required_flag().equals("CheckSelectionFag")) {

                            }
                            else
                            {
                                if (validation.contains(getobject.getParenttitle())) {
                                    View listItem = optiondata.getChildAt(i);
                                    listItem.setVisibility(View.GONE);
                                }
                            }*/



                        }

                    }










                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        return convertView;
    }
}
