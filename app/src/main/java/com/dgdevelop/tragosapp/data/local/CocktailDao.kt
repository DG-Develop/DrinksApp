package com.dgdevelop.tragosapp.data.local

import androidx.room.*
import com.dgdevelop.tragosapp.data.model.CocktailEntity
import com.dgdevelop.tragosapp.data.model.FavoritesEntity

@Dao
interface CocktailDao {

    @Query("SELECT * FROM favoritesTable")
    suspend fun getAllFavoriteDrinks(): List<FavoritesEntity>

    /* This Like operator is needed due that the API returns blank spaces in the name */
    @Query("SELECT * FROM cocktailTable WHERE trago_nombre LIKE '%' || :cocktailName || '%'")
    suspend fun getCocktails(cocktailName: String): List<CocktailEntity>

    /* Lo que hace el parametro onConflict es cuando haya un dato que se repita su id o su que el
    * contenido sea el mismo, lo remplazara por este nuevo dato para evitar duplicidad de datos */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCocktail(cocktail: CocktailEntity)

    @Delete
    suspend fun deleteFavoriteCocktail(favorites: FavoritesEntity)
}