package com.shrihari.updatelivelocation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.shrihari.updatelivelocation.database.Constants.Companion.TABLE_LOCATION
import com.shrihari.updatelivelocation.database.DbManager

class LocationHelper {
    var LOCATION_REFRESH_TIME = 5000 // 5 seconds. The Minimum Time to get location update
    var LOCATION_REFRESH_DISTANCE =
        0 // 0 meters. The Minimum Distance to be changed to get location update
    @SuppressLint("MissingPermission")
    fun startListeningUserLocation(context: Context, myListener: MyLocationListener) {
        val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        DbManager.createInstance(context)
        var dbManager: DbManager? = null
        dbManager = DbManager.getInstance()
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                myListener.onLocationChanged(location) // calling listener to inform that updated location is available
                dbManager.open()
                dbManager.insertLocationData(
                    "" + location.latitude,
                    "" + location.longitude,
                    "" + location.accuracy, TABLE_LOCATION
                )
                dbManager.close()
            }

            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        }
        mLocationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            LOCATION_REFRESH_TIME.toLong(),
            LOCATION_REFRESH_DISTANCE.toFloat(),
            locationListener
        )
    }
}

interface MyLocationListener {
    fun onLocationChanged(location: Location?)
}