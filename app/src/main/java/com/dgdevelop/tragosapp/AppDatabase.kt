package com.dgdevelop.tragosapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dgdevelop.tragosapp.data.model.CocktailEntity
import com.dgdevelop.tragosapp.data.local.CocktailDao
import com.dgdevelop.tragosapp.data.model.FavoritesEntity

@Database(entities = [CocktailEntity::class, FavoritesEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun  cocktailDao() : CocktailDao
}