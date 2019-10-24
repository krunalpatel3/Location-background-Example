package com.krunal.locationexample.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.krunal.locationexample.Database.LogsEntity;
import com.krunal.locationexample.Database.Repository;

import java.util.List;

public class LogsActivityViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<LogsEntity>> mlist;

    public LogsActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mlist = mRepository.getLogsList();
    }

    public LiveData<List<LogsEntity>> getpetslist(){
        return mlist;
    }


}
