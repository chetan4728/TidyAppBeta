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
public class PolyciesFragment extends Fragment {


   View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_polycies, container, false);
        ClassAPI.set_title.setText("PRIVACY POLICY");
        TextView first = (TextView)view.findViewById(R.id.first);
        TextView second = (TextView)view.findViewById(R.id.second);
        TextView third = (TextView)view.findViewById(R.id.third);
        TextView fourth = (TextView)view.findViewById(R.id.fourth);
        TextView fifth = (TextView)view.findViewById(R.id.fifth);

        first.setText(Html.fromHtml("<p>\n" +
                "We may\n" +
                "collect personal identification information from Users in a variety of ways,\n" +
                "including, but not limited to, when Users visit our site, register on the site,\n" +
                "place an order, fill out a form, respond to a survey, subscribe to the\n" +
                "newsletter and in connection with other activities, services, features or\n" +
                "resources we make available on our Site. Users may be asked for appropriate,\n" +
                "name, email address, mailing address, phone number, and credit card\n" +
                "information.\n" +
                "</p>\n" +
                "\n" +
                "<p>\n" +
                "Users may,\n" +
                "however, choose to visit our Site anonymously.\n" +
                "</p>\n" +
                "<p>\n" +
                "We will\n" +
                "collect personal identification information from Users only if they voluntarily\n" +
                "submit such information to us. Users can always refuse to supply personally\n" +
                "identification information, except that it may prevent them from engaging in\n" +
                "certain Site related activities.\n" +
                "</p>"));


        second.setText(Html.fromHtml("<p>TidyHomz collects\n" +
                "and uses Users personal information for the following purposes:</p>"));
        third.setText(Html.fromHtml("<p>\n" +
                "Tidy Casa\n" +
                "Private Limited&nbsp;has the discretion to update this privacy policy at any\n" +
                "time. When we do, post a notification on the main page of our Website,revise\n" +
                "the updated date at the bottom of this page,send you an email. We encourage Users\n" +
                "to frequently check this page for any changes to stay informed about how we are\n" +
                "helping to protect the personal information we collect. You acknowledge and\n" +
                "agree that it is your responsibility to review this privacy policy periodically\n" +
                "and become aware of modifications.\n" +
                "\n" +
                "</p>"));
        fourth.setText(Html.fromHtml("<p>\n" +
                "\n" +
                "Users may\n" +
                "find advertising or other content on our Site that link to the sites and\n" +
                "services of our partners, suppliers, advertisers, sponsors, licensors and other\n" +
                "third parties. We do not control the content or links that appear on these\n" +
                "sites and are not responsible for the practices employed by websites linked to\n" +
                "or from our Site. In addition, these sites or services, including their content\n" +
                "and links, may be constantly changing. These sites and services may have their\n" +
                "own privacy policies and customer service policies. Browsing and interaction on\n" +
                "any other website, including websites which have a link to our Site, is subject\n" +
                "to that website's own terms and policies.\n" +
                "</p>\n"));
        fifth.setText(Html.fromHtml("<p>\n" +
                "\n" +
                "If you have any questions about this\n" +
                "Privacy Policy, the practices of this site, or your dealings with this site,\n" +
                "please contact us at: <br>\n" +
                "Tidy Casa Private Limited,<br>\n" +
                "Anjali Complex,<br>\n" +
                "Khadkeshwar,<br>\n" +
                "Aurangabad,<br>\n" +
                "Maharashtra –\n" +
                "431001\n" +
                "\n" +
                "</p>"));
        return view;
    }

}
