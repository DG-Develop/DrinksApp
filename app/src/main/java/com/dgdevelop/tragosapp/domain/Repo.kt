package com.dgdevelop.tragosapp.domain

import com.dgdevelop.tragosapp.data.model.Cocktail
import com.dgdevelop.tragosapp.data.model.CocktailEntity
import com.dgdevelop.tragosapp.data.model.FavoritesEntity
import com.dgdevelop.tragosapp.vo.Resource
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun getCocktailList(cocktailName: String): Flow<Resource<List<Cocktail>>?>
    suspend fun getFavoriteCocktails(): Resource<List<Cocktail>>
    suspend fun saveCocktail(cocktail: FavoritesEntity)
    suspend fun deleteCocktail(cocktail: FavoritesEntity): Resource<List<Cocktail>>
}