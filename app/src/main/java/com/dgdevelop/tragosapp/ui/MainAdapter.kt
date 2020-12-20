package com.dgdevelop.tragosapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dgdevelop.tragosapp.R
import com.dgdevelop.tragosapp.base.BaseViewHolder
import com.dgdevelop.tragosapp.data.model.Drink
import kotlinx.android.synthetic.main.tragos_row.view.*
import java.lang.IllegalStateException

class MainAdapter (
    private val context: Context,
    private val tragosList: MutableList<Drink>,
    private val itemClickListener: OnTragoClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>(){

    interface OnTragoClickListener{
        fun onTragoClick(drink: Drink, position: Int)
    }

    fun deleteDrink(position: Int){
        tragosList.removeAt(position)
        notifyDataSetChanged()
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> =
        MainViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.tragos_row,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MainViewHolder -> holder.bind(tragosList[position], position)
            else -> throw IllegalStateException("Se olvido bindear el viewHolder")
        }
    }

    override fun getItemCount(): Int = tragosList.size

    // Con el inner class esta clase esta ligado a su clase padre o contenedora y lo que hace es
    // que cuando muera el ciclo de vida de su clase padre o contenedora esta clase tambien finalice
    // su ciclo de vida en caso de no poner inner class lo que hara es que cada clase tenga
    // su propio ciclo de vida y cuando muera el ciclo de vida de su clase contenedor esta clase
    // siga en pie.
    // Otra ventaja de las inner class es que puedes utilizar los atributos de la clase padre
    // o contenedora
    inner class MainViewHolder(itemView: View): BaseViewHolder<Drink>(itemView){
        override fun bind(item: Drink, position: Int) {
            Glide.with(context).load(item.imagen).centerCrop().into(itemView.img_trago)
            itemView.txt_titulo.text = item.nombre
            itemView.txt_descripcion.text = item.descripcion
            itemView.setOnClickListener { itemClickListener.onTragoClick(item, position) }
        }
    }
}