package com.npi_grupo4.guiaestudiantes

import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.kml.KmlLayer


class Centros : Fragment(), LocationListener {

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

        mapa = googleMap

        gestorPosicion.actualizarPosActual(requireContext(), requireActivity(), googleMap)

        if ( !gestorPosicion.getPuedoAccederLoc() ) {
            var position = LatLng(37.1886273, -3.5907775 )

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0F))

        }

        val kmlFile = KmlLayer(googleMap, R.raw.mapas_campus_ugr, requireActivity())
        kmlFile.addLayerToMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opcion_brujula -> {
                brujula = !item.isChecked
                item.isChecked = brujula
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_mapas, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onLocationChanged(p0: Location?) {
        mapa?.let { gestorPosicion.actualizarPosActual(requireContext(), requireActivity(), it) }
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_centros) as SupportMapFragment?
        GestorPermisos.getLocationPermission(requireContext(), requireActivity())
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        var mapa: GoogleMap? = null
        var brujula = false
    }

}