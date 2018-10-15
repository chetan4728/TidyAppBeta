package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.Dataset.UserReviewModel;
import com.tidyhomz.admin.tidyappbeta.R;

import java.util.ArrayList;

/**
 * Created by Admin on 7/18/2017.
 */

public class UserRatingAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserReviewModel> Review;

    public UserRatingAdapter(Context context,ArrayList<UserReviewModel> Review) {
        this.context = context;
        this.Review = Review;
    }

    @Override
    public int getCount() {
        return Review.size();
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

        if(convertView ==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_review_item_view, null);

        }

        UserReviewModel obj = Review.get(position);

        TextView reUser = (TextView)convertView.findViewById(R.id.nameUser);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView ReviewDesc = (TextView)convertView.findViewById(R.id.ReviewDesc);
        RatingBar Rt = (RatingBar)convertView.findViewById(R.id.pop_ratingbar_product);

        reUser.setText("Posted By "+obj.getReviewerName());
        date.setText("Posted On "+obj.getDateon());
        ReviewDesc.setText(obj.getReview());
        Rt.setRating(Float.parseFloat(obj.getUserrating()));
        return convertView;
    }
}
