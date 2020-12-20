package com.dgdevelop.tragosapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dgdevelop.tragosapp.AppDatabase
import com.dgdevelop.tragosapp.R
import com.dgdevelop.tragosapp.data.DataSourceImpl
import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.domain.RepoImpl
import com.dgdevelop.tragosapp.domain.TragosDao
import com.dgdevelop.tragosapp.ui.viewmodel.MainViewModel
import com.dgdevelop.tragosapp.ui.viewmodel.VMFactory
import com.dgdevelop.tragosapp.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favoritos.*
import javax.inject.Inject

@AndroidEntryPoint
class FavoritosFragment : Fragment(), MainAdapter.OnTragoClickListener {

    @Inject
    lateinit var tragosDao: TragosDao

    private lateinit var adapter: MainAdapter

    private val viewModel by activityViewModels<MainViewModel>{
        VMFactory(RepoImpl(DataSourceImpl(AppDatabase.getDatabase(requireActivity().applicationContext))))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        Log.d("tragosDao", "onCreate: ${tragosDao.hashCode()}")
    }

    private fun setupObservers(){
        viewModel.getTragoFavoritos().observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Resource.Loading ->{}
                is Resource.Success -> {
                    val lista = result.data.map {
                        Drink(it.tragoId, it.imagen, it.nombre, it.descripcion, it.hasAlcohol)
                    }.toMutableList() /*Hago casteo de una Lista a una Lista Mutable*/

                    adapter = MainAdapter(requireContext(), lista, this)
                    rvTragosFavoritos.adapter = adapter
                }
                is Resource.Failure -> {}
            }
        })
    }

    private fun setupRecyclerView(){
        rvTragosFavoritos.layoutManager = LinearLayoutManager(requireContext())
        rvTragosFavoritos.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    override fun onTragoClick(drink: Drink, position: Int) {
        viewModel.deleteDrink(DrinkEntity(drink.tragoId, drink.imagen, drink.nombre, drink.descripcion, drink.hasAlcohol))
        adapter.deleteDrink(position)
    }
}