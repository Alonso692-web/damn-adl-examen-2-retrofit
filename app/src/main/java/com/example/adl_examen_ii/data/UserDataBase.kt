package com.example.adl_examen_ii.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adl_examen_ii.entities.User

@Database(
    entities = [User::class],
    version = 1,
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    // Definimos un Singleton - Tener solo una instancia
    // Para no tener que instarciar - solo usar las bases de datos
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempDB = INSTANCE
            // Metodo 1
            // Metodo 2
            // Metodo 3
            if (tempDB != null) {
                return tempDB
            }
            // Solo se tenga un accceso al mismo tiempo en procesos concurrentes o en solicitudes
            // concurrentes
            // Solo el metodo 1 tenga la facultad de crearla
            synchronized(this) {
                // Generamos la instancia de la base de datos
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}