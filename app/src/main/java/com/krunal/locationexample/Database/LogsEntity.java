package com.krunal.locationexample.Database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Logs_tbl")
public class LogsEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Log_Id")
    private int id;

    @ColumnInfo(name = "latitude")
    private Double latitude;

    @ColumnInfo(name = "longitude")
    private Double longitude;

    @ColumnInfo(name = "DateTime")
    private String DateTime;

    @ColumnInfo(name = "Location_Status")
    private String Location_Status;

    @Ignore
    public LogsEntity(int id,Double latitude, Double longitude, String DateTime, String Location_Status) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.DateTime = DateTime;
        this.Location_Status = Location_Status;
    }


    public LogsEntity(Double latitude, Double longitude, String DateTime, String Location_Status) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.DateTime = DateTime;
        this.Location_Status = Location_Status;
    }

    public String getLocation_Status() {
        return Location_Status;
    }

    public void setLocation_Status(String location_Status) {
        Location_Status = location_Status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
