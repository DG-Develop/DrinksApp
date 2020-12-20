package com.dgdevelop.tragosapp.di

import com.dgdevelop.tragosapp.data.DataSourceImpl
import com.dgdevelop.tragosapp.domain.DataSource
import com.dgdevelop.tragosapp.domain.Repo
import com.dgdevelop.tragosapp.domain.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


/* En este caso el ActivityModule servira para proveer los datos de los viewmodels, ahora
* se hizo uso de ActivityRetainedComponent por que asi percitimos los datos de una acticity
* cuando este de manera vertical u hortizontal en cambio el ActivityComponent solo se centra
* en el ciclo de vida de un activity sin importar que este en vertical u horizontal ya que estos
* los trata de manera individual.
* Entonces haciendo uso de ActivityRetainedComponent podremos proveer estos datos del viewmodel
* solamente en el ciclo de vida del activity sin importar la orientacion del dispositivo*/
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract  fun bindRepoImpl(repoImpl: RepoImpl): Repo

    @Binds
    abstract  fun bindDataSourceImpl(dataSourceImpl: DataSourceImpl): DataSource
}