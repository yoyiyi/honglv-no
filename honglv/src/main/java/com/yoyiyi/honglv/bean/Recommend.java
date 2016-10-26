package com.yoyiyi.honglv.bean;

import java.util.List;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public class Recommend {

    /**
     * title : 新增连载动漫
     * url : http://www.hltm.tv/list/7.html
     */

    private TabBean tab;
    /**
     * update : 连载至3集
     * name : WWW.迷糊餐厅
     * url : http://www.hltm.tv/view/12490.html
     * img : http://www.hltm.tv/uploads/editor/2016/10/20161016075142506.jpg
     */

    private List<ListBean> list;

    public TabBean getTab() {
        return tab;
    }

    public void setTab(TabBean tab) {
        this.tab = tab;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class TabBean {
        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ListBean {
        private String update;
        private String name;
        private String url;
        private String img;

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
