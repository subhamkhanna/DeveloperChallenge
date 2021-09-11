package com.example.paypayapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paypayapplication.networking.CurrencyService
import com.example.paypayapplication.networking.repository.MainRepository
import com.example.paypayapplication.networking.response.CurrencyList
import com.example.paypayapplication.networking.response.CurrencyLiveResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyAPIViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val TAG: String = CurrencyAPIViewModel::class.java.simpleName

    var currencyList = MutableLiveData<CurrencyList>()
    var currencyListError = MutableLiveData<String>()

    var currencyLiveResponse = MutableLiveData<CurrencyLiveResponse>()
    var currencyLiveError = MutableLiveData<String>()


    fun getCurrencyList(){
        val call = mainRepository.getAllCurrencyList()
        call.enqueue(object : Callback<CurrencyList> {
            override fun onResponse(
                call: Call<CurrencyList>?,
                response: Response<CurrencyList>?
            ) {
                Log.d(TAG, "sx1  11");
                if(response != null)
                    currencyList.postValue(response.body())

              //  val gson = Gson()
                //Log.d("$TAG sx1", "response  " + gson.toJson(response!!.body()))

                //var jsonObj = gson.toJson(response.body().currencies)

                //var string = jsonObj.toString()

                //var string1 = string.substring(1, string.length-1)


                //Log.d(TAG, "sx1 jsonObject "+string1)

                //var stringList = string1.split(',')
                //var map : HashMap<String, String> ?= HashMap()
                //currencyValues.clear()
                //currencyKeys.clear()

                //currencyKeys.add("select key")
                //currencyValues.add("select value")

                //for(i in 0.. stringList.size-1){
                  //  Log.d(TAG, "sx1 List "+stringList[i])
                    //var stringList1 = stringList[i].split(':')
                    //map?.put(stringList1[0],stringList1[1])
                    //currencyKeys.add(stringList1[0].substring(1, stringList1[0].length-1))
                    //currencyValues.add(stringList1[1].substring(1, stringList1[1].length-1))

                //}

                /*for(key in map?.entries!!) {
                    Log.d(TAG, "sx1 Map "+key.key+ "  "+key.value)

                }*/

                //for(i in 0..currencyKeys.size-1) {
                  //  Log.d(TAG, "Currency List sx1 $i "+currencyKeys.get(i)+ " "
                    //        +currencyValues.get(i))
                //}

                //if(currencyKeys.size > 0) {
                  //  setSpinner()
                //}

            }

            override fun onFailure(call: Call<CurrencyList>?, t: Throwable?) {
                Log.d(TAG, "sx1  12 "+t.toString());
                currencyListError.postValue(t?.message)
            }
        })


    }

    fun getLiveCurrencyPrices(){
        val call = mainRepository.getLiveCurrencyPrice()

        call.enqueue(object : Callback<CurrencyLiveResponse> {
            override fun onResponse(
                call: Call<CurrencyLiveResponse>?,
                response: Response<CurrencyLiveResponse>?
            ) {
                Log.d(TAG, "sx1  11");
                if(response != null)
                    currencyLiveResponse.postValue(response.body())

               // val gson = Gson()
              //  Log.d("$TAG sx1", "response  " + gson.toJson(response!!.body()))
              //  var jsonObj = gson.toJson(response.body().quotes)
                /* val map: java.util.HashMap<*, *>? = Gson().fromJson(
                     jsonObj.toString(),
                     HashMap::class.java
                 )*/

             //   var string = jsonObj.toString()

             //   var string1 = string.substring(1, string.length-1)


            //    Log.d(TAG, "sx1 jsonObject "+string1)

             //   var stringList = string1.split(',')
                // var liveCurrencyMap : HashMap<String, Double> ?= HashMap()

            //    for(i in 0.. stringList.size-1){
            //        Log.d(TAG, "sx1 List "+stringList[i])
             //       var stringList1 = stringList[i].split(':')
             //       liveCurrencyMap?.put(stringList1[0].substring(4, stringList1[0].length-1),stringList1[1].toDouble())

             //   }

            //    for(key in liveCurrencyMap?.entries!!) {
              //      Log.d(TAG, "sx1 Map "+key.key+ "  "+key.value)
             //   }

            }

            override fun onFailure(call: Call<CurrencyLiveResponse>?, t: Throwable?) {
                Log.d(TAG, "sx1  12 "+t.toString());
                currencyLiveError.postValue(t?.message)
           }
        })


    }


}