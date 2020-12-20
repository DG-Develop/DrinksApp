package com.dgdevelop.tragosapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.domain.service.TragosDao

@Database(entities = [DrinkEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun  tragoDao() : TragosDao
}