package ru.indychkov.tollroadsapp.interactor;

import java.util.List;

import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;

public interface ITollRoadRepository {
    List<String> getAllRoadsName();
    List<TollRoadPart> getAllRoadPart(String selectedRoad);

    List<TollRoadPart> getAllPartsFromMoscow(String selectedRoad);
    List<TollRoadPart> getAllPartsToMoscow(String selectedRoad);

    List<TollRoadPart> getFinalPath(String selectedRoad, boolean isFromMoscow, double sectionFrom, double sectionTo);

    TollRoadPrice getPrice(String part_name, int category);
}
