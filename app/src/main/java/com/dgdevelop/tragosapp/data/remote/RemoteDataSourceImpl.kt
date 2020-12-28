package com.dgdevelop.tragosapp.data.remote

import com.dgdevelop.tragosapp.data.DataSource
import com.dgdevelop.tragosapp.data.model.Cocktail
import com.dgdevelop.tragosapp.data.model.CocktailEntity
import com.dgdevelop.tragosapp.data.model.FavoritesEntity
import com.dgdevelop.tragosapp.domain.service.WebService
import com.dgdevelop.tragosapp.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val webService: WebService
) : DataSource {

    @ExperimentalCoroutinesApi
    override suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>?> =
        callbackFlow {
            offer(
                Resource.Success(
                    webService.getCocktailByName(cocktailName)?.cocktailList ?: listOf()
                )
            )
            awaitClose{ close() }
        }

    override suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun saveCocktail(cocktail: CocktailEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>? {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoritesCocktails(): Resource<List<Cocktail>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCocktail(cocktail: FavoritesEntity) {
        TODO("Not yet implemented")
    }
}
