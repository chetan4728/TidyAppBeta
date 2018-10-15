package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tidyhomz.admin.tidyappbeta.Dataset.NotificationModel;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.util.ArrayList;

/**
 * Created by Admin on 9/27/2017.
 */

public class NotificationAdapter extends BaseAdapter {
    Context context;
    ArrayList<NotificationModel> ArrayList;
    ArrayList<Integer> clickArray;
    int notification_id;

    public NotificationAdapter(Context context, java.util.ArrayList<NotificationModel> arrayList, int notification_id) {
        this.context = context;
        this.ArrayList = arrayList;
        this.notification_id = notification_id;

    }

    @Override
    public int getCount() {
        return ArrayList.size();
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

        clickArray= new ArrayList<>();
        final NotificationModel object = ArrayList.get(position);
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notification_list_item,null);
        }


        TextView title = (TextView)convertView.findViewById(R.id.title);
        title.setText(object.getNotification_title());
        TextView desc = (TextView)convertView.findViewById(R.id.desc);
        desc.setText(object.getNotification_desc());
        LinearLayout notlayout = (LinearLayout)convertView.findViewById(R.id.notlayout);

        String imageUrl = object.getNotification_image();
        imageUrl = imageUrl.replace(" ","%20");
        ImageView notificationImage = (ImageView)convertView.findViewById(R.id.notificationImage);
        Picasso.with(context.getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder2)
                .into(notificationImage);


        int not_id = Integer.parseInt(object.getNotification_type_id().toString());
        if(notification_id==not_id)
        {
            notlayout.setBackgroundColor(Color.parseColor("#dddddd"));
        }

        RelativeTimeTextView v = (RelativeTimeTextView)convertView.findViewById(R.id.timestamp); //Or just use Butterknife!
        v.setReferenceTime(Long.parseLong(object.getNotificatin_data()));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(object.getNotificatin_type_name().equals("JOIN US"))
                {
                    ((MainActivity) context).signup();
                }
                else if(object.getNotificatin_type_name().trim().equals("PRODUCT"))
                {

                    ((MainActivity) context).showProductDetail(Integer.parseInt(object.getNotification_type_id()),object.getNotification_title());
                }
                else if(object.getNotificatin_type_name().equals("CATEGORY"))
                {

                    ((MainActivity) context).loadCategoryProduct(object.getNotification_type_id(),object.getNotification_title());

                }
                else if(object.getNotificatin_type_name().equals("SPECIAL"))
                {
                    ((MainActivity) context).LoadSpecialBannerFragment();

                }
                else if(object.getNotificatin_type_name().equals("WALLET"))
                {
                    ((MainActivity) context).LoadSpecialBannerFragment();

                }
                else
                {


                }
                ((MainActivity)context).updateNotificationflag(object.getNotification_id());
            }
        });

        return convertView;
    }
}
