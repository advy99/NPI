package com.npi_grupo4.guiaestudiantes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class Calendario : Fragment() {
    //Declaramos las instancias de nuestro layout
    lateinit var webview: WebView
    lateinit var barra: ProgressBar

    //Declaramos la variable pdf, que contendrá la url que
    //estamos cargando en todo momento
    lateinit var pdf: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    //El método onCreateView es el que nos va a interesar, porque es el
    //que mostrará por pantalla lo que le digamos al iniciar el fragment
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //Inicializamos la vista al fragment_comedores
        val view: View = inflater.inflate(R.layout.fragment_comedores, container, false)

        //Inicializamos las instancias del layout usando findViewById
        webview = view.findViewById(R.id.pdf_comedores) as WebView
        barra = view.findViewById(R.id.progressBar2) as ProgressBar
        webview.getSettings().setJavaScriptEnabled(true)

        //Inicializamos la variable pdf a la página que contiene el menú semanal
        //de los comedores de la UGR
        pdf = "https://docencia.ugr.es/sites/vicerrectorados_files/vic_docencia/public/inline-files/calendario_grado_master_2021%20%28definitivo%29.pdf"

        //Le damos permisos a la webview para poder usar javaScript, para poder
        //acercar y alejar el pdf con gestos

        webview.settings.javaScriptEnabled = true
        webview.settings.setSupportZoom(true)
        webview.settings.builtInZoomControls = true
        webview.clearCache(true)

        //Para que salga la barra al iniciar el fragment
        webview.visibility = View.GONE
        barra.visibility = View.VISIBLE

        //Modificamos el objeto webChromeClient de nuestra instancia webview
        //para que cada vez que accedamos a una nueva pestaña, esta quede
        //invisible mientras carga y podamos ver la progressBar.
        //Una vez la pagina ha cargado, la haremos visible y pondremos la
        //progressBar en invisible.
        webview.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100 && barra.visibility == ProgressBar.GONE) {
                    barra.visibility = ProgressBar.VISIBLE
                    webview.visibility = View.GONE
                }
                barra.progress = progress
                if (progress == 100) {
                    barra.visibility = ProgressBar.GONE
                    webview.visibility = View.VISIBLE
                }
            }
        }

        //Modificamos el objeto webViewClient de nuestra WebView para que se nos permita ir
        //accediendo a diferentes pestañas desde la webview inicial
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                webview.loadUrl(request.url.toString())
                return false
            }
        }

        //Usamos las facilidades que nos ofrece Google Drive para poder visualizar pdf's
        //online
        webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf)

        //Devolvemos la vista
        return view
    }

}