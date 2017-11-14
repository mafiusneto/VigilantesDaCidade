package br.com.odnumiar.vigilantesdacidade.views

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.odnumiar.vigilantesdacidade.R
import br.com.odnumiar.vigilantesdacidade.models.Coordenadas
import br.com.odnumiar.vigilantesdacidade.orm.Posts
import br.com.odnumiar.vigilantesdacidade.util.Constants
import br.com.odnumiar.vigilantesdacidade.util.Funcoes
import br.com.odnumiar.vigilantesdacidade.util.GlobalParam
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_ac__denunciar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Ac_Denunciar() : AppCompatActivity(), Parcelable {

    val TIRAR_FOTO = 1//1020394857;
    //public val MEDIA_TYPE_IMAGE = 1
    //public val MEDIA_TYPE_VIDEO = 2
    //var mCamera: Camera = Camera()

    private var msg :String = ""
    private var coord = Coordenadas(0.0,0.0,0)
    private lateinit  var bitmap :Bitmap
    private val func = Funcoes()

    constructor(parcel: Parcel) : this() {
        msg = parcel.readString()
        bitmap = parcel.readParcelable(Bitmap::class.java.classLoader)
    }

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

        /*
        try{
            //var s:Posts = Posts()
            var l:Posts = SugarRecord.findById(Posts::class.java,1L)
            Toast.makeText(this@Ac_Denunciar,l.description.toString(),Toast.LENGTH_SHORT).show()
        } catch (e:Exception){
            Log.e(Constants.MYTAG,"ERRO - "+e.message.toString()+"\n"+e.stackTrace.toString())
            Toast.makeText(this@Ac_Denunciar,"Erro 1",Toast.LENGTH_SHORT).show()
        }
        */
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
                Log.d(Constants.MYTAG, "Security Exception, no location available")
            }
        }
        addText(msg)
    }

    fun fu_Back(v:View){
        //função voltar
        // finish()
        onBackPressed()
    }

    fun fu_Post(v:View){
        //var fmt:SimpleDateFormat = SimpleDateFormat('dd/MM/yyyy')

        var fmt :SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            var p :Posts = Posts()

            p.userId = GlobalParam.vUserId
            p.description = GlobalParam.vUserName+"\n/n"+ etDescricao.text.toString()
            p.image = "adsadsa"
            p.imageL = "adsasd"
            p.lat = coord.lat
            p.long = coord.long
            p.date = fmt.format(Date().time)
            //p.id = 500L
            p.save()

            Toast.makeText(this@Ac_Denunciar,"Salvo com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        } catch (e:Exception){
            Log.e(Constants.MYTAG,e.message.toString()+"\n"+e.stackTrace.toString())
            Toast.makeText(this@Ac_Denunciar,"Erro!", Toast.LENGTH_SHORT).show()
        }
        //func.saveFile(this@Ac_Denunciar,bitmap,"testepost")
        //addText("teste")
        //postar denuciar
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
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(msg)
        parcel.writeParcelable(bitmap, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ac_Denunciar> {
        override fun createFromParcel(parcel: Parcel): Ac_Denunciar {
            return Ac_Denunciar(parcel)
        }

        override fun newArray(size: Int): Array<Ac_Denunciar?> {
            return arrayOfNulls(size)
        }
    }

}