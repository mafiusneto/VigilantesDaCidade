package br.com.odnumiar.vigilantesdacidade.views

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.odnumiar.vigilantesdacidade.MainActivity
import br.com.odnumiar.vigilantesdacidade.R
import br.com.odnumiar.vigilantesdacidade.models.AsyncCallback
import br.com.odnumiar.vigilantesdacidade.models.User
import br.com.odnumiar.vigilantesdacidade.mvp.ConnectionService
import br.com.odnumiar.vigilantesdacidade.util.Constants
import br.com.odnumiar.vigilantesdacidade.util.Funcoes
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : Activity() {

    val progress: ProgressDialog by lazy { ProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    fun fu_cadastrar(v:View){
        showProgressDialog()
        if ((etEmail_01.text.toString() == "") || (etPassword_01.text.toString() == "") || (etName_01.text.toString() == "") ) {
            Toast.makeText(this@CadastroActivity,"Preencha todos os campos", Toast.LENGTH_SHORT).show()
            hideProgressDialog()

        }else{
            var user = User(etName_01.text.toString(),
                    etPassword_01.text.toString().hashCode().toString(),
                    etEmail_01.text.toString(),
                    "",
                    0
                    );
            /*
            var login = Login(etEmail.text.toString(),
                    etPassword.text.toString(),
                    "")

            login.pass =  login.pass.toString()
            */
            var conn = ConnectionService()

            conn.fu_requestCadastro(user, this@CadastroActivity,
                    object : AsyncCallback() {
                        override fun onSuccessLogin(result:User){
                            Toast.makeText(this@CadastroActivity,"Cadastrado:"+result.name, Toast.LENGTH_SHORT).show()

                            var funcao = Funcoes()
                            funcao.SetPrefToken(Constants.USER_NAME, result.name, this@CadastroActivity)
                            funcao.SetPrefToken(Constants.USER_ID, result.id.toString(), this@CadastroActivity)
                            funcao.SetPrefToken(Constants.USER_TOKEN, result.token, this@CadastroActivity)
                            funcao.SetPrefToken(Constants.KEY_LOGIN, result.token, this@CadastroActivity)

                            val intent = Intent(this@CadastroActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            //SetPref Token(login.token)
                            //showResult(result)
                            //hideProgressDialog()
                        }

                        override fun onFailure(result: String) {
                            Toast.makeText(this@CadastroActivity,"Autenticação inválida:"+result, Toast.LENGTH_SHORT).show()
                            //showResult(result)
                            hideProgressDialog()
                        }
                    })

            hideProgressDialog()


        }
    }fun showProgressDialog() {
        progress.setCancelable(false)
        progress.setMessage("Loading ...")
        progress.show()
    }

    fun hideProgressDialog() {
        progress.dismiss()
    }
}
