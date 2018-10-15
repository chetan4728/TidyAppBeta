package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.R;


public class MoreDetailProductFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_more_detail_product, container, false);

        String Title = getArguments().getString("ProductTitle");
        String Desc = getArguments().getString("ProductDesc");
        String Feature = getArguments().getString("ProductFeature");

        TextView Descdd = (TextView)view.findViewById(R.id.Desc);
        Descdd.setText(Desc);

        TextView FeatureText = (TextView)view.findViewById(R.id.Feature);
        FeatureText.setText(Feature);

        return view;
    }

}
