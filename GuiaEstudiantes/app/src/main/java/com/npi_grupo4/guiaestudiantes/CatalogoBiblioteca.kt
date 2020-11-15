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

        barra.max = 100

        webView.settings.setJavaScriptEnabled(true)
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.getSettings().setLoadWithOverviewMode(true)
        webView.getSettings().setUseWideViewPort(true);

        //Para mostrar la barra el iniciar el fragment
        webView.visibility = View.GONE
        barra.visibility = View.VISIBLE

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

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                if (request.url.toString().endsWith("/!") or request.url.toString().endsWith(".pdf")){
                    webView.stopLoading();



                    var url : String = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + request.url.toString();
                    webView.loadUrl(url)
                }
                else {
                    webView.loadUrl(request.url.toString())
                }

                return false
            }
        }

        webView.loadUrl("https://granatensis.ugr.es/discovery/search?vid=34CBUA_UGR:VU1")
        webView.settings.setJavaScriptEnabled(true);
        webView.settings.setSupportZoom(true);
        webView.settings.builtInZoomControls = true;

        return view
    }


}