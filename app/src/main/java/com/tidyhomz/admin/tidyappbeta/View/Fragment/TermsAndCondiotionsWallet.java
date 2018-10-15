package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.R;

import org.xml.sax.XMLReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsAndCondiotionsWallet extends Fragment {



View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_terms_and_condiotions_wallet, container, false);
        TextView termsnconditions = (TextView)view.findViewById(R.id.termsnconditions);

        termsnconditions.setText(Html.fromHtml("<ul>" +
                "<li> &nbsp; This program is applicable on for those who download our app and register themselves as users on the app.</li></br>" +
                "<li> &nbsp; Once the registration is complete, the unique referral code will be found in the Refer And Earn Section in My Account.</li></br>" +
                "<li> &nbsp; One Referral code can be sent and used by a maximum of 20 people.</li></br>" +
                "<li> &nbsp; To use the Referral code, each person also must download the app and register as a customer. The Referral code will be applicable only if the customer has shopped for above Rs 1,000 or more.</li></br>" +
                "<li> &nbsp; Each person who uses the referral code will get a cashback of Rs 150 in their wallet after the delivery of the product.</li></br>" +
                "<li> &nbsp;  Moreover the person who has generated and given the code will also earn Rs 150 in their wallet first time their friends use the code(after the completion of their friend's order). The more you Refer the more you earn!</li></br>" +
                "<li> &nbsp; The money in your Tidy wallet is redeemable on a purchase of Rs 1,000 or more.</li></br>" +
                "<li> &nbsp; Maximum money that can be used from the Tidy wallet is 50 percent of the order (i.e. on the MRP of the product) and cart amount above Rs 1,000.</li></br>" +
                "<li> &nbsp; Tidy Cash is the virtual discount and cannot be converted into actual cash.</li></br>" +
                "<li> &nbsp; Tidy Cash policies can be modified and defined by Tidy Casa Private Limited at its sole discretion.</li></br>" +
                "<li> &nbsp; Tidy Casa Private Limited reserves all the rights towards distribution, redemption, cancellation and usage of Tidy Cash.</li></br>" +
                "<li> &nbsp; Tidy Casa Private Limited has all the rights to claim/decline any Tidy Cash used by its customer for purchasing the product.</li></br>" +
                "<li> &nbsp; Tidy Cash does not impose any liability on Tidy Casa Private Limited or Tidy Homz.</li></br>" +
                "<li> &nbsp; In the event that an item is returned post-delivery or the user refuses to accept delivery, no Tidy Cash shall be credited back.</li></br>" +
                "<li> &nbsp; The maximum amount that a tidy wallet can acquire is Rs.10,000. </li></br>" +
                "</ul>", null, new MyTagHandler()));




        return view;
    }

    public class MyTagHandler implements Html.TagHandler {
        boolean first= true;
        String parent=null;
        int index=1;
        @Override
        public void handleTag(boolean opening, String tag, Editable output,
                              XMLReader xmlReader) {

            if(tag.equals("ul")) parent="ul";
            else if(tag.equals("ol")) parent="ol";
            if(tag.equals("li")){
                if(parent.equals("ul")){
                    if(first){
                        output.append("\n\n\t•");
                        first= false;
                    }else{
                        first = true;
                    }
                }
                else{
                    if(first){
                        output.append("\n\n\t"+index+". ");
                        first= false;
                        index++;
                    }else{
                        first = true;
                    }
                }
            }
        }
    }

}
