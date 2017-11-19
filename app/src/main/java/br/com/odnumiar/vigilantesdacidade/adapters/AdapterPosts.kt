package br.com.odnumiar.vigilantesdacidade.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.odnumiar.vigilantesdacidade.R
import br.com.odnumiar.vigilantesdacidade.models.Evento
import br.com.odnumiar.vigilantesdacidade.orm.Posts
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.itemlist_eventos.view.*
import kotlinx.android.synthetic.main.itemlist_posts.view.*

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
        holder.itemView.tvIt_UserName.text = lista[position].userName
        holder.itemView.tvIt_Title.text = "Denúncia em "+lista[position].date
        holder.itemView.tvIt_Description.text = lista[position].description

        if (lista[position].image != "") {
            Picasso.with(context).load(lista[position].image).into(holder.itemView.ivIt_Post)

        } else{
            if (position % 2 != 0){
                Picasso.with(context).load("http://arquivos.tribunadonorte.com.br/fotos/161087.jpg").into(holder.itemView.ivIt_Post) //http://i.imgur.com/DvpvklR.png
            }else{
                Picasso.with(context).load("http://www.tribunafeirense.com.br/imagem/73/3/multa-para-quem-sujar-as-ruas-custa-ate-rs-2-mil-em-salvador.jpg").into(holder.itemView.ivIt_Post) //http://i.imgur.com/DvpvklR.png
            }
        }

        //


        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        //holder.itemView.tvDescription.text = eventos[position].desc
        //holder.itemView.setOnClickListener { clickListener(eventos[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.itemlist_posts,parent,false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}