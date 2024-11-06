package com.example.cmpe_277_hackathon_project;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataPointDao {
    @Insert
    void insert(DataPoint dataPoint);

    @Query("SELECT * FROM data_points WHERE country = :country AND indicator = :indicator ORDER BY year")
    List<DataPoint> getDataPoints(String country, String indicator);
}
