package com.academy.fundamentals.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

/**
 * Created By Yamin on 20-09-2018
 */
public class MovieModel implements Parcelable {

    private String name;
    @DrawableRes
    private int imageRes;
    @DrawableRes
    private int backImageRes;
    private String overview;
    private String releaseDate;
    private String trailerUrl;

    public MovieModel() {   }

    protected MovieModel(Parcel in) {
        name = in.readString();
        imageRes = in.readInt();
        backImageRes = in.readInt();
        overview = in.readString();
        releaseDate = in.readString();
        trailerUrl = in.readString();
    }

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

    @DrawableRes
    public int getBackImageRes() {
        return backImageRes;
    }

    public void setBackImageRes(@DrawableRes int backImageRes) {
        this.backImageRes = backImageRes;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "name='" + name + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", overview=" + (TextUtils.isEmpty(overview) ? "Empty" : overview.length()) +
                ", imageRes=" + (imageRes == 0 ? "Nun" : "OK") +
                ", backImageRes=" + (backImageRes == 0 ? "Nun" : "OK") +
                '}';
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(imageRes);
        parcel.writeInt(backImageRes);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(trailerUrl);
    }
}
