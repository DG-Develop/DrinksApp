package com.dgdevelop.tragosapp.data

import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.vo.Resource

interface DataSource {

    suspend fun getTragoByName(tragoName: String): Resource<List<Drink>>

    suspend fun insertTragoIntoRoom(trago: DrinkEntity)

    suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>>

    suspend fun deleteDrink(drink: DrinkEntity)
}