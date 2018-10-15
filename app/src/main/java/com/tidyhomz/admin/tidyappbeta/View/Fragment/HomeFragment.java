package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Adapters.DynamicDesignDataAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.DynamicDesignModuleDataAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.HomePagerAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.HorizontalCategoryAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.RecentlyViewAdapter;
import com.tidyhomz.admin.tidyappbeta.Adapters.YouMayLikeAdapter;
import com.tidyhomz.admin.tidyappbeta.Dataset.BannerDatasetModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.DynamicDesignDataContentModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.DynamicDesignDataModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.HomeSliderModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.HorizontalcatModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.RecentlyViewdModel;
import com.tidyhomz.admin.tidyappbeta.Dataset.YouMayLikeModel;
import com.tidyhomz.admin.tidyappbeta.DesignHelpers.RoundRectCornerImageView;
import com.tidyhomz.admin.tidyappbeta.Helpers.AppSharedPreferences;
import com.tidyhomz.admin.tidyappbeta.Helpers.MyGridView;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.Helpers.Utility;
import com.tidyhomz.admin.tidyappbeta.Helpers.VolleyUtils;
import com.tidyhomz.admin.tidyappbeta.R;
import com.tidyhomz.admin.tidyappbeta.View.Activity.MainActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;


public class HomeFragment extends Fragment {

    private HomePagerAdapter HomePagerAdapter;
    private ArrayList<HomeSliderModel> HomeSliderModel;
    private JSONArray HomeSliderArray = null;
    private HorizontalCategoryAdapter HorizontalCategoryAdapter;
    private ArrayList<HorizontalcatModel> HorizontalcatModel;
    private JSONArray HorizontalcatArray = null;

    private JSONArray StaticLayoutOne = null;
    private YouMayLikeAdapter YouMayLikeAdapter;
    private ArrayList<YouMayLikeModel> YouMayLikeModel;
    private JSONArray YouMayLikeArray = null;


    private  ArrayList<RecentlyViewdModel> RecentlyViewList;
    private  JSONArray RecentlyViewProduct =null;

    private  JSONArray DataLayoutDesign =null;
    private  JSONArray DatalayoutDesigndata = null;
    private ArrayList<Integer> Module_id;
    private  ArrayList<DynamicDesignDataContentModel> DynamicDesignDataContentModel;
    private  ArrayList<DynamicDesignDataModel> DynamicDesignDataModel;
    private DynamicDesignDataAdapter DynamicDesignDataAdapter;
    private DynamicDesignModuleDataAdapter DynamicDesignModuleDataAdapter;


    private ArrayList<BannerDatasetModel> BannerArray;
    private JSONArray Banners;

    MyGridView NewArrivalGridView,DynamicGridviewOne,DynamicGridviewTwo,YouMayLike,containerGrid;
    ProgressBar pg;
    RecyclerView productCatHorizontalList;
    FrameLayout homedata;
    private   ArrayList<Integer> SessionRecentProduct;
    ListView recentlyViewlist,moduleLoader;
    LinearLayout viewmore1,viewmore2,viewmore3,viewmore4,viewmore5;
    AppSharedPreferences App;
    ViewPager Viewpager;
    RoundRectCornerImageView bannerTop,bannerMiddle,bannerBottom;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        App = new AppSharedPreferences(getContext());
        SessionRecentProduct = new ArrayList<>();
        pg = new ProgressBar(getActivity());




        pg.show();
        LoadData();
        LoadUserRecentProduct();
        view = inflater.inflate(R.layout.fragment_home, container, false);

        moduleLoader = (ListView)view.findViewById(R.id.moduleLoader);


        YouMayLike = (MyGridView)view.findViewById(R.id.DynamicGridviewThree);

        recentlyViewlist = (ListView)view.findViewById(R.id.recentlyViewlist);
        productCatHorizontalList =  (RecyclerView)view.findViewById(R.id.productCatHorizontalList);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(((AppCompatActivity)getActivity()), LinearLayoutManager.HORIZONTAL, false);
        productCatHorizontalList.setLayoutManager(horizontalLayoutManagaer);

        Viewpager = (ViewPager)view.findViewById(R.id.vp_slider);
        homedata = (FrameLayout)view.findViewById(R.id.homedata);
        bannerTop = (RoundRectCornerImageView) view.findViewById(R.id.bannerTop);
        bannerMiddle = (RoundRectCornerImageView)view.findViewById(R.id.bannerMiddle);
        bannerBottom = (RoundRectCornerImageView)view.findViewById(R.id.bannerBottom);
        bannerTop.setVisibility(View.GONE);
        bannerMiddle.setVisibility(View.GONE);
        bannerBottom.setVisibility(View.GONE);

