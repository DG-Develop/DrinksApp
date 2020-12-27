package com.dgdevelop.tragosapp.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dgdevelop.tragosapp.R
import com.dgdevelop.tragosapp.base.BaseViewHolder
import com.dgdevelop.tragosapp.data.model.Drink
import com.dgdevelop.tragosapp.data.model.DrinkEntity
import com.dgdevelop.tragosapp.data.model.asDrinkEntity
import kotlinx.android.synthetic.main.tragos_row.view.*


class FavoritesAdapter(
    private val context: Context,
    private val itemClickListener: OnCocktailClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var cocktailList = listOf<Drink>()

    interface OnCocktailClickListener{
        fun onCocktailClick(drink: Drink, position: Int)
        fun onCocktailDeleteLongClick(drink: DrinkEntity, position: Int)
    }

    fun setCocktailList(cocktailList: List<Drink>){
        this.cocktailList = cocktailList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> =
        MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.tragos_row, parent, false)
        )

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(cocktailList[position], position)
        }
    }

    override fun getItemCount(): Int = cocktailList.size

    private inner class MainViewHolder(itemView: View): BaseViewHolder<Drink>(itemView){
        override fun bind(item: Drink, position: Int) {
            Glide.with(context).load(item.imagen).centerCrop().into(itemView.img_trago)
            itemView.txt_titulo.text = item.nombre
            itemView.txt_descripcion.text = item.descripcion

            itemView.setOnLongClickListener {
                itemClickListener.onCocktailDeleteLongClick(item.asDrinkEntity(),position)
                return@setOnLongClickListener true
            }

            itemView.setOnClickListener {
                itemClickListener.onCocktailClick(item,position)
            }
        }
    }
}