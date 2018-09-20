package com.academy.fundamentals.model;

import android.support.annotation.DrawableRes;
import android.text.TextUtils;

/**
 * Created By Yamin on 20-09-2018
 */
public class MovieModel {

    private String name;
    @DrawableRes
    private int imageRes;
    private String overview;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DrawableRes
    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(@DrawableRes int imageRes) {
        this.imageRes = imageRes;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "name='" + name + '\'' +
                ", overview=" + (TextUtils.isEmpty(overview) ? "Empty" : overview.length()) +
                ", imageRes=" + (imageRes == 0 ? "Nun" : "OK") +
                '}';
    }
}
