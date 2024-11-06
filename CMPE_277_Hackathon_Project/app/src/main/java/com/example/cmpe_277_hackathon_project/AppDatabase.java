package com.example.cmpe_277_hackathon_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataPoint.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataPointDao dataPointDao();
}
