package com.example.adl_examen_ii

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adl_examen_ii.entities.CommentsEntity
import com.example.adl_examen_ii.entities.PostEntity


class CustomAdapterComments(var listadoComments: List<CommentsEntity>) :
    RecyclerView.Adapter<CustomAdapterComments.ViewHolder>() {

    /**
     * Toodo es interno al adaptador
     */

    // Despues inicializamos la variable
    private lateinit var miListener: onItemClickListener

    interface onItemClickListener {
        // Despues implementamos lo que hacen los metodos
        fun onItemClick(commentsEntity: CommentsEntity)
        fun onItemLongClick(commentsEntity: CommentsEntity)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        miListener = listener
    }

    // Internamente requerimos una clase
    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        // Referencia a los elementos de mi diseño

        val tituloComments: TextView = itemView.findViewById(R.id.tvTituloComments)
        val bodyComments: TextView = itemView.findViewById(R.id.tvBodyComments)

        // Confirmar que se crearon las vistas
        init {
            itemView.setOnClickListener {
                miListener.onItemClick(listadoComments[adapterPosition])
            }
            itemView.setOnLongClickListener {
                miListener.onItemLongClick(listadoComments[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Crear la lista -  Inflate
        val vista: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_comments, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun getItemCount() = listadoComments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind - Unir los datos con la vista
        val post = listadoComments[position]
        holder.tituloComments.text = "Nombre: ${post.name}"
        holder.bodyComments.text = "Comentrio: ${post.body}"
    }

    fun updateListPost(newComments: List<CommentsEntity>) {
        listadoComments = newComments
        notifyDataSetChanged()
    }
}