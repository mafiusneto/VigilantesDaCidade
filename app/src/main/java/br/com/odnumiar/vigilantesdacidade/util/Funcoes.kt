package br.com.odnumiar.vigilantesdacidade.util

import android.content.Context
import android.preference.PreferenceManager
import android.widget.Toast
import android.graphics.Bitmap
import android.util.Log
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import android.graphics.BitmapFactory
import android.location.Criteria
import android.location.LocationManager
import br.com.odnumiar.vigilantesdacidade.models.Coordenadas
import java.io.FileInputStream


/**
 * Created by Neto on 29/10/2017.
 */
class Funcoes {

    fun SetPref(key:String, value :String, context:Context){
        var pref = PreferenceManager.getDefaultSharedPreferences(context)
        var editor = pref.edit()

        //save infos
        editor.putString( key,value)
        editor.commit()

        //Toast.makeText(context, "Login OK", Toast.LENGTH_SHORT).show()
    }

    fun GetPref(key:String, context:Context):String {
        var pref = PreferenceManager.getDefaultSharedPreferences(context)
        var value = pref.getString(key,"") //NOTHING!!!

        return  value;
    }

    fun saveFile(context: Context, b: Bitmap, picName: String) {
        var fos: FileOutputStream? = null
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE)
            b.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: FileNotFoundException) {
            Log.d(Constants.MYTAG, "file not found")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d(Constants.MYTAG, "io exception")
            e.printStackTrace()
        } finally {
                fos?.close()
        }
    }

    fun loadBitmap(context: Context, picName: String): Bitmap? {
        var b: Bitmap? = null
        var fis: FileInputStream? = null
        try {
            fis = context.openFileInput(picName)
            b = BitmapFactory.decodeStream(fis)
        } catch (e: FileNotFoundException) {
            Log.d(Constants.MYTAG, "file not found")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d(Constants.MYTAG, "io exception")
            e.printStackTrace()
        } finally {
            fis?.close()
        }
        return b
    }

    fun getLocation(context:Context): Coordenadas { //: LatLng?
        // Get the location manager
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
            //msg ="Erro"
            e.printStackTrace()
            //return null
        }

        return coordenada
    }
}