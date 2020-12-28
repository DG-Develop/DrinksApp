package com.dgdevelop.tragosapp.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.dgdevelop.tragosapp.R
import com.dgdevelop.tragosapp.data.model.Cocktail
import com.dgdevelop.tragosapp.ui.viewmodel.MainViewModel
import com.dgdevelop.tragosapp.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.OnTragoClickListener {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mainAdapter = MainAdapter(requireContext(), this)
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
    }

    private fun setupObservers() {
        viewModel.fetchCocktailList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    empty_container.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    if (result.data.isEmpty()) {
                        empty_container.visibility = View.VISIBLE
                        /* Este return es un retorno locar al llamado de la funcion lambda, es decir
                        * al Observer, un ejemplo mas claro seria el siguiente:
                        * fun foo() {
                            listOf(1, 2, 3, 4, 5).forEach {
                            if (it == 3) return@forEach // local return to the caller of the lambda, i.e. the forEach loop
                                print(it)
                            }
                            print(" done with implicit label")
                          } */
                        return@Observer
                    }
                    mainAdapter.setCocktailList(result.data)
                    empty_container.visibility = View.GONE
                }
                is Resource.Failure -> {
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

    private fun setupSearchView() {
        /* Configurando el searchview */
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /* Cuando se envia lo que esta en el text */
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setCocktail(query!!)
                return false
            }

            /* Reacciona siempre que cambie el texto del searchview */
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favoritos ->{
                findNavController().navigate(R.id.action_mainFragment_to_favoritosFragment)
                false
            }
            else -> false
        }
    }

    override fun onCocktailClick(cocktail: Cocktail, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("cocktail", cocktail)
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
        rv_tragos.adapter = mainAdapter
    }
}