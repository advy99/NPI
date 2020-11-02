package com.npi_grupo4.guiaestudiantes

import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_bibliotecas.view.*
import kotlinx.android.synthetic.main.fragment_pagina_inicio.*
import java.lang.Float

/**
 * A simple [Fragment] subclass.
 * Use the [Bibliotecas.newInstance] factory method to
 * create an instance of this fragment.
 */
class Bibliotecas : Fragment() {



    private var posiciones = ArrayList<LatLng>()
    private var informacion = ArrayList<ArrayList<String>>()

    private var indice = 0

    private lateinit var texto_centro: TextView
    private lateinit var texto_direccion: TextView
    private lateinit var texto_prestamos: TextView
    private lateinit var texto_horario: TextView
    private lateinit var catalogo: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)



        var infoETSIIT = ArrayList<String>()
        infoETSIIT.add("E.T.S.I.I.T")
        infoETSIIT.add("C/ Periodista Daniel Saucedo Aranda s/n")
        infoETSIIT.add("bibcirinformatica@ugr.es")
        infoETSIIT.add("8:30/9:00 h. a 20:30/21:00 h")

        var infoBellas = ArrayList<String>()
        infoBellas.add("Facultad de Bellas Artes")
        infoBellas.add("Avenida de Andalucía, s/n")
        infoBellas.add("mbolivar@ugr.es")
        infoBellas.add("8:30/9:00 h. a 20:30/21:00 h")

        var infoEducacion = ArrayList<String>()
        infoEducacion.add("Facultad de Educacion")
        infoEducacion.add("Campus Universitario de Cartuja")
        infoEducacion.add("bibgeseducacion@ugr.es")
        infoEducacion.add("8:30/9:00 h. a 20:30/21:00 h")

        var infoFarmacia = ArrayList<String>()
        infoFarmacia.add("Facultad de Farmacia")
        infoFarmacia.add("Campus Universitario de Cartuja")
        infoFarmacia.add("laguaza@ugr.es")
        infoFarmacia.add("8:30/9:00 h. a 20:30/21:00 h")

        var infoMedicina = ArrayList<String>()
        infoMedicina.add("Facultad de Medicina")
        infoMedicina.add("Parque Tecnológico de CC. de la Salud")
        infoMedicina.add("bibcirbiosanitaria@ugr.es")
        infoMedicina.add("8:30/9:00 h. a 20:30/21:00 h")

        informacion.add(infoETSIIT)
        informacion.add(infoBellas)
        informacion.add(infoEducacion)
        informacion.add(infoFarmacia)
        informacion.add(infoMedicina)


        posiciones.add(LatLng(37.1962126,-3.6246538)) // informatica
        posiciones.add(LatLng(37.1948506, -3.6256143) )// bellas artes
        posiciones.add(LatLng(37.1930924,-3.6019477)) // educacion
        posiciones.add(LatLng(37.1949702,-3.598714)) // farmacia
        posiciones.add(LatLng(37.1493728,-3.606892) ) // medicina

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catalogo.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_bibliotecas_to_catalogoBiblioteca)
        }

    }

    private fun cargarMasCercano() {
        GestorPermisos.getLocationPermission(requireContext(), requireActivity())
        var location = LocationServices.getFusedLocationProviderClient(requireContext())

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

                } else {
                    Toast.makeText(requireActivity(), "Si activas la ubicación, te saldrá el grado de la facultad más cercana", Toast.LENGTH_LONG).show()
                }

                actualizarBiblioteca()
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sample, menu)
        menu.add("Facultad de Bellas Artes")
        menu.add("Facultad de Ciencias de la Educación")
        menu.add("Facultad de Farmacia")
        menu.add("Facultad de Medicina")
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // ponemos los indices por separado, por si actualizamos la web en otro sitio con otra accion
        when (item.toString()) {
            "E.T.S. de Ingenierías Informática y de Telecomunicación" -> {
                indice = 0
            }


            "Facultad de Bellas Artes" -> {
                indice = 1

            }


            "Facultad de Ciencias de la Educación" -> {
                indice = 2

            }

            "Facultad de Farmacia" -> {
                indice = 3

            }

            "Facultad de Medicina" -> {
                indice = 4

            }
        }

        actualizarBiblioteca()

        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bibliotecas, container, false)

        texto_centro = view.centro
        texto_direccion = view.direccion
        texto_prestamos = view.correo_prestamos
        texto_horario = view.horario
        catalogo = view.button

        cargarMasCercano()

        return view
    }


    private fun actualizarBiblioteca(){
        texto_centro.text = informacion[indice][0]
        texto_direccion.text = informacion[indice][1]
        texto_prestamos.text = informacion[indice][2]
        texto_horario.text = informacion[indice][3]
    }

}