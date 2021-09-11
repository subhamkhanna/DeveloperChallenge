package com.example.paypayapplication.networking.response

import com.google.gson.annotations.SerializedName

class CurrencyList (

        @SerializedName("success") val success : Boolean,
        @SerializedName("terms") val terms : String,
        @SerializedName("privacy") val privacy : String,
        @SerializedName("currencies") val currencies : Currencies
)