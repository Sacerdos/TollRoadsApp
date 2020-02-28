package ru.indychkov.tollroadsapp.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.indychkov.tollroadsapp.data.DownloadTollRoadRepository;
import ru.indychkov.tollroadsapp.data.TollRoadRepository;
import ru.indychkov.tollroadsapp.data.database.TollRoadDatabase;
import ru.indychkov.tollroadsapp.interactor.DownloadTollRoadInteractor;
import ru.indychkov.tollroadsapp.interactor.IDownloadTollRoadRepository;
import ru.indychkov.tollroadsapp.interactor.ITollRoadRepository;
import ru.indychkov.tollroadsapp.interactor.TollRoadsInteractor;

public class TollRoadViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context applicationContext;

    TollRoadViewModelFactory(@NonNull Context context) {
        applicationContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (TollRoadViewModel.class.equals(modelClass)) {
            TollRoadDatabase db = TollRoadDatabase.getInstance(applicationContext);
            ITollRoadRepository tollRoadRepository = new TollRoadRepository(db);
            Executor executor = Executors.newSingleThreadExecutor();
            TollRoadsInteractor tollRoadsInteractor = new TollRoadsInteractor(tollRoadRepository);
            IDownloadTollRoadRepository downloadTollRoadRepository = new DownloadTollRoadRepository();
            DownloadTollRoadInteractor downloadTollRoadInteractor = new DownloadTollRoadInteractor(downloadTollRoadRepository,
                    db);
            return (T) new TollRoadViewModel(
                    db,
                    executor,
                    tollRoadsInteractor,
                    downloadTollRoadInteractor);
        } else {
            return super.create(modelClass);
        }
    }
}
