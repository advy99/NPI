package com.npi_grupo4.guiaestudiantes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var texto_casilla: EditText;
    private lateinit var boton: Button;
    private lateinit var salida: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        texto_casilla = findViewById(R.id.texto_prueba)
        boton = findViewById(R.id.button)
        salida = findViewById(R.id.textView)

        boton.setOnClickListener { view->

            when (view.id) {
                R.id.button->mostrar()
            }
        }

    }

    public fun mostrar(){
        salida.setText(texto_casilla.getText())
    }
}