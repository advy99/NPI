package com.npi_grupo4.guiaestudiantes

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.kml.KmlLayer


class Centros : Fragment() {

    private var mLocationPermissionGranted = false
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

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

//        var lm: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
//        val location: Location? = lm!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//        val longitude: Double = location.getLongitude()
//        val latitude: Double = location.getLatitude()


//        val location = LatLng(37.197055556, -3.624111111)
//
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0F))

        val kmlFile = KmlLayer(googleMap, R.raw.mapas_campus_ugr, requireActivity())
        kmlFile.addLayerToMap()
        Toast.makeText(requireActivity(), "HOLASF", Toast.LENGTH_LONG).show()


//        val ETSIIT = LatLng(37.197055556, -3.624111111)
//        googleMap.addMarker(MarkerOptions().position(ETSIIT).title("Marker in ETSIIT"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ETSIIT))
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
        getLocationPermission();
        mapFragment?.getMapAsync(callback)
    }

    private fun getLocationPermission() {
        val permissions = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true
            } else {
                ActivityCompat.requestPermissions(requireActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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