package br.com.odnumiar.vigilantesdacidade.util

import br.com.odnumiar.vigilantesdacidade.models.Login
import br.com.odnumiar.vigilantesdacidade.models.User
import br.com.odnumiar.vigilantesdacidade.orm.Posts
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by Neto on 17/10/2017.
 */
interface SessionConnection {

    @POST("login.php/")
    fun login2(@Body login: Login): Call<User>

    @POST("user.php/")
    fun cadastro(@Body user:User): Call<User>


    @GET("posts.php/")
    fun requestPosts(): Call<List<Posts>>

    @POST("posts.php/")
    @Multipart
    fun sendNewPost( @Part image2 :MultipartBody.Part, @Part("posts") post:Posts): Call<Posts> //, @Part("image2") image2:MultipartBody.Part
    //fun sendNewPost(@Body post:Posts): Call<Posts>


    //EXEMPLOS--------------------------------------------------------------------------------
    /*
    @GET("posts/{id}")
    fun requestPostsGet(@Path("id")id:Int): Call<Post>


    @GET("login")
    fun login(@Body user:User): Call<User>


    internal interface Service {
        @Multipart
        @POST("/")
        fun postImage(@Part image: MultipartBody.Part, @Part("name") name: RequestBody): Call<ResponseBody>
    }
    */
}
