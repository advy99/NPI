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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.Navigation.findNavController
import java.lang.Math.toDegrees
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
    private var rotacionAnterior: Float = 0f

    var dedosEnPantalla = 0

    var tiempoRotacionAnterior = System.currentTimeMillis()
    var tiempoAnterior = System.currentTimeMillis()

    private var accion: Accion = Accion.NINGUNA
    private var curvaC = false
    private var dibujandoC = false

    private lateinit var campo_magnetico: Sensor
    private lateinit var acelerometro: Sensor
    private lateinit var gravedad: Sensor
    private lateinit var giroscopio: Sensor
    private lateinit var aceleracionLineal: Sensor
    private lateinit var rotacion: Sensor
    private lateinit var proximidad: Sensor

    // para la brujula y la orientacion
    private val mGravity = FloatArray(3)
    private val mGeomagnetic = FloatArray(3)
    private val matriz_R = FloatArray(9)
    private val I = FloatArray(9)
    private var azimuth = 0f
    private val azimuthFix = 0f

    private var gestorPos = GestorPosicion()


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

        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.let {
            campo_magnetico = it
            sensorManager.registerListener(this, campo_magnetico, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
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
        val current_fragment = (navigation.currentDestination?.label ?:"" )


        // usamos pointer down y pointer up ya que son dos dedos
        if ( event.actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
            accion = Accion.ATRAS
            recorridoX = x
            recorridoY = y
            dedosEnPantalla = event.pointerCount
        } else if ( event.actionMasked == MotionEvent.ACTION_POINTER_UP) {

            recorridoX = x - recorridoX
            recorridoY = y - recorridoY

            if (accion == Accion.ATRAS && recorridoX > 20){
                navigation.navigateUp()
            }

        } else if ( event.actionMasked == MotionEvent.ACTION_DOWN) {
            accion = Accion.COMEDORES
            recorridoX = x
            recorridoY = y
            dedosEnPantalla = event.pointerCount
            curvaC = false
            dibujandoC = true

        } else if ( event.actionMasked == MotionEvent.ACTION_UP ) {


            if ( accion == Accion.COMEDORES && recorridoX > 100 && curvaC && current_fragment == "fragment_pagina_inicio"){
                navigation.navigate(R.id.action_paginaInicio_to_comedores)
                curvaC = false
                dibujandoC = false
            }

        } else if ( event.actionMasked == MotionEvent.ACTION_MOVE) {
            Log.i("a", " " + x + " " + y)
            if ( abs(y - previousY) < 100 && previousX < x && dedosEnPantalla == 2) {
                accion = Accion.ATRAS

            } else if ( !curvaC && dedosEnPantalla == 1 && y - previousY >= 0 ) {
                if ( previousX - x >= 0 ) {
                    accion = Accion.COMEDORES
                } else if ( y - recorridoY >= 70 ) {
                    accion = Accion.COMEDORES
                    curvaC = true

                } else {
                    dibujandoC = false
                }

            } else if ( curvaC && dedosEnPantalla == 1 && y - previousY >= 0 ) {
                if ( previousX - x <= 0) {
                    accion = Accion.COMEDORES
                } else {
                    accion = Accion.NINGUNA
                    dibujandoC = false
                }
            } else {
                accion = Accion.NINGUNA
                dibujandoC = false
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

        val alpha = 0.97f;

        when(event.sensor.type) {
            Sensor.TYPE_LINEAR_ACCELERATION -> {

                val epsilon = 10

                val soloEjeZ = abs(event.values[0]) < epsilon && abs(event.values[1]) < epsilon


                if (event.values[2] < -11 && tiempoActual - tiempoAnterior > 500 && soloEjeZ) {
                    if (current_fragment == "fragment_pagina_inicio") {
                        navigation.navigate(R.id.action_paginaInicio_to_centros)
                        tiempoAnterior = tiempoActual

                    }
                } else if ( event.values[2] > 11 && tiempoActual - tiempoAnterior > 500 && soloEjeZ) {
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
                mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0]
                mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1]
                mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2]
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

            Sensor.TYPE_MAGNETIC_FIELD -> {
                mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha) * event.values[0]
                mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha) * event.values[1]
                mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha) * event.values[2]

            }


        }


        if ( (Centros.brujula && current_fragment == "fragment_centros") ||
             (SitiosInteres.brujula && current_fragment == "fragment_sitios_interes") && abs(tiempoRotacionAnterior - System.currentTimeMillis()) > 1000) {

            val epsilon = 5f
            val success = SensorManager.getRotationMatrix(matriz_R, I, mGravity, mGeomagnetic);
            if (success) {
                var orientation = FloatArray(3)
                SensorManager.getOrientation(matriz_R, orientation);
                // Log.d(TAG, "azimuth (rad): " + azimuth);
                azimuth = toDegrees(orientation[0].toDouble()).toFloat(); // orientation
                azimuth = (azimuth + azimuthFix + 360) % 360;

                if ( abs(rotacionAnterior - azimuth) > epsilon || (azimuth + epsilon) % 360 > abs(rotacionAnterior - azimuth) ) {
                    if ( current_fragment == "fragment_centros") {
                        Centros.mapa?.let { gestorPos.rotarMapa(it, azimuth) }
                    } else {
                        SitiosInteres.mapa?.let { gestorPos.rotarMapa(it, azimuth) }
                    }
                    rotacionAnterior = azimuth
                    tiempoRotacionAnterior = System.currentTimeMillis()
                }

            }
        }







    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int){

    }


}