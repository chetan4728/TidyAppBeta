package com.tidyhomz.admin.tidyappbeta.API;

import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Admin on 5/4/2017.
 */

public class ClassAPI {


   /*Doamin Url*/

    public static final String Domain ="http://android.tidyhomz.com/";
    public static final String key ="bf9a598efdca9c541c7c97b0690eb494";
    //public static final String Domain ="https://tidyhomz.com/";
  // public static final String key ="bf9a598efdca9c541c7c97b0690eb494";

    /* Load Navigation categories */
    public static final String TAG_CAT = Domain+"index.php?route=android/api/androidmenu&key="+key;
    public static final String SUB_CAT = Domain+"index.php?route=android/api/androidsubmenu&key="+key+"&sub_cat_id=";

    public static  final String JSON_ARRAY = "result";
    public static  final  String PRODUCT_DATA = "ProductResult";
    public static  final String SLIDER_DATA = "sliderData";
    public static  final String SLIDER_PRODUCTLIST = "productListTop";
    public static  final String get_static_layout_data = "get_static_layout_data";


    /* Main Categories */

    public static final String HOME_API = Domain+"index.php?route=android/api/API_SERVICE_HOME&key="+key;

    public static final String TAG_LAOD_PRODUCT = Domain+"index.php?route=android/api/getCategoryProduct&key="+key+"&cat_id=";
    public static final String GET_PRODUCT_WISHLIST = Domain + "index.php?route=android/api/getWishListData&key="+key+"&wishlist_array=";
    public static final String GET_PRODUCT_RECENT = Domain + "index.php?route=android/api/getrecentListData&key="+key+"&recent_array=";
    public static final String GETPRODUCTDETAIL = Domain + "index.php?route=android/api/product_detail_desc&key="+key+"&product_id=";
    public static final String CHECKPINCODE = Domain + "index.php?route=android/api/checkPincode&key="+key+"&pincode=";
    public static final String CHECKLOGIN = Domain+"index.php?route=android/api/login&key="+key+"";
    public static final String ADDTOCART = Domain + "index.php?route=android/cart/add&key="+key+"&product_id=";



