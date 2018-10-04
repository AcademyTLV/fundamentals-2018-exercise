package com.academy.fundamentals.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created By Yamin on 20-09-2018
 */
public class MovieModel implements Parcelable {

    private String name;
    private String imageUri;
    private String backImageUri;
    private String overview;
    private String releaseDate;
    private String trailerUrl;

    public MovieModel() {   }

    protected MovieModel(Parcel in) {
        name = in.readString();
        imageUri = in.readString();
        backImageUri = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        trailerUrl = in.readString();
    }

    public MovieModel(String name, String imageUri, String backImageUri, String overview, String releaseDate, String trailerUrl) {
        this.name = name;
        this.imageUri = imageUri;
        this.backImageUri = backImageUri;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.trailerUrl = trailerUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageRes() {
        return imageUri;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getBackImageUri() {
        return backImageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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
                ", imageRes=" + (imageUri == null ? "Nun" : "OK") +
                ", backImageRes=" + (backImageUri == null ? "Nun" : "OK") +
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
        parcel.writeString(imageUri);
        parcel.writeString(backImageUri);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(trailerUrl);
    }
}
