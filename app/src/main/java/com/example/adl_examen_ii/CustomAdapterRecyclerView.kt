package com.example.adl_examen_ii

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adl_examen_ii.entities.UserEntity


class CustomAdapterRecyclerView(var listadoUsuarios: List<UserEntity>) :
    RecyclerView.Adapter<CustomAdapterRecyclerView.ViewHolder>() {

    /**
     * Toodo es interno al adaptador
     */

    // Despues inicializamos la variable
    private lateinit var miListener: onItemClickListener

    interface onItemClickListener {
        // Despues implementamos lo que hacen los metodos
        fun onItemClick(userEntity: UserEntity)
        fun onItemLongClick(userEntity: UserEntity)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        miListener = listener
    }

    // Internamente requerimos una clase
    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        // Referencia a los elementos de mi diseño
        val nombreUsuario: TextView = itemView.findViewById(R.id.tvNombre)
        val usernameUsuario: TextView = itemView.findViewById(R.id.tvUsername)
        val emailUsuario: TextView = itemView.findViewById(R.id.tvEmail)

        // Confirmar que se crearon las vistas
        init {
            itemView.setOnClickListener {
                miListener.onItemClick(listadoUsuarios[adapterPosition])
            }
            itemView.setOnLongClickListener {
                miListener.onItemLongClick(listadoUsuarios[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Crear la lista -  Inflate
        val vista: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_usuario, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun getItemCount() = listadoUsuarios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind - Unir los datos con la vista
        val usuario = listadoUsuarios[position]
        holder.nombreUsuario.text = "Nombre: ${usuario.name}"
        holder.usernameUsuario.text = "Username: ${usuario.username}"
        holder.emailUsuario.text = "Email: ${usuario.email}"
    }

    fun updateListUsers(newUsers: List<UserEntity>) {
        listadoUsuarios = newUsers
        notifyDataSetChanged()
    }
}