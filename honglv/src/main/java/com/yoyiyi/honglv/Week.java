package com.yoyiyi.honglv;

/**
 * Created by yoyiyi on 2016/10/26.
 */
enum Week {
    MON("一", 0), TUE("二", 1), WED("三", 2), THU("四", 3),
    FRI("五", 4), SAT("六", 5), SUN("日", 6);
    private int i;
    private String week;

    Week(String week, int i) {
        this.week = week;
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
