package com.aslanovaslan.currency.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.ERROR
import android.util.LogPrinter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslanovaslan.currency.Adapter.CurrencyAdapter
import com.aslanovaslan.currency.Model.CryptoModel
import com.aslanovaslan.currency.R
import com.aslanovaslan.currency.Services.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CurrencyAdapter.Listener {

    private val BASE_URL = "https://api.nomics.com/v1/"
    private var cryiptoList: ArrayList<CryptoModel>? = null
    private val TAG = "\$className$"
    private var job:Job?=null;
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var compositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeVar()

        getDataFromApi()
    }

    private fun initializeVar() {
        compositeDisposable = CompositeDisposable()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    private fun getDataFromApi() {
        /*    val retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CryptoAPI::class.java)

           job= CoroutineScope(Dispatchers.Main).launch {
               val response=retrofit.getData()
               withContext(Dispatchers.Main){
                   if (response.isSuccessful) {
                       response.body()?.let { it ->
                           cryiptoList= ArrayList(it)
                           cryiptoList?.let {
                               currencyAdapter=CurrencyAdapter(it,this@MainActivity)
                               recyclerView.adapter=currencyAdapter
                           }
                       }
                   }
               }
           }
     */

       val retrofit = Retrofit
           .Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
           .build().create(CryptoAPI::class.java)

       compositeDisposable.add(
           retrofit.getData()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(this::handleResponse)
       )


/*
       val servce = retrofit.create(CryptoAPI::class.java)
       val call = servce.getData()
       call.enqueue(object : Callback<List<CryptoModel>> {
           override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
               t.printStackTrace()
           }

           override fun onResponse(
               call: Call<List<CryptoModel>>,
               response: Response<List<CryptoModel>>
           ) {
               if (response.isSuccessful) {
                   response.body()?.let { it ->
                       cryiptoList = ArrayList(it)
                       cryiptoList?.let {
                           currencyAdapter = CurrencyAdapter(it, this@MainActivity)
                           recyclerView.adapter=currencyAdapter
                       }
                       /*
                       for (cryptoModel:CryptoModel in cryiptoList!!) {
                           println(cryptoModel.currency.toString())
                           println(cryptoModel.price)
                       }
                        */
                   }
               }
           }

       })

*/
   }

   private fun handleResponse(cryptoModelList: List<CryptoModel>) {
       cryiptoList = ArrayList(cryptoModelList)
       cryiptoList?.let {
           currencyAdapter = CurrencyAdapter(it, this@MainActivity)
           recyclerView.adapter = currencyAdapter
       }
   }

   override fun onClickItem(cryptoModel: CryptoModel) {
       Toast.makeText(applicationContext, cryptoModel.currency, Toast.LENGTH_SHORT).show()

       val intent = Intent(applicationContext, InfoCrypto::class.java)
       intent.putExtra("currency", cryptoModel.currency)
       intent.putExtra("price", cryptoModel.price)
       startActivity(intent)
   }
}
