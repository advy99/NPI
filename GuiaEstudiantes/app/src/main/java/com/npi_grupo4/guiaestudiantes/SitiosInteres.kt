package com.npi_grupo4.guiaestudiantes

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.kml.KmlLayer

class SitiosInteres : Fragment() {

    var gestorPosicion = GestorPosicion()

    private lateinit var  mapa : GoogleMap


    private val callback_update = LocationSource.OnLocationChangedListener() {
        gestorPosicion.actualizarPosActual(requireContext(), requireActivity(), mapa)
    }



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

        mapa = googleMap

        gestorPosicion.actualizarPosActual(requireContext(), requireActivity(), googleMap)

        if ( !gestorPosicion.getPuedoAccederLoc() ) {
            var position = LatLng(37.1886273, -3.5907775 )

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0F))

        }


        val kmlFile = KmlLayer(googleMap, R.raw.sitios_interes, requireActivity())
        kmlFile.addLayerToMap()
        //Toast.makeText(requireActivity(), "HOLASF", Toast.LENGTH_LONG).show()


//        val ETSIIT = LatLng(37.197055556, -3.624111111)
//        googleMap.addMarker(MarkerOptions().position(ETSIIT).title("Marker in ETSIIT"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ETSIIT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_sitios_interes) as SupportMapFragment?
        GestorPermisos.getLocationPermission(requireContext(), requireActivity())
        mapFragment?.getMapAsync(callback)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sitios_interes, container, false)
    }


}