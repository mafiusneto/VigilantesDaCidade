package br.com.odnumiar.vigilantesdacidade.util

import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import android.widget.Toast
import br.com.odnumiar.vigilantesdacidade.models.Coordenadas
import br.com.odnumiar.vigilantesdacidade.util.Location.*


/**
 * Created by Neto on 29/10/2017.
 */
class Funcoes {

    private var currentlocation: CurrentLatLong? = null

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

        return  value
    }


    /*
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
    */
    fun getLocation(context:Context, activity:Activity): Coordenadas { //: LatLng?
        var coordenada = Coordenadas(0.0,0.0,0)

        currentlocation = CurrentLatLong()
        var status: Int? = currentlocation?.currentlatlong(activity)

        if (status == 1) {
            var lat:String = currentlocation?.currentLat.toString()
            var lng:String = currentlocation?.currentLong.toString()

            coordenada.lat = lat.toDouble()
            coordenada.long = lng.toDouble()
            coordenada.ativo = 1
            //tvLocationCurrent.text = "Localização atual:"
            //tvResultLatLong?.text = "Latitude: " + currentlocation?.currentLat + "\n" + "Longitude: " + currentlocation?.currentLong
        } else {
            Toast.makeText(context, "Localização não obtida! Verifique sua conexão e GPS.", Toast.LENGTH_LONG).show()
        }
        /*
        // Get the location manager
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val bestProvider = locationManager.getBestProvider(criteria, true)
        val location = locationManager.getLastKnownLocation(bestProvider)

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
        */

        return coordenada
    }
}