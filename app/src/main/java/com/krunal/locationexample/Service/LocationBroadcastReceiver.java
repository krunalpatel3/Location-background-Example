package com.krunal.locationexample.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.krunal.locationexample.Database.LogsEntity;
import com.krunal.locationexample.Database.Repository;
import com.krunal.locationexample.R;
import com.krunal.locationexample.Utility.ClsGlobal;

import static com.krunal.locationexample.Utility.ClsGlobal.getCurruntDateTime;
import static com.krunal.locationexample.Utility.ClsGlobal.getEntryDateFormat;

public class LocationBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Receiver", "onReceive call");

        String str = "";
        String LocationStatus = "";
        double latitude = 0.0;
        double longitude = 0.0;

        if (ClsGlobal.checkPermission(context)) {
            LocationStatus = LocationStatus.concat("Location Permission Granted ");
        } else {
            LocationStatus = LocationStatus.concat("Location Permission Not Granted ");
        }

        GPSTracker gps = new GPSTracker(context);
        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            str = "Your Locion is - \nLat: " + latitude
                    + "\nLog: " + longitude;

            LocationStatus = LocationStatus.concat("and Location is Enable. ");


            sendNotification("LocationJob", "onStartJob: " + str, "", context);
        } else {
            LocationStatus = LocationStatus.concat("and Location is Not Enable. ");
        }

        Log.e("LocationStatus", LocationStatus);

        Repository repository = new Repository(context);
        repository.Insert(new LogsEntity(latitude, longitude,
                getEntryDateFormat(getCurruntDateTime()), LocationStatus));


        gps.stopUsingGPS();

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
