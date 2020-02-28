package ru.indychkov.tollroadsapp.data;

import java.util.List;

import ru.indychkov.tollroadsapp.data.database.TollRoadDatabase;
import ru.indychkov.tollroadsapp.data.model.TollRoadName;
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
    public List<TollRoadPart> getRoadPart(String selectedRoad) {
        return db.tollRoadDAO().getPartsForRoad(selectedRoad);
    }
}
