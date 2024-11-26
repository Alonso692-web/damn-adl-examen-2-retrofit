package com.example.adl_examen_ii

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adl_examen_ii.entities.PostEntity
import com.example.adl_examen_ii.entities.UserEntity


class CustomAdapterPosts(var listadoPost: List<PostEntity>) :
    RecyclerView.Adapter<CustomAdapterPosts.ViewHolder>() {

    /**
     * Toodo es interno al adaptador
     */

    // Despues inicializamos la variable
    private lateinit var miListener: onItemClickListener

    interface onItemClickListener {
        // Despues implementamos lo que hacen los metodos
        fun onItemClick(postEntity: PostEntity)
        fun onItemLongClick(postEntity: PostEntity)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        miListener = listener
    }

    // Internamente requerimos una clase
    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        // Referencia a los elementos de mi diseño

        val tituloPost: TextView = itemView.findViewById(R.id.tvTitulo)
        val bodyPost: TextView = itemView.findViewById(R.id.tvBody)

        // Confirmar que se crearon las vistas
        init {
            itemView.setOnClickListener {
                miListener.onItemClick(listadoPost[adapterPosition])
            }
            itemView.setOnLongClickListener {
                miListener.onItemLongClick(listadoPost[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Crear la lista -  Inflate
        val vista: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_posts, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun getItemCount() = listadoPost.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind - Unir los datos con la vista
        val post = listadoPost[position]
        holder.tituloPost.text = "Título: ${post.title}"
        holder.bodyPost.text = "Mensaje: ${post.body}"
    }

    fun updateListPost(newPost: List<PostEntity>) {
        listadoPost = newPost
        notifyDataSetChanged()
    }
}