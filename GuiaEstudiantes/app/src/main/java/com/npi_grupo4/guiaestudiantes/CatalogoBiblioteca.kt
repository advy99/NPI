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
import kotlinx.android.synthetic.main.fragment_catalogo_biblioteca.view.*

class CatalogoBiblioteca : Fragment() {
    //Declaramos las instancias de nuestro layout
    lateinit var webView : WebView
    lateinit var barra : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_catalogo_biblioteca, container, false)

        webView = view.pagina_web
        barra = view.barra_progreso

        //Le damos permisos a la webview para poder usar javaScript, para poder
        //acercar y alejar el pdf con gestos

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.clearCache(true)

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
                webView.loadUrl(request.url.toString())
                return false
            }
        }

        webView.loadUrl("https://granatensis.ugr.es/discovery/search?vid=34CBUA_UGR:VU1")


        return view
    }


}