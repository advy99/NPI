package com.npi_grupo4.guiaestudiantes

import android.app.Activity
import android.content.Context
import android.location.Location
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import java.lang.Float

class GestorPosicion {

    private var puedoAccederLoc = false
    private var marcadorPosActual: Marker? = null

    // actualizar la posicion de un mapa a la posicion actual
    fun actualizarPosActual(context: Context, activity: Activity, map: GoogleMap) {
        // pedimos permisos
        GestorPermisos.getLocationPermission(context, activity)

        var location = LocationServices.getFusedLocationProviderClient(context)

        var position : LatLng? = null

        // si tenemos permisos
        if (GestorPermisos.locationPermissionGranted()) {

            location.lastLocation.addOnSuccessListener { loc: Location? ->
                // si es capaz de acceder (podemos tener permiso pero no tener la ubicacion activada)
                if ( loc != null){

                    // eliminamos el marcador anterior en el mapa
                    marcadorPosActual?.remove()

                    // actualizamos la posicion
                    position =  LatLng(loc!!.latitude, loc!!.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLng(position))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0F))
                    puedoAccederLoc = true


                    // actualizamos el marcador del mapa
                    var marcador = MarkerOptions()
                    marcador.position(position!!)
                    marcador.title("Posición actual")
                    marcador.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    map.addMarker(marcador)

                } else {
                    puedoAccederLoc = false
                    Toast.makeText(activity, "Activa la ubicación. Centrando en Granada", Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    // rotar el mapa dada una rotacion
    fun rotarMapa(map: GoogleMap, rotacion: kotlin.Float = 0.0F) {
        var cam_pos = map.cameraPosition
        var cam_nueva_pos = CameraPosition.Builder()

        // copiamos los valores de la camara anterior, cambiando el bearing, la orientacion
        // a la dada
        cam_nueva_pos.bearing( rotacion )
        cam_nueva_pos.tilt(cam_pos.tilt)
        cam_nueva_pos.zoom(cam_pos.zoom)
        cam_nueva_pos.target(cam_pos.target)

        var cam = cam_nueva_pos.build()

        map.animateCamera(CameraUpdateFactory.newCameraPosition( cam ))
    }

    fun getPuedoAccederLoc() : Boolean{
        return puedoAccederLoc
    }

    fun posicionMasCercana(posiciones: ArrayList<LatLng>, context: Context, activity : Activity ): Int? {

        GestorPermisos.getLocationPermission(context, activity)
        var location = LocationServices.getFusedLocationProviderClient(context)

        var indice: Int? = null

        // si podemos acceder a la ubicacion
        if (GestorPermisos.locationPermissionGranted()) {
            location.lastLocation.addOnSuccessListener { loc: Location? ->

                if ( loc != null){
                    var position = LatLng(loc!!.latitude, loc!!.longitude)

                    var minimo = Float.POSITIVE_INFINITY
                    var resultado = FloatArray(3)

                    // recorremos la lista, y nos quedamos con el minimo
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