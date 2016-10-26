package com.yoyiyi.honglv.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyiyi on 2016/10/19.
 */
public class WeekUpdate implements Parcelable {
    //一周更新 节目单
    /**
     * week : 一
     * list : [{"updateDate":"\r\n
     * 10-24日更新\r\n "
     * ,"name":"游戏王ARC-V第128集",
     * "url":"http://www.hltm.tv/search/?k=%E6%B8%B8%E6%88%8F%E7%8E%8BARC-V"}]
     */

    private String week;
    /**
     * updateDate :
     * 10-24日更新
     * <p>
     * name : 游戏王ARC-V第128集
     * url : http://www.hltm.tv/search/?k=%E6%B8%B8%E6%88%8F%E7%8E%8BARC-V
     */

    private List<ListBean> list;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        private String updateDate;
        private String name;
        private String url;

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.updateDate);
            dest.writeString(this.name);
            dest.writeString(this.url);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.updateDate = in.readString();
            this.name = in.readString();
            this.url = in.readString();
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
        dest.writeString(this.week);
        dest.writeList(this.list);
    }

    public WeekUpdate() {
    }

    protected WeekUpdate(Parcel in) {
        this.week = in.readString();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<WeekUpdate> CREATOR = new Parcelable.Creator<WeekUpdate>() {
        @Override
        public WeekUpdate createFromParcel(Parcel source) {
            return new WeekUpdate(source);
        }

        @Override
        public WeekUpdate[] newArray(int size) {
            return new WeekUpdate[size];
        }
    };
}
