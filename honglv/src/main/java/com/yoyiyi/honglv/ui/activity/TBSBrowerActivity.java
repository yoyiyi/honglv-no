package com.yoyiyi.honglv.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;

import static com.yoyiyi.honglv.R.id.web;


/**
 * 浏览器界面
 * Created by yoyiyi on 2016/10/27.
 */
public class TBSBrowerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(web)
    WebView mWeb;
    @BindView(R.id.progress)
    Loading mProgress;
    private String mTitle;
    private String mUrl;
    private WebViewClient mWebViewClient;
    private WebChromeClient mWebChromeClient;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tbs;
    }

    @Override
    protected void initWidget() {
        getInfoIntent();
        initToolbar();
        initTBS();
    }

    protected void initTBS() {

        mWebChromeClient = new WebClient();
        mWebViewClient = new WebClientBase();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）

        getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mProgress.start();
        mProgress.setAutoRun(true);
        WebSettings webSettings = mWeb.getSettings();
        //设置js支持
        webSettings.setJavaScriptEnabled(true);
        // 设置支持javascript脚本
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        //设置缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);

        webSettings.setGeolocationEnabled(true);
        //  webSettings.setUseWideViewPort(true);//关键点
        //  webSettings.setLoadWithOverviewMode(true);//全屏
        webSettings.setBuiltInZoomControls(true);// 设置显示缩放按钮
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setDisplayZoomControls(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //  mWeb.setLayerType();
        mWeb.setDrawingCacheEnabled(true);
        mWeb.getSettings().setBlockNetworkImage(true);
        mWeb.setWebViewClient(mWebViewClient);
        mWeb.requestFocus(View.FOCUS_DOWN);
        mWeb.getSettings().setDefaultTextEncodingName("UTF-8");
        mWeb.setWebChromeClient(mWebChromeClient);
        mWeb.loadUrl(mUrl);
      /*  mWeb.setOnTouchListener((v, n) -> {
                    mProgress.setVisibility(View.GONE);
                    return true;
                }
        );
*/
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
            // mUrl = (String) extras.getSerializable("url");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWeb.canGoBack()) {
            mWeb.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class WebClient extends WebChromeClient {
        public WebClient() {
            super();
        }

        @Override
        public void onProgressChanged(WebView webView, int i) {
            if (i == 40) {
                mProgress.stop();
                mProgress.setVisibility(View.GONE);
            } else {
                mProgress.setAutoRun(true);
                mProgress.start();
                mProgress.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(webView, i);
        }

      /*  @Override
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            AlertDialog.Builder alert = new AlertDialog
                    .Builder(TBSBrowerActivity.this)
                    .setTitle(R.string.app_name)
                    .setMessage(s1)
                    .setPositiveButton("确定", (dialog, which) -> jsResult.confirm());
            alert.setCancelable(false);
            alert.create();
            alert.show();
            return false;
        }*/
    }


    class WebClientBase extends WebViewClient {
        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            mProgress.stop();
            mProgress.setVisibility(View.GONE);
            mWeb.getSettings().setBlockNetworkImage(false);
            // int w = mWeb.getView().getWidth();
            // int h = mWeb.getView().getHeight();
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mWeb.measure(w, h);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            mProgress.setVisibility(View.GONE);
            String errorHtml = "<html><body><h2>找不到网页</h2></body></html>";
            mWeb.loadDataWithBaseURL(null, errorHtml, "text/html", "UTF-8", null);
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
}
