package com.npi_grupo4.guiaestudiantes

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.fragment_grados.*
import kotlin.math.abs



private const val DEBUG_TAG = "Gestures"

enum class Accion{
    ATRAS, NINGUNA, COMEDORES, CENTROS
}


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, SensorEventListener {

    private lateinit var mDetector: GestureDetectorCompat;
    private lateinit var sensorManager: SensorManager

    private var previousX: Float = 0f
    private var previousY: Float = 0f
    private var recorridoX: Float = 0f
    private var recorridoY: Float = 0f

    var tiempoAnterior = System.currentTimeMillis()

    private var accion: Accion = Accion.NINGUNA

    private lateinit var acelerometro: Sensor
    private lateinit var gravedad: Sensor
    private lateinit var giroscopio: Sensor
    private lateinit var aceleracionLineal: Sensor
    private lateinit var rotacion: Sensor
    private lateinit var proximidad: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDetector = GestureDetectorCompat(this, this)
        mDetector.setOnDoubleTapListener(this)

        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let {
            acelerometro = it
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)?.let {
            gravedad = it
            sensorManager.registerListener(this, gravedad, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.let {
            giroscopio = it
            sensorManager.registerListener(this, giroscopio, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)?.let {
            aceleracionLineal = it
            sensorManager.registerListener(this, aceleracionLineal, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.let {
            rotacion = it
            sensorManager.registerListener(this, rotacion, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)?.let {
            proximidad = it
            sensorManager.registerListener(this, proximidad, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }

    }

    // PANTALLA

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        Toast.makeText(this, "Doble toque", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x: Float = event.x
        val y: Float = event.y

        val navigation = findNavController(this, R.id.nav_frag)

        if ( event.action == MotionEvent.ACTION_MOVE) {
            if ( abs(y - previousY) < 50 && previousX < x ) {
                accion = Accion.ATRAS
            } else {
                accion = Accion.NINGUNA
            }
        } else if ( event.action == MotionEvent.ACTION_UP) {

            recorridoX = x - recorridoX
            recorridoY = y - recorridoY

            if (accion == Accion.ATRAS && recorridoX > 20){
                navigation.navigateUp()
            }
        } else if ( event.action == MotionEvent.ACTION_DOWN) {
            if (accion == Accion.ATRAS){
                recorridoX = x
                recorridoY = y
            }
        }

        previousY = y
        previousX = x

        return true

    }

    override fun onDown(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDown: $event")
        return true
    }

    override fun onFling(
            event1: MotionEvent,
            event2: MotionEvent,
            velocityX: Float,
            velocityY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onFling: $event1 $event2")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onLongPress: $event")
    }

    override fun onScroll(
            event1: MotionEvent,
            event2: MotionEvent,
            distanceX: Float,
            distanceY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onScroll: $event1 $event2")
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapUp: $event")
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: $event")
        return true
    }





    // SENSORES HARDWARE

    override fun onSensorChanged(event: SensorEvent){

        val navigation = findNavController(this, R.id.nav_frag)

        val tiempoActual = System.currentTimeMillis()

        val current_fragment = (navigation.currentDestination?.label ?:"" )

        when(event.sensor.type) {
            Sensor.TYPE_LINEAR_ACCELERATION -> {

                if (event.values[2] < -9 && tiempoActual - tiempoAnterior > 1000) {
                    if (current_fragment == "fragment_pagina_inicio") {
                        navigation.navigate(R.id.action_paginaInicio_to_centros)
                        tiempoAnterior = tiempoActual

                    }
                } else if ( event.values[2] > 9 && tiempoActual - tiempoAnterior > 1000) {
                    // si detectamos el gesto y han pasado mas de 100ms
                    navigation.navigateUp()
                    tiempoAnterior = tiempoActual


                }

            }

            Sensor.TYPE_GRAVITY -> {

            }

            Sensor.TYPE_GYROSCOPE -> {

            }

            Sensor.TYPE_ACCELEROMETER -> {

            }

            Sensor.TYPE_ROTATION_VECTOR -> {


            }

            Sensor.TYPE_PROXIMITY -> {
                if (event.values[0] < 4 && tiempoActual - tiempoAnterior > 1000) {
                    if (current_fragment == "fragment_pagina_inicio") {
                        navigation.navigate(R.id.action_paginaInicio_to_bibliotecas)
                        tiempoAnterior = tiempoActual

                    }
                }

            }

        }






    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int){

    }


}