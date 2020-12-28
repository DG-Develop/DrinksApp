package com.dgdevelop.tragosapp.domain

import com.dgdevelop.tragosapp.data.DataSource
import com.dgdevelop.tragosapp.data.DefaultCocktailDataSource
import com.dgdevelop.tragosapp.data.model.Cocktail
import com.dgdevelop.tragosapp.data.model.CocktailEntity
import com.dgdevelop.tragosapp.data.model.FavoritesEntity
import com.dgdevelop.tragosapp.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoImpl @Inject constructor(private val defaultCocktailDataSource: DefaultCocktailDataSource) : Repo {

    @ExperimentalCoroutinesApi
    override suspend fun getCocktailList(cocktailName: String): Flow<Resource<List<Cocktail>>?> =
        defaultCocktailDataSource.getCocktailByName(cocktailName)

    override suspend fun getFavoriteCocktails(): Resource<List<Cocktail>> =
        defaultCocktailDataSource.getFavoritesCocktails()

    override suspend fun saveCocktail(cocktail: FavoritesEntity)  {
        defaultCocktailDataSource.saveFavoriteCocktail(cocktail)
    }

    override suspend fun deleteCocktail(cocktail: FavoritesEntity): Resource<List<Cocktail>> {
        defaultCocktailDataSource.deleteCocktail(cocktail)
        return getFavoriteCocktails()
    }
}