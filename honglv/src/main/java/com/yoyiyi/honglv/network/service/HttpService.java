package com.yoyiyi.honglv.network.service;

import com.yoyiyi.honglv.bean.AnimaList;
import com.yoyiyi.honglv.bean.BangumiDetail;
import com.yoyiyi.honglv.bean.Carousel;
import com.yoyiyi.honglv.bean.HotNew;
import com.yoyiyi.honglv.bean.NewDetail;
import com.yoyiyi.honglv.bean.News;
import com.yoyiyi.honglv.bean.Ranking;
import com.yoyiyi.honglv.bean.RecentUpdate;
import com.yoyiyi.honglv.bean.Recommend;
import com.yoyiyi.honglv.bean.Search;
import com.yoyiyi.honglv.bean.TopicDetail;
import com.yoyiyi.honglv.bean.TopicList;
import com.yoyiyi.honglv.bean.WeekUpdate;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public interface HttpService {
    //轮播图
    @GET("carousel")
    Observable<List<Carousel>> getCarousel();

    //首页推荐
    @GET("addRecommend")
    Observable<List<Recommend>> getRecommend();

    /**
     * 番剧
     *
     * @param ver    版本
     * @param letter 字母
     * @param sort   排序方式
     * @param tab    类型
     * @param page   页数
     * @return
     */
    @GET("list")
    Observable<AnimaList> getBangumi(
            @Query("ver") Integer ver
            , @Query("letter") String letter
            , @Query("sort") Integer sort
            , @Query("tab") Integer tab
            , @Query("page") Integer page);

    /**
     * 一周更新
     */
    @GET("weekUpdate")
    Observable<List<WeekUpdate>> getWeekUpdate();

    @GET("topics")
    Observable<List<TopicList>> getTopicList();

    @GET("topicDetail")
    Observable<TopicDetail> getTopicDetail(
            @Query("page") Integer page
            , @Query("url") String url);

    @GET("hotNewAnimate")
    Observable<List<HotNew>> getNewHotAnimate();

    @GET("ranking")
    Observable<List<Ranking>> getRanking();

    @GET("news")
    Observable<News> getNews(@Query("page") Integer page);

    @GET("recentUpdate")
    Observable<List<RecentUpdate>> getRecentUpdate();


    @GET("newsDetail")
    Observable<NewDetail> getNewsDetail(@Query("url") String url);

    @GET("detail")
    Observable<BangumiDetail> getBangumiDetail(@Query("url") String url);

    @GET("search")
    Observable<Search> getSearchResult(@Query("k") String k, @Query("page") Integer page);

}
