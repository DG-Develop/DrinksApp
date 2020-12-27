package com.dgdevelop.tragosapp.data

import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.vo.Resource

interface DataSource {

    /* Esta funcion estara llendo a buscar datos en la api por lo que puede que haya datos
    *  incorrectos y por ende traiga datos nulos por eso marcamos el Null safety en esta funcion*/
    suspend fun getTragoByName(tragoName: String): Resource<List<Drink>>?

    suspend fun insertTragoIntoRoom(trago: DrinkEntity)

    suspend fun getTragosFavoritos(): Resource<List<Drink>>

    suspend fun deleteDrink(drink: DrinkEntity)
}