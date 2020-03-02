package ru.indychkov.tollroadsapp.interactor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;

public class TollRoadsInteractor  {
    private final ITollRoadRepository tollRoadRepository;

    public TollRoadsInteractor(ITollRoadRepository tollRoadRepository) {
        this.tollRoadRepository = tollRoadRepository;
    }

    public List<String> getRoadNames(){
        return tollRoadRepository.getAllRoadsName();
    }

    public List<TollRoadPart> getAllRoadPart(String selectedRoad){
        return tollRoadRepository.getAllRoadPart(selectedRoad);
    }

    public List<TollRoadPart> getAllPartsFromMoscow(String selectedRoad) {
        return tollRoadRepository.getAllPartsFromMoscow(selectedRoad);
    }

    public List<TollRoadPart> getAllPartsToMoscow(String selectedRoad) {
        return tollRoadRepository.getAllPartsToMoscow(selectedRoad);
    }

    public List<TollRoadPart> getFinalPath(String selectedRoad, boolean isFromMoscow, double sectionFrom, double sectionTo) {
        return tollRoadRepository.getFinalPath(selectedRoad, isFromMoscow, sectionFrom, sectionTo);
    }

    public TollRoadPrice getPrice(String part_name, int category) {
        return tollRoadRepository.getPrice(part_name, category);
    }
}
