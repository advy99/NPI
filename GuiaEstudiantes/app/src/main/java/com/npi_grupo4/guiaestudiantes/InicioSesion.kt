package com.npi_grupo4.guiaestudiantes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_inicio_sesion.*
import kotlinx.android.synthetic.main.fragment_inicio_sesion.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [InicioSesion.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioSesion : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_sesion, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        botonContinuar.setOnClickListener({
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_inicioSesion_to_paginaInicio)
        })
    }


}