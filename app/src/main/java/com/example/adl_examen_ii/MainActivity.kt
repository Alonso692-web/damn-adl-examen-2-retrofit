package com.example.adl_examen_ii

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adl_09.repositories.UserRepository
import com.example.adl_examen_ii.data.UserDatabase
import com.example.adl_examen_ii.entities.AddressEntity
import com.example.adl_examen_ii.entities.GeoEntity
import com.example.adl_examen_ii.entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.adl_examen_ii.entities.User
import com.example.adl_examen_ii.repositories.PostRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toList

class MainActivity : AppCompatActivity() {

    private val userRepository = UserRepository()
    lateinit var db: UserDatabase

    lateinit var recyclerViewUsers: RecyclerView
    var adaptadorRecyler: CustomAdapterRecyclerView = CustomAdapterRecyclerView(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Base de datos
        db = UserDatabase.getDatabase(this)

        // Condiguración recycler view
        recyclerViewUsers = findViewById(R.id.rvListaUsuarios)
        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        recyclerViewUsers.adapter = adaptadorRecyler

        // Obtener todos los usuarios si hay conexión a internet
        if (isInternetAvailable(this)) {
            obtenerUsuarios()
        } else {
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show()
            verificarBaseDeDatos()

        }

        // Poner los escuchadores para clic corto y largo
        adaptadorRecyler.setOnItemClickListener(object :
            CustomAdapterRecyclerView.onItemClickListener {
            override fun onItemClick(user: UserEntity) {
                mostrarPost(user)
            }

            override fun onItemLongClick(user: UserEntity) {
                guardarUsuario(user)
            }

        })

    }

    private fun mostrarPost(user: UserEntity) {
        val intentPost = Intent(this@MainActivity, PostsActivity::class.java)
        intentPost.putExtra("id", user.id.toString())
        intentPost.putExtra("nombre", user.name)
        startActivity(intentPost)
    }

    private fun verificarBaseDeDatos() {
        lifecycleScope.launch {
            db.userDao().getAll().collectLatest { users ->
                if (users.isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        "No hay usuarios en la base de datos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    datosGuardados()
                }
            }
        }
    }

    private fun datosGuardados() {
        val newUsers = mutableListOf<UserEntity>() // Inicializa la lista correctamente

        // Usa el scope adecuado, por ejemplo lifecycleScope en actividades/fragments
        lifecycleScope.launch {
            db.userDao().getAll().collect { users ->
                // Limpia la lista antes de agregar nuevos datos
                newUsers.clear()
                users.forEach { user ->
                    newUsers.add(
                        UserEntity(
                            id = user.id,
                            name = user.nameUser,
                            username = user.usernameUser,
                            email = user.emailUser,
                            address = AddressEntity(
                                id = user.id,
                                street = user.addressUser.split(",")[0],
                                suite = user.addressUser.split(",")[1],
                                city = user.addressUser.split(",")[2],
                                zipcode = user.addressUser.split(",")[3],
                                geo = GeoEntity(
                                    lat = user.addressUser.split(",")[4],
                                    lng = ""
                                )
                            )
                        )
                    )
                }
                // Actualiza el adaptador después de llenar la lista
                adaptadorRecyler.updateListUsers(newUsers)
            }
        }
    }

    private fun guardarUsuario(user: UserEntity) {
        GlobalScope.launch {
            try {
                db.userDao().add(
                    User(
                        nameUser = user.name,
                        usernameUser = user.username,
                        emailUser = user.email,
                        addressUser = "${user.address.street}, ${user.address.suite}, ${user.address.city}, ${user.address.zipcode}, ${user.address.geo.lat}, ${user.address.geo.lng}",
                    )
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Toast.makeText(this, "Usuario: ${user.name} \nGuardado con éxito", Toast.LENGTH_SHORT)
            .show()
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

    private fun obtenerUsuarios() {
        // Mandar a segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            // Mandar a srgundo plano
            try {
                // Segundo plano
                val usuarios = userRepository.getAllUsers()
                // Mandar a primer plano = UI - Vista
                withContext(Dispatchers.Main) {
                    // Agregar el listado al list view o recycler view o text view
                    adaptadorRecyler.updateListUsers(usuarios)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error interno del servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}