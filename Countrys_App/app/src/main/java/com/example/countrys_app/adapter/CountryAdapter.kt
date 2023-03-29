package com.example.countrys_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.countrys_app.R
import com.example.countrys_app.model.Country
import com.example.countrys_app.util.downloadFromUrl
import com.example.countrys_app.util.placeHolderProgressBar
import com.example.countrys_app.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

//Adapter yazma amacımız recylerView'a verileri gönderebilmek.
class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(var view: View): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion

            holder.view.setOnClickListener {
                val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(0)
                Navigation.findNavController(it).navigate(action)
            }

        holder.view.imageView.downloadFromUrl(countryList[position].flag,
            placeHolderProgressBar(holder.view.context)
        )



    }

    //swipe yaptığmız zaman Adaptörün bunu bilmesi lazım

    fun updateCountryList(newCountryLiat : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryLiat)
        notifyDataSetChanged()
    }


}