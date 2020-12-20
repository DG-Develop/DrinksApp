package com.dgdevelop.tragosapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.domain.TragosDao

@Database(entities = [DrinkEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun  tragoDao() : TragosDao

    companion object{
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            /* Con el Elvis Operator (?:) es lo mismo que decir
            * if(INSTANCE == NULL) *Hacer algo* else INSTANCE
            * Si es igual a null has algo y si no devulveme la instancia*/
            INSTANCE = INSTANCE ?: Room
                .databaseBuilder(context.applicationContext, AppDatabase::class.java, "tabla_tragos")
                .build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}