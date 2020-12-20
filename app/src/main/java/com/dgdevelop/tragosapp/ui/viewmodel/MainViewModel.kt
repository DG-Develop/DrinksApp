package com.dgdevelop.tragosapp.ui.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.domain.Repo
import com.dgdevelop.tragosapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(private val repo:Repo): ViewModel(){

    private val tragosData = MutableLiveData<String>()

    /* Funcion que setea el nombre del trago a buscar*/
    fun setTrago(tragoName: String){
        tragosData.value = tragoName
    }

    /* Por defecto en la lista de busqueda solo apareceran los tragos de margarita */
    init {
        setTrago("margarita")
    }

    /* En este metodo fetchTragosList lo que hace es buscar una lista de tragos dependiendo de lo
    *  lo que se puso en el searchview y con el distictUnitlChanged se aplica siempre y cuando se
    *  cambie el valor dentro del SearchView.
    *  Por ejemeplo cuando queremos buscar el trago de margarita y le das a buscar te traera la
    *  lista de manera normal, pero cuando vuelvas a darle buscar y este texto no cambio
    *  entonces no se resolvera este fecth y por ultimo con el switchMap, mapeamos el liveData
    *  para que nos emita la lista de resultados pasandole como resultado el valor que contiene
    *  el MutableLiveData de tragosData que en este caso es un string
    * */
    val fetchTragosList = tragosData.distinctUntilChanged().switchMap {nombreTrago ->
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getTragosList(nombreTrago))
            }catch (e: Exception){
                emit(Resource.Failure(e))
            }
        }
    }
    /* viewModelScope sirve para cuando ya no se destruya el activity o fragment que utilice esta
    * funcion, limpie todo el scope que maneje dentro de el, tambien nos sirve para ejecutar
    * corutinas con el .launch y asi evitar crear bloques de suspenciones en la UI*/
    fun guardarTrago(trago: DrinkEntity){
        viewModelScope.launch {
            repo.insertTrago(trago)
        }
    }

    fun getTragoFavoritos() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getTragosFavoritos())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun deleteDrink(drink: DrinkEntity){
        viewModelScope.launch {
            repo.deleteDrink(drink)
        }
    }
}