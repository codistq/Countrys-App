package com.example.countrys_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

 abstract class BaseViewModel(application: Application) : AndroidViewModel(application),CoroutineScope {


    private val job  = Job()
    override val coroutineContext: CoroutineContext
            //İşini yap daha sonra Main thread'e dön
        get() = job + Dispatchers.Main

     //Bir şey kapatılırsa eğer işi iptal et.
     override fun onCleared() {
         super.onCleared()
         job.cancel()
     }

}