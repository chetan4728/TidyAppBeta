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
public class TermsAndConditionFragmnet extends Fragment {


   View view;
   TextView GENERAL,PROHIBITIONS,termSale,warnaty,softwate,NVALIDITY,privacy,
           INDEMNITY,aggreement,govering,COMPLAINTS,ASSIGNMENT,DISCOUNTS,special,
           colors,refund,shipping;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_terms_and_condition_fragmnet, container, false);
        GENERAL = (TextView)view.findViewById(R.id.GENERAL);
        PROHIBITIONS = (TextView)view.findViewById(R.id.PROHIBITIONS);
        warnaty  = (TextView)view.findViewById(R.id.warnaty);
        termSale = (TextView)view.findViewById(R.id.termSale);
        softwate = (TextView)view.findViewById(R.id.softwate);
        NVALIDITY = (TextView)view.findViewById(R.id.NVALIDITY);
        privacy = (TextView)view.findViewById(R.id.privacy);
        INDEMNITY = (TextView)view.findViewById(R.id.INDEMNITY);
        aggreement = (TextView)view.findViewById(R.id.aggreement);
        govering = (TextView)view.findViewById(R.id.govering);
        COMPLAINTS = (TextView)view.findViewById(R.id.COMPLAINTS);
        ASSIGNMENT = (TextView)view.findViewById(R.id.ASSIGNMENT);
        DISCOUNTS = (TextView)view.findViewById(R.id.DISCOUNTS);
        special = (TextView)view.findViewById(R.id.special);
        colors = (TextView)view.findViewById(R.id.colors);
        refund = (TextView)view.findViewById(R.id.refund);
        shipping =  (TextView)view.findViewById(R.id.shipping);
        ClassAPI.set_title.setText("TERMS & CONDITIONS");

        GENERAL.setText(Html.fromHtml("<p>Welcome to <a href=\"http://www.tidyhomz.com/\">www.tidyhomz.com</a>.&nbsp;The Website including any copyright, trademarks, tradenames, patents and intellectual property rights associated with tidyhomz and/or the Website vest solely in Tidy Casa Private Limited (“TidyHomz”).&nbsp; TidyHomz is a company registered under the Companies Act, 2013 with its registered office situated at Anjali Complex, Khadkeshwar, Aurangabad MAH-431001</p><br>\n" +
                "\n" +
                "\n" +
                "<p>This document governs your relationship with the Website and/or TidyHomz. Access to and use of this Website and the products and services available through this Website (collectively, the \"Services\") are subject to the following terms, conditions and notices (the \"Terms of Service\").Therefore, you are required to read the Terms of Service carefully before accepting.&nbsp; By using the Site and/or Services, you indicate your understanding and acceptance of the Terms of use.You agree to cease the use of the Site and/or any of the Services should you not agree with any of the Terms of Service.</p>\n" +
                "\n" +
                "\n" +
                "<p>This Website may contain links to other websites (the \"Linked Sites\"), which are not operated by Tidy Homz and hereby confirms that it has no control over such Linked Sites and accepts no responsibility for them or for any loss or damage that may arise from your use of such Linked Sites. Your use of the Linked Sites will be subject to the terms of use and service contained with in each such site.</p>\n" +
                "\n" +
                "\n" +
                "<p>By using the Website, you agree to the collection, transfer, storage and use of any personal information provided by you on the Website by Tidy Homz. The data is stored and controlled on servers located in Aurangabad, Maharashtra, India ], India as further described in our Privacy Policy. By submitting your resume with your replies, you give permission to Tidy Homz to publicly display your resume which can be freely accessed by anyone. You also agree to receive marketing communications from us unless you specifically indicate otherwise in writing to us through hello@tidyhomz.com</p>"));



        PROHIBITIONS.setText(Html.fromHtml("<p>You must not misuse this Website. By your use of the Website or your contractual relationship with TidyHomz, you will not: (a) commit or encourage a criminal offense; (b) transmit or distribute a virus, trojan, worm, logic bomb or any other material which is malicious, technologically harmful, in breach of confidence or in any way offensive or obscene; (c) hack into any aspect of the Service; (d) corrupt data; cause annoyance to other users; (e) infringe upon the rights of any other person's proprietary rights; send any unsolicited advertising or promotional material, commonly referred to as \"spam\";or attempt to affect the performance or functionality of any computer facilities of or accessed through this Website. Breaching this provision would constitute a criminal offense and <span style=\"color:#002060\">TidyHomz&nbsp;</span>will report any such breach to the relevant law enforcement authorities and disclose your identity to them.</p><br>\n" +
                "\n" +
                "\n" +
                "<p>We will not beliable for any loss or damage caused by a distributed denial-of-service attack, viruses or other technologically harmful material that may infect your computer equipment, computer programs, data or other proprietary material due to your use of this Website or to your downloading of any material posted on it, or on any website linked to it.\t\t\t\t\t</p>\n" +
                "\n" +
                "<p>Further, if you use the Website by registering on it, you are responsible for maintaining the confidentiality of your User ID, password, email address and for restricting access to your computer, computer system, computer network and your Tidy Homz account, and you are responsible for all activities that occur under your User ID and password.If you access the Website using any electronic device other than by registration on the Site, the Terms of Service remains applicable to you in the same manneras if you are a registered user on the Website.</p>"));


        termSale.setText(Html.fromHtml("<p>By placing an order you are offering to purchase a product on the Website subject to the terms and conditions herein.All orders are subject to availability and confirmation of the order price.Dispatch times may vary according to availability and subject to any delays resulting from postal delays or force majeure for which we will not be responsible.</p><br>\n" +
                "\n" +
                "\n" +
                "<p>In order to contract with <span style=\"color:#002060\">www.tidyhomz.com</span> you must be over 18 years of age and possess a valid credit or debit card issued by a bank or any payment wallet(such as PayTM, PayU, PayPal, etc.)acceptable to us. <span style=\"color:#002060\">www.tidyhomz.com&nbsp;</span>retains the right to refuse any request made by you. If your order is accepted we will inform you by email and we will confirm the identity of the party which you have contracted with. This will usually be <span style=\"color:#002060\">www.tidyhomz.com</span>or may in some cases be a third party. Where a contract is made with a third party <span style=\"color:#002060\">www.tidyhomz.com</span> is not acting as either agent or principal and the contract is made between yourself and that third party and will be subject to the terms of sale which they supply you.When placing an order you undertake that all details you provide to us are true and accurate, that you are an authorized user of the credit or debit card used to place your order and that there are sufficient funds to cover the cost of the goods. The cost of foreign products and services may fluctuate. All prices advertisedare subject to such changes.</p>"));

        warnaty.setText(Html.fromHtml("<p>You expressly acknowledge and agree that use of the Website and the Products and/or Services is entirely at your own risk and that the Website and the Product and/or Service are provided on an \"as is\" or \"as available\" basis,without any warranties of any kind. All express and implied warranties,including, without limitation, the warranties of merchantability, fitness for a particular purpose, and non-infringement of proprietary rights are expressly disclaimed to the fullest extent permitted by law.</p>\n" +
                "\n" +
                "\n" +
                "<p>Tidy Homz and/or the Website makes no warranties or representations about the accuracy or completeness of the Website and/or the content or the content of any Linked Websites linked to the Site and assumes no liability or responsibility for any(i) errors, mistakes, or inaccuracies of the content on the Website and/or Linked Website, (ii) personal injury or property damage, of any nature whatsoever, resulting from your access to and use of the Site and the Service,(iii) any unauthorized access to or use of our servers and/or any and all personal information and/or financial information stored there in, (iv) any interruption or cessation of transmission to or from the Website, (iv) any bugs, viruses, trojan horses, or the like which may be transmitted to or through the website by any third party, and/or (v) any errors or omissions in any content or for any loss or damage of any kind incurred as a result of the use of any content posted, emailed, communicated, transmitted, or otherwise made available via the Website or the Products and/or Services.</p>"));
        softwate.setText(Html.fromHtml("<p>The intellectual property rights in all software and content (including photographic images) made available to you on or through this Website including all intellectual property rights in the domain name of the Website remains the property of Tidy Homz or its licensors and is protected by copyright laws and treaties around the world. All such rights are reserved by Tidy Homz and its licensors. You may store, print and display the content supplied solely for your own personal use. You are not permitted to publish, manipulate, distribute or otherwise reproduce, in any format, any of the content or copies of the content supplied to you or which appears on this Website nor may you use any such content in connection with any business or commercial enterprise.</p>"));
        NVALIDITY.setText(Html.fromHtml("<p>If any part of the Terms of Service is or becomes unenforceable (including any provision in which we exclude our liability to you) the enforceability of any other part of the Terms of Service will not be affected all other clauses remaining in full force and effect. So far as possible where any clause/sub-clause or part of a clause/sub-clause can be severed to render the remaining part valid, the clause shall be interpreted accordingly. Alternatively, you agree that the clause shall be rectified and interpreted in such a way that closely resembles the original meaning of the clause /sub-clause as is permitted by law.</p>"));
        privacy.setText(Html.fromHtml("<p>Our privacy policy, which sets out how we will use your information, can be found at <span style=\"color:#002060\">Privacy Policy&nbsp;</span>By using this Website, you consent to the processing described there in and warrant that all data provided by you is accurate.&nbsp; The Privacy Policy is in corporated in the Terms of Service by reference.</p>"));
        INDEMNITY.setText(Html.fromHtml("<p>You agree to indemnify, defend and hold harmless the Website, Tidy Homz, its directors, officers, employees,consultants, agents, and affiliates, from any and all third party claims,liability, damages and/or costs (including, but not limited to, legal fees)arising from your use this Website or your breach of the Terms of Service.</p>"));
        aggreement.setText(Html.fromHtml("<p>The above Terms of Service constitute the entire agreement of the parties and supersede any and all preceding and contemporaneous agreements between you and Tidy Homz. Any waiver of any provision of the Terms of Service will be effective only if in writing and signed by a Director of Tidy Homz</p>"));
        govering.setText(Html.fromHtml("<p>The Terms of Service including the Privacy Policy and any amendments and/or modifications there to shall be governed by and construed in accordance with the laws of India and the courts at Aurangabad shall have exclusive jurisdiction on all matters and disputes arising out of and/or relating to the Website or Service.</p>"));
        COMPLAINTS.setText(Html.fromHtml("<p>We operate a complaints handling procedure which we will use to try to resolve disputes when they first arise,please feel free to e-mail us your grievances, comments or feedback on hello@tidyhomz.com we will get back to you.</p>"));
        ASSIGNMENT.setText(Html.fromHtml("<p>The Terms of Service, and any rights and licenses granted here under, may not be transferred or assigned by you, but may be assigned by Tidy Homz without restriction. Any assignment or transfer by you shall be null and void.</p>"));
        DISCOUNTS.setText(Html.fromHtml("<p>Only One coupon code can be used at one time. 2 Coupons can't be clubbed together unless mentioned on the coupon itself</p>\n" +
                "\n" +
                "<p>Tidyhomz all coupon discount applicable on a product <b>MRP</b>&nbsp;.</p>"));
        special.setText(Html.fromHtml("<p>Product of The Week is weekly offer in this you provide one coupon code for one of the &nbsp;product of Tidyhomz. you are &nbsp;using &nbsp;coupon code &amp; get 30 percentage discount on <b>MRP.</b></p>\n" +
                "<p>Coupons are not applicable for <b>Combo offer </b>  offer. </p>"));
        colors.setText(Html.fromHtml("<p>Although the best effort is made to photograph all items correctly and as accurately as possible- but the product color may vary slightly due to photographic lighting sources or your monitor settings. We also describe our products accurately and mention the sizes and specifications in as much detail as we can, but for any further questions or details on any products- please call or email us. </p>"));
        refund.setText(Html.fromHtml("<p>\n" +
                "1.Once the product is received, we will take 48-72 hours for checking and processing the return.\n" +
                "</p>\n" +
                "<p>\n" +
                "2.Post that the refund will be &nbsp;made in the form of credit in your customer account for you to use in the future.\n" +
                "</p>\n" +
                "<p>\n" +
                "3.Account credit is valid for 1 year.</p><p></p>"));

        shipping.setText(Html.fromHtml("<p>Orders are dispatched within 3 - 5 working days or as per the delivery date specified by us at the time of placing the order. Most orders are delivered within 8 to 15 working days. Delivery of all orders will be duly done to the address as mentioned by you at the time of placing the order.<br>\n" + "</p>"));
        return  view;
    }



}
