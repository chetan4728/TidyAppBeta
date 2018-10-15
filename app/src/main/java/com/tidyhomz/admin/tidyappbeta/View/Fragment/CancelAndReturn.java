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


public class CancelAndReturn extends Fragment {


  View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cancel_and_return, container, false);
        TextView returnPol = (TextView)view.findViewById(R.id.returnPol);
        ClassAPI.set_title.setText("CANCEL & RETURNS");
        returnPol.setText(Html.fromHtml("<p>If unfortunately you have to cancel an order, please do so within 12 hours of placing the order,&nbsp;If you think, you have received the product in a bad condition or if the packaging is tampered with or damaged before delivery,please refuse to accept the package and return the package to the delivery person.&nbsp;&nbsp;Please make sure that the original product tag and packing is intact when you send us the product back. Return Period is 15 days from the date the product was received by the customer.</p>\n" +
                "&nbsp; &nbsp; - &nbsp;A wrong product has been dispatched and in the event that a product does not match the item selected during order confirmation.<br>\n" +
                "&nbsp; &nbsp; - &nbsp;If you identify a quality or a manufacturing defect on the received product. <br>\n" +
                "\n" +
                "&nbsp; &nbsp; - &nbsp;If the product has been received in a damaged condition.  <br>\n" +
                "<p>&nbsp; &nbsp; &nbsp; In these cases, we will take back the product and exchange it for a new one or give you store credit to purchase another product from our website. </p>\n" +
                "\n" +
                "<p>Unfortunately, we are unable to accept returns for any other reasons. </p>\n" +
                "\n" +
                "<p>Points to remember:</p>\n" +
                "\n" +
                "<p>   &nbsp; &nbsp; - &nbsp;No returns or cancellations are allowed on customized products. <br>\n" +
                "      &nbsp; &nbsp; - &nbsp;Our return policy is applicable within 15 days from the receipt of the product and applicable only for the following \n" +
                "        reasons: damaged product, manufacturing defect or incorrect product. The time frame starts from the date the product was delivered as per the confirmation from our logistics partners. You can initiate return in the &nbsp; account section of the website. <br>\n" +
                "     &nbsp; &nbsp; - &nbsp;Please make sure that the original product tag and packing is intact when you send us the product back. <br>\n" +
                "     &nbsp; &nbsp; - &nbsp;Product should be in unused and original condition. <br>\n" +
                "     &nbsp; &nbsp; - &nbsp;\tPlease send us 2 images (one of the broken part and one of the whole product) for us to ascertain the reason of the return. You can email the images to us at <b>hello@tidyhomz.com</b> or<b> whatsapp</b> us on +91 7755992179<br>\n" +
                "     &nbsp; &nbsp; - &nbsp;\tWe will then initiate the return process and arrange for a reverse pickup with our logistics partner\n" +
                "</p>\n" +
                "\n" +
                "<p>(Also please note: Product colors may vary between the images shown on the website and social media platforms against the actual product) </p>"));
        return view;
    }

}
