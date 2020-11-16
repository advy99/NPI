package com.npi_grupo4.guiaestudiantes

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object GestorPermisos {

    // Los permisos inicialmente no se han otorgado
    private var mLocationPermissionGranted = false
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    // Devuelve si los permisos han sido otorgados o no
    fun locationPermissionGranted() : Boolean {
        return mLocationPermissionGranted
    }

    // Solicita los permisos
    fun getLocationPermission(contexto: Context, actividad: Activity) {
        // Introducimos en un array los permisos que debemos solicitar
        val permissions = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)

        // Comprobamos si todos los permisos han sido otorgados
        if (ContextCompat.checkSelfPermission(contexto, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(contexto, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                // Todos los permisos han sido otorgados
                mLocationPermissionGranted = true
            } else {
                // Faltan permisos
                ActivityCompat.requestPermissions(actividad,permissions,LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            // Faltan permisos
            ActivityCompat.requestPermissions(actividad,permissions,LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    // Comprueba que no todos los permisos se ha otorgado correctamente
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mLocationPermissionGranted = false

        // Recorre todos los permisos y comprueba que se ha otorgado correctamente
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