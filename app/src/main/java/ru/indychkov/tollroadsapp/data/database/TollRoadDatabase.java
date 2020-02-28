package ru.indychkov.tollroadsapp.data.database;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.Executor;

import ru.indychkov.tollroadsapp.data.model.TollRoadName;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;


@Database(entities = {TollRoadName.class, TollRoadPart.class, TollRoadPrice.class}, version = 1, exportSchema = false)
public abstract class TollRoadDatabase extends RoomDatabase {
    private static final String TAG = "TollRoadsDatabase";
    public abstract TollRoadDAO tollRoadDAO();
    private static final Object LOCK = new Object();
    private static volatile TollRoadDatabase sInstance;
    private static final String DATABASE_NAME = "toll_road.db";
    public static TollRoadDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        TollRoadDatabase.class, DATABASE_NAME)
                        .build();

            }
        }
        return sInstance;
    }
}