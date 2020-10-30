package com.npi_grupo4.guiaestudiantes

import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Centros : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_centros, container, false)
        val webview = view.findViewById(R.id.mapa_centros) as WebView
        webview.getSettings().setJavaScriptEnabled(true)

        webview.settings.javaScriptEnabled = true
        webview.clearCache(true)
        webview.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String?) {
                if (view.title == "") view.reload()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                webview.loadUrl("https://www.google.com/maps/d/embed?mid=1k7hR-nnrQivEs6-5LWRwE9aLRDMvJjaT")
                return true
            }

            override fun onReceivedSslError(
                    view: WebView,
                    handler: SslErrorHandler,
                    error: SslError
            ) {
                println("before handler")
                handler.proceed()
                println("after handler")
            }
        }

        webview.loadUrl("https://www.google.com/maps/d/embed?mid=1k7hR-nnrQivEs6-5LWRwE9aLRDMvJjaT")

        return view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Centros().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}