package com.npi_grupo4.guiaestudiantes


import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment


class Grados : Fragment() {

    var web : String = "https://grados.ugr.es/informatica/pages/infoacademica/guias_docentes/guiasdocentes_curso_actual"
    lateinit var webView : WebView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true;
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sample, menu)
        menu.add("Facultad de Bellas Artes")
        menu.add("E.T.S. de Arquitectura")
        menu.add("Facultad de Ciencias de la Educación")
        menu.add("Facultad de Farmacia")
        menu.add("Facultad de Medicina")
        super.onCreateOptionsMenu(menu, inflater)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.toString()) {
            "E.T.S. de Ingenierías Informática y de Telecomunicación" -> {
                web = "https://grados.ugr.es/informatica/pages/infoacademica/guias_docentes/guiasdocentes_curso_actual"
                webView.loadUrl(web)
                webView.settings.setJavaScriptEnabled(true);
                webView.settings.setSupportZoom(true);
                webView.settings.builtInZoomControls = true;
            }

            "Facultad de Bellas Artes" -> {
                web = "https://grados.ugr.es/bellasartes/pages/infoacademica/guias-docentes"
                webView.loadUrl(web)
                webView.settings.setJavaScriptEnabled(true);
                webView.settings.setSupportZoom(true);
                webView.settings.builtInZoomControls = true;
            }

            "E.T.S. de Arquitectura" -> {
                web = "https://grados.ugr.es/arquitectura/pages/infoacademica/guias"
                webView.loadUrl(web)
                webView.settings.setJavaScriptEnabled(true);
                webView.settings.setSupportZoom(true);
                webView.settings.builtInZoomControls = true;
            }

            "Facultad de Ciencias de la Educación" -> {
                web = "https://grados.ugr.es/primaria/pages/infoacademica/estudios"
                webView.loadUrl(web)
                webView.settings.setJavaScriptEnabled(true);
                webView.settings.setSupportZoom(true);
                webView.settings.builtInZoomControls = true;
            }

            "Facultad de Farmacia" -> {
                web = "https://grados.ugr.es/farmacia/pages/guiasdocentes/gd2019"
                webView.loadUrl(web)
                webView.settings.setJavaScriptEnabled(true);
                webView.settings.setSupportZoom(true);
                webView.settings.builtInZoomControls = true;
            }

            "Facultad de Medicina" -> {
                web = "https://grados.ugr.es/medicina/pages/infoacademica/estudios"
                webView.loadUrl(web)
                webView.settings.setJavaScriptEnabled(true);
                webView.settings.setSupportZoom(true);
                webView.settings.builtInZoomControls = true;
            }
        }
        return true
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_grados, container, false)

        webView = view.findViewById(R.id.webView)
        progressBar = view.findViewById(R.id.progressBar);
        webView.loadUrl(web)
        webView.settings.setJavaScriptEnabled(true);
        webView.settings.setSupportZoom(true);
        webView.settings.builtInZoomControls = true;



        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Grados.
         */
    }
}