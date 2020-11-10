package com.npi_grupo4.guiaestudiantes

import android.app.Activity
import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import java.lang.Float

class GestorPosicion {

    fun getPosicionActual(context: Context, activity : Activity) : LatLng? {
        GestorPermisos.getLocationPermission(context, activity)

        var location = LocationServices.getFusedLocationProviderClient(context)

        var position : LatLng? = null

        if (GestorPermisos.locationPermissionGranted()) {
            location.lastLocation.addOnSuccessListener { loc: Location? ->

                if ( loc != null){
                    position =  LatLng(loc!!.latitude, loc!!.longitude)

                }


            }
        }

        return position
    }


    fun posicionMasCercana(posiciones: ArrayList<LatLng>, context: Context, activity : Activity ): Int? {

        GestorPermisos.getLocationPermission(context, activity)
        var location = LocationServices.getFusedLocationProviderClient(context)

        var indice: Int? = null

        if (GestorPermisos.locationPermissionGranted()) {
            location.lastLocation.addOnSuccessListener { loc: Location? ->

                if ( loc != null){
                    var position = LatLng(loc!!.latitude, loc!!.longitude)

                    var minimo = Float.POSITIVE_INFINITY
                    var resultado = FloatArray(3)

                    for (pos in 0..posiciones.size-1) {
                        Location.distanceBetween(posiciones[pos].latitude, posiciones[pos].longitude, position.latitude, position.longitude, resultado)
                        if ( minimo > resultado[0]){
                            minimo = resultado[0]
                            indice = pos
                        }
                    }

                }
            }
        }

        return indice

    }

}