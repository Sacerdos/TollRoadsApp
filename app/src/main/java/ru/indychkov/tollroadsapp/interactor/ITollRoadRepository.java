package ru.indychkov.tollroadsapp.interactor;

import java.util.List;

import ru.indychkov.tollroadsapp.data.model.TollRoadName;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;

public interface ITollRoadRepository {
    List<String> getAllRoadsName();
    List<TollRoadPart> getRoadPart(String selectedRoad);
}
