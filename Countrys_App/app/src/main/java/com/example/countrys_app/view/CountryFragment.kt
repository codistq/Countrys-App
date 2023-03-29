package com.example.countrys_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.example.countrys_app.R
import com.example.countrys_app.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var viewModel : CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom()



        arguments?.let {
            //Unik ID yi aldık.
            //Feedfragmenttan Uuid değerini alabilceğim bir değişken tanımladık.
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
        country?.let {
            countryName.text = country.countryName
            countryCapital.text = country.countryCapital
            countryRegion.text = country.countryRegion
            countryCurrency.text = country.countryCurrency
            countryLanguage.text = country.countryLanguage

        }

        })
    }


   }

