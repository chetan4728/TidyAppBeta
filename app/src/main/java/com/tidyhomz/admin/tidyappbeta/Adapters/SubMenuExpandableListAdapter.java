package com.tidyhomz.admin.tidyappbeta.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 9/1/16.
 */
public class SubMenuExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mListDataHeader; // header titles
    private List<String> mGroupHeaderId;
    private List<String> mchildHeaderId;
    private List<String> child;
    private HashMap<String, List<String>> mListDataChild;
    private int row_index = -1;

    public SubMenuExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData, List<String> GroupHeaderId, List<String> childHeaderId,
                                        List<String> child)
    {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.mGroupHeaderId = GroupHeaderId;
        this.mchildHeaderId = childHeaderId;
        this.child = child;


    }

    @Override
    public int getGroupCount() {
        int i = mListDataHeader.size();
        return i;


    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mListDataChild.get(
                this.mListDataHeader.get(groupPosition))
                .size();


    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //Log.d("CHILD", mListDataChild.get(this.mListDataHeader.get(groupPosition))
        //        .get(childPosition).toString());
        return this.mListDataChild.get(
                this.mListDataHeader.get(groupPosition))
                .get(childPosition);

    }







    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }




    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.submenu_list_item, null);








            TextView LblGroupId = (TextView) convertView.findViewById(R.id.GroupID);
            LblGroupId.setText(mGroupHeaderId.get(groupPosition));
            TextView lblListHeader = (TextView)convertView.findViewById(R.id.submenu);
            lblListHeader.setText(mListDataHeader.get(groupPosition));
        }


        ImageView image = (ImageView)convertView.findViewById(R.id.expandableIcon);

        if (getChildrenCount(groupPosition) == 0)
        {
            image.setVisibility(View.INVISIBLE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mContext instanceof MainActivity) {
                       ((MainActivity) mContext).loadCategoryProduct(mGroupHeaderId.get(groupPosition),mListDataHeader.get(groupPosition));
                    }


                }
            });

        }
        else
        {

            int imageResourceId = isExpanded ? R.drawable.down :R.drawable.ar_right;
            image.setImageResource(imageResourceId);
            image.setVisibility(View.VISIBLE);
        }


        //lblListHeader.setText(headerTitle.getIconName());
        //headerIcon.setImageResource(MainActivity.icon[groupPosition]);
        return convertView;




    }


    protected void bindGroupView(View view, Context paramContext, Cursor cursor, boolean paramBoolean){


    }


    @Override
    public View getChildView(int groupPosition, final  int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = child.get(childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.submenu_child_item, null);
        }


        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);

        txtListChild.setText(childText);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mContext instanceof MainActivity) {
                    ((MainActivity) mContext).loadCategoryProduct(mchildHeaderId.get(childPosition),childText);
                }


            }


        });
        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}