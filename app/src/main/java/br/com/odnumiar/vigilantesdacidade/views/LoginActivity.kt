package br.com.odnumiar.vigilantesdacidade.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import br.com.odnumiar.vigilantesdacidade.R
import android.app.ProgressDialog
import android.preference.PreferenceManager
import android.widget.*
import br.com.odnumiar.vigilantesdacidade.util.Constants
import br.com.odnumiar.vigilantesdacidade.models.AsyncCallback
import br.com.odnumiar.vigilantesdacidade.models.Login
import br.com.odnumiar.vigilantesdacidade.mvp.ConnectionService
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(){

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
            Toast.makeText(this@LoginActivity,"Autenticação inválida, preencha os campos",Toast.LENGTH_SHORT).show()
            hideProgressDialog()

        }else{
            var login = Login(etEmail.text.toString(),
                    etPassword.text.toString(),
                    "")

            login.pass =  login.pass.hashCode().toString()

            var conn = ConnectionService()
            conn.requestLogin2(login, this,
                    object : AsyncCallback() {
                        override fun onSuccess(result:String){
                            Toast.makeText(this@LoginActivity,"resulto:"+result,Toast.LENGTH_SHORT).show()
                            //SetPrefToken(login.token)
                            //showResult(result)
                            hideProgressDialog()
                        }

                        override fun onFailure(result: String) {
                            Toast.makeText(this@LoginActivity,"Autenticação inválida:"+result,Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this@LoginActivity,"Autenticação inválida!",Toast.LENGTH_SHORT).show()
        }
        */
    }

    fun fu_cadastro(v:View){
        Toast.makeText(this@LoginActivity,"Cadastro!",Toast.LENGTH_SHORT).show()
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

        Toast.makeText(this@LoginActivity, "Login OK", Toast.LENGTH_SHORT).show()
    }



    fun Save_SP(v:View) {
    }

}

