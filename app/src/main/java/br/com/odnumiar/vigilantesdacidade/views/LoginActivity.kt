package br.com.odnumiar.vigilantesdacidade.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks

import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask

import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.EditorInfo

import java.util.ArrayList

import br.com.odnumiar.vigilantesdacidade.R

import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.widget.*
import br.com.odnumiar.vigilantesdacidade.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(){

    // UI references.
    private var mEmailView: AutoCompleteTextView? = null
    private var mPasswordView: EditText? = null
    private var mProgressView: View? = null
    private var mLoginFormView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.

    }

    fun fu_logar(v:View){
        if (etEmail.text.toString() == "123" && etPassword.text.toString() == "123") {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }else{
            Toast.makeText(this@LoginActivity,"Autenticação inválida!",Toast.LENGTH_SHORT).show()
        }
    }

}

