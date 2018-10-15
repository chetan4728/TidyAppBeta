package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tidyhomz.admin.tidyappbeta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProceedBillingDetailFragment extends Fragment {


    public ProceedBillingDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proceed_billing_detail, container, false);
    }

}
