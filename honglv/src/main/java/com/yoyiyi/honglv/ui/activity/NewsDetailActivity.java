package com.yoyiyi.honglv.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;
import com.yoyiyi.honglv.bean.NewDetail;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.ui.widget.AutoScrollText;
import com.yoyiyi.honglv.ui.widget.empty.EmptyLayout;
import com.yoyiyi.honglv.utils.TDevice;

import net.qiujuer.genius.ui.widget.Loading;

import java.net.URLEncoder;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by yoyiyi on 2016/10/27.
 */
public class NewsDetailActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    AutoScrollText mTitles;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.web)
    WebView mWeb;
    @BindView(R.id.progress)
    Loading mLoading;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    private String mTitle;
    private WebViewClient mWebViewClient;
    private WebChromeClient mWebChromeClient;
    private String mUrl;
    private NewDetail mDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initWidget() {
        mLoading.setAutoRun(true);
        mLoading.start();
        getInfoIntent();
        initToolbar();
        requestData();
    }

    private void requestData() {
        String url = "";
        try {
            url = URLEncoder.encode(mUrl, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpManager
                .getHttpManager()
                .getHttpService()
                .getNewsDetail(url)
                .compose(bindToLifecycle())
                .map(newDetail -> newDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newDetail -> {
                    if (newDetail != null) {
                        mDetail = newDetail;
                        initAdapter();
                        finishTask();
                    }
                }, e -> showEmptyView());
    }

    private void finishTask() {
        mEmpty.setVisibility(View.GONE);
    }

    private void initAdapter() {
        initTBS();
        initAllWidget();
    }

    private void initAllWidget() {
        mTitles.setText(mDetail.getTitle());
        mTime.setText(mDetail.getTime());
    }

    protected void initTBS() {

        mWebChromeClient = new WebClient();
        mWebViewClient = new WebClientBase();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）

        getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mLoading.start();
        mLoading.setAutoRun(true);
        WebSettings webSettings = mWeb.getSettings();
        //设置js支持
        webSettings.setJavaScriptEnabled(true);
        // 设置支持javascript脚本
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
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
        //mWeb.loadUrl(mUrl);
        Logger.d(mDetail.getContent());
        // mWeb.loadDataWithBaseURL(null,);
        mWeb.loadData(TDevice.getNewContent(mDetail.getContent()), "text/html;charset=utf-8", null);
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
            // mContent = extras.getString("content");
            mUrl = extras.getString("url");
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
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            AlertDialog.Builder alert = new AlertDialog
                    .Builder(NewsDetailActivity.this)
                    .setTitle(R.string.app_name)
                    .setMessage(s1)
                    .setPositiveButton("确定", (dialog, which) -> jsResult.confirm());
            alert.setCancelable(false);
            alert.create();
            alert.show();
            return true;
        }
    }


    class WebClientBase extends WebViewClient {
        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            mLoading.stop();
            mLoading.setVisibility(View.GONE);
            mWeb.getSettings().setBlockNetworkImage(false);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            mLoading.setVisibility(View.GONE);
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

    private void showEmptyView() {
        mEmpty.setVisibility(View.VISIBLE);
        mEmpty.HideButton();
        mLoading.setVisibility(View.GONE);
        mEmpty.setEmptyTv("加载错误啦！！");
        mEmpty.setEmptyIv(R.drawable.ic_empty_error);
    }
}
