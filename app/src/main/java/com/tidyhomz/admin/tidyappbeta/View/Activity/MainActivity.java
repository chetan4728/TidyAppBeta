package com.tidyhomz.admin.tidyappbeta.View.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.API.SharedPrefManager;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.AboutUsFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.CancelAndReturn;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.EmptyCartFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.HomeFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.LoginFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.MenuFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.PolyciesFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.ProductDetailFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.ProductImageZoomFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.ProductViewFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.SearchFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.SignUpFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.SpecialFragmentBanner;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.SubMenuFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.SupportChatFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.TermsAndConditionFragmnet;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.UserAccountFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.UserNotifications;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.UserOrderHistoryFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.ViewCartFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.ViewMoreFragment;
import com.tidyhomz.admin.tidyappbeta.View.Fragment.WishListFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar,toolbar2;
    ActionBarDrawerToggle toggle;
    private static MainActivity mActivity;
    private ArrayList<Integer> RecentlyView = new ArrayList<>();
    AppSharedPreferences App;
    private ProgressDialog progressDialog;
    ProgressBar pb;
    JSONArray WishListServerData;
    JSONArray CartSERVERDATA;
    String NotoficationFlag;
    int notification_id_flag;
    TextView cartViewCount;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        GetUpdateDetail();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main2);
        setSupportActionBar(toolbar);
       // this.LoadNotification();
         NotoficationFlag = getIntent().getStringExtra("NotificationFlag");
        //Toast.makeText(getApplicationContext(), ""+NotoficationFlag, Toast.LENGTH_SHORT).show();
         notification_id_flag = getIntent().getIntExtra("id",0);

        App = new AppSharedPreferences(this);

        builder = new AlertDialog.Builder(this);
        pb =  new ProgressBar(getApplicationContext());
        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        //Toast.makeText(this, ""+token, Toast.LENGTH_SHORT).show();


        ShowActionBar();
        ClassAPI.secondarytoolbar = (LinearLayout)findViewById(R.id.secondarytoolbar);

        ClassAPI.globalFrame = (FrameLayout)findViewById(R.id.main_menu);
        ClassAPI.Fragment_container = (FrameLayout)findViewById(R.id.Fragment_container);
        ClassAPI.Fragment_container.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.StatusBar));
        }


        //TextView SessionUsername =(TextView)headerview.findViewById(R.id.SessionUsername);
        //SessionUsername.setText("admin");


        ClassAPI.drawerglobal = (DrawerLayout) findViewById(R.id.drawer_layout);
        ClassAPI.drawerglobal = (DrawerLayout) findViewById(R.id.drawer_layout);
        ClassAPI.ProductResasonFlag = (TextView)findViewById(R.id.checkID);
        toggle = new ActionBarDrawerToggle(
                this, ClassAPI.drawerglobal, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        ClassAPI.drawerglobal.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);










        Button searchbtn = (Button)findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideActionbar();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);


                SearchFragment SearchFragment = new SearchFragment();
                  fragmentManager = getSupportFragmentManager();
                 fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");

                 fragmentTransaction.replace(R.id.Fragment_container,SearchFragment,"SearchFragment");
                fragmentTransaction.commit();

                //replaceFragment(SearchFragment);
            }
        });



        // UserData();
        AppSharedPreferences App = new AppSharedPreferences(getBaseContext());
        App.editor.putString(App.FragmentFlag,"set");

        this.registerReceiver(this.mConnReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 50);


        }

        ClassAPI.set_title = (TextView)findViewById(R.id.set_title);
        ClassAPI.set_title.setSelected(true);
        ClassAPI.CommanWishlistCount =  (TextView)findViewById(R.id.wishlistcountto);
        ClassAPI.CommanCartListCoount = (TextView)findViewById(R.id.MycartViewCount);
        ClassAPI.notificationIconse = (TextView)findViewById(R.id.notificationIconse);

      //  View headerview =  ((LayoutInflater)(this).getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.secondary_toolbar, null, false);
        ImageView back = (ImageView)findViewById(R.id.backToHome);


        ImageView cartView = (ImageView)findViewById(R.id.cartViewIcon);
        ImageView MylistViewImage = (ImageView)findViewById(R.id.WishlistIcon);
        ImageView notificationIconseImae = (ImageView)findViewById(R.id.notificationicondd);

        notificationIconseImae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadNotification();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setback();
                MainActivity.this.getSupportFragmentManager().popBackStack();
            }
        });
        MylistViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadWishlist();
            }
        });


        cartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadCartList();
            }
        });



        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.Fragment_container, new HomeFragment(),"HomeFragment");
        tx.commit();
        LoadMainMenu();



    }
    private void hideKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    public void Aboutus()
    {
        try {

            hideActionbar();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
          //  fragmentManager = getSupportFragmentManager();
          //  fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
            AboutUsFragment AboutUsFragment = new AboutUsFragment();
         //   fragmentTransaction.replace(R.id.Fragment_container,AboutUsFragment,"AboutUsFragment");
          //  fragmentTransaction.commit();
            replaceFragment(AboutUsFragment);

        }catch (Exception e)
        {

        }

    }

    public void LoadNotification()
    {

        try {

                //"My_FRAGMENT" is its tag

                hideActionbar();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                Bundle bundle = new Bundle();
                bundle.putInt("notification_id", notification_id_flag);
               // fragmentManager = getSupportFragmentManager();
               // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
                UserNotifications UserNotifications = new UserNotifications();
              //  fragmentTransaction.replace(R.id.Fragment_container, UserNotifications, "UserNotifications");
                UserNotifications.setArguments(bundle);
              //  fragmentTransaction.commit();
                replaceFragment(UserNotifications);

        }catch (Exception e)
        {

        }


    }
    public void TermsAndCondtion()
    {


        try {

            hideActionbar();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
           // fragmentManager = getSupportFragmentManager();
           // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
            TermsAndConditionFragmnet TermsAndConditionFragmnet = new TermsAndConditionFragmnet();
           // fragmentTransaction.replace(R.id.Fragment_container,TermsAndConditionFragmnet,"TermsAndConditionFragmnet");
           // fragmentTransaction.commit();
            replaceFragment(TermsAndConditionFragmnet);
        }catch (Exception e)
        {

        }

    }

    public void ReturnPolicy()
    {

        try {

            hideActionbar();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            //fragmentManager = getSupportFragmentManager();
           // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
            CancelAndReturn CancelAndReturn = new CancelAndReturn();
           // fragmentTransaction.replace(R.id.Fragment_container,CancelAndReturn,"CancelAndReturn");
           // fragmentTransaction.commit();
            replaceFragment(CancelAndReturn);
        }catch (Exception e)
        {

        }

    }

    public void Privacy()
    {

        try {

            hideActionbar();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
           // fragmentManager = getSupportFragmentManager();
          //  fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
            PolyciesFragment PolyciesFragment = new PolyciesFragment();
           // fragmentTransaction.replace(R.id.Fragment_container,PolyciesFragment,"PolyciesFragment");
           // fragmentTransaction.commit();
            replaceFragment(PolyciesFragment);
        }catch (Exception e)
        {

        }

    }
    public  void supportChat()
    {

        try {

            hideActionbar();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
           // fragmentManager = getSupportFragmentManager();
           // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
            SupportChatFragment SupportChatFragment = new SupportChatFragment();
           // fragmentTransaction.replace(R.id.Fragment_container,SupportChatFragment,"SupportChatFragment");
            //fragmentTransaction.commit();
            replaceFragment(SupportChatFragment);
        }catch (Exception e)
        {

        }



    }


        public void loadCartList()
        {


            try {



                    hideActionbar();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    Bundle bundle = new Bundle();
                    bundle.putString("product_id", "0");
                    bundle.putString("singleView", "0");
                    ViewCartFragment ViewCartFragment = new ViewCartFragment();
                    ViewCartFragment.setArguments(bundle);
                    replaceFragment(ViewCartFragment);

            }catch (Exception e)
            {

            }




        }
        public void removeproduct()
        {
            try {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                ViewCartFragment ViewCartFragment = new ViewCartFragment();
                Bundle bundle = new Bundle();
                bundle.putString("product_id", "0");
                bundle.putString("singleView", "0");
                ViewCartFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.Fragment_container,ViewCartFragment,"ViewCartFragment");
                fragmentTransaction.commit();
            }
            catch (Exception e)
            {

            }

        }

    public void replaceFragment (Fragment fragment){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

       // Toast.makeText(getApplication(), ""+fragmentPopped, Toast.LENGTH_SHORT).show();
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.Fragment_container, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }



    public void showProductDetail(int id, String ProductName)
    {


        try {

            hideActionbar();

            AppSharedPreferences session = new AppSharedPreferences(this);
            if(RecentlyView.contains(Integer.valueOf(id)))
            {

            }
            else
            {
                RecentlyView.add(Integer.valueOf(id));
            }




            session.editor.putInt(session.RecentlYcount, RecentlyView.size());

            for(int i = 0; i<RecentlyView.size(); i++) {
                session.editor.putInt(session.RECENTLYVIEW + i,RecentlyView.get(i));
            }


            session.editor.commit();



            Bundle bundle=new Bundle();
            bundle.putString("product_id", String.valueOf(id));
            bundle.putString("product_Name",ProductName);
            String TitleProduct = ProductName;

            TitleProduct =  TitleProduct.replace("&amp;","&");
            ClassAPI.set_title.setText(TitleProduct);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
            ProductDetailFragment ProductDetailFragment = new ProductDetailFragment();
            ProductDetailFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.Fragment_container,ProductDetailFragment,"ProductDetail");
            fragmentTransaction.commit();
            replaceFragment(ProductDetailFragment);
        }catch (Exception e)
        {

        }






    }


    private boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onResume() {
        super.onResume();

        doubleBackToExitPressedOnce = false;


    }

    public  void My_orders()
    {

        try {

            AppSharedPreferences App = new AppSharedPreferences(MainActivity.this);


            if(App.pref.getString(App.LOGINSTATUS,"").equals("1")) {

                hideActionbar();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            //    fragmentManager = getSupportFragmentManager();
               // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
                UserOrderHistoryFragment UserOrderHistoryFragment = new UserOrderHistoryFragment();
               // fragmentTransaction.replace(R.id.Fragment_container, UserOrderHistoryFragment, "UserOrderHistoryFragment");
              //  fragmentTransaction.commit();
                replaceFragment(UserOrderHistoryFragment);
            }
            else
            {
                login();
            }
        }catch (Exception e)
        {

        }


    }



    @Override
    public void onBackPressed() {
        try {
            hideKeyboard();

            ClassAPI.drawerglobal = (DrawerLayout) findViewById(R.id.drawer_layout);

            if (ClassAPI.drawerglobal.isDrawerOpen(GravityCompat.START)) {

                ClassAPI.drawerglobal.closeDrawer(GravityCompat.START);

            }


            if(getSupportFragmentManager().getBackStackEntryCount()>0)
            {


                if(getSupportFragmentManager().getBackStackEntryCount()==1) {

                    AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
                    int hight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());
                    appbar.setVisibility(View.VISIBLE);
                    appbar.getLayoutParams().height = hight;
                    ShowActionBar();

                }
                getSupportFragmentManager().popBackStack();
            }

            else {





                builder.setMessage("Are you sure you want to exit ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        finish();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                if(!App.pref.getString(App.RateUs,"").equals("1"))
                {
                    rateUs();
                }

            }
}
catch (Exception e)
{

}




    }

    public void rateUs()
    {
        try {

            builder.setMessage("Do you like Tidyhomz");

            builder.setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(i);
                    AppSharedPreferences app = new AppSharedPreferences(getApplicationContext());
                    app.editor.putString(app.RateUs,"1");
                    app.editor.commit();
                }
            });

            builder.setNegativeButton("Rate Later", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }catch (Exception e)
        {


        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

    try {


        if(NotoficationFlag.equals("1"))
        {

            LoadNotification();

        }

        }catch (Exception e)
        {



        }



        getMenuInflater().inflate(R.menu.main2, menu);
        MenuItem menuItem = menu.findItem(R.id.action_drawer_bell);
        RelativeLayout badgeLayout = (RelativeLayout) menuItem.getActionView();
        ClassAPI.itemMessagesBadgeTextView = (TextView) badgeLayout.findViewById(R.id.hotlist_hot);
        ClassAPI.cartViewCount = (TextView) badgeLayout.findViewById(R.id.cartViewcount);
        ClassAPI.NotificationCount = (TextView)badgeLayout.findViewById(R.id.notificationcount);


        ImageView wishlist = (ImageView) badgeLayout.findViewById(R.id.hotlist_bell);
        ImageView cartView = (ImageView) badgeLayout.findViewById(R.id.cartView);
        ImageView notification = (ImageView) badgeLayout.findViewById(R.id.notification);

        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWishlist();
            }
        });
        cartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideActionbar();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                Bundle bundle = new Bundle();
                bundle.putString("product_id","0");
                bundle.putString("singleView","0");
              //  fragmentManager = getSupportFragmentManager();
               // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
                ViewCartFragment ViewCartFragment = new ViewCartFragment();
                //fragmentTransaction.replace(R.id.Fragment_container,ViewCartFragment,"ViewCartFragment");
                ViewCartFragment.setArguments(bundle);
               // fragmentTransaction.commit();
                replaceFragment(ViewCartFragment);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LoadNotification();
            }
        });
        UserData();
        AppSharedPreferences App = new AppSharedPreferences(getBaseContext());
        int count = App.pref.getInt(App.COUNT, 0);


        int CARTVIEWCOUNT = App.pref.getInt(App.CARTCOUNT, 0);
        String t = String.valueOf(count);



        String CartCount = String.valueOf(CARTVIEWCOUNT);
        ClassAPI.itemMessagesBadgeTextView.setText(t);
        ClassAPI.CommanWishlistCount.setText(t);
        ClassAPI.cartViewCount.setText(CartCount);
        ClassAPI.CommanCartListCoount.setText(CartCount);

        if(count >0)
        {
            ClassAPI.itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
            ClassAPI.CommanWishlistCount.setVisibility(View.VISIBLE);
        }
        else
        {
            ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
            ClassAPI.CommanWishlistCount.setVisibility(View.GONE);
        }



        if(CARTVIEWCOUNT >0)
        {
            ClassAPI.cartViewCount.setVisibility(View.VISIBLE);
            ClassAPI.CommanCartListCoount.setVisibility(View.VISIBLE);
        }
        else
        {
            ClassAPI.cartViewCount.setVisibility(View.GONE);
            ClassAPI.CommanCartListCoount.setVisibility(View.GONE);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }




    public  void getCatId(int cat_id, String cat_name)
    {

        try {
           fragmentManager = getSupportFragmentManager();
            Bundle bundle=new Bundle();
            bundle.putString("cat_id", String.valueOf(cat_id));
            bundle.putString("cat_Name",cat_name);
            ClassAPI.set_title.setText(cat_name);
            fragmentTransaction = fragmentManager.beginTransaction();
            SubMenuFragment SubMenuFragment = new SubMenuFragment();
           fragmentTransaction.setCustomAnimations(R.anim.right, R.anim.left);
            SubMenuFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.sub_menu,SubMenuFragment,"sub_menu");
            fragmentTransaction.commit();
            replaceFragment(SubMenuFragment);
            ClassAPI.globalFrame.setVisibility(View.GONE);

        }catch (Exception e)
        {

        }



    }

    public void removeOrderData()
    {

            try {
                AppSharedPreferences App = new AppSharedPreferences(this);
                App.editor.remove("SETCOUPON");
                App.editor.remove("SETGIFT");
                App.editor.remove("COUNT");
                App.editor.remove("WalletAmount");
                App.editor.commit();
                UserData();

            }catch (Exception e)
            {

            }

    }
    public void home()
    {
        try {

          //  fragmentManager = getSupportFragmentManager();
         //   fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
            HomeFragment HomeFragment = new HomeFragment();
          //  fragmentTransaction.replace(R.id.Fragment_container,HomeFragment,"HomeFragment");
           // fragmentTransaction.commit();
            replaceFragment(HomeFragment);
        }
        catch  (Exception e)
        {

        }

    }

    public void login()
    {
        try {
            hideActionbar();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
           // fragmentManager = getSupportFragmentManager();
           // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
            LoginFragment LoginFragment = new LoginFragment();
           // fragmentTransaction.replace(R.id.Fragment_container,LoginFragment,"LoginFragment");
           // fragmentTransaction.commit();
            replaceFragment(LoginFragment);

        }catch (Exception e)
        {

        }

    }

    public void signup()
    {
          try {
              hideActionbar();
              DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
              drawer.closeDrawer(GravityCompat.START);
             // fragmentManager = getSupportFragmentManager();
             // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
              SignUpFragment SignUpFragment = new SignUpFragment();
             // fragmentTransaction.replace(R.id.Fragment_container,SignUpFragment,"SignUpFragment");
            //  fragmentTransaction.commit();
              replaceFragment(SignUpFragment);

          }catch (Exception e)
          {

          }

    }

    public  void LoadSpecialBannerFragment()
    {
        try {
            hideActionbar();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
           // fragmentManager = getSupportFragmentManager();
           // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
            SpecialFragmentBanner SpecialFragmentBanner = new SpecialFragmentBanner();
          //  fragmentTransaction.replace(R.id.Fragment_container,SpecialFragmentBanner,"SpecialFragmentBanner");
            //fragmentTransaction.commit();
            replaceFragment(SpecialFragmentBanner);
            //startActivity(new Intent(MainActivity.this, SpecialOfferActivity.class));
        }catch (Exception e)
        {

        }

    }


    public void MyAccount()
    {
        try {

            if(App.pref.getString(App.LOGINSTATUS,"").equals("1")) {

                hideActionbar();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                ClassAPI.set_title.setText("My Account");
               // fragmentManager = getSupportFragmentManager();
               // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
                UserAccountFragment UserAccountFragment = new UserAccountFragment();
               // fragmentTransaction.replace(R.id.Fragment_container,UserAccountFragment,"UserAccountFragment");
               // fragmentTransaction.commit();
                replaceFragment(UserAccountFragment);
            }
            else
            {
                login();
            }

        }catch (Exception e)
        {


        }




    }



    public  void loadMoreProduct(String ModuleName,int module_id)
    {

        try {

            hideActionbar();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            Bundle bundle=new Bundle();
            bundle.putInt("module_id",module_id);
            bundle.putString("ModuleName",ModuleName);
           // fragmentManager = getSupportFragmentManager();
           // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
            ViewMoreFragment ViewMoreFragment = new ViewMoreFragment();
            ViewMoreFragment.setArguments(bundle);
            replaceFragment(ViewMoreFragment);
            //fragmentTransaction.replace(R.id.Fragment_container,ViewMoreFragment,"ViewMoreFragment");
            //fragmentTransaction.commit();
        }catch (Exception e)
        {

        }



    }
    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {

                boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
                boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

                NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

                String message;
                int color;
                message = "Sorry not connected to internet..";
                color = Color.WHITE;
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

                AppSharedPreferences App = new AppSharedPreferences(context);
                if(currentNetworkInfo.isConnected()){
                    pb.hide();



                    App.editor.putString(App.NETWORKSTATUS,"true");
                    //ClassAPI.NETWORK_FLAG= "true";

                    if(getSupportFragmentManager().getBackStackEntryCount()>0)
                    {
                        if(getSupportFragmentManager().getBackStackEntryCount()==1) {

                            AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
                            int hight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());
                            appbar.setVisibility(View.VISIBLE);
                            appbar.getLayoutParams().height = hight;
                            ShowActionBar();

                        }


                    }

                }else{

                    //ClassAPI.NETWORK_FLAG= "false";
                    App.editor.putString(App.NETWORKSTATUS,"false");


                    snackbar.show();
                }


                App.editor.commit();


                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(color);

            }catch (Exception e)
            {

            }



        }

    };

    public void loadCategoryProduct(String ParamId,String ParamName)
    {

        try {

            hideActionbar();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            Bundle bundle=new Bundle();
            bundle.putString("paramCatId",ParamId);
            bundle.putString("ParamCatName",ParamName);
            ClassAPI.set_title.setText(ParamName);
           // fragmentManager = getSupportFragmentManager();
            //fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
            ProductViewFragment ProductViewFragment = new ProductViewFragment();
            ProductViewFragment.setArguments(bundle);
          //  fragmentTransaction.replace(R.id.Fragment_container,ProductViewFragment,"ProductCat");
           // fragmentTransaction.commit();
            replaceFragment(ProductViewFragment);
        }catch (Exception e)
        {

        }






    }


    public void loadProductImageZoom(String Productimagepath)
    {

        hideActionbar();
        Bundle bundle=new Bundle();
        bundle.putString("imagepath",Productimagepath);
       // fragmentManager = getSupportFragmentManager();
      //  fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ProductImageZoomFragment ProductImageZoomFragment = new ProductImageZoomFragment();
        ProductImageZoomFragment.setArguments(bundle);
       // fragmentTransaction.replace(R.id.Fragment_container,ProductImageZoomFragment,"productZoom");
       // fragmentTransaction.commit();
        replaceFragment(ProductImageZoomFragment);




    }





    public void loadWishlist()
    {

        try {

                hideActionbar();
                ClassAPI.drawerglobal = (DrawerLayout) findViewById(R.id.drawer_layout);
                ClassAPI.drawerglobal.closeDrawer(GravityCompat.START);
               // fragmentManager = getSupportFragmentManager();
                ClassAPI.set_title.setText("Wishlist");
               // fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("HomeFragment");
                WishListFragment WishListFragment = new WishListFragment();
                //fragmentTransaction.replace(R.id.Fragment_container, WishListFragment, "WishListFragment");
               // fragmentTransaction.commit();
            replaceFragment(WishListFragment);


        }catch (Exception e)
        {

        }







    }


    public  void hideActionbar()
    {

        LinearLayout secondaryappbar = (LinearLayout)findViewById(R.id.secondarytoolbar);
        secondaryappbar.setVisibility(View.VISIBLE);
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
        int hight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        appbar.getLayoutParams().height = hight;

    }

    public  void ShowActionBar()
    {
        LinearLayout secondaryappbar = (LinearLayout)findViewById(R.id.secondarytoolbar);
        secondaryappbar.setVisibility(View.GONE);
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
        appbar.setVisibility(View.VISIBLE);
        int hight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());
        appbar.getLayoutParams().height = hight;

    }


    public  void bothhidebar()
    {
        LinearLayout secondaryappbar = (LinearLayout)findViewById(R.id.secondarytoolbar);
        secondaryappbar.setVisibility(View.GONE);
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
        appbar.setVisibility(View.GONE);
        int hight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        appbar.getLayoutParams().height = hight;

    }

    public  void Setback()
    {
        if(getSupportFragmentManager().getBackStackEntryCount()==1)
        ShowActionBar();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        ClassAPI.drawerglobal  = (DrawerLayout) findViewById(R.id.drawer_layout);
        ClassAPI.drawerglobal .closeDrawer(GravityCompat.START);
        return true;
    }

    public void  LoadMainMenu()
    {

            try {
              FragmentTransaction dx = getSupportFragmentManager().beginTransaction();
               dx.add(R.id.main_menu, new MenuFragment(),"Menu");
                dx.commit();
                //replaceFragment( new MenuFragment());

            }
            catch (Exception e)
            {

            }

    }



    private ViewDragHelper draggerObj;


    private Field mDragger;
    private Field mEdgeSize;
    private int edge;
    private DrawerLayout mDrawer;

    public void closeDrag()
    {
        try
        {
            mDragger = ClassAPI.drawerglobal.getClass().getDeclaredField("mLeftDragger");
        }
        catch (NoSuchFieldException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mDragger.setAccessible(true);

        try
        {
            draggerObj = (ViewDragHelper) mDragger.get(ClassAPI.drawerglobal);
        }
        catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try
        {
            mEdgeSize = draggerObj.getClass().getDeclaredField("mEdgeSize");
        }
        catch (NoSuchFieldException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mEdgeSize.setAccessible(true);

        try
        {
            edge = mEdgeSize.getInt(draggerObj);
        }
        catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try
        {
            mEdgeSize.setInt(draggerObj, edge * 0);
        }
        catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public  void  updateNotificationflag(final int Notification_id)
    {
   try {
       StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.UPDATENOTIFICATIONCOUNT,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {

                       UserData();

                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getApplicationContext(),"opps Somthing get's Worng",Toast.LENGTH_LONG).show();
                   }
               }){
           @Override
           protected Map<String,String> getParams(){
               Map<String,String> params = new HashMap<String, String>();
               params.put("customer_id",App.pref.getString(App.UserId,""));
               params.put("notification_id", String.valueOf(Notification_id));

               return params;
           }

       };

       RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
       requestQueue.add(stringRequest);
   }catch (Exception e)
   {

   }


    }



    public  void  GetUpdateDetail()
    {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ClassAPI.GETUPDATE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            JSONArray Getversion;
                            JSONObject Json = null;
                            try {
                                Json = new JSONObject(response);
                                Getversion = Json.getJSONArray("result");
                                String  version_name = Getversion.getJSONObject(0).getString("versionName");

                                int  version_code = Getversion.getJSONObject(0).getInt("versionCode");


                                PackageManager manager = getPackageManager();
                                PackageInfo info = null;

                                info = manager.getPackageInfo(getPackageName(), 0);


                                String version = info.versionName;
                                int versioncode = info.versionCode;
                                //Toast.makeText(MainActivity.this, ""+version+" "+versioncode, Toast.LENGTH_SHORT).show();
                                if(versioncode==version_code)
                                {
                                    // Toast.makeText(getApplicationContext(), "update not avaiable", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    udpatebox();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"opps Somthing get's Worng",Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }catch (Exception e)
        {

        }



    }


    public void udpatebox()
    {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Update available");
            builder.setMessage("New version available, please update application");
            builder.setCancelable(false);
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(i);
                }
            });

            builder.setNegativeButton("LATER", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }catch (Exception e)
        {

        }

    }


    public void UserData() {
        try {
            AppSharedPreferences App = new AppSharedPreferences(this);
            String UserId = App.pref.getString(App.UserId, "");

            String URL = ClassAPI.GETUSERDATA + UserId;


            VolleyUtils.makeJsonObjectRequestNonCache(this, URL, new VolleyUtils.VolleyResponseListener() {
                @Override
                public void onError(VolleyError message) {

                }

                @Override
                public void onResponse(Object response) {


                    AppSharedPreferences App = new AppSharedPreferences(getBaseContext());
                    JSONObject json = null;
                    try {

                        json = new JSONObject(String.valueOf(response));

                        WishListServerData = json.getJSONArray("wishlist");
                        CartSERVERDATA = json.getJSONArray("cart");


                        App.editor.putInt(App.COUNT, WishListServerData.length());

                        App.editor.putInt(App.CARTCOUNT, CartSERVERDATA.length());

                        App.editor.putString(App.SESSIONKEY, json.getString("session_key"));

                        App.editor.putString(App.PAYTM_MID, json.getString("PAYTM_MID"));
                        App.editor.putString(App.PAYTM_CHANNEL_ID, json.getString("PAYTM_CHANNEL_ID"));
                        App.editor.putString(App.PAYTM_WEBSITE, json.getString("PAYTM_WEBSITE"));
                        App.editor.putString(App.PAYTM_INDUSTRY_TYPE_ID, json.getString("PAYTM_INDUSTRY_TYPE_ID"));
                        App.editor.putString(App.PAYTM_CALLBACK_URL, json.getString("PAYTM_CALLBACK_URL"));

                        App.editor.putString(App.PAYU_MERCHENT_KEY, json.getString("PAYU_MERCHENT_KEY"));
                        App.editor.putString(App.PAYU_ENIRONMENT, json.getString("PAYU_ENIRONMENT"));
                        App.editor.putInt(App.NOTIFICATIONFLAG, json.getInt("notifiaction"));


                        App.editor.commit();


                        if (json.getInt("notifiaction") > 0) {
                            String c = String.valueOf(App.pref.getInt(App.NOTIFICATIONFLAG, 0));
                            ClassAPI.NotificationCount.setText(c);
                            ClassAPI.NotificationCount.setVisibility(View.VISIBLE);
                            ClassAPI.notificationIconse.setVisibility(View.VISIBLE);
                            ClassAPI.notificationIconse.setText(c);
                        } else {
                            ClassAPI.NotificationCount.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < WishListServerData.length(); i++) {
                            JSONObject jsonObject = WishListServerData.getJSONObject(i);
                            int product_id = jsonObject.getInt("product_id");
                            App.editor.putInt(App.WHISHLIST + i, product_id);
                            App.editor.commit();
                        }
                        String WishlistCount = String.valueOf(WishListServerData.length());
                        ClassAPI.itemMessagesBadgeTextView.setText(WishlistCount);
                        ClassAPI.CommanWishlistCount.setText(WishlistCount);


                        for (int j = 0; j < CartSERVERDATA.length(); j++) {

                            JSONObject jsonObject = CartSERVERDATA.getJSONObject(j);
                            int product_id = jsonObject.getInt("product_id");
                            App.editor.putInt(App.CARTCOUNT + j, product_id);
                            App.editor.commit();

                        }
                        String CartViewCount = String.valueOf(CartSERVERDATA.length());
                        ClassAPI.cartViewCount.setText(CartViewCount);
                        ClassAPI.CommanCartListCoount.setText(CartViewCount);


                        if (WishListServerData.length() > 0) {
                            ClassAPI.itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
                            ClassAPI.CommanWishlistCount.setVisibility(View.VISIBLE);
                        } else {
                            ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
                            ClassAPI.CommanWishlistCount.setVisibility(View.GONE);
                        }


                        if (CartSERVERDATA.length() > 0) {
                            ClassAPI.cartViewCount.setVisibility(View.VISIBLE);
                            ClassAPI.CommanCartListCoount.setVisibility(View.VISIBLE);
                        } else {
                            ClassAPI.cartViewCount.setVisibility(View.GONE);
                            ClassAPI.CommanCartListCoount.setVisibility(View.GONE);
                            App.editor.remove("CARTCOUNT");
                            App.editor.commit();

                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {

        }

    }






}
