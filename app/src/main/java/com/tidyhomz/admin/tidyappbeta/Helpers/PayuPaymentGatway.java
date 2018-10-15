package com.tidyhomz.admin.tidyappbeta.Helpers;

import android.content.Context;

import com.payu.india.Extras.PayUChecksum;
import com.payu.india.Extras.PayUSdkDetails;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Payu.Payu;

/**
 * Created by Admin on 9/25/2017.
 */

public class PayuPaymentGatway {

    Context context;
    AppSharedPreferences App;
    private String merchantKey, userCredentials;
    private PaymentParams mPaymentParams;
    private PayuConfig payuConfig;
    private PayUChecksum checksum;

    public PayuPaymentGatway(Context context) {
        this.context = context;
        App = new AppSharedPreferences(context);
        Payu.setInstance(context);
        PayUSdkDetails payUSdkDetails = new PayUSdkDetails();

    }
}
