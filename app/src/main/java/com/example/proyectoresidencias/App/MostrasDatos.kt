package com.example.proyectoresidencias.App

import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoresidencias.R
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


class MostrasDatos : AppCompatActivity() {
    lateinit var verDat: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mostras_datos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        verDat= findViewById(R.id.verDat)
        val carpetaDat = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Datos")
        val file = File(carpetaDat, "Datos.txt")
        if (file.exists()) {
            // Leer el contenido del archivo
            val fileInputStream = file.inputStream()
            val reader = BufferedReader(InputStreamReader(fileInputStream))
            val datos = StringBuilder()
            reader.forEachLine { datos.append(it).append("\n") }
            // Mostrar los datos en el TextView
            verDat.text = datos.toString()
            // Leer los datos desde el archivo "datos"
            // Cerrar el stream
            reader.close()
        } else {
            // Si el archivo no existe, mostrar un mensaje
            "No se encontraron datos guardados".also { verDat.text = it }
        }
    }
}