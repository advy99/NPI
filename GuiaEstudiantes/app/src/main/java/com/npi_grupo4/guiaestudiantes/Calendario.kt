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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Calendario.newInstance] factory method to
 * create an instance of this fragment.
 */
class Calendario : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var webview: WebView
    lateinit var pdf: String
    lateinit var barra: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_comedores, container, false)
        webview = view.findViewById(R.id.pdf_comedores) as WebView
        barra = view.findViewById(R.id.progressBar2) as ProgressBar
        webview.getSettings().setJavaScriptEnabled(true)
        pdf = "https://docencia.ugr.es/sites/vicerrectorados_files/vic_docencia/public/inline-files/calendario_grado_master_2021%20%28definitivo%29.pdf"

        webview.settings.javaScriptEnabled = true
        webview.settings.setSupportZoom(true)
        webview.settings.builtInZoomControls = true
        webview.clearCache(true)

        //Para que salga la barra al iniciar el fragment
        webview.visibility = View.GONE
        barra.visibility = View.VISIBLE

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

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                webview.loadUrl(request.url.toString())
                return false
            }
        }

        webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Comedores.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Comedores().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}