package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;


public class OrderSuccessFragment extends Fragment {


   View view;
   AppSharedPreferences App;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_order_success, container, false);
        ClassAPI.set_title.setText("Success");

        ((MainActivity) getActivity()).removeOrderData();






        Button backbutton = (Button)view.findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassAPI.secondarytoolbar.setVisibility(View.GONE);

                ((MainActivity) getActivity()).ShowActionBar();
            int count =  getActivity().getSupportFragmentManager().getBackStackEntryCount();
                for(int i = 0; i < count; i++) {
                    getActivity().getSupportFragmentManager().popBackStack();

            }

                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.Fragment_container)).commit();


        }
        });

        return  view;
    }

}
