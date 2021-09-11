package com.example.paypayapplication.networking.repository

import com.example.paypayapplication.networking.CurrencyService

class MainRepository constructor(private val currencyService: CurrencyService) {

    fun getAllCurrencyList() = currencyService.
    getCurrenciesList("8be5579d5df8a754aabd86bdc65836fe")

    fun getLiveCurrencyPrice() = currencyService.
    getLiveCurrencies("8be5579d5df8a754aabd86bdc65836fe", "USD")

}