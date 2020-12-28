package com.dgdevelop.tragosapp.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dgdevelop.tragosapp.R
import com.dgdevelop.tragosapp.base.BaseViewHolder
import com.dgdevelop.tragosapp.data.model.*
import kotlinx.android.synthetic.main.tragos_row.view.*


class FavoritesAdapter(
    private val context: Context,
    private val itemClickListener: OnCocktailClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var cocktailList = listOf<Cocktail>()

    interface OnCocktailClickListener{
        fun onCocktailClick(cocktail: Cocktail, position: Int)
        fun onCocktailDeleteLongClick(favorites: FavoritesEntity, position: Int)
    }

    fun setCocktailList(cocktailList: List<Cocktail>){
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

    private inner class MainViewHolder(itemView: View): BaseViewHolder<Cocktail>(itemView){
        override fun bind(item: Cocktail, position: Int) {
            Glide.with(context).load(item.image).centerCrop().into(itemView.img_cocktail)
            itemView.txt_title.text = item.name
            itemView.txt_description.text = item.description

            itemView.setOnLongClickListener {
                itemClickListener.onCocktailDeleteLongClick(item.asFavoriteEntity(),position)
                return@setOnLongClickListener true
            }

            itemView.setOnClickListener {
                itemClickListener.onCocktailClick(item,position)
            }
        }
    }
}