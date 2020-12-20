package com.dgdevelop.tragosapp.domain

import com.dgdevelop.tragosapp.data.DataSource
import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(private val dataSource: DataSource) : Repo {

    override suspend fun getTragosList(tragoName: String): Resource<List<Drink>> =
        dataSource.getTragoByName(tragoName)!!

    override suspend fun getTragosFavoritos(): Resource<MutableList<Drink>> =
        dataSource.getTragosFavoritos()

    override suspend fun insertTrago(trago: DrinkEntity) {
        dataSource.insertTragoIntoRoom(trago)
    }

    override suspend fun deleteDrink(drink: DrinkEntity) {
        dataSource.deleteDrink(drink)
    }
}