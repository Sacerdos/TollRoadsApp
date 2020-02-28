package ru.indychkov.tollroadsapp.interactor;

import java.util.List;

import ru.indychkov.tollroadsapp.data.model.TollRoadName;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;

public class TollRoadsInteractor  {
    private final ITollRoadRepository tollRoadRepository;

    public TollRoadsInteractor(ITollRoadRepository tollRoadRepository) {
        this.tollRoadRepository = tollRoadRepository;
    }

    public List<String> getRoadNames(){
        return tollRoadRepository.getAllRoadsName();
    }

    public List<TollRoadPart> getRoadPart(String selectedRoad){
        return tollRoadRepository.getRoadPart(selectedRoad);
    }
}
