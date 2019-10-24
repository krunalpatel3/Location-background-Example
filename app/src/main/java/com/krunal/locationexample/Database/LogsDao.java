package com.krunal.locationexample.Database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LogsDao {


    @Query("select * from Logs_tbl")
    LiveData<List<LogsEntity>> getLogsList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(LogsEntity logsEntity);


}
