package ru.indychkov.tollroadsapp.data;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.indychkov.tollroadsapp.data.model.TollRoadName;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;
import ru.indychkov.tollroadsapp.interactor.IDownloadTollRoadRepository;

public class DownloadTollRoadRepository implements IDownloadTollRoadRepository {
    private static final String BASE_URL = "https://dychkov.mockit.io";
    private final JSONApi jsonApi;

    public DownloadTollRoadRepository() {
        System.out.println("Ошибка4");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JSONApi.class);
    }

    @NonNull
    @Override
    public List<TollRoadName> loadTollRoadNames() throws IOException {
        Response<List<TollRoadName>> response = jsonApi.getRoads()
                .execute();
        if (response.body() == null || response.errorBody() != null) {
            System.out.println("Ошибка1");
            throw new IOException("Не удалось загрузить названия дорог");
        }
        for (TollRoadName element :
                response.body()) {
            System.out.println(element.toString());
        }
        return response.body();

    }

    @NonNull
    @Override
    public List<TollRoadPart> loadTollRoadParts() throws IOException {

        Response<List<TollRoadPart>> response = jsonApi.getParts()
                .execute();
        if (response.body() == null || response.errorBody() != null) {
            System.out.println("Ошибка2");
            throw new IOException("Не удалось загрузить участки дороги");
        }
        for (TollRoadPart element :
                response.body()) {
            System.out.println(element.toString());
        }
        return response.body();
    }

    @NonNull
    @Override
    public List<TollRoadPrice> loadTollRoadPrices() throws IOException {
        Response<List<TollRoadPrice>> response = jsonApi.getPrices()
                .execute();
        if (response.body() == null || response.errorBody() != null) {
            System.out.println("Ошибка3");
            throw new IOException("Не удалось загрузить цены");
        }
        return response.body();
    }
}
