package com.academy.fundamentals.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class VideoModel {

    @PrimaryKey
    private int movieId;

    private String id;

    private String key;

    public VideoModel() {

    }

    public VideoModel(int movieId, String id, String key) {
        this.movieId = movieId;
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
