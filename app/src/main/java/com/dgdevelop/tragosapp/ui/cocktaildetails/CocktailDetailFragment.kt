package com.dgdevelop.tragosapp.ui.cocktaildetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.dgdevelop.tragosapp.R
import com.dgdevelop.tragosapp.data.model.Cocktail
import com.dgdevelop.tragosapp.data.model.CocktailEntity
import com.dgdevelop.tragosapp.data.model.FavoritesEntity
import com.dgdevelop.tragosapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tragos_detalle.*

@AndroidEntryPoint
class CocktailDetailFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var cocktail: Cocktail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            cocktail = it.getParcelable("cocktail")!!
            // Si usas data class puedes ver todo el contenido de la clase
            Log.d("Detalles_frag", "$cocktail")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tragos_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(cocktail.image).centerCrop().into(ivCocktail)
        tvCocktailTitle.text = cocktail.name
        tvCocktailDesc.text = cocktail.description
        /* if(cocktail.hasAlcohol == "Non_Alcoholic"){
             tvHasAlcohol.text = "Bebida sin alcohol"
         }else{
             tvHasAlcohol.text = "Bebida con alcohol"
         }*/

        fabBtnSave.setOnClickListener {
            viewModel.saveCocktail(
                FavoritesEntity(
                    cocktail.cocktailId,
                    cocktail.image,
                    cocktail.name,
                    cocktail.description,
                    cocktail.hasAlcohol
                )
            )
            Toast.makeText(requireContext(), "Cocktail saved to favorites", Toast.LENGTH_SHORT)
                .show()
        }

    }
}