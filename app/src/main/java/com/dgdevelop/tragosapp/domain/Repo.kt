package com.dgdevelop.tragosapp.domain

import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.vo.Resource

interface Repo {
    suspend fun getTragosList(tragoName: String): Resource<List<Drink>>?
    suspend fun getTragosFavoritos(): Resource<MutableList<Drink>>
    suspend fun insertTrago(trago: DrinkEntity)
    suspend fun deleteDrink(drink: DrinkEntity)
}