package com.dgdevelop.tragosapp.data

import com.dgdevelop.tragosapp.data.model.*
import com.dgdevelop.tragosapp.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class DefaultCocktailDataSource @Inject constructor(
    private val netWorkDataSource: DataSource,
    private val localDataSource: DataSource
): DataSource {

    @ExperimentalCoroutinesApi
    override suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>?> = callbackFlow {
        offer(getCachedCocktails(cocktailName))

        netWorkDataSource.getCocktailByName(cocktailName).collect {
            when(it){
                is Resource.Success ->  {
                    for(cocktail in it.data){
                        saveCocktail(cocktail.asCocktailEntity())
                    }
                    offer(getCachedCocktails(cocktailName))
                }
                is Resource.Failure -> {
                    offer(getCachedCocktails(cocktailName))
                }
            }
        }

        awaitClose { cancel() }
    }

    override suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity) =
        localDataSource.saveFavoriteCocktail(cocktail)

    override suspend fun saveCocktail(cocktail: CocktailEntity) =
        localDataSource.saveCocktail(cocktail)


    override suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>? =
        localDataSource.getCachedCocktails(cocktailName)

    override suspend fun getFavoritesCocktails(): Resource<List<Cocktail>> =
        localDataSource.getFavoritesCocktails()

    override suspend fun deleteCocktail(cocktail: FavoritesEntity) =
        localDataSource.deleteCocktail(cocktail)
}