package com.example.adl_examen_ii

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adl_examen_ii.entities.PostEntity
import com.example.adl_examen_ii.repositories.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsActivity : AppCompatActivity() {

    private val postRepository = PostRepository()

    var adapterPost: CustomAdapterPosts = CustomAdapterPosts(emptyList())
    lateinit var rvListaPost: RecyclerView
    lateinit var btnRegresarPost: Button
    lateinit var tvNombrePost: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_posts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnRegresarPost = findViewById(R.id.btnRegresarPost)
        tvNombrePost = findViewById(R.id.tvNombreUserPost)

        // Configurar el recycler view
        rvListaPost = findViewById(R.id.rvListaPost)
        rvListaPost.layoutManager = LinearLayoutManager(this)
        rvListaPost.adapter = adapterPost

        // Recibir los extras de la actividad Main
        val extraNombre = intent.getStringExtra("nombre") ?: "User"
        val extraId = intent.getStringExtra("id") ?: "1"

        tvNombrePost.text = "Usuario: $extraNombre"

        // Mostrar la lista de las Publicaciones
        mostrarPosts(extraId.toLong())

        btnRegresarPost.setOnClickListener {
            finish()
        }

        adapterPost.setOnItemClickListener(object :
            CustomAdapterPosts.onItemClickListener {
            override fun onItemClick(post: PostEntity) {
                mostrarMensajes(post)
            }

            override fun onItemLongClick(post: PostEntity) {
                finish()
            }

        })
    }

    private fun mostrarMensajes(post: PostEntity) {
        // Mandar a la actividad para mostrar los mensajes de cada publicación
        val intentMensajes = Intent(this@PostsActivity, CommentsActivity::class.java)
        intentMensajes.putExtra("id", post.id.toString())
        intentMensajes.putExtra("title", post.title)
        intentMensajes.putExtra("body", post.body)
        startActivity(intentMensajes)
    }

    private fun mostrarPosts(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            // Mandar a segundo plano
            try {
                // Segundo plano
                val posts = postRepository.getAllPost(id)
                // Mandar a primer plano = UI - Vista
                withContext(Dispatchers.Main) {
                    // Agregar el listado al list view o recycler view o text view
                    adapterPost.updateListPost(posts)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PostsActivity,
                        "Error interno del servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}