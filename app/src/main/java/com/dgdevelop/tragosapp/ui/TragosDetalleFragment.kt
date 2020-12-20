package com.dgdevelop.tragosapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.dgdevelop.tragosapp.AppDatabase
import com.dgdevelop.tragosapp.R
import com.dgdevelop.tragosapp.data.DataSourceImpl
import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.domain.RepoImpl
import com.dgdevelop.tragosapp.ui.viewmodel.MainViewModel
import com.dgdevelop.tragosapp.ui.viewmodel.VMFactory
import kotlinx.android.synthetic.main.fragment_tragos_detalle.*

class TragosDetalleFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>{
        VMFactory(RepoImpl(DataSourceImpl(AppDatabase.getDatabase(requireActivity().applicationContext))))
    }

    private lateinit var drink: Drink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            drink = it.getParcelable("drink")!!
            // Si usas data class puedes ver todo el contenido de la clase
            Log.d("Detalles_frag", "$drink")
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
        Glide.with(requireContext()).load(drink.imagen).centerCrop().into(ivTrago)
        tvTragoTitle.text = drink.nombre
        tvTragoDesc.text = drink.descripcion
        if(drink.hasAlcohol == "Non_Alcoholic"){
            tvHasAlcohol.text = "Bebida sin alcohol"
        }else{
            tvHasAlcohol.text = "Bebida con alcohol"
        }

        fabBtnSave.setOnClickListener {
            viewModel.guardarTrago(DrinkEntity(
                drink.tragoId, drink.imagen, drink.nombre, drink.descripcion, drink.hasAlcohol
            ))
            Toast.makeText(requireContext(), "Se guardo el trago a favoritos", Toast.LENGTH_SHORT)
                .show()
        }

    }
}