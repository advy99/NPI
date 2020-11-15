package com.npi_grupo4.guiaestudiantes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_pagina_inicio.*

class PaginaInicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pagina_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        img_centros.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_centros)
        }

        img_comedores.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_comedores)
        }

        img_calendario.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_calendario)
        }

        img_grados.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_grados)
        }

        img_biblioteca.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_bibliotecas)
        }

        img_sitios_interes.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_sitiosInteres)
        }

        
        text_centros.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_centros)
        }

        text_comedores.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_comedores)
        }

        text_calendario.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_calendario)
        }

        text_grados.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_grados)
        }

        text_biblioteca.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_bibliotecas)
        }

        text_sitios_interes.setOnClickListener { view ->
            val navigation = Navigation.findNavController(view)
            navigation.navigate(R.id.action_paginaInicio_to_sitiosInteres)
        }
    }

}