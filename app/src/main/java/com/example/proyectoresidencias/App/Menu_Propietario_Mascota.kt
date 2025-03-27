package com.example.proyectoresidencias.App

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoresidencias.R
import java.io.File

class Menu_Propietario_Mascota : AppCompatActivity() {
    private lateinit var BotonProp: AppCompatButton
    private lateinit var BotonMasc: AppCompatButton
    //private val carpetaDat = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Datos")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_propietario_mascota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        BotonProp = findViewById(R.id.BotonProp)
        BotonMasc = findViewById(R.id.BotonMasc)
        BotonMasc.setOnClickListener {// Ver datos
            val intent = Intent(this, MostrasDatos::class.java)
            startActivity(intent)
        }
        BotonProp.setOnClickListener {// Ver datos
            val intent = Intent(this, Lista_Datos_Prop::class.java)
            startActivity(intent)
        }
    }
}