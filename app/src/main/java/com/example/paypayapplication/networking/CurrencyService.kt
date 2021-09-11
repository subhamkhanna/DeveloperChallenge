package com.example.paypayapplication.networking

import com.example.paypayapplication.networking.response.CurrencyList
import com.example.paypayapplication.networking.response.CurrencyLiveResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

   // var base_url =
  //  var access_key = "8be5579d5df8a754aabd86bdc65836fe"

    @GET("list?")
    fun getCurrenciesList(@Query("access_key") access_key : String) : Call<CurrencyList>


    @GET("live?")
    fun getLiveCurrencies(@Query("access_key") access_key : String,
                          @Query("source") source : String) : Call<CurrencyLiveResponse>



    companion object {

        var currencyService: CurrencyService? = null

        fun getInstance() : CurrencyService {

            if (currencyService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://api.currencylayer.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    //.client(client)
                    .build()
                currencyService = retrofit.create(CurrencyService::class.java)
            }
            return currencyService!!
        }
    }


}