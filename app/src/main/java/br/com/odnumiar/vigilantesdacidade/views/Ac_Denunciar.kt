package br.com.odnumiar.vigilantesdacidade.views

import android.Manifest
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.odnumiar.vigilantesdacidade.R
import android.provider.MediaStore
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import android.net.Uri
import android.os.Build
import android.support.v4.app.ActivityCompat.requestPermissions
import android.view.View
import kotlinx.android.synthetic.main.activity_ac__denunciar.*
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import br.com.odnumiar.vigilantesdacidade.util.Constants
import br.com.odnumiar.vigilantesdacidade.util.GlobalParam
import android.location.Criteria
import br.com.odnumiar.vigilantesdacidade.models.Coordenadas
import br.com.odnumiar.vigilantesdacidade.util.Funcoes


class Ac_Denunciar : AppCompatActivity() {

    val TIRAR_FOTO = 1//1020394857;
    private var msg :String = ""
    private var coord = Coordenadas(0.0,0.0,0)
    private lateinit  var bitmap :Bitmap
    private val func = Funcoes()

    init{
        //Log.d("DEBUG_VC", "executado init de ConnectionService")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ac__denunciar)

        if (GlobalParam.vTiraFoto == 1) {
            fu_tirarFoto(btEnviar)
            //fu_chamaCamera()
            //GlobalParam.vTiraFoto = 0
        }
    }
    fun addText(v:String){
        etDescricao.setText(v)
    }

    fun setBitmapImage(b:Bitmap){
        bitmap = b
    }

    fun fu_tirarFoto (v:View){
        try {
            fu_chamaCamera()
        } finally {
            try {
                // Request location updates
                //locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
                coord = func.getLocation(this@Ac_Denunciar) //getLocation()
                msg = "latitude:"+coord.lat.toString() + "| Logitude: "+coord.long.toString()
            } catch(ex: SecurityException) {
                Log.d(Constants.MYTAG, "Security Exception, no location available");
            }
        }
        addText(msg)
    }
    /*
    fun fu_Back(v:View){
       //função voltar
        finish()
    }
    */
    fun fu_Post(v:View){
        func.saveFile(this@Ac_Denunciar,bitmap,"testepost")
        addText("teste")
        //postar denuciar
        Toast.makeText(this@Ac_Denunciar,"teste post",Toast.LENGTH_SHORT).show()
    }

    fun fu_chamaCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, TIRAR_FOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TIRAR_FOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val bundle = data.extras
                    val bitmap = bundle.get("data") as Bitmap
                    setBitmapImage(bitmap)
                    ivPhoto.setImageBitmap(bitmap)
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(baseContext, "A captura foi cancelada",
                            Toast.LENGTH_SHORT).show()
                    onBackPressed()
                } else {
                    Toast.makeText(baseContext, "A câmera foi fechada",
                            Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }else{
                Toast.makeText(baseContext, "A captura foi cancelada",
                        Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }else{
            onBackPressed()
        }
    }
    /*
    fun getLocation():Coordenadas{ //: LatLng?
        // Get the location manager
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val bestProvider = locationManager.getBestProvider(criteria, false)
        val location = locationManager.getLastKnownLocation(bestProvider)
        //val lat: Double?
        //val lon: Double?
        var coordenada = Coordenadas(0.0,0.0,0)
        try {
            coordenada.lat = location.latitude
            coordenada.long = location.longitude
            coordenada.ativo = 1
            //msg ="Lat: "+lat.toString()+" | long: "+lon.toString()
            //return LatLng(lat, lon)
        } catch (e: NullPointerException) {
            msg ="Erro"
            e.printStackTrace()
            //return null
        }

        return coordenada
    }

    fun fazerLigacao(v: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED){

                requestPermissions(this@Ac_Denunciar,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        0)

            }else{
                callNumber()
            }
        }else{
            callNumber()
        }
        //onLaunchCamera(v)
    }

    fun callNumber(){
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:9834")))
    }
    */

}