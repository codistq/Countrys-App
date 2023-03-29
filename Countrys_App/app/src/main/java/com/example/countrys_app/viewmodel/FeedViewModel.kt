package com.example.countrys_app.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countrys_app.model.Country
import com.example.countrys_app.service.CountryAPIService
import com.example.countrys_app.service.CountryDatabase
import com.example.countrys_app.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

//Her Fragment'ın bir ViewModel'ı olur.
class FeedViewModel(application: Application) : BaseViewModel(application) {
    private val countryAPIService  = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences  = CustomSharedPreferences(getApplication())
    private var refreshTime = 10*60*1000*1000*1000L


    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData(){
        val updateTime = customPreferences.getTime()
        if(updateTime !=null && updateTime !=0L &&System.nanoTime() - updateTime <refreshTime){
            getDataFromSQLite()
        }else{

            getDataFromAPI()
        }
    }

    private fun getDataFromSQLite(){
        launch {
            val countries  = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_SHORT).show()
        }
    }

    fun getDataFromAPI(){

        countryLoading.value = true

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                    }
                })

        )
    }


    private fun showCountries(countryList : List<Country>){
        countries.value = countryList
        countryLoading.value = false
        countryError.value = false
    }

    private fun storeInSQLite(list : List<Country>){

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
           val listLong =  dao.insertAll(*list.toTypedArray())  //-->individual --> Listeyi bir eleman haline getiriyor.

            var i = 0
            while(i <list.size){
                list[i].uuid = listLong[i].toInt()
                i += 1
            }
            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }


}