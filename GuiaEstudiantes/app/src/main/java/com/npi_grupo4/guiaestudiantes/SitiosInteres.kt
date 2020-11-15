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


class SitiosInteres : Fragment(), LocationListener {

    // Guardamos una instancia de la clase GestorPosicion que nos permite hacer una gestion externa
    // de la ubicación
    var gestorPosicion = GestorPosicion()

    // Esta función se ejecuta una vez el mapa ha sido cargado y esta listo para usarse
    private val callback = OnMapReadyCallback { googleMap ->
        // Creamos un mapa
        mapa = googleMap

        // Actualizamos la posición actual a través del gestor de posición
        gestorPosicion.actualizarPosActual(requireContext(), requireActivity(), googleMap)

        // Si el metodo anterior falla, el gestor de posición activa un flag que marca si se ha
        // podido acceder a la ubicación o no, por esto comprobamos que si no se ha podido acceder
        // establecemos una ubicación por defecto centrada en la ciudad de granada y centramos
        // el mapa en ella
        if ( !gestorPosicion.getPuedoAccederLoc() ) {
            var position = LatLng(37.1886273, -3.5907775 )

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0F))
        }

        // Finalmente cargamos el archivo kml que contiene los datos del mapa
        val kmlFile = KmlLayer(googleMap, R.raw.sitios_interes, requireActivity())
        kmlFile.addLayerToMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Activa la opción para que este fragment tenga un menu de opciones realizando una
        // llamada a onCreateOptionsMenu(Menu, MenuInflater)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Activa y desactiva el item brujula
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
        // Crea el menu de opciones para activar y desactivar la brujula
        inflater.inflate(R.menu.menu_mapas, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onLocationChanged(p0: Location?) {
        // Actualiza la ubiciación cuando esta cambia llamando al gestor de posición
        mapa?.let { gestorPosicion.actualizarPosActual(requireContext(), requireActivity(), it) }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Carga el fragment de google map
        return inflater.inflate(R.layout.fragment_sitios_interes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_sitios_interes) as SupportMapFragment?
        // Solicita los permisos de ubicación
        GestorPermisos.getLocationPermission(requireContext(), requireActivity())
        // Activa la rutina que inicializa el mapa
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        var mapa: GoogleMap? = null
        var brujula = false
    }

}