package com.academy.fundamentals.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.model.VideoModel;

import java.util.Collection;
import java.util.List;


@Dao
public interface VideoDao {

    @Query("SELECT * FROM VideoModel WHERE movieId = :movieId")
    VideoModel getVideo(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VideoModel videoModel);

}
