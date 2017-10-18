package br.com.odnumiar.vigilantesdacidade.models

import java.util.*

/**
 * Created by Neto on 17/10/2017.
 */
class Post(var id :Long,
           var descrition :String,
           var gps_lat :Float,
           var gps_log :Float,
           var image :String,
           var user_id :Long,
           var date_post :String) {}