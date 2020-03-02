package ru.indychkov.tollroadsapp.data.model;



import androidx.annotation.NonNull;
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

    @ColumnInfo(name = "category")
    @SerializedName("category")
    @Expose
    private int category;

    @ColumnInfo(name = "base_price")
    @SerializedName("base_price")
    @Expose
    private int base_price;

    @ColumnInfo(name = "base_night_price")
    @SerializedName("base_night_price")
    @Expose
    private int base_night_price;

    @ColumnInfo(name = "base_avtodor_price")
    @SerializedName("base_avtodor_price")
    @Expose
    private int base_avtodor_price;

    @ColumnInfo(name = "base_avtodor_night_price")
    @SerializedName("base_avtodor_night_price")
    @Expose
    private int base_avtodor_night_price;

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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getBase_price() {
        return base_price;
    }

    public void setBase_price(int base_price) {
        this.base_price = base_price;
    }

    public int getBase_night_price() {
        return base_night_price;
    }

    public void setBase_night_price(int base_night_price) {
        this.base_night_price = base_night_price;
    }

    public int getBase_avtodor_price() {
        return base_avtodor_price;
    }

    public void setBase_avtodor_price(int base_avtodor_price) {
        this.base_avtodor_price = base_avtodor_price;
    }

    public int getBase_avtodor_night_price() {
        return base_avtodor_night_price;
    }

    public void setBase_avtodor_night_price(int base_avtodor_night_price) {
        this.base_avtodor_night_price = base_avtodor_night_price;
    }


    @Ignore
    @Override
    @NonNull
    public String toString() {
        return "TollRoadPrice{" +
                "price_id=" + price_id +
                ", part_name='" + part_name + '\'' +
                ", category=" + category +
                ", base_price=" + base_price +
                ", base_night_price=" + base_night_price +
                ", base_avtodor_price=" + base_avtodor_price +
                ", base_avtodor_night_price=" + base_avtodor_night_price +
                '}';
    }
}

