package com.pzh.yiqiplay.bean;

/**
 * Created by pzh on 16/6/7.
 */
public class ShuduBean extends BaseBean {
    public boolean isCheckable() {
        return checkable;
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private boolean checkable;
    private int num;
}
