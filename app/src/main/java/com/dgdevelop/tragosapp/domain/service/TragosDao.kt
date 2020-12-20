package com.dgdevelop.tragosapp.domain.service

import androidx.room.*
import com.dgdevelop.tragosapp.data.model.DrinkEntity

@Dao
interface TragosDao {

    @Query("SELECT * FROM tragosEntity")
    suspend fun getAllFavoriteDrinks(): List<DrinkEntity>

    /* Lo que hace el parametro onConflict es cuando haya un dato que se repita su id o su que el
    * contenido sea el mismo, lo remplazara por este nuevo dato para evitar duplicidad de datos */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(trago: DrinkEntity)

    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)
}