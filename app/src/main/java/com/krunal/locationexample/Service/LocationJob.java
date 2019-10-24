package com.krunal.locationexample.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.krunal.locationexample.Database.LogsEntity;
import com.krunal.locationexample.Database.Repository;
import com.krunal.locationexample.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LocationJob extends JobService {

    private static final String TAG = LocationJob.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Service created");

    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "onStartJob");

        String str = "";
        GPSTracker gps = new GPSTracker(this);
        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            str ="Your Locion is - \nLat: " + latitude
                    + "\nLog: " + longitude;

//            Repository repository = new Repository(this);
//            repository.Insert(new LogsEntity(latitude,longitude,getEntryDateFormat(getCurruntDateTime())));

        }

        sendNotification("LocationJob","onStartJob: " + str,"",this);

        gps.stopUsingGPS();

        jobFinished(params,true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobFinished(params,true);
        return true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Service destroyed");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }


    public static void sendNotification(String title, String message, String mode, Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "default")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);

        if (mode.equalsIgnoreCase("Send PDF")) {
            notification.setOngoing(true);
        }

        notificationManager.notify(1, notification.build());
    }




}
