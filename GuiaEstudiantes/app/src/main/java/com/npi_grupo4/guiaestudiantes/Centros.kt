package com.npi_grupo4.guiaestudiantes

import android.Manifest
import android.accessibilityservice.AccessibilityService
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.maps.android.data.kml.KmlLayer


class Centros : Fragment() {

    var gestorPosicion = GestorPosicion()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        val pos = gestorPosicion.getPosicionActual(requireContext(), requireActivity())

        if ( pos != null ) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16.0F))

        } else {
            var position = LatLng(37.1886273, -3.5907775 )

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0F))
            Toast.makeText(activity, "Activa la ubicación. Centrando en Granada", Toast.LENGTH_LONG).show()

        }

        val kmlFile = KmlLayer(googleMap, R.raw.mapas_campus_ugr, requireActivity())
        kmlFile.addLayerToMap()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_centros, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        GestorPermisos.getLocationPermission(requireContext(), requireActivity())
        mapFragment?.getMapAsync(callback)
    }



}