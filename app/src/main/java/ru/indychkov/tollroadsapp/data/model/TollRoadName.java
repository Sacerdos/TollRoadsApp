package ru.indychkov.tollroadsapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "road_name", indices = {@Index(value = "main_name", unique = true)})
public class TollRoadName {
    @PrimaryKey()
    @ColumnInfo(name = "road_id")
    @SerializedName("road_id")
    @Expose
    private long road_id;

    @ColumnInfo(name = "main_name")
    @SerializedName("main_name")
    @Expose
    private String main_name;

    public long getRoad_id() {
        return road_id;
    }

    public void setRoad_id(long road_id) {
        this.road_id = road_id;
    }

    public String getMain_name() {
        return main_name;
    }

    public void setMain_name(String main_name) {
        this.main_name = main_name;
    }

    @Ignore
    @Override
    public String toString() {
        return "TollRoadName{" +
                "road_id=" + road_id +
                ", main_name='" + main_name + '\'' +
                '}';
    }
}
