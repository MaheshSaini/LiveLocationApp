package com.shrihari.updatelivelocation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.shrihari.updatelivelocation.LocationHelper
import com.shrihari.updatelivelocation.MyLocationListener
import com.shrihari.updatelivelocation.R

class LocationService : Service() {
    private val NOTIFICATION_CHANNEL_ID = "my_notification_location"
    override fun onCreate() {
        super.onCreate()
        isServiceStarted = true
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_launcher_background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = NOTIFICATION_CHANNEL_ID
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1, builder.build())
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        LocationHelper().startListeningUserLocation(
            this, object : MyLocationListener {
                override fun onLocationChanged(location: Location?) {
                    mLocation = location
                    Log.d(
                        "MY LOCATION:",
                        "onLocationChanged: MY Accuracy: " + location?.accuracy
                    )
                    Log.d(
                        "MY LOCATION:",
                        "onLocationChanged:MY Latitude: " + location?.latitude
                    )
                    Log.d(
                        "MY LOCATION:",
                        "onLocationChanged:MY Longitude: " + location?.longitude
                    )
                }
            })
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false

    }

    companion object {
        var mLocation: Location? = null
        var isServiceStarted = false
    }
}