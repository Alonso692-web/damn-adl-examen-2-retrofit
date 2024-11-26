package com.example.adl_examen_ii

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
import com.example.adl_examen_ii.entities.CommentsEntity
import com.example.adl_examen_ii.entities.PostEntity
import com.example.adl_examen_ii.repositories.CommentsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsActivity : AppCompatActivity() {

    private val commentsRepository = CommentsRepository()

    var adapterComments: CustomAdapterComments = CustomAdapterComments(emptyList())
    lateinit var recyclerComments: RecyclerView
    lateinit var tvNombreComments: TextView
    lateinit var btnRegresarComments: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comments)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recibir los extras de la actividad Main
        val extraId = intent.getStringExtra("id") ?: "1"
        val extraBody = intent.getStringExtra("body") ?: "Body"
        val extraTitle = intent.getStringExtra("title") ?: "Title"

        tvNombreComments = findViewById(R.id.tvNombreCommentsPost)
        btnRegresarComments = findViewById(R.id.btnRegresarComments)
        // Poner el nombre de la publicaciónen el text view
        tvNombreComments.text = "Publicación: ${extraTitle}"

        recyclerComments = findViewById(R.id.rvCommentsPost)
        recyclerComments.layoutManager = LinearLayoutManager(this)
        recyclerComments.adapter = adapterComments

        mostrarComentarios(extraId.toLong())

        btnRegresarComments.setOnClickListener {
            finish()
        }

        adapterComments.setOnItemClickListener(object :
            CustomAdapterComments.onItemClickListener {
            override fun onItemClick(comments: CommentsEntity) {
                Toast.makeText(
                    this@CommentsActivity,
                    "Comentario: ${comments.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onItemLongClick(comments: CommentsEntity) {
                finish()
            }

        })
    }

    private fun mostrarComentarios(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            // Mandar a segundo plano
            try {
                // Segundo plano
                val comments = commentsRepository.getAllComments(id)
                // Mandar a primer plano = UI - Vista
                withContext(Dispatchers.Main) {
                    Log.d("Cat", "Comentarios: ${comments}")
                    // Agregar el listado al list view o recycler view o text view
                    adapterComments.updateListPost(comments)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@CommentsActivity,
                        "Error interno del servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
}