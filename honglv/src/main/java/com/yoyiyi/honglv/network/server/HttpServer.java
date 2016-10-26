package com.yoyiyi.honglv.network.server;

import com.yoyiyi.honglv.bean.AnimaList;
import com.yoyiyi.honglv.bean.Carousel;
import com.yoyiyi.honglv.bean.HotNew;
import com.yoyiyi.honglv.bean.TopicDetail;
import com.yoyiyi.honglv.bean.TopicList;
import com.yoyiyi.honglv.bean.WeekUpdate;
import com.yoyiyi.honglv.network.manager.HttpManager;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public class HttpServer {
    /*public static void getAll(Observable.Transformer t, Subscriber sub) {
        Observable all = HttpManager.getHttpManager().getHttpService().getAll();
        HttpManager.getHttpManager().doHttpRequest(t, all, sub);
    }*/

    public static void getCorousel(Observable.Transformer t,
                                   Subscriber<List<Carousel>> subscriber) {
        Observable<List<Carousel>> carousel =
                HttpManager
                        .getHttpManager()
                        .getHttpService()
                        .getCarousel();
        HttpManager.getHttpManager().doHttpRequest(t, carousel, subscriber);

    }

    /**
     * @param ver    版本
     * @param letter 字母
     * @param sort   排序方式
     * @param tab    类型
     * @param page   页数
     * @return
     */
    public static void getBangumi(Observable.Transformer t,
                                  Integer ver,
                                  String letter,
                                  Integer sort,
                                  Integer tab,
                                  Integer page,
                                  Subscriber<AnimaList> subscriber) {
        Observable<AnimaList> anima =
                HttpManager
                        .getHttpManager()
                        .getHttpService()
                        .getBangumi(ver, letter, sort, tab, page);
        HttpManager.getHttpManager().doHttpRequest(t, anima, subscriber);

    }

    public static void getWeekUpdate(Observable.Transformer t, Subscriber<List<WeekUpdate>> subscriber) {
        Observable<List<WeekUpdate>> weekUpdate =
                HttpManager
                        .getHttpManager()
                        .getHttpService()
                        .getWeekUpdate();
        HttpManager.getHttpManager().doHttpRequest(t, weekUpdate, subscriber);

    }

    public static void getTopicList(Observable.Transformer t, Subscriber subscriber) {
        Observable<List<TopicList>> topicList = HttpManager.getHttpManager().getHttpService().getTopicList();
        HttpManager.getHttpManager().doHttpRequest(t, topicList, subscriber);

    }

    public static void getTopicDetail(Observable.Transformer t, Integer page, String url, Subscriber subscriber) {
        Observable<TopicDetail> topicDetail = HttpManager.getHttpManager().getHttpService()
                .getTopicDetail(page, url);
        HttpManager.getHttpManager().doHttpRequest(t, topicDetail, subscriber);


    }

    public static void getHotNewAnimate(Observable.Transformer t,
                                        Subscriber<List<HotNew>> subscriber) {
        Observable<List<HotNew>> newHotAnimate = HttpManager.getHttpManager().getHttpService().getNewHotAnimate();
        HttpManager.getHttpManager().doHttpRequest(t, newHotAnimate, subscriber);
    }
}