    public static final String VIEWCART = Domain + "index.php?route=android/cart/getcartlist&key="+key+"&customer_id=";
    public static final String REMOVECART = Domain + "index.php?route=android/cart/remove_cart&key="+key+"&cart_id=";
    public static final String ADDWISHLIST = Domain + "index.php?route=android/wishlist/addWishList&key="+key+"&product_id=";
    public static final String REMOVEWISHLIST = Domain + "index.php?route=android/wishlist/removeWihslist&key="+key+"&product_id=";
    public static final String GETUSERDATA = Domain + "index.php?route=android/wishlist/getWishlist&key="+key+"&customer_id=";
    public static final String UPDATEQUNITIY = Domain + "index.php?route=android/cart/edit&key="+key+"&cart_id=";
    public static final String MODULEDATAGET = Domain + "index.php?route=android/api/getlayoutModuleDataDemo&key="+key+"&module_id=";
    public static final String SAVERATING = Domain + "index.php?route=android/api/saveRating&key="+key;
    public static final String REGISTER = Domain + "index.php?route=android/register/register&key="+key;
    public static final String ADDRESS = Domain + "index.php?route=android/register/getUseraddress&key="+key;
    public static final String GETCOUNTRY = Domain + "index.php?route=android/register/getCountryNameAll&key="+key;
    public static final String GETSTATE = Domain + "index.php?route=android/register/getStateNameAll&key="+key;
    public static final String SUBMITREG = Domain + "index.php?route=android/address/add&key="+key;
    public static final String UPDATEDEFAULTADDRESS = Domain + "index.php?route=android/address/updateDefault_address&key="+key;
    public static final String DELETEADDRESS = Domain + "index.php?route=android/address/delete&key="+key+"";
    public static final String GETEDITADDRESS = Domain + "index.php?route=android/address/geteditList&key="+key;
    public static final String UPDATEPROFILE = Domain+"index.php?route=android/register/updateProfile&key="+key;
    public static final String GETPROFILEDETAIL = Domain+"index.php?route=android/register/getProfile&key="+key;
    public static final String UPDATEPASSWORD = Domain+"index.php?route=android/register/updatePassword&key="+key;
    public static final String ORDERHISTORY = Domain+"index.php?route=android/order/index&key="+key+"";
    public static final String ORDERHISTORYDETAIL = Domain+"index.php?route=android/order/info&key="+key;
    public static final String PLACEREORDER = Domain+"index.php?route=android/order/reorder&key="+key;
    public static final String RETURNORDER= Domain+"index.php?route=android/return/add&key="+key;
    public static final String ADDRETURN= Domain+"index.php?route=android/return/addreturn&key="+key;
    public static final String APPLYCPN = Domain+"index.php?route=android/coupon/coupon&key="+key;
    public static final String APPLYGIFT= Domain+"index.php?route=android/voucher/voucher&key="+key;
    public static final String GETSHIPPINGMETHOD= Domain+"index.php?route=android/shipping_method/index&key="+key;
    public static final String GETPAYMENTMETHOD= Domain+"index.php?route=android/payment_method/index&key="+key;;
    public static final String GETNOTIFICATION= Domain+"index.php?route=android/api/getNotification&key="+key;
    public static final String GETRANSACTION= Domain+"index.php?route=android/api/transaction&key="+key;
    public static final String CONFIRMORDER = Domain+"index.php?route=android/confirm/confirmOrder&key="+key;
    public static final String SUCCESS = Domain+"index.php?route=android/success/index&key="+key;
    public static final String SpecialOfferBanner = Domain+"index.php?route=android/api/SpecialOfferBanner&key="+key;
    public static final String CANCELORDER = Domain+"index.php?route=android/order/cancel_order&key="+key;
    public static final String UPDATENOTIFICATIONCOUNT = Domain+"index.php?route=android/api/updateNotificationCount&key="+key;
    public static final String GETUPDATE = Domain+"index.php?route=android/api/getUpdate&key="+key;
    public static final String GETWALLETTRANSACTION = Domain+"index.php?route=android/register/gettransaction&key="+key;
    public static final String ApplyRefferCode = Domain+"index.php?route=android/register/aplyrefferCode&key="+key;
    public static final String CheckWalletCash = Domain+"index.php?route=android/coupon/CheckwalletCash&key="+key;
    public static final String REFERCODE = Domain+"index.php?route=android/api/referoffer&key="+key;
    public static final String FORGETPASSWORD = Domain+"index.php?route=android/forgotten/index&key="+key;
    public static final String GETPINICODEVERIFY = Domain+"index.php?route=verification/verification/checkPincode&key="+key;
    public static final String SERVERJSON = Domain+"ServerJson/sample.json";
    public static final String CHATAPI = "https://tawk.to/chat/5639a209aa42e28c5a1384ce/default/?$_tawk_popout=true";

    /*PAYTM PAYMENT DETAIL*/

    public static final String PayrmChecksum = Domain+"catalog/controller/android/paytm/generateChecksum.php";
    public static final String PayuChecksum = Domain+"catalog/controller/android/payu/index.php";
    /* Constant Variables  Are Initialized in Main Activity*/
    public static FrameLayout globalFrame;
    public static FrameLayout Fragment_container;
    public  static TextView itemMessagesBadgeTextView;
    public  static TextView cartViewCount;
    public  static LinearLayout secondarytoolbar;
    public static  TextView CommanWishlistCount;
    public static TextView CommanCartListCoount;
    public static TextView Pincode;
    public  static  TextView set_title;
    public  static TextView option_name_for_laptop;
    public static DrawerLayout drawerglobal;
    public static TextView ProductResasonFlag;
    public static TextView NotificationCount ;
    public static  TextView notificationIconse;
}
