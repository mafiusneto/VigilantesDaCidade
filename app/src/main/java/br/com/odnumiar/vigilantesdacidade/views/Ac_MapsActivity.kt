package br.com.odnumiar.vigilantesdacidade.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.odnumiar.vigilantesdacidade.R
import br.com.odnumiar.vigilantesdacidade.adapters.AdapterPosts
import br.com.odnumiar.vigilantesdacidade.models.AsyncCallback
import br.com.odnumiar.vigilantesdacidade.models.Coordenadas
import br.com.odnumiar.vigilantesdacidade.mvp.ConnectionService
import br.com.odnumiar.vigilantesdacidade.orm.Posts
import br.com.odnumiar.vigilantesdacidade.util.Funcoes
import br.com.odnumiar.vigilantesdacidade.util.GlobalParam

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_main.*

class Ac_MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var coord =Coordenadas(0.0,0.0,0)
    private val funcao = Funcoes()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ac__maps)

        coord = funcao.getLocation(this@Ac_MapsActivity, this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fu_ConsultaPostagens(coord.lat.toString(), coord.long.toString())
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //var coord = funcao.getLocation(this@Ac_MapsActivity)
        var local = LatLng(coord.lat, coord.long)
        /*
        var lista :ArrayList<Posts> = ArrayList<Posts>(fu_ConsultaPostagens(coord.lat.toString(), coord.long.toString()))
        for (post in lista){
            local = LatLng(post.lat, post.long)
            mMap.addMarker(MarkerOptions().position(local).title(post.id.toString()))//.snippet(coord.lat.toString())
        }
        local = LatLng(coord.lat, coord.long)
        */
        mMap.addMarker(MarkerOptions().position(local).title("Meu Local"))//.snippet(coord.lat.toString())
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local,18.5f))
    }

    fun addMark(googleMap: GoogleMap, post:Posts) {
        mMap = googleMap

        val local = LatLng(post.lat, post.long)
        mMap.addMarker(MarkerOptions().position(local).title(post.description.toString()))//.snippet(coord.lat.toString())
        mMap.moveCamera(CameraUpdateFactory.newLatLng(local))
    }


    fun fu_ConsultaPostagens(lat:String, lon:String):List<Posts>{

        var conn = ConnectionService()

        var lista = ArrayList<Posts>()

        conn.requestPosts2 (lat, lon, this@Ac_MapsActivity,
                object : AsyncCallback() {
                    override fun onSuccess(result:List<Posts>){
                        //Toast.makeText(this@MainActivity,result,Toast.LENGTH_SHORT).show()
                        lista = ArrayList<Posts>(result)

                        for (post in lista){
                            addMark(mMap, post)
                        }
                    }

                    override fun onFailure(result: String) {
                        Toast.makeText(this@Ac_MapsActivity,"Falha ao consultar postagens!:"+result, Toast.LENGTH_SHORT).show()
                        //showResult(result)
                        //hideProgressDialog()
                    }
                })

        return  lista
    }
}
