package com.krunal.locationexample.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

@Database(entities = {LogsEntity.class},version = 2,exportSchema = false)
abstract class AppDatabase extends RoomDatabase {

    abstract LogsDao getLogsDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE [Logs_tbl] "
                    + " ADD COLUMN [Location_Status] VARCHAR (100)");

        }
    };

    private static AppDatabase buildDatabase(Context context){
        String dbname= "AppDatabase.db";
        return Room.databaseBuilder(context,AppDatabase.class,dbname)
                .addMigrations(MIGRATION_1_2).build();
    }

}
