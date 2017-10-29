package br.com.odnumiar.vigilantesdacidade.util

import android.content.Context
import android.preference.PreferenceManager
import android.widget.Toast

/**
 * Created by Neto on 29/10/2017.
 */
class Funcoes {

    fun SetPrefToken(key:String, value :String, context:Context){
        var pref = PreferenceManager.getDefaultSharedPreferences(context)
        var editor = pref.edit()

        //save infos
        editor.putString( key,value)
        editor.commit()

        //Toast.makeText(context, "Login OK", Toast.LENGTH_SHORT).show()
    }
}