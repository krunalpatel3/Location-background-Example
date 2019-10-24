package com.krunal.locationexample.Activity;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.krunal.locationexample.Database.LogsEntity;
import com.krunal.locationexample.Database.Repository;
import com.krunal.locationexample.Service.GPSTracker;
import com.krunal.locationexample.Service.LocationWorker;
import com.krunal.locationexample.Utility.ClsCheckLocation;

import com.krunal.locationexample.R;
import com.krunal.locationexample.Utility.ClsGlobal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.krunal.locationexample.Utility.ClsGlobal.getCurruntDateTime;
import static com.krunal.locationexample.Utility.ClsGlobal.getEntryDateFormat;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button check, Logs;
    private final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        check = findViewById(R.id.check);
        Logs = findViewById(R.id.Logs);

        requestLocationPermission();

        Log.e("Hour", String.valueOf(ClsGlobal.getCurrentHour()));

        int getCurrentHour = ClsGlobal.getCurrentHour();


        Log.e("isWorkScheduled",
                String.valueOf(ClsGlobal.isWorkScheduled(ClsGlobal.packageName.concat(".Location"))));


        check.setOnClickListener(v -> {

            // To Check if GPS is on or not.
            if (CheckGpsStatus()) {
                ClsCheckLocation.checkLocationServiceNew(MainActivity.this);
            }

            //  JobScheduler do not work properly.
//            JobInfo myJob = null;
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    Log.e("JobInfo", "Build.VERSION_CODES.N inside");
//                    myJob = new JobInfo.Builder(105, new ComponentName(this, LocationJob.class))
//                            .setPeriodic(TimeUnit.HOURS.toMillis(1), TimeUnit.HOURS.toMillis(1))
//                            .setPersisted(true)
//                            .setRequiresCharging(false) // we don't care if the device is charging or not
//                            .build();
//
//                } else {
//                    Log.e("JobInfo", "Build.VERSION_CODES.N else");
//                    myJob = new JobInfo.Builder(105, new ComponentName(this, LocationJob.class))
//                            .setPeriodic(TimeUnit.HOURS.toMillis(1))
//                            .setPersisted(true)
//                            .setRequiresCharging(false) // we don't care if the device is charging or not
//                            .build();
//                }
//
//                JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//                jobScheduler.schedule(myJob);
//
//
//            }

//            String str = "";
//            GPSTracker gps = new GPSTracker(this);
//            // check if GPS enabled
//            if (gps.canGetLocation()) {
//
//                double latitude = gps.getLatitude();
//                double longitude = gps.getLongitude();
//
//                str ="Your Locion is - \nLat: " + latitude
//                        + "\nLog: " + longitude;
//
//              Toast.makeText(this,str,Toast.LENGTH_LONG).show();
//
//            }
//

            /**
             * This is to get location from background every haft an Hour in day time.
             * In night time after 8 pm we will get Location every 2 Hour.
             */

//            if (getCurrentHour >= 8 && getCurrentHour <= 20) {
//                Toast.makeText(this,"30 min",Toast.LENGTH_LONG).show();
//                Log.e("getCurrentHour", "30 min");
//
//                if (!ClsGlobal.isWorkScheduled(ClsGlobal.packageName.concat(".Location.30min"))) {
//                    ClsGlobal.ScheduleWorker(ClsGlobal.packageName.concat(".Location.30min"),30);
//                }
//
//
//            } else {
//                Toast.makeText(this,"120 min",Toast.LENGTH_LONG).show();
//                Log.e("getCurrentHour", "120 min");
//
//                if (!ClsGlobal.isWorkScheduled(ClsGlobal.packageName.concat(".Location.120min"))) {
//                    ClsGlobal.ScheduleWorker(ClsGlobal.packageName.concat(".Location.120min"),120);
//                }
//
//            }


            /**
             * This is to get location from background every Hour.
             *
             */
            ClsGlobal.ScheduleWorker(ClsGlobal.packageName.concat(".Location.1Hour"),60);




        });

        Logs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogsActivity.class);
            startActivity(intent);
        });


    }


    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }


    public boolean CheckGpsStatus() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


}
