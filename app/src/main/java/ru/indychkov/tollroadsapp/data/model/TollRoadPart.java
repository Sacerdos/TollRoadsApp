package ru.indychkov.tollroadsapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "road_part", foreignKeys =
@ForeignKey(entity = TollRoadName.class,
        parentColumns = "main_name",
        childColumns = "main_road_name",
        onDelete = CASCADE),
        indices = {@Index(value = "main_road_name"),
                @Index(value = "part_name", unique = true)})
public class TollRoadPart {
    @PrimaryKey()
    @ColumnInfo(name = "part_id")
    @SerializedName("part_id")
    @Expose
    private long part_id;

    @ColumnInfo(name = "main_road_name")
    @SerializedName("main_road_name")
    @Expose
    private String main_road_name;

    @ColumnInfo(name = "part_name")
    @SerializedName("part_name")
    @Expose
    private String part_name;

    @ColumnInfo(name = "km_start")
    @SerializedName("km_start")
    @Expose
    private int km_start;

    @ColumnInfo(name = "km_end")
    @SerializedName("km_end")
    @Expose
    private int km_end;

    @ColumnInfo(name = "section_order")
    @SerializedName("section_order")
    @Expose
    private double section_order;

    @ColumnInfo(name = "isFromMoscow")
    @SerializedName("isFromMoscow")
    @Expose
    private boolean isFromMoscow;

    @ColumnInfo(name = "isToMoscow")
    @SerializedName("isToMoscow")
    @Expose
    private boolean isToMoscow;

    @ColumnInfo(name = "isOut")
    @SerializedName("isOut")
    @Expose
    private boolean isOut;

    @ColumnInfo(name = "isIn")
    @SerializedName("isIn")
    @Expose
    private boolean isIn;

    public long getPart_id() {
        return part_id;
    }

    public void setPart_id(long part_id) {
        this.part_id = part_id;
    }

    public String getMain_road_name() {
        return main_road_name;
    }

    public void setMain_road_name(String main_road_name) {
        this.main_road_name = main_road_name;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public int getKm_start() {
        return km_start;
    }

    public void setKm_start(int km_start) {
        this.km_start = km_start;
    }

    public int getKm_end() {
        return km_end;
    }

    public void setKm_end(int km_end) {
        this.km_end = km_end;
    }

    public double getSection_order() {
        return section_order;
    }

    public void setSection_order(double section_order) {
        this.section_order = section_order;
    }

    public boolean isFromMoscow() {
        return isFromMoscow;
    }

    public void setFromMoscow(boolean fromMoscow) {
        isFromMoscow = fromMoscow;
    }

    public boolean isToMoscow() {
        return isToMoscow;
    }

    public void setToMoscow(boolean toMoscow) {
        isToMoscow = toMoscow;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
    }

    @Ignore
    @Override
    public String toString() {
        return "TollRoadPart{" +
                "part_id=" + part_id +
                ", main_road_name='" + main_road_name + '\'' +
                ", part_name='" + part_name + '\'' +
                ", km_start=" + km_start +
                ", km_end=" + km_end +
                ", section_order=" + section_order +
                ", isFromMoscow=" + isFromMoscow +
                ", isToMoscow=" + isToMoscow +
                ", isOut=" + isOut +
                ", isIn=" + isIn +
                '}';
    }
}
