package br.com.odnumiar.vigilantesdacidade.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.odnumiar.vigilantesdacidade.R
import br.com.odnumiar.vigilantesdacidade.models.Evento
import br.com.odnumiar.vigilantesdacidade.orm.Posts
import kotlinx.android.synthetic.main.itemlist_eventos.view.*

/**
 * Created by Neto on 29/08/2017.
 */

class AdapterPosts constructor(val context: Context,
                               val lista:ArrayList<Posts>,
                               val clickListener: (Posts)->Unit)
    : RecyclerView.Adapter<AdapterPosts.ViewHolder>() {

    init{}

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvTitle.text = lista[position].date
        holder.itemView.tvDescription.text = lista[position].description
        //holder.itemView.tvDescription.text = eventos[position].desc
        //holder.itemView.setOnClickListener { clickListener(eventos[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.itemlist_eventos,parent,false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}