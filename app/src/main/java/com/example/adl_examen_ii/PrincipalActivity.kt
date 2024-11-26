package com.example.adl_examen_ii

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PrincipalActivity : AppCompatActivity() {
    lateinit var btnSalir: Button
    lateinit var btnCreditos: Button
    lateinit var btnIrMain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSalir = findViewById(R.id.btnSalir)
        btnCreditos = findViewById(R.id.btnCreditos)
        btnIrMain = findViewById(R.id.btnIrMain)

        btnCreditos.setOnClickListener {
            startActivity(Intent(this, CreditosActivity::class.java))
        }

        btnIrMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            finishAffinity()
        }

    }
}