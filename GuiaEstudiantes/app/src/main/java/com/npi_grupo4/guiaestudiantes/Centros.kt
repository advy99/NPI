package com.npi_grupo4.guiaestudiantes

<<<<<<< HEAD
import android.net.http.SslError
=======
import android.Manifest
import android.accessibilityservice.AccessibilityService
import android.content.pm.PackageManager
import android.location.Location
>>>>>>> main
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
=======
import android.widget.Toast
import androidx.core.app.ActivityCompat
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

>>>>>>> main

class Centros : Fragment() {
    // TODO: Rename and change types of parameters

<<<<<<< HEAD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
=======

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


        GestorPermisos.getLocationPermission(requireContext(), requireActivity())
        var location = LocationServices.getFusedLocationProviderClient(requireContext())

        var position = LatLng(37.1886273, -3.5907775 )

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0F))

        if (GestorPermisos.locationPermissionGranted()) {
            location.lastLocation.addOnSuccessListener { loc: Location? ->

                if ( loc != null){
                    var position = LatLng(loc!!.latitude, loc!!.longitude)

                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0F))
                } else {
                    Toast.makeText(requireActivity(), "Ativa la ubicacion. Centrando en Granada", Toast.LENGTH_LONG).show()

                }


            }
        }


        val kmlFile = KmlLayer(googleMap, R.raw.mapas_campus_ugr, requireActivity())
        kmlFile.addLayerToMap()
        //Toast.makeText(requireActivity(), "HOLASF", Toast.LENGTH_LONG).show()


//        val ETSIIT = LatLng(37.197055556, -3.624111111)
//        googleMap.addMarker(MarkerOptions().position(ETSIIT).title("Marker in ETSIIT"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ETSIIT))
>>>>>>> main
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_centros, container, false)
        val webview = view.findViewById(R.id.mapa_centros) as WebView
        webview.getSettings().setJavaScriptEnabled(true)

<<<<<<< HEAD
        webview.settings.javaScriptEnabled = true
        webview.clearCache(true)
        webview.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String?) {
                if (view.title == "") view.reload()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                webview.loadUrl("https://www.google.com/maps/d/embed?mid=1k7hR-nnrQivEs6-5LWRwE9aLRDMvJjaT")
                return true
            }

            override fun onReceivedSslError(
                    view: WebView,
                    handler: SslErrorHandler,
                    error: SslError
            ) {
                println("before handler")
                handler.proceed()
                println("after handler")
            }
        }

        webview.loadUrl("https://www.google.com/maps/d/embed?mid=1k7hR-nnrQivEs6-5LWRwE9aLRDMvJjaT")

        return view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Centros().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
=======
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        GestorPermisos.getLocationPermission(requireContext(), requireActivity())
        mapFragment?.getMapAsync(callback)
    }



>>>>>>> main
}