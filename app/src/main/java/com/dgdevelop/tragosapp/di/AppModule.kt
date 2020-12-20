package com.dgdevelop.tragosapp.di

import android.content.Context
import androidx.room.Room
import com.dgdevelop.tragosapp.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    /* Lo que hace el anotador @ApplicationContext es proveer el contexto de la aplicacion en donde
    * se esta llamando, esto quiere decir, que cuando mandes a llamar el metodo no necesitas
    * pasarle un contexto a dicho metodo ya que con el anotador identifica el contexto en donde
    * se esta llamando.*/
    @Singleton /* Provee una instancia unica de este proveedor */
    @Provides /* Metodo que provee de una instancia */
    fun provideRoomInstance(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, AppDatabase::class.java, "tabla_tragos")
        .build() /* Conexion con Room */

    @Singleton
    @Provides
    fun provideTragosDao(db: AppDatabase) = db.tragoDao()
}