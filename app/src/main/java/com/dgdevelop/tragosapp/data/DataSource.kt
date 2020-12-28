package com.dgdevelop.tragosapp.data

import com.dgdevelop.tragosapp.data.model.Cocktail
import com.dgdevelop.tragosapp.data.model.CocktailEntity
import com.dgdevelop.tragosapp.data.model.FavoritesEntity
import com.dgdevelop.tragosapp.vo.Resource
import kotlinx.coroutines.flow.Flow

interface DataSource {

    /* Esta funcion estara llendo a buscar datos en la api por lo que puede que haya datos
    *  incorrectos y por ende traiga datos nulos por eso marcamos el Null safety en esta funcion*/
    suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>?>

    suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity)

    suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>?

    suspend fun saveCocktail(cocktail: CocktailEntity)

    suspend fun getFavoritesCocktails(): Resource<List<Cocktail>>

    suspend fun deleteCocktail(cocktail: FavoritesEntity)
}