package br.com.odnumiar.vigilantesdacidade.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

class ConnReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        var cm:ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        var netInfo = cm.activeNetworkInfo

        var txt = ""
        if(netInfo != null && netInfo.isConnectedOrConnecting){
            txt = "CONECTADO!!!"
            Log.i(Constants.MYTAG,txt)
        }else{
            txt = "DESCONECTADO!!!"
            Log.i(Constants.MYTAG,txt)
            Toast.makeText(context, txt,Toast.LENGTH_LONG).show()
        }

    }
}
