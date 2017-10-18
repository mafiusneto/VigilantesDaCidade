package br.com.odnumiar.vigilantesdacidade.mvp

import android.content.Context
import br.com.odnumiar.vigilantesdacidade.models.AsyncCallback
import br.com.odnumiar.vigilantesdacidade.models.Login
import br.com.odnumiar.vigilantesdacidade.util.Constants
import br.com.odnumiar.vigilantesdacidade.util.SessionConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Neto on 17/10/2017.
 */
class ConnectionService {

    fun requestLogin(login:Login, context: Context, callback: AsyncCallback) {
        var URL :String = ""
        if (login.user == "netolbv@gmail.com" && login.pass == "123"){ //teste login sucesso
            URL = Constants.URL_LOGIN_OK

        }else{  //teste login invalido
            URL = Constants.URL_LOGIN_ERRO

        }

        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(SessionConnection::class.java)

        var auth = service.login(login)

        auth.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>?) {
                response?.let {
                    response.body()?.let {

                        val result :Login =  response.body() as Login //é um Post

                        var str  =      result

                                        /*
                                        "token:${result?.token}\n" +
                                        "id:${result?.id}\n" +
                                        "title:${result?.title}\n" +
                                        "body:${result?.body} "
                                        */

                        callback.onSuccess(str)

                    } ?: run {
                        //mainPresenter.result(context.getString(R.string.error))
                    }
                } ?: run {
                    //mainPresenter.result(context.getString(R.string.error))
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                //.result(context.getString(R.string.error))
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