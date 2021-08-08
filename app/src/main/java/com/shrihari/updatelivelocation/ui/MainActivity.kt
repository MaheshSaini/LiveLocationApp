package com.shrihari.updatelivelocation.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shrihari.updatelivelocation.service.LocationService
import com.shrihari.updatelivelocation.R
import com.shrihari.updatelivelocation.database.Constants.Companion.ACCURACY
import com.shrihari.updatelivelocation.database.Constants.Companion.END_TIME
import com.shrihari.updatelivelocation.database.Constants.Companion.LATITUDE
import com.shrihari.updatelivelocation.database.Constants.Companion.LONGITUDE
import com.shrihari.updatelivelocation.database.Constants.Companion.START_TIME
import com.shrihari.updatelivelocation.database.Constants.Companion.TABLE_LOCATION
import com.shrihari.updatelivelocation.database.DbManager
import com.shrihari.updatelivelocation.utils.SharedPreferencesUtil
import com.shrihari.updatelivelocation.utils.Utils.Companion.getDatetime
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonStartService = findViewById<Button>(R.id.buttonStartService)
        val buttonStopService = findViewById<Button>(R.id.buttonStopService)
        val buttonExportData = findViewById<Button>(R.id.buttonExportData)
        val txtShowData = findViewById<TextView>(R.id.txtShowData)
        DbManager.createInstance(this)
        val dbManager = DbManager.getInstance()
        SharedPreferencesUtil.deletePreferences(this, START_TIME)
        SharedPreferencesUtil.deletePreferences(this, END_TIME)
        buttonStartService.setOnClickListener {
            SharedPreferencesUtil.savePreferences(this, START_TIME, getDatetime())
            // delete from table
            dbManager.open()
            dbManager.DeleteCases(
                "delete from "
                        + TABLE_LOCATION
            )
            dbManager.close()
            ContextCompat.startForegroundService(this, Intent(this, LocationService::class.java))

        }

        buttonStopService.setOnClickListener {
            SharedPreferencesUtil.savePreferences(this, END_TIME, getDatetime())
            stopService(Intent(this, LocationService::class.java))
        }
        buttonExportData.setOnClickListener {
            txtShowData.text = ""
            Toast.makeText(this, "Get data", Toast.LENGTH_LONG).show()
            val json = JSONObject()
            json.put("trip_id", "1")
            json.put(START_TIME, SharedPreferencesUtil.getPreferences(this, START_TIME, ""))
            json.put(END_TIME, SharedPreferencesUtil.getPreferences(this, END_TIME, ""))
            val array = JSONArray()
            val item = JSONObject()
            dbManager.open()
            val cur = dbManager.selectQuery("select * from  " + TABLE_LOCATION + "")
            if (cur.count > 0) while (!cur.isAfterLast) {
                if (cur != null && !cur.isClosed) {
                    item.put(LATITUDE, cur.getString(1))
                    item.put(LONGITUDE, cur.getString(2))
                    item.put(ACCURACY, cur.getString(3))
                    array.put(item)
                    json.put("locations", array)
                }
                cur.moveToNext()
            }
            cur.close()
            dbManager.close()
            txtShowData.text = "" + json.toString()
            Log.v("OUTPUT:", "" + json.toString())
        }

        if (!checkPermission()) {
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val result1 = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1
        )
    }
}