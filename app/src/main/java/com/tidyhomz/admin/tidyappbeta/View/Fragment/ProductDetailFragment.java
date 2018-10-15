package com.tidyhomz.admin.tidyappbeta.View.Fragment;



import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.ProductDetailOptionAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.ProductDetailthumbviewAdapter;

import com.tidyhomz.admin.tidyappbeta.Adapters.RelatedProductAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.ProductOptionDataset;

import com.tidyhomz.admin.tidyappbeta.Dataset.RelatedProductModel;

import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.MyGridView;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.Utility;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.Adapters.ImageListProductDetailAdapter;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//import cn.pedant.SweetAlert.SweetAlertDialog;


public class ProductDetailFragment extends Fragment {



    ProgressBar progressBar;
    private  JSONArray  ProductImageJson = null;
    private JSONObject ProductinfoJson = null;
    private  JSONArray  ProductDetailJson = null;
    private  JSONArray  UserReviewProduct = null;
    private  JSONArray  Productoptions = null;
    public static ArrayList<Integer>  USERCARTLIST;
    private  JSONArray  SimilerProduct = null;
    RelatedProductAdapter RelatedProductAdapter;
    private ArrayList<RelatedProductModel> RealtedProductModel;
    private ProductDetailOptionAdapter ProductDetailOptionAdapter;
    LinearLayout share,addWish;

    ArrayList<String> validation;
    private static ViewPager ProductView;
    private ArrayList<String> ProductImageList;
    private ArrayList<String> ProductthumbList;
    private ArrayList<String> ProductHDImage;
    private ArrayList<ProductOptionDataset>ProductOptionDataset;


