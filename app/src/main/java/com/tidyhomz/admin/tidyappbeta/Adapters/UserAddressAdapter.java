package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Dataset.AddressDatasetModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.UserAddressFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 8/24/2017.
 */

public class UserAddressAdapter extends BaseAdapter  {

    private ArrayList<AddressDatasetModel>   AddressList;
    private Context context;
    int selected = 0;
    Fragment userAddressFragment;
     ImageView mOverflowIcon;
    public UserAddressAdapter(Context context, ArrayList<AddressDatasetModel> AddressList, Fragment userAddressFragment) {

        this.AddressList = AddressList;
        this.context = context;
        this.userAddressFragment = userAddressFragment;

    }

    @Override
    public int getCount() {
        return AddressList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final AddressDatasetModel obj = AddressList.get(position);

        if(convertView ==null)
        {
            LayoutInflater view = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.user_address_item_list,null);
        }

        RadioButton radio = (RadioButton) convertView.findViewById(R.id.addtessselection);
        radio.setTag(position);



        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView addtess1 = (TextView)convertView.findViewById(R.id.addtess1);
        TextView addtress2 = (TextView)convertView.findViewById(R.id.addtress2);
        TextView city = (TextView)convertView.findViewById(R.id.city);
        TextView state = (TextView)convertView.findViewById(R.id.state);
        TextView mobile = (TextView)convertView.findViewById(R.id.mobile);

        name.setText(obj.getFirstname()+" "+obj.getLastname());
        addtess1.setText(obj.getAddress_1());
        addtress2.setText(obj.getAddress_2());
        city.setText(obj.getCity()+" "+obj.getPostcode());
        state.setText(obj.getState()+" "+obj.getCountry());
        mobile.setText(obj.getTelephone());

        mOverflowIcon = (ImageView) convertView.findViewById(R.id.editaddtess);
        mOverflowIcon.setOnClickListener(new myClickListener(position));


        if(obj.getDefault_yes() == 1)
        {
            radio.setChecked(true);

            String add = obj.getAddress_1()+" " +obj.getAddress_2()+" "+obj.getPostcode();
            AppSharedPreferences App = new AppSharedPreferences(context);
            App.editor.putString(App.USERADDRESSID, String.valueOf(obj.getAddress_id()));
            App.editor.putString(App.USERADDRESS, add);
            App.editor.putString(App.USERADDRESSNAME, obj.getFirstname()+" "+obj.getLastname());
            App.editor.putString(App.USERADDRESSMOBILE, obj.getTelephone());
            App.editor.putString(App.USERADDRESSCOUNTREY, obj.getCountry_id());
            App.editor.putString(App.USERADDRESSZHONE, obj.getZone_id());
            App.editor.commit();
        }
        else {
            radio.setChecked(false);
        }

        radio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ((UserAddressFragment) userAddressFragment).update_default_address(obj.getAddress_id());

            }
        });




        return convertView;
    }






    class myClickListener implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        long id;

        public myClickListener(long id) {
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            PopupMenu popup = new PopupMenu(context, v);
            popup.setOnMenuItemClickListener(this);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.popup, popup.getMenu());
            popup.show();
        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            final AddressDatasetModel obj = AddressList.get((int) id);
            AppSharedPreferences App = new AppSharedPreferences(context);
            final String customer_id = App.pref.getString(App.UserId,"");


            if(item.toString().trim().equals("Edit"))
            {
                ((UserAddressFragment) userAddressFragment).editAdress(obj.getAddress_id());

            }
            else if((item.toString().trim().equals("Delete")))
            {

                 if(AddressList.size()>1) {

                     StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.DELETEADDRESS,
                             new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {

                                     Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                                     ((UserAddressFragment) userAddressFragment).getaddresList();
                                     notifyDataSetChanged();


                                 }
                             },
                             new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {
                                     Toast.makeText(context, "opps Somthing get's Worng", Toast.LENGTH_LONG).show();
                                 }
                             }) {
                         @Override
                         protected Map<String, String> getParams() {
                             Map<String, String> params = new HashMap<String, String>();
                             params.put("customer_id", customer_id);
                             params.put("address_id", String.valueOf(obj.getAddress_id()));
                             return params;
                         }

                     };

                     RequestQueue requestQueue = Volley.newRequestQueue(context);
                     requestQueue.add(stringRequest);
                 }
                 else
                 {
                     Toast.makeText(context, "You must have at least one address!", Toast.LENGTH_SHORT).show();
                 }

            }

            return false;
        }

    }
}
