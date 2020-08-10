package com.aslanovaslan.currency.Services
import com.aslanovaslan.currency.Model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
interface CryptoAPI {
    // GET

    @GET("prices?key=3e6678c115c43cf69e4971f775b3e45d")
    fun getData():Observable<List<CryptoModel>>
    /*
       @GET("prices?key=3e6678c115c43cf69e4971f775b3e45d")
       fun getData():Call<List<CryptoModel>>

    suspend fun getData():Response<List<CryptoModel>> */
}