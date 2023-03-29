package com.example.countrys_app.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countrys_app.model.Country

@Dao
interface CountryDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>
    //Insert -->INSERT INTO
    //suspend -->coeoutine , pause & resume
    //vararg -->multiple country objects
    //List<Long> -->primary Key

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()


}