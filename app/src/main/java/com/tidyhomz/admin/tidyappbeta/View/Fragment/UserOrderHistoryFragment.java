package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.orderHistoryAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.OrderHistoryModel;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserOrderHistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    View view;
    String customer_id;
    JSONArray GetOrderUrl;
    ArrayList<OrderHistoryModel> OrderHistoryModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    orderHistoryAdapter orderHistoryAdapter;
    ListView OrderHistory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ClassAPI.set_title.setText("Order Histroy");
        view = inflater.inflate(R.layout.fragment_user_order_history, container, false);
        AppSharedPreferences App = new AppSharedPreferences(getActivity());
        customer_id = App.pref.getString(App.UserId,"");
        OrderHistory = (ListView)view.findViewById(R.id.orderhistory);
        get_order_history();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);



        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        get_order_history();
                                    }
                                }
        );

        OrderHistory.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(OrderHistory != null && OrderHistory.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = OrderHistory.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = OrderHistory.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
            }});
        return view;

    }


    public void load_Detail_order(int order_id, String orderDate, String status)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("order_id",order_id);
        bundle.putString("order_date",orderDate);
        bundle.putString("status",status);

        OrderDetailFragment OrderDetailFragment = new OrderDetailFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Fragment_container, OrderDetailFragment);
        fragmentTransaction.addToBackStack(null);
        OrderDetailFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }
    public  void get_order_history()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.ORDERHISTORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                        //Log.e("",response);
                        getData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"opps Somthing get's Worng",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("customer_id",customer_id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void getData(String response)
    {
        OrderHistoryModel  = new ArrayList<>();
        JSONObject json = null;
        try {
            json = new JSONObject(response);
            GetOrderUrl = json.getJSONArray("result");

            for (int i=0;i<GetOrderUrl.length();i++)
            {
                JSONObject object = GetOrderUrl.getJSONObject(i);
                OrderHistoryModel setdata = new OrderHistoryModel();


                setdata.setOrder_id(object.getInt("order_id"));
                setdata.setName(object.getString("name"));
                setdata.setStatus(object.getString("status"));
                setdata.setDate_added(object.getString("date_added"));
                setdata.setProducts(object.getString("products"));
                setdata.setTotal(object.getString("total"));
                OrderHistoryModel.add(setdata);

            }
            String count = String.valueOf(GetOrderUrl.length());
            TextView orderCount= (TextView)view.findViewById(R.id.orderCount);
            orderCount.setText("You Have Total "+count+" Orders");
            orderHistoryAdapter orderHistoryAdapter = new orderHistoryAdapter(getActivity(),OrderHistoryModel,UserOrderHistoryFragment.this);
            OrderHistory.setAdapter(orderHistoryAdapter);
            swipeRefreshLayout.setRefreshing(false);

        } catch (JSONException e) {


            e.printStackTrace();
        }



    }

    @Override
    public void onRefresh() {
        get_order_history();
    }
}
