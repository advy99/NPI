package com.npi_grupo4.guiaestudiantes

import android.app.Activity
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Tasks.await
import java.lang.Float

class GestorPosicion {

    private var puedoAccederLoc = false

    fun actualizarPosActual(context: Context, activity : Activity, map: GoogleMap) {
        GestorPermisos.getLocationPermission(context, activity)

        var location = LocationServices.getFusedLocationProviderClient(context)

        var position : LatLng? = null


        if (GestorPermisos.locationPermissionGranted()) {

            location.lastLocation.addOnSuccessListener { loc: Location? ->

                if ( loc != null){
                    position =  LatLng(loc!!.latitude, loc!!.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLng(position))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0F))
                    puedoAccederLoc = true


                } else {
                    puedoAccederLoc = false
                    Toast.makeText(activity, "Activa la ubicación. Centrando en Granada", Toast.LENGTH_LONG).show()

                }

            }
        }
    }

    fun getPuedoAccederLoc() : Boolean{
        return puedoAccederLoc
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