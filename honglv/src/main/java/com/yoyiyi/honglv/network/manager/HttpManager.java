package com.yoyiyi.honglv.network.manager;

import com.yoyiyi.honglv.base.BaseApplication;
import com.yoyiyi.honglv.network.service.HttpService;
import com.yoyiyi.honglv.utils.TDevice;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public class HttpManager {

    public static final String BASE_URL = "http://hltm-api.tomoya.cn/";
    //短缓存有效期为1分钟
    // public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;
    //public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    //  public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    // public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static OkHttpClient mOkHttpClient;
    private volatile static HttpManager INSTANCE;
    private static HttpService sApiService;

    //设置连接超时的值
    private static final int TIMEOUT = 20;

    private HttpManager() {
        initOkHttpClient();
        initRetrofit();

    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                //返回的字符串，而不是json数据格式，要使用下面的转换器
                // .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sApiService = retrofit.create(HttpService.class);
    }


    public static HttpManager getHttpManager() {
        if (null == INSTANCE) {
            synchronized (HttpManager.class) {
                if (null == INSTANCE) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    public HttpService getHttpService() {
        return sApiService;
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (HttpManager.class) {
                if (mOkHttpClient == null) {
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(BaseApplication.get_context().getCacheDir(), "honglv"),
                            1024 * 1024 * 100);
                    //新建一个okHttp
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                          .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = chain -> {
        Request request = chain.request();
        if (!TDevice.hasInternet()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        Response originalResponse = chain.proceed(request);
        if (TDevice.hasInternet()) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                    .removeHeader("Pragma").build();
        }
    };

   /* public Observable<ActivityCenterInfo> getActivityCenterList(int page, int pageSize) {
        return sApiService.getActivityCenterList(page, pageSize);
    }*/

    /**
     * 处理http请求——RX
     *
     * @param pObservable
     * @param pSubscriber
     */

    public void doHttpRequest(Observable pObservable, Subscriber pSubscriber) {
        Observable observable = pObservable
                .interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(pSubscriber);
    }

    public void doHttpRequest(Observable.Transformer t, Observable pObservable, Subscriber pSubscriber) {
        Observable observable = pObservable
               // .interval(1, TimeUnit.SECONDS)
                .compose(t)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(pSubscriber);
    }

   /* //Action1
    public void doHttpRequest(Observable.Transformer t, Observable pObservable) {
        Observable observable = pObservable
                //.interval(5, TimeUnit.SECONDS)
                .compose(t)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(action1);*/

    // }


  /*  *//**
     * json方式传参
     * 将json格式的字符串转换成RequestBody
     *
     * @param pBody
     * @return
     *//*
    public RequestBody getPostBody(String pBody) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), pBody);
        return body;
    }*/

    /**
     * 处理http请求——常规
     *
     * @param pCall
     * @param pCallback
     */
    public void doHttpRequest(Call pCall, Callback pCallback) {
        Call call = pCall;
        call.enqueue(pCallback);
    }
}