        homedata.setVisibility(View.GONE);


        return view;
    }




    public void LoadData()
    {

    Log.d("home",ClassAPI.HOME_API);
        try {
            VolleyUtils.makeJsonObjectRequest(getActivity(), ClassAPI.HOME_API, new VolleyUtils.VolleyResponseListener() {
                @Override
                public void onError(VolleyError vollyError) {

                    String message = null;
                    if (vollyError instanceof NetworkError) {

                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (vollyError instanceof ServerError) {
                        message = "The server could not be found. Please try again after some time!!";
                    } else if (vollyError instanceof AuthFailureError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (vollyError instanceof ParseError) {
                        message = "Parsing error! Please try again after some time!!";
                    } else if (vollyError instanceof NoConnectionError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (vollyError instanceof TimeoutError) {
                        message = "Connection TimeOut! Please check your internet connection.";
                    }
                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onResponse(Object response) {


                    if(response!=null) {
                        ExtractData((String) response);

                    }
                }
            });
        }catch (Exception e)
        {

        }



    }

    public void  ExtractData(String data)
    {

        //Toast.makeText(getActivity(), ""+data, Toast.LENGTH_SHORT).show();

        HomeSliderModel = new ArrayList<>();
        YouMayLikeModel = new ArrayList<>();
        HorizontalcatModel = new ArrayList<>();
        DynamicDesignDataModel = new ArrayList<>();
        DynamicDesignDataContentModel = new ArrayList<>();
        BannerArray = new ArrayList<>();

        JSONObject jsonObject=null;
        try {

            jsonObject = new JSONObject(data);
            HomeSliderArray = jsonObject.getJSONArray(ClassAPI.SLIDER_DATA);
            HorizontalcatArray = jsonObject.getJSONArray(ClassAPI.SLIDER_PRODUCTLIST);
            DataLayoutDesign = jsonObject.getJSONArray("layoutDesignData");
            StaticLayoutOne = jsonObject.getJSONArray(ClassAPI.get_static_layout_data);
            YouMayLikeArray = jsonObject.getJSONArray("suggestion");
            Banners =  jsonObject.getJSONArray("banners");

           //Log.i("banners", String.valueOf(Banners));

           // StaticBannerTwo = jsonObject.getJSONArray(ClassAPI.STATIC_BANNER);









           for(int i=0;i<HomeSliderArray.length();i++) {

                HomeSliderModel  SliderObject  =  new HomeSliderModel();
                JSONObject ImageItem = HomeSliderArray.getJSONObject(i);
                SliderObject.setImageUrl(ImageItem.getString("image_url"));
                SliderObject.setSliderType(ImageItem.getString("slider_type"));
                SliderObject.setSliderId(ImageItem.getString("slider_id"));
                SliderObject.setLink(ImageItem.getString("link"));
                HomeSliderModel.add(SliderObject);
                Log.d("slider",ImageItem.getString("image_url"));
            }

            for(int k=0;k<Banners.length();k++)
           {
               BannerDatasetModel setdata = new BannerDatasetModel();
               final JSONObject BannersItem = Banners.getJSONObject(k);




               if(BannersItem.getInt("banner_position")==1)
               {


                   String imgurl = BannersItem.getString("banner_image");
                   imgurl = imgurl.replace(" ","%20");


                   Picasso.with(getActivity()).load(imgurl).into(bannerTop);

                   if(BannersItem.getInt("banner_status")==1)
                   {
                       bannerTop.setVisibility(View.VISIBLE);
                   }
                   else
                   {
                       bannerTop.setVisibility(View.GONE);
                   }




                   if(BannersItem.getInt("banner_type_id")==1)
                   {


                       bannerTop.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               ((MainActivity) getActivity()).signup();
                           }
                       });
                   }
                   else if(BannersItem.getInt("banner_type_id")==2)
                   {

                       bannerTop.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               try {
                                   ((MainActivity) getActivity()).showProductDetail(Integer.parseInt(BannersItem.getString("banner_item_id")),BannersItem.getString("banner_title"));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });


                   }
                   else  if(BannersItem.getInt("banner_type_id")==3)
                   {


                       bannerTop.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               try {
                                   ((MainActivity) getActivity()).loadCategoryProduct(BannersItem.getString("banner_item_id"),BannersItem.getString("banner_title"));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });



                   }
                   else
                   {

                       bannerTop.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               try {
                                   ((MainActivity) getActivity()).loadCategoryProduct(BannersItem.getString("banner_item_id"),BannersItem.getString("banner_title"));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });
                   }


               }
               else if(BannersItem.getInt("banner_position")==2)
               {
                   String imgurl = BannersItem.getString("banner_image");
                   imgurl = imgurl.replace(" ","%20");
                   Picasso.with(getActivity()).load(imgurl).into(bannerMiddle);

                   if(BannersItem.getInt("banner_status")==1)
                   {
                       bannerMiddle.setVisibility(View.VISIBLE);
                   }
                   else
                   {
                       bannerMiddle.setVisibility(View.GONE);
                   }


                   if(BannersItem.getInt("banner_type_id")==1)
                   {




                       bannerMiddle.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               ((MainActivity) getActivity()).signup();
                           }
                       });
                   }
                   else if(BannersItem.getInt("banner_type_id")==2)
                   {


                       bannerMiddle.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               try {
                                   ((MainActivity) getActivity()).showProductDetail(Integer.parseInt(BannersItem.getString("banner_item_id")),BannersItem.getString("banner_title"));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });


                   }
                   else if(BannersItem.getInt("banner_type_id")==3)
                   {


                       bannerMiddle.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               try {
                                   ((MainActivity) getActivity()).loadCategoryProduct(BannersItem.getString("banner_item_id"),BannersItem.getString("banner_title"));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });



                   }
                   else
                   {

                   }

               } else if(BannersItem.getInt("banner_position")==3)
               {
                   String imgurl = BannersItem.getString("banner_image");
                   imgurl = imgurl.replace(" ","%20");
                   Picasso.with(getActivity()).load(imgurl).into(bannerBottom);
                   if(BannersItem.getInt("banner_status")==1)
                   {
                       bannerBottom.setVisibility(View.VISIBLE);
                   }
                   else
                   {
                       bannerBottom.setVisibility(View.GONE);
                   }

                   if(BannersItem.getInt("banner_type_id")==1)
                   {



                       bannerBottom.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               ((MainActivity) getActivity()).signup();
                           }
                       });
                   }
                   else if(BannersItem.getInt("banner_type_id")==2)
                   {


                       bannerBottom.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               try {
                                   ((MainActivity) getActivity()).showProductDetail(Integer.parseInt(BannersItem.getString("banner_item_id")),BannersItem.getString("banner_title"));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });


                   }
                   else if(BannersItem.getInt("banner_type_id")==3)
                   {


                       bannerBottom.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               try {
                                   ((MainActivity) getActivity()).loadCategoryProduct(BannersItem.getString("banner_item_id"),BannersItem.getString("banner_title"));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });



                   }
                   else
                   {

                   }

               }

           }


            for(int j=0;j<HorizontalcatArray.length();j++) {
                HorizontalcatModel SlidercatObject  = new HorizontalcatModel();
                JSONObject ProductListImageItem = HorizontalcatArray.getJSONObject(j);
                String ProductListImageUrl = ProductListImageItem.getString("menuIcon");
                String catgory_id = ProductListImageItem.getString("category_id");
                String ProductListname = ProductListImageItem.getString("name");
                if(ProductListname.equals("Storage Utilities")) {
                    ProductListname = ProductListname.replace("Storage Utilities", "Storage...");
                }
                else
                {
                    ProductListname = ProductListname.replace("Decor", " Decor");
                }

                SlidercatObject.setImageIcon(ProductListImageUrl);
                SlidercatObject.setCatName(ProductListname);
                SlidercatObject.setCategoryId(catgory_id);
                HorizontalcatModel.add(SlidercatObject);


            }



            for(int i=0;i<DataLayoutDesign.length();i++) {


                DynamicDesignDataModel object  =  new DynamicDesignDataModel();
                JSONObject arrayobject = DataLayoutDesign.getJSONObject(i);
                object.setModule_id(arrayobject.getInt("module_id"));
                object.setModuleName(arrayobject.getString("module_name"));
                object.setStatus(arrayobject.getInt("module_status"));
                object.setModuleData(arrayobject.getJSONArray("module_data"));
                DatalayoutDesigndata = arrayobject.getJSONArray("module_data");
                DynamicDesignDataModel.add(object);


            }


            for (int k=0;k<YouMayLikeArray.length();k++)
            {
                JSONObject ImageItemt = YouMayLikeArray.getJSONObject(k);
                YouMayLikeModel obj = new YouMayLikeModel();
                obj.setProduct_id(ImageItemt.getInt("product_id"));
                obj.setProductImage(ImageItemt.getString("image"));
                String name = ImageItemt.getString("name");
                name = name.replace("&amp;","&");
                obj.setProductName(name);
                YouMayLikeModel.add(obj);

            }

            for(int m=0;m<StaticLayoutOne.length();m++) {

                int app = m + 1;
                JSONObject ImageItemt = StaticLayoutOne.getJSONObject(m);
                int resID = getActivity().getResources().getIdentifier("static_layout_one_content_" + app, "id", getActivity().getPackageName());
                final SimpleDraweeView iv = (SimpleDraweeView) view.findViewById(resID);
                String image = ImageItemt.getString("image");
                image = image.replace(" ","%20");

                iv.setImageURI(image);
                final String title =ImageItemt.getString("layout_name");
                final String type = ImageItemt.getString("layout_item_type");
                final String layou_name =  ImageItemt.getString("layout_item_name");

                TextView Text = (TextView) view.findViewById(R.id.DynamicGridLayoutHeader);
                Text.setText(title);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(type.equals("1")) {
                            ((MainActivity) getActivity()).showProductDetail(Integer.parseInt(String.valueOf(iv.getTag())), layou_name);
                        }
                        else if(type.equals("2"))
                        {
                            ((MainActivity) getActivity()).loadCategoryProduct(String.valueOf(iv.getTag()), layou_name);
                        }

                    }
                });
                iv.setTag(ImageItemt.getInt("item_id"));
            }


        }
        catch (JSONException e)
        {
            e.printStackTrace();
           // Log.e(TAG, "Json Exception: " + e.getMessage());
        }




        HomePagerAdapter = new HomePagerAdapter(((AppCompatActivity)getActivity()),HomeSliderModel);
        Viewpager.setAdapter(HomePagerAdapter);
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        int mar = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        Viewpager.setPadding(left, 0, right, 0);
        Viewpager.setClipToPadding(false);
        Viewpager.setPageMargin(mar);
        Viewpager.setCurrentItem(50);

        HorizontalCategoryAdapter = new HorizontalCategoryAdapter(((AppCompatActivity)getActivity()),HorizontalcatModel);
        productCatHorizontalList.setAdapter(HorizontalCategoryAdapter);

        DynamicDesignDataAdapter = new DynamicDesignDataAdapter(getActivity(),DynamicDesignDataModel);
        moduleLoader.setAdapter(DynamicDesignDataAdapter);
        Utility.setListViewHeightBasedOnChildren(moduleLoader);

        YouMayLikeAdapter = new YouMayLikeAdapter(((AppCompatActivity)getActivity()),YouMayLikeModel);
        YouMayLike.setAdapter(YouMayLikeAdapter);









       homedata.setVisibility(View.VISIBLE);


        pg.dismiss();


    }


    public void LoadUserRecentProduct()
    {

        AppSharedPreferences session =  new AppSharedPreferences(getActivity());

        int count = session.pref.getInt(session.RecentlYcount, 0);

        for(int i=0;i<count;i++) {

            SessionRecentProduct.add(session.pref.getInt(session.RECENTLYVIEW + i, i));

        }


        String url = ClassAPI.GET_PRODUCT_RECENT+SessionRecentProduct;
        url=url.replaceAll(" ", "%20");

        VolleyUtils.makeJsonObjectRequest(getActivity(), url, new VolleyUtils.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {

            }

            @Override
            public void onResponse(Object response) {
                if(response!=null) {
                    loadRecentViewProduct((String) response);


                }

            }
        });



    }

    public void loadRecentViewProduct(String data)
    {

        RecentlyViewList =new ArrayList<>();
        JSONObject jsobject = null;
        try {
            jsobject = new JSONObject(data);
            RecentlyViewProduct =jsobject.getJSONArray(ClassAPI.JSON_ARRAY);
            LinearLayout rs = (LinearLayout)view.findViewById(R.id.RecentlyViewlayout);
            if(RecentlyViewProduct.length()>0)
            {
                rs.setVisibility(View.VISIBLE);
            }
            for(int i=0;i<RecentlyViewProduct.length();i++)
            {
                jsobject = RecentlyViewProduct.getJSONObject(i);
                RecentlyViewdModel obj = new RecentlyViewdModel();
                obj.setProductId(jsobject.getInt("product_id"));
                obj.setProductName(jsobject.getString("name"));
                obj.setProductImage(jsobject.getString("image"));
                obj.setProductPriice(jsobject.getString("price"));
                obj.setSpecialProductPriice(jsobject.getString("special"));
                obj.setDiscount(jsobject.getString("discount"));
                RecentlyViewList.add(obj);
            }


        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        RecentlyViewAdapter Radapter = new RecentlyViewAdapter(getActivity(),RecentlyViewList);
        recentlyViewlist.setAdapter(Radapter);

        Utility.setListViewHeightBasedOnChildren(recentlyViewlist);

    }


}
