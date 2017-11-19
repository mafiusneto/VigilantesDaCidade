package br.com.odnumiar.vigilantesdacidade.views

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
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
import android.os.Environment.DIRECTORY_PICTURES
import android.support.v4.content.FileProvider
import br.com.odnumiar.vigilantesdacidade.models.AsyncCallback
import br.com.odnumiar.vigilantesdacidade.mvp.ConnectionService
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class Ac_Denunciar() : AppCompatActivity(){

    val TIRAR_FOTO = 1//1020394857;
    var mCurrentPhotoPath:String =""
    //public val MEDIA_TYPE_IMAGE = 1
    //public val MEDIA_TYPE_VIDEO = 2
    //var mCamera: Camera = Camera()

    //private var msg :String = ""
    private var coord = Coordenadas(0.0,0.0,0)
    private lateinit  var bitmap :Bitmap
    private val func = Funcoes()
    private var p :Posts = Posts()

    constructor(parcel: Parcel) : this() {
        //msg = parcel.readString()
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
                //msg = "latitude:"+coord.lat.toString() + "| Logitude: "+coord.long.toString()
            } catch(ex: SecurityException) {
                Log.d(Constants.MYTAG, "Security Exception, no location available")
            }
        }
        //addText(msg)
    }

    fun fu_Back(v:View){
        //função voltar
        // finish()
        onBackPressed()
    }

    fun fu_Post(v:View){
        //var fmt:SimpleDateFormat = SimpleDateFormat('dd/MM/yyyy')

        var fmt :SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        try {

            p.userId = GlobalParam.vUserId
            p.userName = GlobalParam.vUserName
            p.description = etDescricao.text.toString()
            p.image = ""
            p.imageL = ""
            p.lat = coord.lat
            p.long = coord.long
            p.date = fmt.format(Date().time)
            //p.id = 500L
            //Toast.makeText(this@Ac_Denunciar,"Salvo com sucesso", Toast.LENGTH_SHORT).show()
            fu_Post2(v);
            p.save()

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
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, TIRAR_FOTO)
            //dispatchTakePictureIntent()
            Log.d(Constants.MYTAG, "OK fu_chamaCamera")
        } catch (e:Exception){
            Log.e(Constants.MYTAG, "Erro: "+e.message.toString()+"\n"+e.printStackTrace().toString())
        }
    }

    fun fu_Post2(v:View){

        //showProgressDialog()
        if (true) {
            //var user = User("",etPassword.text.toString(),etEmail.text.toString(),"",0)
            //user.pass =  user.pass.hashCode().toString()

            var conn = ConnectionService()

            conn.sendPost (p,bitmap, this@Ac_Denunciar,
                    object : AsyncCallback() {
                        override fun onSuccess(result:Posts){
                            /*
                            GlobalParam.vUserToken = ""
                            GlobalParam.vUserId = result.id
                            GlobalParam.vUserName= result.name
                            GlobalParam.vUserToken = result.token

                            Toast.makeText(this@Ac_Denunciar, GlobalParam.vUserToken, Toast.LENGTH_SHORT).show()

                            if (ckbManterConectado.isChecked){
                                var funcao = Funcoes()
                                funcao.SetPref(Constants.USER_TOKEN,GlobalParam.vUserToken,this@Ac_Login)
                                funcao.SetPref(Constants.USER_NAME,GlobalParam.vUserName,this@Ac_Login)
                                funcao.SetPref(Constants.USER_ID,GlobalParam.vUserId.toString(),this@Ac_Login)
                            }

                            val intent = Intent(this@Ac_Denunciar, ::class.java)
                            startActivity(intent)
                            finish()


                            */
                            p.image = result.image
                            Toast.makeText(this@Ac_Denunciar,"Postado!",Toast.LENGTH_SHORT).show()
                            //Toast.makeText(this@Ac_Denunciar,"resulto:"+result,Toast.LENGTH_SHORT).show()
                            //hideProgressDialog()
                        }

                        override fun onFailure(result: String) {
                            Toast.makeText(this@Ac_Denunciar,"Erro na postagem!:"+result,Toast.LENGTH_LONG).show()
                            //showResult(result)
                            //hideProgressDialog()
                        }
                    })

            //hideProgressDialog()


        }
    }

    private fun writeToFile(scaledBitmap: Bitmap): String {
        val f: File = File(Environment.getExternalStorageDirectory(), "${Date().time}.png");
        f.createNewFile();
        //Convert bitmap to byte array
        val bos: ByteArrayOutputStream = ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);

        //write the bytes in file
        val fos: FileOutputStream = FileOutputStream(f)
        fos.write(bos.toByteArray());
        fos.flush();
        fos.close();

        return f.absolutePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TIRAR_FOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val bundle = data.extras
                    bitmap = bundle.get("data") as Bitmap
                    //setBitmapImage(bitmap) //add ao bitmap geral
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


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath()
        return image
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the Fil
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(this@Ac_Denunciar,
                        "br.com.odnumiar.vigilantesdacidade",//"com.example.android.fileprovider",
                        photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, TIRAR_FOTO)
            }
        }

    }

}