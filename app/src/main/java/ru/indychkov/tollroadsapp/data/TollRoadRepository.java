package ru.indychkov.tollroadsapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.indychkov.tollroadsapp.data.database.TollRoadDatabase;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.interactor.ITollRoadRepository;

public class TollRoadRepository implements ITollRoadRepository {
    TollRoadDatabase db;

    public TollRoadRepository(TollRoadDatabase db) {
        this.db = db;
    }


    @Override
    public List<String> getAllRoadsName() {
        return db.tollRoadDAO().getAllTollRoadNames();
    }

    @Override
    public List<TollRoadPart> getAllRoadPart(String selectedRoad) {
        return db.tollRoadDAO().getAllPartsForRoad(selectedRoad);
    }

    @Override
    public List<TollRoadPart> getAllPartsFromMoscow(String selectedRoad) {
        return db.tollRoadDAO().getAllPartsFromMoscow(selectedRoad);
    }

    @Override
    public List<TollRoadPart> getAllPartsToMoscow(String selectedRoad) {
        return db.tollRoadDAO().getAllPartsToMoscow(selectedRoad);
    }

    @Override
    public List<TollRoadPart> getFinalPath(String selectedRoad, boolean isFromMoscow, double sectionFrom, double sectionTo) {
        return db.tollRoadDAO().getFinalPath(selectedRoad, sectionFrom, sectionTo);
    }
}
