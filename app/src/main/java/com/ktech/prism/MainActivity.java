package com.ktech.prism;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


@SuppressWarnings("deprecation")
public class MainActivity extends Activity  {
    private WebView wv1;
    private ProgressDialog progressDialog;
    @SuppressLint({"SetJavaScriptEnabled", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait"); // Setting Message
        progressDialog.setTitle("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
        wv1=(WebView)findViewById(R.id.webView);
        WebSettings webSettings = wv1.getSettings();
        wv1.setWebViewClient(new MyBrowser());
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setAppCacheEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getPath());
        wv1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        //improve
        wv1.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        wv1.loadUrl("https://www.facebook.com");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wv1.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            wv1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        if(wv1.canGoBack()) {
            wv1.goBack();
        } else{
            super.onBackPressed();
        }
    }
}