    ImageListProductDetailAdapter ImageListProductDetailAdapter;
    ProductDetailthumbviewAdapter ProductDetailthumbviewAdapter;
    RecyclerView Thumbview;
    TextView SetProductTitle,ProductSpecialPrice,ProductPrice,discount,ratingcount,reviewCount,ProductDesc;
    RatingBar rt;
    TableLayout tl,FeatureTable;
    TableRow tr,ntr;
    RelativeLayout rl;
    MyGridView similerGrid;
    TextView readmore;
    boolean TOPCURRENTSELECTIONFLAG = false;
    boolean SELECTEDITEMFLAG = false;
    boolean CheckStaticFlag = false;
    public ListView optiondata;
    HashMap<String, String> hash = null;
    ArrayList<String>  spinnerValue;
    ArrayList<String>  spinnerId;
    public RelativeLayout optionImgsrd;
    public static ArrayList<Integer>  UserWhishList;
    NestedScrollView sc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_product_detail, container, false);


        final String product_id = getArguments().getString("product_id");

        String pincode = getArguments().getString("pincode");

        progressBar = new ProgressBar(getActivity());
        progressBar.show();

        getProductDetail();
        USERCARTLIST = new ArrayList<>();
        UserWhishList = new ArrayList<>();



        SELECTEDITEMFLAG = false;
        CheckStaticFlag = false;

        ProductView = (ViewPager)view.findViewById(R.id.productView);

        Thumbview =  (RecyclerView)view.findViewById(R.id.Thumbview);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(((AppCompatActivity)getActivity()), LinearLayoutManager.HORIZONTAL, false);
        Thumbview.setLayoutManager(horizontalLayoutManagaer);
        SetProductTitle = (TextView)view.findViewById(R.id.ProductTitle);
        ProductSpecialPrice = (TextView)view.findViewById(R.id.ProductSpecialPrice);
        ProductPrice = (TextView)view.findViewById(R.id.ProductPrice);
        discount = (TextView)view.findViewById(R.id.discount);
        ratingcount = (TextView)view.findViewById(R.id.ratingcount);
        reviewCount = (TextView)view.findViewById(R.id.reviewCount);
        tl = (TableLayout) view.findViewById(R.id.maintable);
        rt =(RatingBar)view.findViewById(R.id.pop_ratingbar_product);
        rl= (RelativeLayout)view.findViewById(R.id.productDetail);
        similerGrid = (MyGridView)view.findViewById(R.id.similerGrid);
        ProductDesc = (TextView)view.findViewById(R.id.ProductDesc);
        ClassAPI.Pincode = (TextView)view.findViewById(R.id.pincode);
        readmore = (TextView)view.findViewById(R.id.readmore);
        optionImgsrd = (RelativeLayout)view.findViewById(R.id.optionImgsrd);
        sc = (NestedScrollView)view.findViewById(R.id.sc);
        sc.setVisibility(View.GONE);
        final AppSharedPreferences app = new AppSharedPreferences(getActivity());
        String pincode_get = app.pref.getString(app.PINCODE,"");
        final String Customer_id = app.pref.getString(app.UserId,"");
        final String LOGINSTATUS = app.pref.getString(app.LOGINSTATUS,"");
        final String PINCODE = app.pref.getString(app.PINCODE,"");
        if(!pincode_get.equals(""))
        {
            ClassAPI.Pincode.setText(pincode_get);
        }

        ProductView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {


            }

            public void onPageSelected(int position) {

                Updatestate(position);

            }
        });

        final LinearLayout checkpincode = (LinearLayout)view.findViewById(R.id.checkforpincode);




        checkpincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckPincodeFragment  Fr = new CheckPincodeFragment();
                //FragmentManager fragmentManager = getFragmentManager();
                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               // fragmentTransaction.replace(R.id.Fragment_container, Fr);
               // fragmentTransaction.addToBackStack("ProductDetail");
               // fragmentTransaction.commit();
                ((MainActivity)getActivity()).replaceFragment(Fr);
            }
        });


        final Button AddToCart = (Button)view.findViewById(R.id.AddToCart);
        final Button opencart = (Button)view.findViewById(R.id.opencart);
        spinnerValue = new ArrayList<>();
        spinnerId = new ArrayList<>();

        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(LOGINSTATUS.equals("1"))
                {

                    if(PINCODE.trim().length()==0)
                    {

                        sc.smoothScrollTo(0,checkpincode.getBottom());


                        new AlertDialog.Builder(getActivity())
                                .setTitle("Opps!")
                                .setMessage("Check the servicability of your pincode")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Get me there", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        CheckPincodeFragment ck = new CheckPincodeFragment();

                                        ((MainActivity)getActivity()).replaceFragment(ck);
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();

                        //Toast.makeText(getActivity(), "Check the servicability of your pincode", Toast.LENGTH_SHORT).show();
                       /* new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops")
                                .setContentText("Check the servicability of your pincode")
                                .setConfirmText("Get me there")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        CheckPincodeFragment ck = new CheckPincodeFragment();

                                        ((MainActivity)getActivity()).replaceFragment(ck);
                                    }
                                })
                                .show();*/

                       
                    }
                    else
                    {

                        ArrayList<String> FlagSet = new ArrayList<String>();


                        for (int i = 0; i < optiondata.getChildCount(); i++) {
                            final ProductOptionDataset getobject = ProductOptionDataset.get(i);

                            // Get row's spinner
                            View listItem = optiondata.getChildAt(i);
                            int required = getobject.getRequired();
                            final String  custome_required = getobject.getCustomerequired();
                            final String custome_required_flag = getobject.getCustome_required_flag();
                            final String  Type = getobject.getType();
                            final Spinner spinner = (Spinner) listItem.findViewById(R.id.optionspinner);
                            final EditText name = (EditText)listItem.findViewById(R.id.optionusername);







                            if(SELECTEDITEMFLAG==true&&getobject.getCustomerequired().equals("required")) {

                                if(Type.equals("text")) {
                                    if (name.getText().length() > 1) {
                                        TOPCURRENTSELECTIONFLAG = true;

                                    } else {
                                        name.setBackgroundResource(R.drawable.error_border);
                                        final NestedScrollView sc = (NestedScrollView) getView().findViewById(R.id.sc);
                                        //  sc.smoothScrollTo(0, spinner.getBottom());
                                        TOPCURRENTSELECTIONFLAG = false;
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {

                                                        name.setBackgroundResource(R.drawable.black_border);
                                                    }
                                                },
                                                2500);

                                        final LinearLayout option_header = (LinearLayout)view.findViewById(R.id.option_header);
                                        sc.post(new Runnable() {
                                            public void run() {
                                                sc.smoothScrollTo(0,option_header.getBottom());
                                            }
                                        });
                                        Toast.makeText(getActivity(), "Please Enter "+getobject.getName(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }


                       /* Check Spinner  validation Start here  */

                            for (int j = 0; j < spinner.getChildCount(); j++) {


                                final View listItemchild = spinner.getChildAt(j);

                                TextView id_value = (TextView) listItemchild.findViewById(R.id.id_value);
                                TextView sub = (TextView) listItemchild.findViewById(R.id.sub);



                                int cuurent = Integer.parseInt((String) id_value.getText());


                                if(required > 0)
                                {

                                    if(cuurent == 0)
                                    {


                                        TOPCURRENTSELECTIONFLAG = false;
                                        spinner.setBackgroundResource(R.drawable.error_border);
                                        final NestedScrollView sc = (NestedScrollView)getView().findViewById(R.id.sc);
                                        //sc.smoothScrollTo(0,spinner.getBottom());

                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        spinner.setBackgroundResource(R.drawable.black_border);
                                                    }
                                                },
                                                2500);
                                        final LinearLayout option_header = (LinearLayout)view.findViewById(R.id.option_header);
                                        sc.post(new Runnable() {
                                            public void run() {
                                                sc.smoothScrollTo(0,option_header.getBottom());
                                            }
                                        });
                                        Toast.makeText(getActivity(), "Please Select "+getobject.getName(), Toast.LENGTH_SHORT).show();


                                    }
                                    else
                                    {



                                        TOPCURRENTSELECTIONFLAG = true;

                                        if(spinner.getSelectedItem().toString().trim().equals("Yes"))
                                        {
                                            SELECTEDITEMFLAG = true;



                                        }
                                        else if(spinner.getSelectedItem().toString().trim().equals("No"))
                                        {

                                            SELECTEDITEMFLAG = false;

                                        }
                                    }
                                }
                                else
                                {

                                    TOPCURRENTSELECTIONFLAG = true;
                                }





                                if(SELECTEDITEMFLAG==true) {


                                    if (getobject.getCustomerequired().equals("required")) {



                                        if (cuurent == 0) {

                                            TOPCURRENTSELECTIONFLAG = false;
                                            spinner.setBackgroundResource(R.drawable.error_border);
                                            name.setBackgroundResource(R.drawable.error_border);
                                            final NestedScrollView sc = (NestedScrollView) getView().findViewById(R.id.sc);
                                            // sc.smoothScrollTo(0, spinner.getBottom());

                                            new android.os.Handler().postDelayed(
                                                    new Runnable() {
                                                        public void run() {
                                                            spinner.setBackgroundResource(R.drawable.black_border);
                                                        }
                                                    },
                                                    2500);
                                            sc.post(new Runnable() {
                                                public void run() {
                                                    sc.smoothScrollTo(0, spinner.getScrollY());
                                                }
                                            });

                                            Toast.makeText(getActivity(), "Please Select "+getobject.getName(), Toast.LENGTH_SHORT).show();

                                        } else {


                                            TOPCURRENTSELECTIONFLAG = true;

                                        }
                                    } else {

                                        TOPCURRENTSELECTIONFLAG = true;
                                    }


                                }



                                FlagSet.add(String.valueOf(TOPCURRENTSELECTIONFLAG));

                            }


                        }






                        if(FlagSet.contains("false"))
                        {

                        }
                        else {

                            for (int i = 0; i < optiondata.getChildCount(); i++) {


                                final ProductOptionDataset getobject = ProductOptionDataset.get(i);

                                // Get row's spinner
                                View listItem = optiondata.getChildAt(i);
                                final Spinner spinner = (Spinner) listItem.findViewById(R.id.optionspinner);
                                final EditText name = (EditText)listItem.findViewById(R.id.optionusername);



                                for (int j = 0; j < spinner.getChildCount(); j++) {

                                    final View listItemchild = spinner.getChildAt(j);

                                    TextView id_value = (TextView) listItemchild.findViewById(R.id.id_value);

                                    spinnerId.add(String.valueOf(getobject.getProduct_option_id()));
                                    spinnerValue.add(id_value.getText().toString().trim());
                                }

                                if(name.getVisibility()==View.VISIBLE)
                                {


                                    if(name.getText().length()>0) {
                                        spinnerId.add(String.valueOf(getobject.getProduct_option_id()));
                                        spinnerValue.add(name.getText().toString().trim());
                                    }
                                }


                            }






                            AppSharedPreferences app = new AppSharedPreferences(getActivity());
                            String session_id = app.pref.getString(app.SESSIONKEY,"");
                            addProdToCart(getArguments().getString("product_id"),Customer_id,session_id,hash);
                            int cartviewCount = app.pref.getInt(app.CARTVIEWCOUNT,0);


                            if(cartviewCount > 0) {

                                for (int m = 0; m < cartviewCount; m++) {

                                    USERCARTLIST.add(app.pref.getInt(app.CARTVIEWCOUNT + m, m));



                                }
                                if(USERCARTLIST.contains(product_id)) {

                                    Toast.makeText(getActivity(), "Aleady added to cart", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    for(int i = 0; i<USERCARTLIST.size(); i++) {


                                        app.editor.putInt(app.CARTVIEWCOUNT + i,USERCARTLIST.get(i));
                                    }


                                    app.editor.commit();
                                }



                            }


                            AddToCart.setVisibility(View.GONE);
                            opencart.setVisibility(View.VISIBLE);

                            opencart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    ViewCartFragment ViewCartFragment = new ViewCartFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("product_id",product_id);
                                    bundle.putString("singleView","0");
                                   // FragmentManager fragmentManager = getFragmentManager();
                                  //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                  //  fragmentTransaction.replace(R.id.Fragment_container, ViewCartFragment);
                                  //  fragmentTransaction.addToBackStack("ProductDetail");
                                    ViewCartFragment.setArguments(bundle);
                                   // fragmentTransaction.commit();

                                    ((MainActivity)getActivity()).replaceFragment(ViewCartFragment);
                                }
                            });




                        }

                    }



                }


                else
                {

                    LoginFragment LoginFragment = new LoginFragment();
                   // FragmentManager fragmentManager = getFragmentManager();
                   // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                   // fragmentTransaction.replace(R.id.Fragment_container, LoginFragment);
                   // fragmentTransaction.addToBackStack("ProductDetail");
                  //  fragmentTransaction.commit();
                    ((MainActivity)getActivity()).replaceFragment(LoginFragment);
                }


            }});




        int count = app.pref.getInt(app.COUNT, 0);

        final int product_idt = Integer.parseInt(getArguments().getString("product_id"));
        final LikeButton likeButton  = (LikeButton)view.findViewById(R.id.likeButton);
        if(count > 0) {

            for (int m = 0; m < count; m++) {


                UserWhishList.add(app.pref.getInt(app.WHISHLIST + m, m));
                if(UserWhishList.get(m)==product_idt)
                {
                    likeButton.setLiked(true);
                }

            }
        }

        share = (LinearLayout)view.findViewById(R.id.sharelink);
        addWish = (LinearLayout)view.findViewById(R.id.addWish);

        TextView liketext = (TextView)view.findViewById(R.id.liketext);
        liketext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserWhishList.contains(product_idt)) {
                    addWishlistServer(String.valueOf(product_id));
                    UserWhishList.add(product_idt);
                    likeButton.setLiked(true);
                    Toast.makeText(getActivity(), "Product added to wishlist", Toast.LENGTH_SHORT).show();

                    app.editor.putInt(app.COUNT, UserWhishList.size());


                    for (int i = 0; i < UserWhishList.size(); i++) {


                        app.editor.putInt(app.WHISHLIST + i, UserWhishList.get(i));
                    }


                    app.editor.commit();

                    String t = String.valueOf(UserWhishList.size());
                    ClassAPI.itemMessagesBadgeTextView.setText(t);
                    ClassAPI.CommanWishlistCount.setText(t);
                    if (UserWhishList.size() > 0) {
                        ClassAPI.itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
                        ClassAPI.CommanWishlistCount.setVisibility(View.VISIBLE);
                    } else {
                        ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
                        ClassAPI.CommanWishlistCount.setVisibility(View.GONE);
                    }
                }
                else
                {
                    removeWishlistProduct(String.valueOf(product_id));
                    for(int d=0;d< UserWhishList.size();d++){

                        if(product_idt==UserWhishList.get(d)) {
                            UserWhishList.remove(d);
                            likeButton.setLiked(false);
                            Toast.makeText(getActivity(), "Product removed from wishlist", Toast.LENGTH_SHORT).show();

                        }
                    }

                    app.editor.putInt(app.COUNT, UserWhishList.size());


                    for(int i = 0; i<UserWhishList.size(); i++) {


                        app.editor.putInt(app.WHISHLIST + i,UserWhishList.get(i));
                    }


                    app.editor.commit();

                    String t = String.valueOf(UserWhishList.size());
                    ClassAPI.itemMessagesBadgeTextView.setText(t);
                    ClassAPI.CommanWishlistCount.setText(t);
                    if(UserWhishList.size() >0)
                    {
                        ClassAPI.itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
                        ClassAPI.CommanWishlistCount.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
                        ClassAPI.CommanWishlistCount.setVisibility(View.GONE);
                    }
                }
            }
        });

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(!UserWhishList.contains(product_idt)) {
                    addWishlistServer(String.valueOf(product_id));
                    UserWhishList.add(product_idt);
                    Toast.makeText(getActivity(), "Product added to wishlist", Toast.LENGTH_SHORT).show();

                    app.editor.putInt(app.COUNT, UserWhishList.size());


                    for (int i = 0; i < UserWhishList.size(); i++) {


                        app.editor.putInt(app.WHISHLIST + i, UserWhishList.get(i));
                    }


                    app.editor.commit();

                    String t = String.valueOf(UserWhishList.size());
                    ClassAPI.itemMessagesBadgeTextView.setText(t);
                    ClassAPI.CommanWishlistCount.setText(t);
                    if (UserWhishList.size() > 0) {
                        ClassAPI.itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
                        ClassAPI.CommanWishlistCount.setVisibility(View.VISIBLE);
                    } else {
                        ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
                        ClassAPI.CommanWishlistCount.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {

                removeWishlistProduct(String.valueOf(product_id));
                for(int d=0;d< UserWhishList.size();d++){

                    if(product_idt==UserWhishList.get(d)) {
                        UserWhishList.remove(d);

                        Toast.makeText(getActivity(), "Product removed from wishlist", Toast.LENGTH_SHORT).show();

                    }
                }

                app.editor.putInt(app.COUNT, UserWhishList.size());


                for(int i = 0; i<UserWhishList.size(); i++) {


                    app.editor.putInt(app.WHISHLIST + i,UserWhishList.get(i));
                }


                app.editor.commit();

                String t = String.valueOf(UserWhishList.size());
                ClassAPI.itemMessagesBadgeTextView.setText(t);
                ClassAPI.CommanWishlistCount.setText(t);
                if(UserWhishList.size() >0)
                {
                    ClassAPI.itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
                    ClassAPI.CommanWishlistCount.setVisibility(View.VISIBLE);
                }
                else
                {
                    ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
                    ClassAPI.CommanWishlistCount.setVisibility(View.GONE);
                }

            }






        });




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    final String appPackageName = getActivity().getPackageName();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "" + share.getTag());
                    sendIntent.setType("text/plain");
                    getActivity().startActivity(sendIntent);
                }catch (Exception e)
                {

                }


            }
        });
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return view;
    }

    public void addWishlistServer(String Product_id)
    {

        AppSharedPreferences App = new AppSharedPreferences(getActivity());
        String UserId = App.pref.getString(App.UserId,"");
        String URL = ClassAPI.ADDWISHLIST+Product_id+"&customer_id="+UserId;
        //Log.e("",URL);
        VolleyUtils.makeJsonObjectRequestNonCache(getActivity(), URL, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {


            }
        });
    }


    public void removeWishlistProduct(String Product_id)
    {

        AppSharedPreferences App = new AppSharedPreferences(getActivity());
        String UserId = App.pref.getString(App.UserId,"");
        String URL = ClassAPI.REMOVEWISHLIST+Product_id+"&customer_id="+UserId;

        VolleyUtils.makeJsonObjectRequestNonCache(getActivity(), URL, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {


            }
        });
    }


    public static void sendData(int position)
    {
        ProductView.setCurrentItem(position);
    }

    public void getProductDetail()
    {
        try {

            String cat_id = getArguments().getString("product_id");
            final String URL;
            URL = ClassAPI.GETPRODUCTDETAIL+cat_id;
            VolleyUtils.makeJsonObjectRequestNonCache(getActivity(), URL, new VolleyUtils.VolleyResponseListener() {
                @Override
                public void onError(VolleyError message) {

                }

                @Override
                public void onResponse(Object response) {

                    ExtractDetail((String) response);
                    progressBar.hide();
                    rl.setVisibility(View.VISIBLE);
                }
            });

        }catch (Exception e)
        {

        }


    }


    public  void ExtractDetail(String data)
    {
        ProductImageList = new ArrayList<>();
        ProductthumbList = new ArrayList<>();
        RealtedProductModel = new ArrayList<>();
        ProductHDImage = new ArrayList<>();
        ProductOptionDataset = new ArrayList<>();

        JSONObject getjson = null;



        try {
            getjson = new JSONObject(data);
            ProductinfoJson = getjson.getJSONObject("ProductInfo");

            ProductImageJson = ProductinfoJson.getJSONArray("images");

            ProductDetailJson = ProductinfoJson.getJSONArray("attribute_groups");
            SimilerProduct = ProductinfoJson.getJSONArray("products");
            UserReviewProduct = ProductinfoJson.getJSONArray("All_reviews");
            Productoptions = ProductinfoJson.getJSONArray("options");

            String frontImage = ProductinfoJson.getString("popup");
            String shareLink  = ProductinfoJson.getString("share");
            share.setTag(shareLink);






            final String TitleProduct = ProductinfoJson.getString("name");
            String name = ProductinfoJson.getString("name");
            name =  name.replace("&amp;","&");
            SetProductTitle.setText(name);
            final Typeface childFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Rupee_Foradian.ttf");
            ProductSpecialPrice.setTypeface(childFont);
            ProductPrice.setTypeface(childFont);
            ProductSpecialPrice.setText("`" +ProductinfoJson.getString("special"));
            ProductPrice.setText("`" +ProductinfoJson.getString("price"));
            ratingcount.setText("Rating "+ProductinfoJson.getString("rating"));
            reviewCount.setText("Reviews "+ProductinfoJson.getString("reviews"));

            LinearLayout panneloutofstock = (LinearLayout)getView().findViewById(R.id.panneloutofstock);
            LinearLayout productButtonpannel = (LinearLayout)getView().findViewById(R.id.productButtonpannel);


            if(ProductinfoJson.getInt("quantity")==0)
            {
                panneloutofstock.setVisibility(View.VISIBLE);
                productButtonpannel.setVisibility(View.GONE);

                TextView stockStatus = (TextView)getView().findViewById(R.id.stockStatus);
                TextView stocktext = (TextView)getView().findViewById(R.id.stocktext);



                stockStatus.setVisibility(View.VISIBLE);
                stockStatus.setText(ProductinfoJson.getString("stock_status"));
                stocktext.setText(ProductinfoJson.getString("stock_status"));
            }
            else
            {
                productButtonpannel.setVisibility(View.VISIBLE);
            }

            final String desdd=Html.fromHtml(ProductinfoJson.getString("description")).toString();

            String des=Html.fromHtml(ProductinfoJson.getString("description")).toString();
            ProductDesc.setText(Html.fromHtml(des));
            discount.setText("("+ProductinfoJson.getString("flatdiscount")+"%)"+" off");
            rt.setRating(Float.parseFloat(ProductinfoJson.getString("rating")));

            if (ProductinfoJson.getString("special").equalsIgnoreCase("false")) {
                ProductPrice.setVisibility(View.GONE);
                discount.setVisibility(View.GONE);
                ProductSpecialPrice.setText("`"+ProductinfoJson.getString("price"));
            } else {
                ProductPrice.setVisibility(View.VISIBLE);
                ProductPrice.setPaintFlags(ProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }



            frontImage = frontImage.replace(" ","%20");
            ProductImageList.add(frontImage);
            ProductthumbList.add(frontImage);
            ProductHDImage.add(frontImage);

            for(int i=0;i<ProductImageJson.length();i++) {
                String Image = ProductImageJson.getJSONObject(i).getString("popup");
                Image = Image.replace(" ","%20");
                ProductImageList.add(Image);

                String thumb = ProductImageJson.getJSONObject(i).getString("thumb");
                thumb = thumb.replace(" ","%20");



                ProductthumbList.add(thumb);
                ProductHDImage.add(Image);


            }



            for(int j=0;j<ProductDetailJson.length();j++)
            {
                JSONObject jo = ProductDetailJson.getJSONObject(j);


                JSONArray claims = jo.getJSONArray("attribute");

                for(int s=0;s<claims.length();s++) {
                    JSONObject mo = claims.getJSONObject(s);

                    if(!mo.getString("name").equals("Features"))
                    {



                        tr = new TableRow(getActivity());
                        tr.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));



                        TextView companyTV = new TextView(getActivity());
                        String attribute = mo.getString("name").replaceFirst("\\s++$", "");
                        attribute =  attribute.replace("&amp;","&");
                        companyTV.setText(attribute);
                        companyTV.setTextColor(Color.parseColor("#616161"));
                        companyTV.setTypeface(null, Typeface.BOLD);
                        companyTV.setGravity(Gravity.LEFT);
                        companyTV.setTextSize(12);
                        companyTV.setPadding(0,20,30,10);
                        tr.addView(companyTV);  // Adding textView to tablerow.


                        TextView valueTV = new TextView(getActivity());
                        String datad = mo.getString("text").replaceFirst("\\s++$", "");

                        if(mo.getString("name").trim().equals("Assembly Instruction")) {

                            valueTV.setMovementMethod(LinkMovementMethod.getInstance());
                            valueTV.setClickable(true);
                            String text = "<a href='"+mo.getString("text")+"'> Download Instructions </a>";
                           // Log.e("",text);
                            text = text.replace("&amp;", "&");
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                valueTV.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                valueTV.setText(Html.fromHtml(text));
                            }
                           // valueTV.setText(Html.fromHtml(text));

                        }
                        else
                        {
                            datad = datad.replace("&amp;", "&");
                            valueTV.setText(datad);
                        }

                        valueTV.setTextColor(Color.parseColor("#616161"));
                        valueTV.setGravity(Gravity.LEFT);
                        valueTV.setPadding(0,20,30,10);
                        valueTV.setTextSize(12);
                        tr.addView(valueTV); // Adding textView to tablerow.

                        tl.addView(tr, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                        tl.setColumnShrinkable(1,true);

                        readmore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Bundle bundle = new Bundle();
                                bundle.putString("ProductTitle",TitleProduct);
                                bundle.putString("ProductDesc", String.valueOf(Html.fromHtml(desdd)));
                                MoreDetailProductFragment  MoreDetailProductFragment = new MoreDetailProductFragment();
                               // FragmentManager fragmentManager = getFragmentManager();
                              //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                               // fragmentTransaction.replace(R.id.Fragment_container, MoreDetailProductFragment);
                              //  fragmentTransaction.addToBackStack("ProductDetail");
                                MoreDetailProductFragment.setArguments(bundle);
                             //   fragmentTransaction.commit();

                                ((MainActivity)getActivity()).replaceFragment(MoreDetailProductFragment);

                            }
                        });

                    }

                    else
                    {
                        final String datad = mo.getString("text").replaceFirst("\\s++$", "");
                        readmore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Bundle bundle = new Bundle();
                                bundle.putString("ProductTitle",TitleProduct);
                                bundle.putString("ProductFeature",datad);
                                bundle.putString("ProductDesc", String.valueOf(Html.fromHtml(desdd)));
                                MoreDetailProductFragment  MoreDetailProductFragment = new MoreDetailProductFragment();
                               // FragmentManager fragmentManager = getFragmentManager();
                              //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                               // fragmentTransaction.replace(R.id.Fragment_container, MoreDetailProductFragment);
                               // fragmentTransaction.addToBackStack("ProductDetail");
                                MoreDetailProductFragment.setArguments(bundle);
                              //  fragmentTransaction.commit();
                                ((MainActivity)getActivity()).replaceFragment(MoreDetailProductFragment);
                            }
                        });


                    }
                }


            }



            for(int m=0;m<SimilerProduct.length();m++)
            {
                JSONObject objects = SimilerProduct.getJSONObject(m);
                RelatedProductModel mm = new RelatedProductModel();

                mm.setProduct_id(objects.getInt("product_id"));
                mm.setProductName(objects.getString("name"));
                mm.setProductImage(objects.getString("thumb"));
                RealtedProductModel.add(mm);
            }

            optiondata = (ListView)getView().findViewById(R.id.optiondata);

            if(Productoptions.length()>0) {

                String cat_id = getArguments().getString("product_id");
                String imageUrl = ClassAPI.Domain+"image/catalog/laptopforprint/"+cat_id+".jpg";
                imageUrl = imageUrl.replace(" ", "%20");



                SimpleDraweeView draweeView = (SimpleDraweeView)getView().findViewById(R.id.optionimagesrc);
                GenericDraweeHierarchy hierarchy =  GenericDraweeHierarchyBuilder.newInstance(getActivity().getResources())
                        .setPlaceholderImage(R.drawable.placeholder2).build();
                draweeView.setHierarchy(hierarchy);
                draweeView.setImageURI(imageUrl);

                ClassAPI.option_name_for_laptop = (TextView)getView().findViewById(R.id.option_name_for_laptop);
                validation = new ArrayList<>();
                for (int d = 0; d < Productoptions.length(); d++) {
                    JSONObject objects = Productoptions.getJSONObject(d);
                    ProductOptionDataset getobject = new ProductOptionDataset();
                    getobject.setProduct_option_id(objects.getInt("product_option_id"));
                    getobject.setName(objects.getString("name"));
                    getobject.setType(objects.getString("type"));
                    getobject.setRequired(objects.getInt("required"));
                    getobject.setProduct_option_value(objects.getJSONArray("product_option_value"));
                    getobject.setCustomerequired(objects.getString("custome_required"));
                    getobject.setCustome_required_flag(objects.getString("custome_required_flag"));
                    getobject.setParent_node_flag(objects.getString("parent_node"));
                    getobject.setParenttitle(objects.getString("parent_title"));

                    ProductOptionDataset.add(getobject);

                }


                ProductDetailOptionAdapter = new ProductDetailOptionAdapter(getActivity(), ProductOptionDataset, optiondata,optionImgsrd);
                optiondata.setAdapter(ProductDetailOptionAdapter);
                Utility.setListViewHeightBasedOnChildren(optiondata);

            }
            else
            {
                LinearLayout Text = (LinearLayout)getView().findViewById(R.id.option_header);
                Text.setVisibility(View.GONE);
            }

            LinearLayout RatingReview = (LinearLayout) getView().findViewById(R.id.RatingReview);
            RatingReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle=new Bundle();
                    bundle.putString("ReviewDataset",UserReviewProduct.toString());
                    bundle.putString("Product_id",getArguments().getString("product_id"));
                    bundle.putString("ProductTitle",SetProductTitle.getText().toString());
                    Review_RatingFragment  Review_RatingFragment = new Review_RatingFragment();
                  //  FragmentManager fragmentManager = getFragmentManager();
                   // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                   // fragmentTransaction.replace(R.id.Fragment_container, Review_RatingFragment);
                   // fragmentTransaction.addToBackStack("ProductDetail");
                    Review_RatingFragment.setArguments(bundle);
                   // fragmentTransaction.commit();
                    ((MainActivity)getActivity()).replaceFragment(Review_RatingFragment);
                }
            });



        }
        catch(JSONException e) {
            e.printStackTrace();
        }





        ImageListProductDetailAdapter = new ImageListProductDetailAdapter(getActivity(),ProductImageList,ProductHDImage);
        ProductView.setAdapter(ImageListProductDetailAdapter);

        ProductDetailthumbviewAdapter = new ProductDetailthumbviewAdapter(getActivity(),ProductthumbList,0);
        Thumbview.setAdapter(ProductDetailthumbviewAdapter);
        loaderalatedProduct();

        sc.setVisibility(View.VISIBLE);

    }


    public void Updatestate(int position)
    {

        ProductDetailthumbviewAdapter = new ProductDetailthumbviewAdapter(getActivity(),ProductthumbList, position);
        Thumbview.setAdapter(ProductDetailthumbviewAdapter);
    }

    public void  loaderalatedProduct()
    {

        RelatedProductAdapter = new RelatedProductAdapter(getActivity(),RealtedProductModel);
        similerGrid.setAdapter(RelatedProductAdapter);
    }


    public void addProdToCart(final String ProductId, final String Customer_id, final String session_id, final HashMap<String, String> hash)
    {

        String url =  ClassAPI.ADDTOCART+ProductId+"&customer_id="+Customer_id+"&session_id="+session_id+"&spinnerValue="+spinnerValue+"&spinnerId="+spinnerId;
        url=url.replaceAll(" ", "%20");


        VolleyUtils.makeJsonObjectRequest(getActivity(), url, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {
                ((MainActivity)getActivity()).UserData();
                Toast.makeText(getActivity(), "Product Added Successfully ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
