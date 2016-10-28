package com.yoyiyi.honglv.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yoyiyi on 2016/10/27.
 */
public class TypeBean implements Parcelable {

    private String name;
    private Integer num;
    private int img;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public TypeBean(String name, Integer num,int img) {
        this.num = num;
        this.img = img;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.num);
        dest.writeInt(this.img);
    }

    protected TypeBean(Parcel in) {
        this.name = in.readString();
        this.num = (Integer) in.readValue(Integer.class.getClassLoader());
        this.img = in.readInt();
    }

    public static final Parcelable.Creator<TypeBean> CREATOR = new Parcelable.Creator<TypeBean>() {
        @Override
        public TypeBean createFromParcel(Parcel source) {
            return new TypeBean(source);
        }

        @Override
        public TypeBean[] newArray(int size) {
            return new TypeBean[size];
        }
    };
}
