package br.com.odnumiar.vigilantesdacidade.orm

import com.orm.SugarRecord

/**
 * Created by Neto on 07/11/2017.
 */

class Posts() : SugarRecord(){
    var userId:Int = 0
    var userName:String = ""
    var description:String = ""
    var image:String = ""
    var imageL:String = ""
    var lat:Double = 0.0
    var long:Double = 0.0
    var date:String = ""
}