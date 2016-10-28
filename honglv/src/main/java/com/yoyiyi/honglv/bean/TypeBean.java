package com.yoyiyi.honglv.bean;

/**
 * Created by yoyiyi on 2016/10/27.
 */
public class TypeBean {

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
}
