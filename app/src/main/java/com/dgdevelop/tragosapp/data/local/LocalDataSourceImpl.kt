package com.dgdevelop.tragosapp.data.local

import com.dgdevelop.tragosapp.data.DataSource
import com.dgdevelop.tragosapp.data.model.*
import com.dgdevelop.tragosapp.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val cocktailDao: CocktailDao): DataSource {

    override suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>?>{
        TODO("not implement")
    }

    override suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity) =
        cocktailDao.saveFavoriteCocktail(cocktail)

    override suspend fun saveCocktail(cocktail: CocktailEntity) =
        cocktailDao.saveCocktail(cocktail)

    override suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>? =
        Resource.Success(cocktailDao.getCocktails(cocktailName).asCocktailList())

    override suspend fun getFavoritesCocktails(): Resource<List<Cocktail>> =
        Resource.Success(cocktailDao.getAllFavoriteDrinks().asDrinkList())

    override suspend fun deleteCocktail(cocktail: FavoritesEntity) =
        cocktailDao.deleteFavoriteCocktail(cocktail)
}