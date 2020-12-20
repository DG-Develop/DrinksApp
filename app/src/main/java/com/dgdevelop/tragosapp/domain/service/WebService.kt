package com.dgdevelop.tragosapp.domain.service

import com.dgdevelop.tragosapp.data.model.DrinkList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    /* En este apartado la cosulta original para  traer un solo resultado es:
    *  search.php/s=tragoName
    *  pero es lo mismo que poner la letra s en una Query y pasarle valor por un strin
    * */
    @GET("search.php")
    suspend fun getTragoByName(@Query(value = "s") tragoName: String): DrinkList?
}