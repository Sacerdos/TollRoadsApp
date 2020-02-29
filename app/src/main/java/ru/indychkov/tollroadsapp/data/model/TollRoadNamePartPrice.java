package ru.indychkov.tollroadsapp.data.model;

public class TollRoadNamePartPrice {
    private TollRoadName tollRoadName;
    private TollRoadPart tollRoadPart;
    private TollRoadPrice tollRoadPrice;

    public TollRoadNamePartPrice(TollRoadName tollRoadName, TollRoadPart tollRoadPart, TollRoadPrice tollRoadPrice) {
        this.tollRoadName = tollRoadName;
        this.tollRoadPart = tollRoadPart;
        this.tollRoadPrice = tollRoadPrice;
    }

    public TollRoadName getTollRoadName() {
        return tollRoadName;
    }

    public void setTollRoadName(TollRoadName tollRoadName) {
        this.tollRoadName = tollRoadName;
    }

    public TollRoadPart getTollRoadPart() {
        return tollRoadPart;
    }

    public void setTollRoadPart(TollRoadPart tollRoadPart) {
        this.tollRoadPart = tollRoadPart;
    }

    public TollRoadPrice getTollRoadPrice() {
        return tollRoadPrice;
    }

    public void setTollRoadPrice(TollRoadPrice tollRoadPrice) {
        this.tollRoadPrice = tollRoadPrice;
    }
}
