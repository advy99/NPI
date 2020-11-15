package com.npi_grupo4.guiaestudiantes


import android.location.Location
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.lang.Float.POSITIVE_INFINITY


class Grados : Fragment() {

    //Declaramos los ArrayList que usaremos en este fragment
    private var posiciones = ArrayList<LatLng>()
    private var webs = ArrayList<String>()
    private var indice = 0

    //Declaramos una instancia de nuestra clase GestorPosicion()
    //que nos ayudará a saber cual es nuestra geolocalización en 
    //el momento de iniciar el fragment
    var gestorPosicion = GestorPosicion()

    //Declaramos las instancias de nuestro layout
    lateinit var webView : WebView
    lateinit var barra : ProgressBar
   
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //Indicamos que este fragment va a disponer de un menú desplegable        
        setHasOptionsMenu(true)

        retainInstance = true;

        //Añadimos al ArrayList de posiciones, las posiciones geográficas
        //de las diferentes facultades que tendrá nuestro menú desplegable

        posiciones.add(LatLng(37.1962126, -3.6246538)) // informatica
        posiciones.add(LatLng(37.1948506, -3.6256143))// bellas artes
        posiciones.add(LatLng(37.1743285, -3.5926879)) // arquitectura
        posiciones.add(LatLng(37.1930924, -3.6019477)) // educacion
        posiciones.add(LatLng(37.1949702, -3.598714)) // farmacia
        posiciones.add(LatLng(37.1493728, -3.606892)) // medicina

        //Añadimos al ArrayList de webs, las webs de las guias docentes de cada una de 
        //las facultades que aparecerán en nuestro menú desplegable

        webs.add("https://grados.ugr.es/informatica/pages/infoacademica/guias_docentes/guiasdocentes_curso_actual")
        webs.add("https://grados.ugr.es/bellasartes/pages/infoacademica/guias-docentes")
        webs.add("https://grados.ugr.es/arquitectura/pages/infoacademica/guias")
        webs.add("https://grados.ugr.es/primaria/pages/infoacademica/estudios")
        webs.add("https://grados.ugr.es/farmacia/pages/guiasdocentes/gd2019")
        webs.add("https://grados.ugr.es/medicina/pages/infoacademica/estudios")

    }

    //Añadimos a nuestro menú desplegable el nombre de unas cuantas facultades de la UGR.
    //(En la app final deberían aparecer todas las facultades de la UGR)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sample, menu)
        menu.add("Facultad de Bellas Artes")
        menu.add("E.T.S. de Arquitectura")
        menu.add("Facultad de Ciencias de la Educación")
        menu.add("Facultad de Farmacia")
        menu.add("Facultad de Medicina")
        super.onCreateOptionsMenu(menu, inflater)
    }

    //Cada vez que seleccionemos una facultad en el menú, cambiará el valor de indice, el cual
    //usaremos para acceder a la guia docente del ArrayList de webs que hemos creado en el onCreate
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // ponemos los indices por separado, por si actualizamos la web en otro sitio con otra accion
        when (item.toString()) {
            "E.T.S. de Ingenierías Informática y de Telecomunicación" -> {
                indice = 0
            }


            "Facultad de Bellas Artes" -> {
                indice = 1

            }

            "E.T.S. de Arquitectura" -> {
                indice = 2

            }

            "Facultad de Ciencias de la Educación" -> {
                indice = 3

            }

            "Facultad de Farmacia" -> {
                indice = 4

            }

            "Facultad de Medicina" -> {
                indice = 5

            }
        }

        cambiarWeb()


        return true
    }

    //Le damos permiso a la webView para poder acercar y alejar
    //el PDF usando gestos y cargamos la nueva URL.
    private fun cambiarWeb() {
        webView.loadUrl(webs[indice])
        webView.settings.setJavaScriptEnabled(true);
        webView.settings.setSupportZoom(true);
        webView.settings.builtInZoomControls = true;
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //Inicializamos la vista de fragment_grados
        val view: View = inflater.inflate(R.layout.fragment_grados, container, false)

        //Inicializamos las instancias del layout
        webView = view.findViewById(R.id.webView)
        barra = view.findViewById(R.id.progressBar)

        barra.max = 100

        //Le damos permisos a la webview para poder usar javaScript, para que la webview
        //se inicie en modo zoom out, y para poder hacer acercar y alejar el pdf con
        //gestos
        webView.settings.setJavaScriptEnabled(true)
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true;

        //Para mostrar la barra el iniciar el fragment
        webView.visibility = View.GONE
        barra.visibility = View.VISIBLE

        //Modificamos el objeto webChromeClient de nuestra instancia webview
        //para que cada vez que accedamos a una nueva pestaña, esta quede
        //invisible mientras carga y podamos ver la progressBar.
        //Una vez la pagina ha cargado, la haremos visible y pondremos la 
        //progressBar en invisible.
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100 && barra.visibility == ProgressBar.GONE) {
                    barra.visibility = ProgressBar.VISIBLE
                    webView.visibility = View.GONE
                }
                barra.progress = progress
                if (progress == 100) {
                    barra.visibility = ProgressBar.GONE
                    webView.visibility = View.VISIBLE
                }
            }
        }

        //Modificamos el objeto webViewClient de nuestra WebView para que se nos permita ir 
        //accediendo a diferentes pestañas desde la webview inicial
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                if (request.url.toString().endsWith("/!") or request.url.toString().endsWith(".pdf")){
                    webView.stopLoading();
                    var pdfUrl : String = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + request.url.toString();
                    webView.loadUrl(pdfUrl)
                }
                else {
                    webView.loadUrl(request.url.toString())
                }
                return false
            }
        }

        //La primera vez que iniciamos el fragment, cargará la url de la facultad que esté más cerca
        //de nosotros usando la clase GestorPosicion(), donde se hace uso de nuestra geolocalización
        val pos = gestorPosicion.posicionMasCercana(posiciones, requireContext(), requireActivity())

        if ( InicioSesion.indice_facultad != -1){
            indice = InicioSesion.indice_facultad
        } else if ( pos != null) {
            indice = pos
        } else {
            indice = 0
        }
        cambiarWeb()

        //Devolvemos la vista
        return view
    }




}
