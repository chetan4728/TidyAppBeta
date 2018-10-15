package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tidyhomz.admin.tidyappbeta.DesignHelpers.TouchImageView;
import com.tidyhomz.admin.tidyappbeta.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductImageZoomFragment extends Fragment {





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

                View view =inflater.inflate(R.layout.fragment_product_image_zoom, container, false);

        TouchImageView sm = (TouchImageView)view.findViewById(R.id.productZoom);
        String image = getArguments().getString("imagepath");

        Picasso.with(getActivity()).load(image).into(sm);


        return view;
    }

}
