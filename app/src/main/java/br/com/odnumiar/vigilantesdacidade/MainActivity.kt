package br.com.odnumiar.vigilantesdacidade

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.odnumiar.vigilantesdacidade.util.Constants
import br.com.odnumiar.vigilantesdacidade.util.Funcoes
import br.com.odnumiar.vigilantesdacidade.util.GlobalParam
import br.com.odnumiar.vigilantesdacidade.views.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val REQUEST_IMAGE_CAPTURE = 1
    //val key = "AUTh"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        var funcao = Funcoes();
        if (GlobalParam.vUserToken == "") {
            GlobalParam.vUserToken = funcao.GetPref(Constants.USER_TOKEN, this@MainActivity)
        }

        if (GlobalParam.vUserToken == ""){
            fu_logOff()
            //chamaIntent(4)  //4 - tela login
            //finish()
        }else{
            //GlobalParam.vUserId = funcao.GetPref(Constants.USER_ID, this@MainActivity).toInt()
            //GlobalParam.vUserName = funcao.GetPref(Constants.USER_NAME, this@MainActivity)
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_src_denunciations) {
            chamaIntent(2)
            //Toast.makeText(this,"???",Toast.LENGTH_SHORT).show()

        } else if (id == R.id.nav_denounce) {
            chamaIntent(1)

        } else if (id == R.id.nav_my_denunciations) {
            chamaIntent(3)
        } else if (id == R.id.nav_manage) {
            chamaIntent(3)
        } else if (id == R.id.nav_share) {
            chamaIntent(4)
        } else if (id == R.id.logoff) {
            fu_logOff()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun chamaIntent(x:Int){
        var acess = false
        when(x){
            1 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if( (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) or
                        (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){

                        ActivityCompat.requestPermissions(this@MainActivity,
                                arrayOf(Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE),
                                0)

                    }else{
                        acess = true
                    }
                }else{
                    acess = true
                }

                if (acess){
                    startActivity(Intent(this@MainActivity, Ac_Denunciar::class.java))
                }
            }
            2 -> {
                val intent = Intent(this@MainActivity, search_problems::class.java)
                startActivity(intent)
                //Toast.makeText(this@MainActivity,"opc 2",Toast.LENGTH_SHORT).show()
            }
            3 -> {
                val intent = Intent(this@MainActivity,Ac_Lista_Denuncias::class.java)
                startActivity(intent)
                //Toast.makeText(this@MainActivity,"opc 2",Toast.LENGTH_SHORT).show()
            }
            4 -> {
                val intent = Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(intent)
                //Toast.makeText(this@MainActivity,"opc 4",Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this@MainActivity,"opc não encontrada: ${x}",Toast.LENGTH_SHORT).show()
            }
        }

        /*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(this@MainActivity,
                            arrayOf(Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE),
                            0);

                }else{
                    chamaIntent(1)
                }
            }else{
                chamaIntent(1)
            }
        */

    }

    fun fu_logOff(){
        var funcao = Funcoes();
        funcao.SetPref(Constants.USER_TOKEN, "",this@MainActivity)
        funcao.SetPref(Constants.USER_NAME, "",this@MainActivity)
        funcao.SetPref(Constants.USER_ID, "",this@MainActivity)

        chamaIntent(4) //tela login
        finish()
    }
    /*
    fun Get_SP ():String {
        var pref = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        var value = pref.getString(Constants.KEY_LOGIN,"") //NOTHING!!!

        return  value;
        //Toast.makeText(this@MainActivity, value, Toast.LENGTH_SHORT).show()
    }

    fun Save_SP(v:View) {
        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        var editor = pref.edit()

        //save infos
        editor.putString(Constants.KEY_LOGIN,"")
        editor.commit()

        Toast.makeText(this@MainActivity, "SAVE OK", Toast.LENGTH_SHORT).show()
    }
    */
}
