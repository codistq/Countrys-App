package com.example.countrys_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countrys_app.model.Country

class CountryViewModel : ViewModel() {

    val countryLiveData  = MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country = Country("Turkey","Europe",
        "Ankara","TRY","Turkish","www.google.com")

        countryLiveData.value = country


    }

}