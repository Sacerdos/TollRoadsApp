package ru.indychkov.tollroadsapp.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.indychkov.tollroadsapp.data.model.TollRoadName;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TollRoadDAO {
    @Insert(onConflict = REPLACE)
    void addTollRoadName(TollRoadName tollRoadName);

    @Insert(onConflict = REPLACE)
    void addTollRoadPart(TollRoadPart tollRoadPart);

    @Insert(onConflict = REPLACE)
    void addTollRoadPrice(TollRoadPrice tollRoadPrice);

    @Query("SELECT main_name from road_name")
    List<String> getAllTollRoadNames();

    @Query("SELECT * from road_part WHERE main_road_name=:main_name")
    List<TollRoadPart> getPartsForRoad(String main_name);
}
