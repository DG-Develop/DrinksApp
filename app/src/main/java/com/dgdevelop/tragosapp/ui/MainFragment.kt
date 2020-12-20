package com.dgdevelop.tragosapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.dgdevelop.tragosapp.AppDatabase
import com.dgdevelop.tragosapp.R
import com.dgdevelop.tragosapp.data.DataSourceImpl
import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.domain.RepoImpl
import com.dgdevelop.tragosapp.ui.viewmodel.MainViewModel
import com.dgdevelop.tragosapp.ui.viewmodel.VMFactory
import com.dgdevelop.tragosapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainAdapter.OnTragoClickListener{

    private val viewModel by viewModels<MainViewModel> {
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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupObservers()
        btnFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favoritosFragment)
        }
    }

    private fun setupObservers(){
        viewModel.fetchTragosList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.i("MVM", "Result: ${result.data}")
                    progressBar.visibility = View.GONE
                    rv_tragos.adapter = MainAdapter(requireContext(), result.data.toMutableList(), this)
                }
                is Resource.Failure -> {
                    Log.i("MVM", "Fallo")
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error al traer los datos ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setupSearchView(){
        /* Configurando el searchview */
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            /* Cuando se envia lo que esta en el text */
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setTrago(query!!)
                return false
            }

            /* Reacciona siempre que cambie el texto del searchview */
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onTragoClick(drink: Drink, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)
        /* En este apartado en lugar de mandarlo directo a la siguiente pantalla lo que se hace es
        * mandarlo primero a que vaya a la animacion que se tienen puesta para la siguiente
        * transicion de pantalla */
        findNavController(this).navigate(R.id.action_mainFragment_to_tragosDetalleFragment, bundle)
    }

    private fun setupRecyclerView() {
        // Poniendo divisores y el divisor es de manera vertical
        rv_tragos.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }
}