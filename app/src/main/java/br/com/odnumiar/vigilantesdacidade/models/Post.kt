package br.com.odnumiar.vigilantesdacidade.models

import java.io.File

/**
 * Created by Neto on 20/10/2017.
 */
class Post(var postId:Int,
           var userId:Int,
           var description:String,
           var image:String,
           var image_file:File,
           var imageL:String,
           var coordenadas:Coordenadas)