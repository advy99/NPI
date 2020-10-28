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
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Comedores.newInstance] factory method to
 * create an instance of this fragment.
 */
class Comedores : Fragment() {
    // TODO: Rename and change types of parameters
    private var pdf: WebView? = null

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
        val webview = view.findViewById(R.id.pdf_comedores) as WebView
        webview.getSettings().setJavaScriptEnabled(true)
        val pdf = "http://scu.ugr.es/?theme=pdf"

        webview.settings.javaScriptEnabled = true
        webview.clearCache(true)
        webview.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String?) {
                if (view.title == "") view.reload()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf)
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