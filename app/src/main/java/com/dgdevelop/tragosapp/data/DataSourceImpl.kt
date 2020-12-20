package com.dgdevelop.tragosapp.data

import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.domain.DataSource
import com.dgdevelop.tragosapp.domain.TragosDao
import com.dgdevelop.tragosapp.vo.Resource
import com.dgdevelop.tragosapp.vo.RetrofitClient
import javax.inject.Inject

class DataSourceImpl @Inject constructor(private val tragosDao: TragosDao): DataSource {

    override suspend fun getTragoByName(tragoName: String): Resource<List<Drink>>{
        return Resource.Success(RetrofitClient.webservice.getTragoByName(tragoName).drinkList)
    }

    override suspend fun insertTragoIntoRoom(trago: DrinkEntity){
        tragosDao.insertFavorite(trago)
    }

    override suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>> =
        Resource.Success(tragosDao.getAllFavoriteDrinks())

    override suspend fun deleteDrink(drink: DrinkEntity) = tragosDao.deleteDrink(drink)
}