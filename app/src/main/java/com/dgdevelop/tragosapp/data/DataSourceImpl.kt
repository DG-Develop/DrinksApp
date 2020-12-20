package com.dgdevelop.tragosapp.data

import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.domain.service.TragosDao
import com.dgdevelop.tragosapp.domain.service.WebService
import com.dgdevelop.tragosapp.vo.Resource
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val tragosDao: TragosDao,
    private val webService: WebService
): DataSource {

    override suspend fun getTragoByName(tragoName: String): Resource<List<Drink>>{
        return Resource.Success(webService.getTragoByName(tragoName).drinkList)
    }

    override suspend fun insertTragoIntoRoom(trago: DrinkEntity){
        tragosDao.insertFavorite(trago)
    }

    override suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>> =
        Resource.Success(tragosDao.getAllFavoriteDrinks())

    override suspend fun deleteDrink(drink: DrinkEntity) = tragosDao.deleteDrink(drink)
}