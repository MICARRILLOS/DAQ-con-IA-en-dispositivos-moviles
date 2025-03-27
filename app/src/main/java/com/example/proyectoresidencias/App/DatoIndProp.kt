package com.example.proyectoresidencias.App

import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoresidencias.R
import java.io.File

class DatoIndProp : AppCompatActivity() {
    private lateinit var nombreIndProp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dato_ind_prop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val datos = intent.getStringExtra("datos") ?: ""
        nombreIndProp = findViewById(R.id.nombreIndProp)
        nombreIndProp.text = datos
    }
}