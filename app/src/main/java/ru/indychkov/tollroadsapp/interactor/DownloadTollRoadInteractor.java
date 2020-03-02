package ru.indychkov.tollroadsapp.interactor;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import ru.indychkov.tollroadsapp.data.database.TollRoadDatabase;
import ru.indychkov.tollroadsapp.data.model.TollRoadName;
import ru.indychkov.tollroadsapp.data.model.TollRoadPart;
import ru.indychkov.tollroadsapp.data.model.TollRoadPrice;

public class DownloadTollRoadInteractor {
    private final IDownloadTollRoadRepository downloadTollRoadRepository;
    private final TollRoadDatabase db;

    public DownloadTollRoadInteractor(IDownloadTollRoadRepository downloadTollRoadRepository,
                                      TollRoadDatabase db) {
        this.downloadTollRoadRepository = downloadTollRoadRepository;
        this.db = db;
    }

    public void insertTollRoadsData() throws LoadTollRoadDataException{
        try{

            new InsertDataDB(db, downloadTollRoadRepository.loadTollRoadNames(),
                    downloadTollRoadRepository.loadTollRoadParts(),
                    downloadTollRoadRepository.loadTollRoadPrices()).execute();

        } catch (IOException e){
            throw new LoadTollRoadDataException("Ошибка с обновлением БД", e);
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class InsertDataDB extends AsyncTask<Void, Void, Void> {
        TollRoadDatabase db;
        List<TollRoadName> names;
        List<TollRoadPart> parts;
        List<TollRoadPrice> prices;

        InsertDataDB(TollRoadDatabase db,
                     List<TollRoadName> names,
                     List<TollRoadPart> parts,
                     List<TollRoadPrice> prices) {

            this.db = db;
            this.names = names;
            this.parts = parts;
            this.prices = prices;
        }


        @Override
        protected Void doInBackground(Void... voids) {

            for (TollRoadName element :
                    names) {
                db.tollRoadDAO().addTollRoadName(element);
            }
            for (TollRoadPart element :
                    parts) {
                db.tollRoadDAO().addTollRoadPart(element);
            }
            for (TollRoadPrice element :
                    prices) {
                System.out.println("error" + element.toString());
                db.tollRoadDAO().addTollRoadPrice(element);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
