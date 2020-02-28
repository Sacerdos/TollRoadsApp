package ru.indychkov.tollroadsapp.interactor;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import ru.indychkov.tollroadsapp.data.model.TollRoadName;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;

public interface IDownloadTollRoadRepository {
    @NonNull
    List<TollRoadName> loadTollRoadNames() throws IOException;
    @NonNull
    List<TollRoadPart> loadTollRoadParts() throws IOException;
    @NonNull
    List<TollRoadPrice> loadTollRoadPrices() throws IOException;
}
