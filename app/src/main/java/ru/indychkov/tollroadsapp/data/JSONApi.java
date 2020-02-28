package ru.indychkov.tollroadsapp.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.indychkov.tollroadsapp.data.model.TollRoadName;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;

public interface JSONApi {
    @GET("/roads")
    Call<List<TollRoadName>> getRoads();
    @GET("/parts")
    Call<List<TollRoadPart>> getParts();
    @GET("/prices")
    Call<List<TollRoadPrice>> getPrices();
}
