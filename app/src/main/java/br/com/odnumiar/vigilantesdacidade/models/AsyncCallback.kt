package br.com.odnumiar.vigilantesdacidade.models

import br.com.odnumiar.vigilantesdacidade.orm.Posts

/**
 * Created by Neto on 17/10/2017.
 */
open class AsyncCallback {
    open fun onSuccess(result:User) {}
    open fun onSuccess(result:Posts) {}
    open fun onSuccess(result:List<Posts>) {}
    open fun onFailure(result:String) {}
    open fun onSuccessLogin(result:User) {}
}