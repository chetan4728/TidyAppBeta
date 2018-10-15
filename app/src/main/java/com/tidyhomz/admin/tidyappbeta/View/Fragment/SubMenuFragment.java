package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.SubMenuExpandableListAdapter;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubMenuFragment extends Fragment {


    private TextView hiidenid;
    private String CatName, CatID;
    private ExpandableListView submenulist;
    public static final String KEY_CAT_ID = "category_id";
    public static String[] subcat_id;
    List<String> GroupItem;
    List<String>ChildItemId;
    List<String> child;
    HashMap<String, List<String>> listDataChild;
    SubMenuExpandableListAdapter mMenuAdapter;
    private JSONArray GroupHeader = null;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_menu, container, false);
        String cat_id = getArguments().getString("cat_id");
        String cat_name = getArguments().getString("cat_Name");
        TextView title = (TextView)view.findViewById(R.id.set_title);
        title.setText(cat_name);
        LinearLayout backimage = (LinearLayout)view.findViewById(R.id.backTo_main);

        backimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                getActivity().getSupportFragmentManager()
                        .beginTransaction().
                        remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.sub_menu)).commit();
                ClassAPI.globalFrame.setVisibility(View.VISIBLE);

                AnimatorSet set = new AnimatorSet();
                // Using property animation


                ObjectAnimator animation = ObjectAnimator.ofFloat(ClassAPI.globalFrame,
                        "translationX", -900f, 0f);
                animation.setDuration(200);
                set.play(animation);
                set.start();


            }
        });



        CatID = cat_id;
        submenulist = (ExpandableListView) view.findViewById(R.id.sub_menu_list);

        ViewTreeObserver vto = submenulist.getViewTreeObserver();


        vto.addOnGlobalLayoutListener(new      ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                submenulist.setIndicatorBounds(submenulist.getMeasuredWidth() - 70, submenulist.getMeasuredWidth());
            }
        });

        prepareListData();




        return view;



    }




    public void prepareListData() {



        VolleyUtils.makeJsonObjectRequest(getActivity(), ClassAPI.SUB_CAT+CatID, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {

                Log.e("", String.valueOf(response));
                load_data((String) response);
            }
        });


       /* CacheRequest cacheRequest = new CacheRequest(0, ClassAPI.SUB_CAT+CatID, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {


                try {
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));

                    JSONObject jsonObject = new JSONObject(jsonString);
                    load_data(jsonObject.toString());


                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        VolllyRequest.getInstance(((AppCompatActivity)getActivity())).addToRequestQueue(cacheRequest);*/



// Adding data header


    }


    public void load_data(String response) {


        GroupItem = new ArrayList<String>();
        ChildItemId = new ArrayList<String>();

        listDataChild = new HashMap<>();
        child = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();

        List<String> Group_id = new ArrayList<String>();
        // Adding data header




        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(response);
            GroupHeader = jsonObject.getJSONArray(ClassAPI.JSON_ARRAY);

            for(int i=0;i<GroupHeader.length();i++) {

                JSONObject jo = GroupHeader.getJSONObject(i);
                String group_name = jo.getString("name");
                String group_id = jo.getString("category_id");
                group_name = group_name.replace("&amp;","&");
                GroupItem.add(group_name);
                Group_id.add(group_id);
                JSONArray claims = jo.getJSONArray("child");



                for (int k = 0; k < claims.length(); k++) {
                    JSONObject d = claims.getJSONObject(k);
                    String child_name = d.getString("name");
                    String child_id = d.getString("category_id");
                    child_name = child_name.replace("&amp;","&");
                    child.add(child_name);
                    ChildItemId.add(child_id);

                }


                if(claims.length() > 0) {
                    listDataChild.put(GroupItem.get(i), child);

                }
                else
                {
                    listDataChild.put(GroupItem.get(i), child2);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMenuAdapter = new SubMenuExpandableListAdapter(((AppCompatActivity)getActivity()),GroupItem, listDataChild,Group_id,ChildItemId,child);
        submenulist.setAdapter(mMenuAdapter);
    }

}
