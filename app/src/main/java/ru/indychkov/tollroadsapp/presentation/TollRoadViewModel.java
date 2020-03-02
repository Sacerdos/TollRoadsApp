package ru.indychkov.tollroadsapp.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import ru.indychkov.tollroadsapp.data.database.TollRoadDatabase;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;
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
    private final MutableLiveData<List<TollRoadPart>> allRoadParts = new MutableLiveData<>();
    private final MutableLiveData<List<TollRoadPrice>> prices = new MutableLiveData<>();
    private final MutableLiveData<List<String>> roadPartsFrom = new MutableLiveData<>();
    private final MutableLiveData<List<String>> roadPartsTo = new MutableLiveData<>();
    private int category;
    private boolean hasAvtodor;
    private boolean isNight;
    private final MutableLiveData<Double> sectionFrom = new MutableLiveData<>();
    private final MutableLiveData<Double> sectionTo = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isOut = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isIn = new MutableLiveData<>();
    private String tempNameFrom;
    private String tempNameTo;
    private boolean isFromMoscow;
    private final MutableLiveData<List<TollRoadPart>> finalPath = new MutableLiveData<>();
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
        category = 1;
        hasAvtodor = false;
        isNight = false;
        isFromMoscow = true;
/*        sectionFrom.postValue(0.0);
        sectionTo.postValue(0.0);
        isIn.postValue(false);
        isOut.postValue(false);*/
    }

    void insertTollRoadsData() {
        executor.execute(() -> {
            try {
                downloadTollRoadInteractor.insertTollRoadsData();
                loadRoadNames();

            } catch (LoadTollRoadDataException e) {
                errors.postValue("Ошибка при обновлении данных с сервера");
            }
        });
    }

    void loadRoadNames() {
        executor.execute(() -> roadNames.postValue(tollRoadsInteractor.getRoadNames()));
    }

    @NonNull
    LiveData<List<String>> getRoadNames() {
        return roadNames;
    }

    @NonNull
    LiveData<List<TollRoadPrice>> getPrices() {
        return prices;
    }

    @NonNull
    LiveData<Double> getSectionFrom() {
        return sectionFrom;
    }

    @NonNull
    LiveData<Double> getSectionTo() {
        return sectionTo;
    }

    public MutableLiveData<List<String>> getRoadPartsFrom() {
        return roadPartsFrom;
    }

    public MutableLiveData<List<String>> getRoadPartsTo() {
        return roadPartsTo;
    }

    @NonNull
    public MutableLiveData<List<TollRoadPart>> getFinalPath() {
        return finalPath;
    }

    public SingleLiveEvent<String> getErrors() {
        return errors;
    }

    public void loadFromRoadParts(String selectedRoad) {
        executor.execute(() -> {
            List<TollRoadPart> roadPart;
            if (isFromMoscow) {
                roadPart = tollRoadsInteractor.getAllPartsFromMoscow(selectedRoad);
            } else {
                roadPart = tollRoadsInteractor.getAllPartsToMoscow(selectedRoad);
            }
            TollRoadViewModel.this.allRoadParts.postValue(roadPart);
            List<String> tempFrom = new ArrayList<>();
            List<String> tempTo = new ArrayList<>();
            for (TollRoadPart element :
                    roadPart) {
                if (isFromMoscow) {


                    tempFrom.add(element.getKm_start() + " km " + (element.isIn() ? "in" : "") + (element.isOut() ? "out" : ""));
                } else {
                    tempFrom.add(element.getKm_end() + " km " + (element.isIn() ? "in" : "") + (element.isOut() ? "out" : ""));
                }
            }
            TollRoadViewModel.this.roadPartsFrom.postValue(tempFrom);
            roadPartsTo.postValue(tempTo);
        });
    }

    public void loadToRoadParts(String selectedRoad) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<String> roadToParts = new ArrayList<>();
                if (allRoadParts.getValue() != null) {
                    if (isFromMoscow) {
                        for (TollRoadPart element :
                                allRoadParts.getValue()) {
                            if ((element.getSection_order() > sectionFrom.getValue() || element.getPart_name().equals(tempNameFrom)) &&
                                    ((!element.isIn()) || element.getPart_name().equals(tempNameFrom))) {
                                roadToParts.add(element.getKm_end() + " km " + (element.isOut() ? "out" : ""));
                            }

                        }
                    } else {
                        for (TollRoadPart element :
                                allRoadParts.getValue()) {
                            if ((element.getSection_order() < sectionFrom.getValue() || element.getPart_name().equals(tempNameFrom)) &&
                                    (!element.isIn() || element.getPart_name().equals(tempNameFrom))) {
                                roadToParts.add(element.getKm_start() + " km " + (element.isOut() ? "out" : ""));
                            }

                        }
                    }
                    roadPartsTo.postValue(roadToParts);
                }


            }
        });
    }

    public void loadFinalPath(String selectedRoad) {
        executor.execute(() -> {
            List<TollRoadPart> finalData = new ArrayList<>();
            List<TollRoadPart> data = tollRoadsInteractor.getFinalPath(selectedRoad, isFromMoscow,
                    Math.min(sectionFrom.getValue(), sectionTo.getValue()), Math.max(sectionFrom.getValue(), sectionTo.getValue()));
            for (TollRoadPart element :
                    data) {

                if (isFromMoscow) {
                    if (element.getSection_order() == Math.min(sectionFrom.getValue(), sectionTo.getValue())) {
                        if (tempNameFrom.equals(element.getPart_name())) {
                            finalData.add(element);
                        }
                    } else {
                        if (element.getSection_order() < Math.max(sectionFrom.getValue(), sectionTo.getValue())) {
                            if (!element.isIn() && !element.isOut()) {
                                finalData.add(element);
                            }
                        }
                        if (element.getSection_order() == Math.max(sectionFrom.getValue(), sectionTo.getValue())) {
                            if (!element.isIn()) {
                                if (tempNameTo.equals(element.getPart_name())) {
                                    finalData.add(element);
                                }
                            }
                        }
                    }

                } else {
                    if (element.getSection_order() == Math.max(sectionFrom.getValue(), sectionTo.getValue())) {
                        if (tempNameFrom.equals(element.getPart_name())) {
                            finalData.add(element);
                        }
                    } else {
                        if (element.getSection_order() > Math.min(sectionFrom.getValue(), sectionTo.getValue())) {
                            if (!element.isIn() && !element.isOut()) {
                                finalData.add(element);
                            }
                        }
                        if (element.getSection_order() == Math.min(sectionFrom.getValue(), sectionTo.getValue())) {
                            if (tempNameTo.equals(element.getPart_name())) {
                                finalData.add(element);
                            }
                        }
                    }

                }

            }
            if (!isFromMoscow) {
                Collections.reverse(finalData);
            }
            finalPath.postValue(finalData);
        });

    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setHasAvtodor(boolean hasAvtodor) {
        this.hasAvtodor = hasAvtodor;
    }

    public void setNight(boolean night) {
        isNight = night;
    }

    public boolean isHasAvtodor() {
        return hasAvtodor;
    }

    public boolean isNight() {
        return isNight;
    }

    public void setIsFromMoscow(boolean isFromMoscow) {
        this.isFromMoscow = isFromMoscow;
    }

    public void setFromSection(String stringFrom, int position) {
        String[] data = stringFrom.split(" ");
        int km = Integer.valueOf(data[0]);
        boolean isInSelected = stringFrom.contains("in");
        boolean isOutSelected = stringFrom.contains("out");
        for (int i = 0; i < allRoadParts.getValue().size(); i++) {
            if ((isFromMoscow && allRoadParts.getValue().get(i).getKm_start() == km) ||
                    (!isFromMoscow && allRoadParts.getValue().get(i).getKm_end() == km)) {
                if (allRoadParts.getValue().get(i).isIn() == isInSelected && !isOutSelected) {
                    this.isIn.postValue(isInSelected);
                    this.sectionFrom.postValue(allRoadParts.getValue().get(i).getSection_order());
                    tempNameFrom = allRoadParts.getValue().get(i).getPart_name();

                    break;
                } else {
                    if (allRoadParts.getValue().get(i).isOut() == isOutSelected) {
                        this.isIn.postValue(isOutSelected);
                        this.sectionFrom.postValue(allRoadParts.getValue().get(i).getSection_order());
                        tempNameFrom = allRoadParts.getValue().get(i).getPart_name();

                        break;
                    }
                }
            }

        }
    }

    public void setToSection(String stringFrom, int position) {
        String[] data = stringFrom.split(" ");
        int km = Integer.valueOf(data[0]);

        boolean isOutSelected = stringFrom.contains("out");
        for (int i = 0; i < allRoadParts.getValue().size(); i++) {
            if ((isFromMoscow && allRoadParts.getValue().get(i).getKm_end() == km) ||
                    (!isFromMoscow && allRoadParts.getValue().get(i).getKm_start() == km)) {
                if (allRoadParts.getValue().get(i).isOut() == isOutSelected) {
                    this.isIn.postValue(isOutSelected);
                    this.sectionTo.postValue(allRoadParts.getValue().get(i).getSection_order());
                    tempNameTo = allRoadParts.getValue().get(i).getPart_name();

                    break;
                }
            }
        }
    }

    public void loadPrices() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<TollRoadPrice> price = new ArrayList<>();
                for (TollRoadPart element :
                        finalPath.getValue()) {
                    price.add(tollRoadsInteractor.getPrice(element.getPart_name(), category));
                }
                prices.postValue(price);
            }
        });


    }


}
