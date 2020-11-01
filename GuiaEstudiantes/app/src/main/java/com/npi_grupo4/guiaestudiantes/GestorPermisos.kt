package com.npi_grupo4.guiaestudiantes

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object GestorPermisos {

    private var mLocationPermissionGranted = false
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    fun locationPermissionGranted() : Boolean {
        return mLocationPermissionGranted
    }

    fun getLocationPermission(contexto: Context, actividad: Activity) {
        val permissions = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ContextCompat.checkSelfPermission(contexto, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(contexto, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true
            } else {
                ActivityCompat.requestPermissions(actividad,permissions,LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            ActivityCompat.requestPermissions(actividad,permissions,LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    public fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mLocationPermissionGranted = false

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.size > 0){
                for (i in 0 until grantResults.size){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        mLocationPermissionGranted = false
                        return
                    }
                }
                mLocationPermissionGranted = true
            }
        }
    }
}