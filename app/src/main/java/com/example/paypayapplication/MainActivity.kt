package com.example.paypayapplication

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paypayapplication.adapter.CurrencyExcangesAdapter
import com.example.paypayapplication.networking.CurrencyService
import com.example.paypayapplication.networking.repository.MainRepository
import com.example.paypayapplication.networking.response.CurrencyList
import com.example.paypayapplication.networking.response.CurrencyLiveResponse
import com.example.paypayapplication.viewmodel.CurrencyAPIViewModel
import com.example.paypayapplication.viewmodel.CurrencyAPIViewModelFactory
import com.google.gson.Gson
import com.jaredrummler.materialspinner.MaterialSpinner


class MainActivity : AppCompatActivity() {

    private val TAG: String =
        MainActivity::class.java.getSimpleName()

    var base_url = "http://api.currencylayer.com/"
    var access_key = "8be5579d5df8a754aabd86bdc65836fe"

    val currencyValues : ArrayList<String> = ArrayList()
    val currencyKeys : ArrayList<String> = ArrayList()
    var spinner : MaterialSpinner ?= null
    var listMap: HashMap<String, String> = HashMap()
    var liveCurrencyMap : HashMap<String, Double> ?= HashMap()
    var exchangeList : RecyclerView ?= null
    val finalPrice : ArrayList<Double> = ArrayList()
    val finalValues : ArrayList<String> = ArrayList()

    var spinnerPosition : Int = 0

    lateinit var viewModel : CurrencyAPIViewModel
    private val currencyService : CurrencyService = CurrencyService.getInstance()

    var currLstRecd : Boolean = false
    var currLiveRecd : Boolean = false

    lateinit var progressCircular : ProgressBar
    lateinit var amountText : EditText
    var amount: Double = 0.0
    var divisor : Double = 1.0
    lateinit var displayValue : TextView

    var handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this,
            CurrencyAPIViewModelFactory(MainRepository(currencyService)))
            .get(CurrencyAPIViewModel::class.java)

        viewModel.currencyList.observe(this, Observer {
            processCurrencyList(it)
        })

        viewModel.currencyLiveResponse.observe(this, Observer {
            processCurrencyLiveResponse(it)
        })

       callAPI()
    }

    override fun onResume() {
        super.onResume()

        displayValue = findViewById<View>(R.id.display_value) as TextView
        amountText = findViewById<View>(R.id.amount_text) as EditText

        amountText.setOnEditorActionListener {view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                    keyEvent == null ||
                    keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                //User finihsed typing
                var value = amountText.text
                amount = value.toString().toDouble()
                Log.d(TAG, "sx1 EditAction Listener "+amount)
                hideSoftKeyboard()
                setAdapter()
                true
            }
            false
        }

        spinner = findViewById<View>(R.id.currency_selector_spinner) as MaterialSpinner

        currencyValues.add("select One")
        currencyKeys.add("select One")

        exchangeList = findViewById<View>(R.id.exchanges_list) as RecyclerView
        progressCircular = findViewById<View>(R.id.progress_circular) as ProgressBar

        setSpinner()

    }

    fun hideSoftKeyboard(){
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }



    internal fun setSpinner() {

        Log.d(TAG, "setSpinner")

        spinner?.setItems(currencyValues)
        spinner?.setOnItemSelectedListener(object :
            MaterialSpinner.OnItemSelectedListener<String>{
            override fun onItemSelected(
                view: MaterialSpinner?,
                p2: Int,
                id: Long,
                item: String?
            ) {
                //TODO("Not yet implemented")
                if(p2 > 0) {
                    spinnerPosition = p2
                    var val1 : Double? = liveCurrencyMap?.get(currencyKeys.get(p2))
                    if(val1 != null)
                        divisor = val1
                    finalPrice.clear()
                    finalValues.clear()

                    amount = (amountText.text).toString().toDouble()

                    for(key in listMap.entries){
                        Log.d(TAG, "sx1 listMap "+key.key + " "+key.value)
                    }
                    setAdapter()
                }
            }

        })

        }

     internal fun setAdapter(){

         if(currLiveRecd && currLiveRecd) {
             progressCircular.visibility  = View.GONE
             exchangeList?.layoutManager = LinearLayoutManager(this)

             finalPrice.clear()
             finalValues.clear()

             val sAmount = amount.toString()

             if(spinnerPosition == 0)
                 displayValue.setText(getString(R.string.default_config, sAmount, "USD"))
             else
                 displayValue.setText(getString(R.string.default_config, sAmount, currencyKeys.get(spinnerPosition)))


             for(key in liveCurrencyMap?.entries!!) {
                 //if(val1 != null) {
                 Log.d(TAG, "sx1 setAdapter Map " + key.key + "  " + (key.value)
                 + listMap.get(key.key).toString())
                 finalPrice.add(Math.floor( ((key.value)*amount / divisor) * 10000)/10000)
                 finalValues.add(listMap.get(key.key).toString())
             }


             exchangeList?.adapter = CurrencyExcangesAdapter(finalValues, finalPrice)
         }
     }

    internal fun callAPI() {
        viewModel.getCurrencyList()

        handler.postDelayed(
            Runnable {
                viewModel.getLiveCurrencyPrices()
            }, 5000)

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }


    internal fun processCurrencyList(currencyList : CurrencyList){
        val gson = Gson()

        Log.d(TAG, "sx1 processCurrencyList")
        var jsonObj = gson.toJson(currencyList.currencies)
        var string = jsonObj.toString()

        string = string.substring(1, string.length-1)



        var stringList = string.split(',')
        currencyValues.clear()
        currencyKeys.clear()

        currencyKeys.add("select key")
        currencyValues.add("select value")

        listMap.clear()

        for(i in 0.. stringList.size-1){
            Log.d(TAG, "sx1 List "+stringList[i])
            var stringList1 = stringList[i].split(':')
            currencyKeys.add(stringList1[0].substring(1, stringList1[0].length-1))
            currencyValues.add(stringList1[1].substring(1, stringList1[1].length-1))
            listMap.put(stringList1[0].substring(1, stringList1[0].length-1),
                    stringList1[1].substring(1, stringList1[1].length-1))

        }

        if(currencyKeys.size > 0) {
            setSpinner()
        }

        currLstRecd = true;
        setAdapter()

    }

    internal fun processCurrencyLiveResponse(currencyLiveResponse: CurrencyLiveResponse){
        val gson = Gson()
        var jsonObj = gson.toJson(currencyLiveResponse.quotes)

        var string = jsonObj.toString()

        string = string.substring(1, string.length-1)


        var stringList = string.split(',')

        for(i in 0.. stringList.size-1){
            Log.d(TAG, "sx1 List "+stringList[i])
            var stringList1 = stringList[i].split(':')
            liveCurrencyMap?.put(stringList1[0].substring(4, stringList1[0].length-1),stringList1[1].toDouble())

        }

        amount = amountText.text.toString().toDouble()

        finalPrice.clear()
        for(key in liveCurrencyMap?.entries!!) {
            //if(val1 != null) {
                Log.d(TAG, "sx1 Map " + key.key + "  " + (key.value) )
                finalPrice.add(Math.floor( ((key.value)*amount ) * 10000)/10000)
                //Log.d(TAG, "sx1 finalPrice "+finalPrice.get())
            //}
        }

        currLiveRecd = true
        setAdapter()

    }

}





