package com.yoyiyi.honglv.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;

/**
 * 浏览器界面
 * Created by yoyiyi on 2016/10/20.
 */
public class BrowserActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web)
    WebView mWeb;
    @BindView(R.id.progress)
    Loading mProgress;
    private String mTitle;
    private String mUrl;
    private WebClientBase mWebClientBase = new WebClientBase();
    private WebClient mClient = new WebClient();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browser;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getInfoIntent();
        initToolbar();
        initWebView();
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setTitle(TextUtils.isEmpty(mTitle) ? "详情" : mTitle);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void getInfoIntent() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mTitle = extras.getString("title");
            mUrl = extras.getString("url");
           mUrl = (String) extras.getSerializable("url");
        }
    }

    private void initWebView() {
        mProgress.start();
        mProgress.setAutoRun(true);
        WebSettings webSettings = mWeb.getSettings();
        //设置js支持
        webSettings.setJavaScriptEnabled(true);
        // 设置支持javascript脚本
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setGeolocationEnabled(true);
        //  webSettings.setUseWideViewPort(true);//关键点
        //  webSettings.setLoadWithOverviewMode(true);//全屏
        webSettings.setBuiltInZoomControls(true);// 设置显示缩放按钮
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setDisplayZoomControls(false);
        webSettings.setAppCacheEnabled(true);
        mWeb.getSettings().setBlockNetworkImage(true);
        mWeb.setWebViewClient(mWebClientBase);
        mWeb.requestFocus(View.FOCUS_DOWN);
        mWeb.getSettings().setDefaultTextEncodingName("UTF-8");
        mWeb.setWebChromeClient(mClient);
        mWeb.loadUrl(mUrl);
        Logger.d(mUrl);
     //  mWeb.loadUrl("http://www.hltm.tv/xieemanhua/15340.html");
    }

    class WebClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            //定义对话框
            AlertDialog.Builder alert = new AlertDialog
                    .Builder(BrowserActivity.this)
                    .setTitle(R.string.app_name)
                    .setMessage(message)
                    .setPositiveButton("确定", (dialog, which) -> result.confirm());

            alert.setCancelable(false);
            alert.create();
            alert.show();
            return true;
        }
    }

    class WebClientBase extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        //加载结束时候
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgress.stop();
            mProgress.setVisibility(View.GONE);
            mWeb.getSettings().setBlockNetworkImage(false);
        }

        //加载错误时候
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mProgress.setVisibility(View.GONE);
            String errorHtml = "<html><body><h2>找不到网页</h2></body></html>";
            view.loadDataWithBaseURL(null, errorHtml, "text/html", "UTF-8", null);
        }
    }

    @Override
    protected void onPause() {
        mWeb.reload();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWeb.destroy();
        super.onDestroy();
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWeb.canGoBack()) {
            mWeb.goBack();
            //goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
            return false;
        }
    }
}
