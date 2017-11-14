package br.com.odnumiar.vigilantesdacidade.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.ProgressDialog
import android.content.Intent
import android.preference.PreferenceManager
import android.widget.*
import br.com.odnumiar.vigilantesdacidade.MainActivity
import br.com.odnumiar.vigilantesdacidade.R
import br.com.odnumiar.vigilantesdacidade.util.Constants
import br.com.odnumiar.vigilantesdacidade.models.AsyncCallback
import br.com.odnumiar.vigilantesdacidade.models.User
import br.com.odnumiar.vigilantesdacidade.mvp.ConnectionService
import br.com.odnumiar.vigilantesdacidade.util.Funcoes
import br.com.odnumiar.vigilantesdacidade.util.GlobalParam
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class Ac_Login : AppCompatActivity(){

    val progress: ProgressDialog by lazy { ProgressDialog(this) }

    // UI references.
    private var mEmailView: AutoCompleteTextView? = null
    private var mPasswordView: EditText? = null
    private var mProgressView: View? = null
    private var mLoginFormView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.

        etEmail.setText("netolbv@gmail.com")
        etPassword.setText("a123")
    }

    fun fu_logar(v:View){

        showProgressDialog()
        if (etEmail.text.toString() == "" || etPassword.text.toString() == "" ) {
            Toast.makeText(this@Ac_Login,"Autenticação inválida, preencha os campos",Toast.LENGTH_SHORT).show()
            hideProgressDialog()

        }else{
            var user = User("",etPassword.text.toString(),etEmail.text.toString(),"",0)

            user.pass =  user.pass.hashCode().toString()

            var conn = ConnectionService()

            conn.requestLogin(user, this@Ac_Login,
                    object : AsyncCallback() {
                        override fun onSuccess(result:User){
                            GlobalParam.vUserToken = ""
                            GlobalParam.vUserId = result.id
                            GlobalParam.vUserName= result.name
                            GlobalParam.vUserToken = result.token

                            Toast.makeText(this@Ac_Login, GlobalParam.vUserToken, Toast.LENGTH_SHORT).show()

                            if (ckbManterConectado.isChecked){
                                var funcao = Funcoes()
                                funcao.SetPref(Constants.USER_TOKEN,GlobalParam.vUserToken,this@Ac_Login)
                                funcao.SetPref(Constants.USER_NAME,GlobalParam.vUserName,this@Ac_Login)
                                funcao.SetPref(Constants.USER_ID,GlobalParam.vUserId.toString(),this@Ac_Login)
                            }

                            val intent = Intent(this@Ac_Login, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            //Toast.makeText(this@Ac_Login,"resulto:"+result,Toast.LENGTH_SHORT).show()
                            //hideProgressDialog()
                        }

                        override fun onFailure(result: String) {
                            Toast.makeText(this@Ac_Login,"Autenticação inválida:"+result,Toast.LENGTH_SHORT).show()
                            //showResult(result)
                            hideProgressDialog()
                        }
                    })

            hideProgressDialog()


        }

        /*
        if (etEmail.text.toString() == "123" && etPassword.text.toString() == "123") {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }else{
            Toast.makeText(this@Ac_Login,"Autenticação inválida!",Toast.LENGTH_SHORT).show()
        }
        */
    }

    fun fu_cadastro(v:View){
        val intent = Intent(this, Ac_Cadastro::class.java)
        startActivity(intent)
        finish()
        //Toast.makeText(this@Ac_Login,"Cadastro!",Toast.LENGTH_SHORT).show()
    }

    fun showProgressDialog() {
        progress.setCancelable(false)
        progress.setMessage("Loading ...")
        progress.show()
    }

    fun hideProgressDialog() {
        progress.dismiss()
    }

    fun SetPrefToken(v :String){
        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        var editor = pref.edit()

        //save infos
        editor.putString(
                Constants.KEY_LOGIN,v)
        editor.commit()

        Toast.makeText(this@Ac_Login, "Login OK", Toast.LENGTH_SHORT).show()
    }



    fun Save_SP(v:View) {
    }

}

