package com.yoyiyi.honglv.bean;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public class Carousel {
    /**
     * img : http://pic.hltm.tv/uploads/editor/2015/08/20150801115445357.jpg
     * url : http://www.hltm.tv/new/12.html
     * title : 每个月都来给红旅打赏下吧
     */
    private String img;
    private String url;
    private String title;

    public Carousel(String name, int car, String url) {
        title = name;
        imgId = car;
        this.url = url;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    private int imgId;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //}
}
