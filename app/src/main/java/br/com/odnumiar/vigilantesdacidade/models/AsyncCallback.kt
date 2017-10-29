package br.com.odnumiar.vigilantesdacidade.models

/**
 * Created by Neto on 17/10/2017.
 */
open class AsyncCallback {
    open fun onSuccess(result:String) {

    }
    open fun onFailure(result:String) {

    }
    open fun onSuccessLogin(result:User) {

    }
}