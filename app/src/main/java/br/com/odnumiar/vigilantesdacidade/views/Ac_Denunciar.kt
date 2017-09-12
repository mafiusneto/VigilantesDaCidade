package br.com.odnumiar.vigilantesdacidade.views

import android.Manifest
import android.app.Activity
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




class Ac_Denunciar : AppCompatActivity() {

    val TIRAR_FOTO = 1;//1020394857;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ac__denunciar)

        chamaCamera()
    }

    fun tirarFoto (v:View){
        chamaCamera()
    }

    fun chamaCamera() {
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
                    ivPhoto.setImageBitmap(bitmap)
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(baseContext, "A captura foi cancelada",
                            Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "A câmera foi fechada",
                            Toast.LENGTH_SHORT).show()
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

    fun fazerLigacao(v: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED){

                requestPermissions(this@Ac_Denunciar,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        0);

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

}
