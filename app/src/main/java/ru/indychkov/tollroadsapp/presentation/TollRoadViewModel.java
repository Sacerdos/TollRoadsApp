package ru.indychkov.tollroadsapp.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import ru.indychkov.tollroadsapp.data.database.TollRoadDatabase;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.interactor.DownloadTollRoadInteractor;
import ru.indychkov.tollroadsapp.interactor.LoadTollRoadDataException;
import ru.indychkov.tollroadsapp.interactor.SingleLiveEvent;
import ru.indychkov.tollroadsapp.interactor.TollRoadsInteractor;

public class TollRoadViewModel extends ViewModel {
    private final TollRoadDatabase db;
    private final Executor executor;
    private final TollRoadsInteractor tollRoadsInteractor;
    private final DownloadTollRoadInteractor downloadTollRoadInteractor;
    private final MutableLiveData<List<String>> roadNames = new MutableLiveData<>();
    private final MutableLiveData<List<TollRoadPart>> roadParts = new MutableLiveData<>();
    private final MutableLiveData<List<String>> roadPartsFrom = new MutableLiveData<>();
    private final MutableLiveData<List<String>> roadPartsTo = new MutableLiveData<>();
    private final SingleLiveEvent<String> errors = new SingleLiveEvent<>();

    public TollRoadViewModel(TollRoadDatabase db,
                             Executor executor,
                             TollRoadsInteractor tollRoadsInteractor,
                             DownloadTollRoadInteractor downloadTollRoadInteractor) {
        this.db = db;
        this.executor = executor;
        this.tollRoadsInteractor = tollRoadsInteractor;
        this.downloadTollRoadInteractor = downloadTollRoadInteractor;
        insertTollRoadsData();
    }

    void insertTollRoadsData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    downloadTollRoadInteractor.insertTollRoadsData();
                } catch (LoadTollRoadDataException e) {
                    errors.postValue("Ошибка при обновлении данных с сервера");
                }
            }
        });
    }

    void loadRoadNames() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                roadNames.postValue(tollRoadsInteractor.getRoadNames());

            }
        });
    }

    @NonNull
    LiveData<List<String>> getRoadNames() {
        return roadNames;
    }

    public MutableLiveData<List<String>> getRoadPartsFrom() {
        return roadPartsFrom;
    }

    public MutableLiveData<List<String>> getRoadPartsTo() {
        return roadPartsTo;
    }

    public SingleLiveEvent<String> getErrors() {
        return errors;
    }

    public void loadRoadPartsFrom(String selectedItem, boolean checked) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<TollRoadPart> roadParts = tollRoadsInteractor.getRoadPart(selectedItem);
                TollRoadViewModel.this.roadParts.postValue(roadParts);
                List<String> tempFrom = new ArrayList<>();
                List<String> tempTo = new ArrayList<>();
                for (TollRoadPart element :
                        roadParts) {
                    if(checked){
                        tempFrom.add(element.getKm_start()+"");
                        tempTo.add(element.getKm_end()+"");
                    }else{
                        tempTo.add(element.getKm_start()+"");
                        tempFrom.add(element.getKm_end()+"");
                    }
                }
                TollRoadViewModel.this.roadPartsFrom.postValue(tempFrom);
                TollRoadViewModel.this.roadPartsTo.postValue(tempTo);
            }
        });
    }


}
