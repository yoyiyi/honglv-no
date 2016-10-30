package com.yoyiyi.honglv.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yoyiyi.honglv.R;
import com.yoyiyi.honglv.base.BaseActivity;
import com.yoyiyi.honglv.bean.BangumiDetail;
import com.yoyiyi.honglv.bean.entity.TitleEntity;
import com.yoyiyi.honglv.network.manager.HttpManager;
import com.yoyiyi.honglv.ui.fragment.help.BaiduFragment;
import com.yoyiyi.honglv.ui.fragment.help.CiliFragment;
import com.yoyiyi.honglv.ui.fragment.help.FtpFragment;
import com.yoyiyi.honglv.ui.widget.RecommendText;
import com.yoyiyi.honglv.ui.widget.empty.EmptyLayout;
import com.yoyiyi.honglv.utils.TDevice;

import net.qiujuer.genius.ui.widget.Loading;

import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yoyiyi.honglv.R.id.web;

/**
 * 番剧详情页面
 * Created by yoyiyi on 2016/10/27.
 */
public class BangumiDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(web)
    WebView mWeb;
    @BindView(R.id.title)
    TextView mTitles;
    @BindView(R.id.cover)
    ImageView mCover;
    @BindView(R.id.add)
    LinearLayout mAdd;
    @BindView(R.id.recommend)
    LinearLayout mRecommend;
    @BindView(R.id.progress)
    Loading mLoading;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    @BindView(R.id.sliding_tabs)
    CommonTabLayout mSlidingTabs;
    private String mTitle;
    private WebViewClient mWebViewClient;
    private WebChromeClient mWebChromeClient;
    private String mUrl;
    private BangumiDetail mDetail;
    private BangumiDetail.OptsBean mOptsBean;
    private BangumiDetail.EditRecommendBean mEditRecommendBean;
    private ArrayList<CustomTabEntity> mEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banggumi_detail;
    }

    @Override
    protected void initWidget() {
        mLoading.setAutoRun(true);
        mLoading.start();
        getInfoIntent();
        initToolbar();
        mWeb.post(() -> requestData());
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
                .getBangumiDetail(url)
                .compose(bindToLifecycle())
                .map(bangumi -> bangumi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bangumiDetail -> {
                    if (bangumiDetail != null) {
                        mDetail = bangumiDetail;
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
        initFragment();
    }

    private void initFragment() {
        for (int i = 0; i < mDetail.getDownload().size(); i++) {
            if (mDetail.getDownload().size() == 0) return;
            mEntities.add(new TitleEntity(mDetail.getDownload().get(i).getTab()));
            switch (mDetail.getDownload().get(i).getTab()) {
                case "FTP下载":
                    mFragments.add(FtpFragment.newInstance(mDetail.getDownload().get(i)));
                    break;
                case "百度网盘下载":
                    mFragments.add(BaiduFragment.newInstance(mDetail.getDownload().get(i)));
                    break;
                case "磁力链接下载":
                    mFragments.add(CiliFragment.newInstance(mDetail.getDownload().get(i)));
                    break;
            }
        }
        mSlidingTabs.setTabData(mEntities, this, R.id.fl_change, mFragments);
    }

    private void initAllWidget() {
        for (int i = 0; i < mDetail.getOpts().size(); i++) {
            mOptsBean = mDetail.getOpts().get(i);
            TextView textView = new TextView(this);
            textView.setText(mOptsBean.getKey() + ":" + mOptsBean.getValue());
            mAdd.addView(textView);
        }
        for (int i = 0; i < mDetail.getEditRecommend().size(); i++) {
            mEditRecommendBean = mDetail.getEditRecommend().get(i);
            RecommendText recommendText = new RecommendText(this);
            recommendText.setInfo(mEditRecommendBean.getName(), mEditRecommendBean.getSort(),
                    mEditRecommendBean.getCount(), mEditRecommendBean.getUrl());
            mRecommend.addView(recommendText);
        }
        Glide.with(this)
                .load(Uri.parse(mDetail.getImg()))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mCover);
        mTitles.setText(mDetail.getName());


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
       mWeb.loadDataWithBaseURL(null, mDetail.getIntro(), "text/html", "utf-8", null);

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
        public void onProgressChanged(WebView webView, int i) {
            if (i == 100) {
                mLoading.stop();
                mLoading.setVisibility(View.GONE);
            } else {
                mLoading.setAutoRun(true);
                mLoading.start();
                mLoading.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(webView, i);
        }

        @Override
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            AlertDialog.Builder alert = new AlertDialog
                    .Builder(BangumiDetailActivity.this)
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
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mWeb.measure(w, h);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            mLoading.setVisibility(View.GONE);
            String errorHtml = "<html><body><h2>找不到网页</h2></body></html>";
            mWeb.loadDataWithBaseURL(null, errorHtml, "text/html", "UTF-8", null);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {

            //  Intent intent = new Intent(BangumiDetailActivity.this, TBSBrowerActivity.class);
            //  intent.putExtra("url", s);
            Bundle bundle = new Bundle();
            bundle.putString("url", s);
            TDevice.launch(BangumiDetailActivity.this, bundle);
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

    private void showEmptyView() {
        mEmpty.setVisibility(View.VISIBLE);
        mEmpty.HideButton();
        mLoading.setVisibility(View.GONE);
        mEmpty.setEmptyTv("没有资源下载！！");
        mEmpty.setEmptyIv(R.drawable.ic_empty_error);
    }
}
