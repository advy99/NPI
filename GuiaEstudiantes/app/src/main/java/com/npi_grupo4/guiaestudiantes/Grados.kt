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

    private var posiciones = ArrayList<LatLng>()
    private var webs = ArrayList<String>()
    private var indice = 0


    var gestorPosicion = GestorPosicion()

    lateinit var webView : WebView
    lateinit var barra : ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        retainInstance = true;

        posiciones.add(LatLng(37.1962126, -3.6246538)) // informatica
        posiciones.add(LatLng(37.1948506, -3.6256143))// bellas artes
        posiciones.add(LatLng(37.1743285, -3.5926879)) // arquitectura
        posiciones.add(LatLng(37.1930924, -3.6019477)) // educacion
        posiciones.add(LatLng(37.1949702, -3.598714)) // farmacia
        posiciones.add(LatLng(37.1493728, -3.606892)) // medicina


        webs.add("https://grados.ugr.es/informatica/pages/infoacademica/guias_docentes/guiasdocentes_curso_actual")
        webs.add("https://grados.ugr.es/bellasartes/pages/infoacademica/guias-docentes")
        webs.add("https://grados.ugr.es/arquitectura/pages/infoacademica/guias")
        webs.add("https://grados.ugr.es/primaria/pages/infoacademica/estudios")
        webs.add("https://grados.ugr.es/farmacia/pages/guiasdocentes/gd2019")
        webs.add("https://grados.ugr.es/medicina/pages/infoacademica/estudios")

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

        val view: View = inflater.inflate(R.layout.fragment_grados, container, false)

        webView = view.findViewById(R.id.webView)
        barra = view.findViewById(R.id.progressBar)

        barra.max = 100

        webView.settings.setJavaScriptEnabled(true)
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true;

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



                    var pdfUrl : String = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + request.url.toString();
                    webView.loadUrl(pdfUrl)
                }
                else {
                    webView.loadUrl(request.url.toString())
                }

                return false
            }
        }

        val pos = gestorPosicion.posicionMasCercana(posiciones, requireContext(), requireActivity())

        if ( pos != null) {
            indice = pos
        } else {
            indice = 0
        }
        cambiarWeb()

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