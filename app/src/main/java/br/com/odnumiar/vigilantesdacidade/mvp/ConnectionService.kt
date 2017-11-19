package br.com.odnumiar.vigilantesdacidade.mvp

import android.content.Context
import android.graphics.Bitmap
import br.com.odnumiar.vigilantesdacidade.models.AsyncCallback
import br.com.odnumiar.vigilantesdacidade.models.Login
import br.com.odnumiar.vigilantesdacidade.models.User
import br.com.odnumiar.vigilantesdacidade.orm.Posts
import br.com.odnumiar.vigilantesdacidade.util.Constants
import br.com.odnumiar.vigilantesdacidade.util.SessionConnection
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream


class ConnectionService {

    init{
        //Log.d("DEBUG_VC", "executado init de ConnectionService")
    }

    constructor(){
        //Log.d("DEBUG_VC", "executando construtor de ConnectionService")
    }

    fun sendPost(post:Posts, image:Bitmap,  context: Context, callback: AsyncCallback){
        var URL :String = Constants.URL_ROOt

        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(SessionConnection::class.java)

        val bos: ByteArrayOutputStream = ByteArrayOutputStream();
        if (image.compress(Bitmap.CompressFormat.PNG, 100, bos)){ //quality 0

            //Convert bitmap to byte array
            var bitmapdata :ByteArray = bos.toByteArray()
            // build request containing file
            val fileBody = RequestBody.create(MediaType.parse("image/jpeg"), bitmapdata) //multipart/form-data
            val filePart = MultipartBody.Part.createFormData("picture", "picture.jpg", fileBody)

            //val fileBody2 = RequestBody.create(okhttp3.MultipartBody.FORM, post.toString())


            var auth = service.sendNewPost(filePart, post ) //fileBody2, filePart

            auth.enqueue(object : Callback<Posts> {

                override fun onResponse(call: Call<Posts>, response: Response<Posts>?) {
                    response?.let {
                        response.body()?.let {

                            val result :Posts =  response.body() as Posts //é um Post

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

                override fun onFailure(call: Call<Posts>, t: Throwable) {
                    //.result(context.getString(R.string.error))

                    callback.onFailure("Erro 3")
                }
            })
        }


    }

    fun requestPosts(id:Int,  context: Context, callback: AsyncCallback){
        var URL :String = Constants.URL_ROOt

        val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(SessionConnection::class.java)

        var auth = service.requestPosts()
        auth.enqueue(object : Callback<List<Posts>> {

            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>?) {
                response?.let {
                    response.body()?.let {

                        val result :List<Posts> =  response.body() as List<Posts>

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

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                //.result(context.getString(R.string.error))

                callback.onFailure("Erro 3")
            }
        })

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