package com.yoyiyi.honglv.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public class Ranking implements Parcelable {
//排行
    /**
     * tab : 2016年7月新番排行榜
     * url : /topiclist/201607yuexinfan.html
     * list : [{"name":"食戟之灵：贰之皿/食戟之灵 第二季","url":"http://www.hltm.tv/view/12445.html","episode":"第13集"},{"name":"剑风传奇","url":"http://www.hltm.tv/view/12444.html","episode":"第12集"},{"name":"ReLIFE/重返17岁","url":"http://www.hltm.tv/view/12482.html","episode":"第13集"},{"name":"驱魔少年 HALLOW","url":"http://www.hltm.tv/view/12454.html","episode":"第13集"},{"name":"禁忌咒纹","url":"http://www.hltm.tv/view/12455.html","episode":"第12集"},{"name":"忧郁的物怪庵","url":"http://www.hltm.tv/view/12443.html","episode":"第13集"},{"name":"情热传说 the X","url":"http://www.hltm.tv/view/12450.html","episode":"第13集"},{"name":"91天","url":"http://www.hltm.tv/view/12477.html","episode":"第12集"},{"name":"吸血鬼仆人","url":"http://www.hltm.tv/view/12474.html","episode":"第12集"},{"name":"发条精灵战记 天镜的极北之星","url":"http://www.hltm.tv/view/12479.html","episode":"第13集"},{"name":"代号Qualidea","url":"http://www.hltm.tv/view/12466.html","episode":"第12集"},{"name":"灵能百分百","url":"http://www.hltm.tv/view/12481.html","episode":"第12集"},{"name":"Rewrite","url":"http://www.hltm.tv/view/12449.html","episode":"第13集"},{"name":"亚尔斯兰战记 风尘乱舞","url":"http://www.hltm.tv/view/12448.html","episode":"第8集"},{"name":"橘色奇迹","url":"http://www.hltm.tv/view/12473.html","episode":"第13集"}]
     */

    private String tab;
    private String url;
    /**
     * name : 食戟之灵：贰之皿/食戟之灵 第二季
     * url : http://www.hltm.tv/view/12445.html
     * episode : 第13集
     */

    private List<ListBean> list;

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        private String name;
        private String url;
        private String episode;

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

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.url);
            dest.writeString(this.episode);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.name = in.readString();
            this.url = in.readString();
            this.episode = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tab);
        dest.writeString(this.url);
        dest.writeList(this.list);
    }

    public Ranking() {
    }

    protected Ranking(Parcel in) {
        this.tab = in.readString();
        this.url = in.readString();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Ranking> CREATOR = new Parcelable.Creator<Ranking>() {
        @Override
        public Ranking createFromParcel(Parcel source) {
            return new Ranking(source);
        }

        @Override
        public Ranking[] newArray(int size) {
            return new Ranking[size];
        }
    };
}
