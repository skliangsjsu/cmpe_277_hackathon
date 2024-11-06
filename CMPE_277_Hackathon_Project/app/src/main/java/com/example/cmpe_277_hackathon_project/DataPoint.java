package com.example.cmpe_277_hackathon_project;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_points")
public class DataPoint {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String country;
    public String indicator;
    public int year;
    public float value;

    public DataPoint(String country, String indicator, int year, float value) {
        this.country = country;
        this.indicator = indicator;
        this.year = year;
        this.value = value;
    }
}

