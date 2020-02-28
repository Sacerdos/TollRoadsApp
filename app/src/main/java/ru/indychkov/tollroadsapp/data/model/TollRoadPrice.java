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

@Entity(tableName = "road_part_price", foreignKeys =
@ForeignKey(entity = TollRoadPart.class,
        parentColumns = "part_name",
        childColumns = "part_name",
        onDelete = CASCADE),
        indices = {@Index(value = "part_name")})
public class TollRoadPrice {
    @PrimaryKey()
    @ColumnInfo(name = "price_id")
    @SerializedName("price_id")
    @Expose
    private long price_id;

    @ColumnInfo(name = "part_name")
    @SerializedName("part_name")
    @Expose
    private String part_name;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    private int price;

    public long getPrice_id() {
        return price_id;
    }

    public void setPrice_id(long price_id) {
        this.price_id = price_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Ignore
    @Override
    public String toString() {
        return "TollRoadPrice{" +
                "price_id=" + price_id +
                ", part_name='" + part_name + '\'' +
                '}';
    }
}

