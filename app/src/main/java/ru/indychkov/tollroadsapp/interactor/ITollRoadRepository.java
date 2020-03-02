package ru.indychkov.tollroadsapp.interactor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.indychkov.tollroadsapp.data.model.TollRoadName;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;

public interface ITollRoadRepository {
    List<String> getAllRoadsName();
    List<TollRoadPart> getAllRoadPart(String selectedRoad);

    List<TollRoadPart> getAllPartsFromMoscow(String selectedRoad);
    List<TollRoadPart> getAllPartsToMoscow(String selectedRoad);

    List<TollRoadPart> getFinalPath(String selectedRoad, boolean isFromMoscow, double sectionFrom, double sectionTo);
}
