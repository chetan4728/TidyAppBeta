package com.tidyhomz.admin.tidyappbeta.View.Fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.tidyhomz.admin.tidyappbeta.API.ClassAPI;
import com.tidyhomz.admin.tidyappbeta.Helpers.ProgressBar;
import com.tidyhomz.admin.tidyappbeta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportChatFragment extends Fragment {

    private WebView myWebView;

   View view;
   RelativeLayout back;
 ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_support_chat, container, false);
        ClassAPI.set_title.setText("Help Center");
        back = (RelativeLayout)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUrl();
            }
        });
        getUrl();
        return  view;
    }

    public void getUrl()
    {
        myWebView = (WebView) view.findViewById(R.id.webView1);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.setWebViewClient(new MyWebViewClient());
        progressBar = new ProgressBar(getContext());
        myWebView.loadUrl(ClassAPI.CHATAPI);

        progressBar.show();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressBar.hide();
            super.onPageFinished(view, url);
        }

    }





}
