package com.example.proyectoresidencias.App

import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoresidencias.R
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class DatosIndividuales : AppCompatActivity() {
    private lateinit var nombreInd: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_individuales)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nombreInd = findViewById(R.id.nombreInd)
        leerDatos()
    }
    private fun leerDatos(){
        val carpetaDat = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Datos")
        val file = File(carpetaDat, "Datos.txt")
        if (file.exists()) {
            // Leer el contenido del archivo
            val fileInputStream = file.inputStream()
            val reader = BufferedReader(InputStreamReader(fileInputStream))
            val datos = StringBuilder()
            val name = intent.getStringExtra("name") ?: "Sin nombre"//recibe el nombre dependiendo del boton seleccionado
            //reader.forEachLine { datos.append(it).append("\n") }
            val datosEncontrados = reader.useLines { lines ->
                lines.find { it.startsWith(name, ignoreCase = true) } // Busca la primera coincidencia
            }
            // Mostrar resultado en el TextView
            nombreInd.text =  datosEncontrados
            reader.close()
        } else {
            // Si el archivo no existe, mostrar un mensaje
            "No se encontraron datos guardados".also { nombreInd.text = it }
        }
    }
}