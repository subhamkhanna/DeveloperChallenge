package com.example.paypayapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paypayapplication.networking.repository.MainRepository
import java.lang.IllegalArgumentException

class CurrencyAPIViewModelFactory constructor(private val repository: MainRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //TODO("Not yet implemented")
        return if (modelClass.isAssignableFrom(CurrencyAPIViewModel::class.java)){
            CurrencyAPIViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("View Model Not Found")
        }
    }

}