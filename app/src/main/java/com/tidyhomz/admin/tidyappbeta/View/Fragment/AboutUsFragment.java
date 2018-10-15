package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {



View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ClassAPI.set_title.setText("About Us");
        TextView text = (TextView)view.findViewById(R.id.text);
        text.setText(Html.fromHtml(" <p> How often do we NOT find something which we need at a particular time while that same thing was lying around for all those days when we did not need it? Happens all the time, doesn’t it? This is a problem we aim to solve with Tidy Homz. The name itself is self-explanatory as are our products. One look at them- and you already have found a use (multiple uses in some cases) and a space in your home. We are an online platform for all things related to home, right from your most needed and basic utility items to some fancier wooden furniture and furnishings as well.</p>" +
                "            <br>" +
                "            <p>Here at Tidy Homz we believe only being pretty isn’t good enough anymore- you have to be useful too! Thus all our products are functional, space saving along with being attractive. Oh and we forgot- long lasting too, as we only use the highest quality raw materials to make sure every product stays with you for a long time! We believe in innovation, in bringing to you something you have not seen or used before and thus we are always on the lookout for newer and better products.</p>\n" +
                "            <p> We are also very strong believers of the thought that an educated society is the best kind of society and thus we believe in educating children and giving back to society right from the start. Thus for every order we get- we will donate a stipulated amount fromour side to educate a child. All you have to do is place an order and you automatically have contributed towards educating our next generation!</p>\n" +
                "            <p>Lastly we want you to be rest assured that once you buy something from us, you become a part of a family called Tidy Homz and we will always serve our family in the best possible way!</p>" +
                "            <p> So Heres To Making homz Tidy one product at a time!Happy Shopping!!! </p>"));
        return view;
    }

}
