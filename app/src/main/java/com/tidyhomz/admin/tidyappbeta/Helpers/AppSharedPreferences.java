package com.tidyhomz.admin.tidyappbeta.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class AppSharedPreferences {



    private Context context;
    public  SharedPreferences pref;
    public Editor editor;
    public static final String WHISHLIST = "UserWhishList";
    public static final String NETWORKSTATUS = "status";
    public static final String COUNT = "Count";
    public static final String RecentlYcount = "CountME";
    public static final String RECENTLYVIEW = "recentProduct";
    public static final String FragmentFlag ="flag";
    public static  final String PRODUCTLISTSTYLE="status";
    public static final String FILTER="filter";
    public static final String PINCODE="PINCODE";
    public static final String UserId = "userId";
    public static final String EmailId = "EmailId";
    public static final String MobileNo = "MobileNo";
    public static final String FullName = "FullName";
    public static final String FIRSTNAME = "FIRSTNAME";
    public static final String LASTNAME = "LASTNAME";
    public static final String LOGINSTATUS = "LOGINSTATUS";
    public static final String SESSIONKEY = "SESSIONKEY";
    public static final String CARTVIEWCOUNT = "CARTVIEWCOUNT";
    public static final String CARTCOUNT = "CARTCOUNT";
    public static final String SETCOUPON = "SETCOUPON";
    public static final String SETGIFT = "SETGIFT";
    public static final String PAYUKEY = "PAYUKEY";
    public static final String PAYTMKEY = "PAYTMKEY";
    public static final String NOTIFICATIONFLAG = "NOTIFICATIONFLAG";
    public static final String NOTIFICATIONFLAGCSTATUS = "NOTIFICATIONFLAGCSTATUS";

    public static final String USERADDRESSID = "USERADDRESSID";
    public static final String USERADDRESSNAME = "USERADDRESSNAME";
    public static final String USERADDRESS = "USERADDRESS";
    public static final String USERADDRESSMOBILE = "USERADDRESSMOBILE";
    public static final String USERADDRESSCOUNTREY = "USERADDRESSCOUNTREY";
    public static final String USERADDRESSZHONE = "USERADDRESSZHONE";
    public static final String NotificationCheckstatus = "NotificationCheckstatus";
    public static final String WalletAmount = "WalletAmount";
    public static final String RateUs = "RateUs";


    /*PAYMENT GATEWAY DETAIL*/

    public static final String PAYTM_MID = "PAYTM_MID";
    public static final String PAYTM_INDUSTRY_TYPE_ID = "PAYTM_INDUSTRY_TYPE_ID";
    public static final String PAYTM_CHANNEL_ID = "PAYTM_CHANNEL_ID";
    public static final String PAYTM_WEBSITE = "PAYTM_WEBSITE";
    public static final String PAYTM_CALLBACK_URL = "PAYTM_CALLBACK_URL";

    public static final String PAYU_MERCHENT_KEY = "PAYU_MERCHENT_KEY";
    public static final String PAYU_ENIRONMENT = "PAYU_ENIRONMENT";


    public static final String OPTIONVALUEID = "OPTIONVALUEID";
    public static final String OPTIONEDIT = "OPTIONEDIT";
    public AppSharedPreferences(Context context)
    {
         this.context=context;
         pref = context.getSharedPreferences("ApplicationPrefernce", Context.MODE_PRIVATE);
         editor = pref.edit();


    }


}
