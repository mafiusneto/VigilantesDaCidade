package br.com.odnumiar.vigilantesdacidade.util

import br.com.odnumiar.vigilantesdacidade.models.Login
import br.com.odnumiar.vigilantesdacidade.models.Post
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
    fun requestPostsGet(@Path("id")id:Int): Call<Post>

    @POST("posts")
    fun sendNewPost(@Body post:Post): Call<Post>

    @GET("login")
    fun login(@Body login:Login): Call<Login>

    @POST("login.php/")
    fun login2(@Body login:Login): Call<Login>
}