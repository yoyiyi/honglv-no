package com.yoyiyi.honglv.ui.activity.another;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.utils.TDevice;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by yoyiyi on 2016/10/31.
 */
public class NewsAnotherActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web)
    WebView mWeb;


    private int id;
    private String title, body;
    private WebClient mWebChromeClient;
    private WebClientBase mWebViewClient;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_another;
    }

    @Override
    protected void initWidget() {
        getTitleIntent();
        initToolbar();
        requestData();
        //initTBS();
    }

    private void requestData() {
        HttpManager.getHttpManager().getHttpService()
                .getNewsDetail(id)
                .compose(bindToLifecycle())
               /* .flatMap(new Func1<ZhihuNewsDetail, Observable<String>>() {
                    @Override
                    public Observable<String> call(ZhihuNewsDetail news) {
                        body = news.getBody();
                        return
                                HttpManager
                                        .getHttpManager()
                                        .getHttpService()
                                        .getCss(news.getCss());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(css ->
                        {
                            body = body + css;
                            initTBS();
                        }, e ->
                                TDevice.showToast("没有数据")
                );*/
                .map(news -> news)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news ->
                        {
                            body = news.getBody();
                            initTBS();
                        }, e ->
                                TDevice.showToast("没有数据")
                );
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar.setTitle(title);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    public void getTitleIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
    }

    protected void initTBS() {

        mWebChromeClient = new WebClient();
        mWebViewClient = new WebClientBase();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）

        getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        WebSettings webSettings = mWeb.getSettings();
        //设置js支持
        webSettings.setJavaScriptEnabled(true);
        // 设置支持javascript脚本
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        //图片太大 没用
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setGeolocationEnabled(true);
        //  webSettings.setUseWideViewPort(true);//关键点
        //  webSettings.setLoadWithOverviewMode(true);//全屏
        webSettings.setBuiltInZoomControls(true);// 设置显示缩放按钮
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setDisplayZoomControls(false);
        webSettings.setAppCacheEnabled(true);

        // webSettings.setLoadWithOverviewMode(true);
        //  webSettings.setUseWideViewPort(true);

        mWeb.setVerticalScrollBarEnabled(false);
        mWeb.setVerticalScrollbarOverlay(false);
        mWeb.setHorizontalScrollBarEnabled(false);
        mWeb.setHorizontalScrollbarOverlay(false);
        //  mWeb.setLayerType();
        mWeb.setDrawingCacheEnabled(true);
        mWeb.getSettings().setLoadsImagesAutomatically(false);
        mWeb.getSettings().setBlockNetworkImage(true);
        mWeb.setWebViewClient(mWebViewClient);
        mWeb.requestFocus(View.FOCUS_DOWN);
        mWeb.getSettings().setDefaultTextEncodingName("UTF-8");
        mWeb.setWebChromeClient(mWebChromeClient);
        //  mWeb.loadData(TDevice.getNewContent(mDetail.getIntro()), "text/html;charset=utf-8", null);
        //mWeb.loadDataWithBaseURL(null, TDevice.getNewContent("<style> img {width:100%;}</style>"+mDetail.getIntro()), "text/html", "utf-8", null);
        mWeb.loadDataWithBaseURL("file:///android_asset/", body, "text/html", "utf-8", null);

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
                    .Builder(NewsAnotherActivity.this)
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
            mWeb.getSettings().setBlockNetworkImage(false);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mWeb.measure(w, h);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            String errorHtml = "<html><body><h2>找不到网页</h2></body></html>";
            mWeb.loadDataWithBaseURL(null, errorHtml, "text/html", "UTF-8", null);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {

            Bundle bundle = new Bundle();
            bundle.putString("url", s);
            TDevice.launch(NewsAnotherActivity.this, bundle);
            return super.shouldOverrideUrlLoading(webView, s);
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
