package br.com.odnumiar.vigilantesdacidade.util

import br.com.odnumiar.vigilantesdacidade.models.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Neto on 17/10/2017.
 */
interface SessionConnection {

    @GET("posts/{id}")
    fun requestPostsGet(@Path("id")id:Int): Call<Login>

    @POST("posts")
    fun sendNewPost(@Body post:Login): Call<Login>

    @POST("login")
    fun login(@Body login:Login): Call<Login>
}