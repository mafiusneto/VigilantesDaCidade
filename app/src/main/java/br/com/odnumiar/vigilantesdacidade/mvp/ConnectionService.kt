package br.com.odnumiar.vigilantesdacidade.mvp

import android.content.Context
import android.util.Log
import br.com.odnumiar.vigilantesdacidade.models.AsyncCallback
import br.com.odnumiar.vigilantesdacidade.models.Login
import br.com.odnumiar.vigilantesdacidade.models.User
import br.com.odnumiar.vigilantesdacidade.util.Constants
import br.com.odnumiar.vigilantesdacidade.util.SessionConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConnectionService {

    init{
        Log.d("DEBUG_VC", "executado init de ConnectionService")
    }

    constructor(){
        Log.d("DEBUG_VC", "executando construtor de ConnectionService")
    }

    fun requestLogin(user:User, context: Context, callback: AsyncCallback) {
        var URL :String = Constants.URL_ROOt

        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(SessionConnection::class.java)

        var l = Login()
        //l.to=user.name
        l.pass=user.pass
        l.email=user.email

        var auth = service.login2(l)

        auth.enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>?) {
                response?.let {
                    response.body()?.let {

                        val result :User =  response.body() as User //é um Post

                        //var str  =      result.token

                        callback.onSuccess(result)

                    } ?: run {
                        callback.onFailure("Erro 1")
                        //mainPresenter.result(context.getString(R.string.error))
                    }
                } ?: run {
                    //mainPresenter.result(context.getString(R.string.error))
                    callback.onFailure("Erro 2")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                //.result(context.getString(R.string.error))

                callback.onFailure("Erro 3")
            }
        })
    }

    fun fu_requestCadastro(user:User, context: Context, callback: AsyncCallback) {
        var URL :String = Constants.URL_ROOt

        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(SessionConnection::class.java)

        var auth = service.cadastro(user)

        auth.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>?) {
                response?.let {
                    response.body()?.let {

                        val result :User =  response.body() as User //é um Post

                        //var str  =      result.token
                        //callback.onSuccess(str)
                        callback.onSuccessLogin(result)

                    } ?: run {
                        callback.onFailure("Errrooooooo1")
                        //mainPresenter.result(context.getString(R.string.error))
                    }
                } ?: run {
                    //mainPresenter.result(context.getString(R.string.error))
                    callback.onFailure("Errrooooooo2")
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                callback.onFailure("Errrooooooo3")
            }

        })
    }

    /*
    fun sendNewPost(post:Post, context: Context, callback: AsyncCallback) {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.URL_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(SessionConnection::class.java)

        var auth = service.sendNewPost(post)

        auth.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>?) {
                response?.let {
                    response.body()?.let {
                        val result = response.body()

                        var str =
                                "postId:${result?.postId}\n" +
                                        "id:${result?.id}\n" +
                                        "title:${result?.title}\n" +
                                        "body:${result?.body} "

                        callback.onSuccess(str)

                    } ?: run {
                        callback.onFailure(context.getString(R.string.error))
                    }
                } ?: run {
                    callback.onFailure(context.getString(R.string.error))
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onFailure(context.getString(R.string.error))
            }
        })
    }
    */
}