package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;


public class UserAccountFragment extends Fragment implements View.OnClickListener {

    View view;
    FragmentManager fragmentManager;
    ProgressBar progressBar;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view  = inflater.inflate(R.layout.fragment_user_account, container, false);
         fragmentManager = getFragmentManager();
         fragmentTransaction = fragmentManager.beginTransaction();

        ClassAPI.set_title.setText("My Account");
        progressBar = new ProgressBar(getActivity());
        LinearLayout addressbook = (LinearLayout)view.findViewById(R.id.addressbook);
        LinearLayout transaction = (LinearLayout)view.findViewById(R.id.transaction);
        LinearLayout ChangePassword = (LinearLayout)view.findViewById(R.id.ChangePassword);
        LinearLayout wishlistview = (LinearLayout)view.findViewById(R.id.wishlistview);
        LinearLayout tidywallet = (LinearLayout)view.findViewById(R.id.tidywallet);
        LinearLayout cartview = (LinearLayout)view.findViewById(R.id.cartview);
        LinearLayout Logoutme = (LinearLayout)view.findViewById(R.id.Logoutme);

        AppSharedPreferences App = new AppSharedPreferences(getActivity());
        String username = App.pref.getString(App.FullName,"");
        String email = App.pref.getString(App.EmailId,"");

        TextView showname = (TextView)view.findViewById(R.id.showname);
        TextView showemail = (TextView)view.findViewById(R.id.showemail);
        TextView editme = (TextView)view.findViewById(R.id.editme);
        editme.setOnClickListener(this);
        showname.setText(username);
        showemail.setText(email);

        addressbook.setOnClickListener(this);
        transaction.setOnClickListener(this);
        wishlistview.setOnClickListener(this);
        ChangePassword.setOnClickListener(this);
        cartview.setOnClickListener(this);
        tidywallet.setOnClickListener(this);
        Logoutme.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        


             int id = v.getId();
            if (id == R.id.wishlistview) {
                WishListFragment  WishListFragment = new WishListFragment();
                //fragmentManager = getFragmentManager();
               // fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.replace(R.id.Fragment_container, WishListFragment);
                //fragmentTransaction.addToBackStack("ProductDetail");
               // fragmentTransaction.commit();

                ((MainActivity)getActivity()).replaceFragment(WishListFragment);

            }
            else if(id == R.id.cartview)
            {
                ViewCartFragment  ViewCartFragment = new ViewCartFragment();
               // fragmentManager = getFragmentManager();
              //  fragmentTransaction = fragmentManager.beginTransaction();
               // fragmentTransaction.replace(R.id.Fragment_container, ViewCartFragment);
               // fragmentTransaction.addToBackStack("ProductDetail");
              //  fragmentTransaction.commit();
                ((MainActivity)getActivity()).replaceFragment(ViewCartFragment);
            }
            else if(id == R.id.editme)
            {
                userProfilefragment  userProfilefragment = new userProfilefragment();
               // fragmentManager = getFragmentManager();
               // fragmentTransaction = fragmentManager.beginTransaction();
               ClassAPI.set_title.setText("Edit Profile");
              //  fragmentTransaction.replace(R.id.Fragment_container, userProfilefragment);
               // fragmentTransaction.addToBackStack("ProductDetail");
               // fragmentTransaction.commit();

                ((MainActivity)getActivity()).replaceFragment(userProfilefragment);
            }

            else if(id == R.id.addressbook)
            {
                UserAddressFragment  UserAddressFragment = new UserAddressFragment();
               // fragmentManager = getFragmentManager();
               // fragmentTransaction = fragmentManager.beginTransaction();
                ClassAPI.set_title.setText("Edit Address");
              //  fragmentTransaction.replace(R.id.Fragment_container, UserAddressFragment);
               // fragmentTransaction.addToBackStack("ProductDetail");
               // fragmentTransaction.commit();
                ((MainActivity)getActivity()).replaceFragment(UserAddressFragment);
            }

            else if(id == R.id.transaction)
            {
                UserTransactionFragment  UserTransactionFragment = new UserTransactionFragment();
                //fragmentManager = getFragmentManager();
                //fragmentTransaction = fragmentManager.beginTransaction();
                ClassAPI.set_title.setText("Transaction");
              //  fragmentTransaction.replace(R.id.Fragment_container, UserTransactionFragment);
               // fragmentTransaction.addToBackStack("ProductDetail");
               // fragmentTransaction.commit();
                ((MainActivity)getActivity()).replaceFragment(UserTransactionFragment);
            }
            else if(id == R.id.ChangePassword)
            {
                UserChangePassword  UserChangePassword = new UserChangePassword();
               // fragmentManager = getFragmentManager();
               // fragmentTransaction = fragmentManager.beginTransaction();
                ClassAPI.set_title.setText("Change Password");
               // fragmentTransaction.replace(R.id.Fragment_container, UserChangePassword);
                //fragmentTransaction.addToBackStack("ProductDetail");
               // fragmentTransaction.commit();
                ((MainActivity)getActivity()).replaceFragment(UserChangePassword);
            }

            else if(id == R.id.tidywallet)
            {
                WalletFragment  WalletFragment = new WalletFragment();
              //  fragmentManager = getFragmentManager();
              //  fragmentTransaction = fragmentManager.beginTransaction();
                ClassAPI.set_title.setText("My Wallet");
              //  fragmentTransaction.replace(R.id.Fragment_container, WalletFragment);
               //fragmentTransaction.addToBackStack("ProductDetail");
              //  fragmentTransaction.commit();
                ((MainActivity)getActivity()).replaceFragment(WalletFragment);
            }

            else if(id == R.id.Logoutme)
            {

               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Sign Out");
                builder.setMessage("Are you sure you want to sign out?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        AppSharedPreferences app = new AppSharedPreferences(getActivity());
                        app.editor.clear();
                        app.editor.commit();
                        int count =  getActivity().getSupportFragmentManager().getBackStackEntryCount();
                        for(int i = 0; i < count; i++) {
                            getActivity().getSupportFragmentManager().popBackStack();
                        }

                        ((MainActivity)getActivity()).ShowActionBar();

                        FragmentTransaction dx = getActivity().getSupportFragmentManager().beginTransaction();
                        dx.replace(R.id.main_menu, new MenuFragment(), "Menu");
                        dx.commit();

                        ClassAPI.secondarytoolbar.setVisibility(View.GONE);
                        ClassAPI.CommanWishlistCount.setVisibility(View.GONE);
                        ClassAPI.CommanCartListCoount.setVisibility(View.GONE);
                        ClassAPI.itemMessagesBadgeTextView.setVisibility(View.GONE);
                        ClassAPI.cartViewCount.setVisibility(View.GONE);
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

            }
        }
    
}
