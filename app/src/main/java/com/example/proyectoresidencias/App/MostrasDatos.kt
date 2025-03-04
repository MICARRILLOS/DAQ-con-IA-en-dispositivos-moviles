package com.example.proyectoresidencias.App

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoresidencias.R
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


class MostrasDatos : AppCompatActivity() {
    private lateinit var verDat: TextView
    private lateinit var container: LinearLayoutCompat
    private val sharedPrefFile = "MySharedPref"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mostras_datos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        verDat = findViewById(R.id.verDat)
        container = findViewById(R.id.container)
        botones()
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
            reader.forEachLine { datos.append(it).append("\n") }
            // Mostrar los datos en el TextView
            verDat.text = datos.toString()
            // Leer los datos desde el archivo "datos"
            // Cerrar el stream
            reader.close()
        } else {
            // Si el archivo no existe, mostrar un mensaje
            "No se encontraron datos guardados".also { verDat.text = it }
            val sharedPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("Nombres") // Borra los datos almacenados
            editor.apply()
            container.removeAllViews() // Elimina todos los botones
            Toast.makeText(this, "Sin datos", Toast.LENGTH_SHORT).show()
        }
    }
    private fun botones(){//Funcion que crea btns con nombre conforme se va guardando datos
        val sharedPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE)
        val listaNombres = sharedPreferences.getStringSet("Nombres", mutableSetOf()) ?: return
        container.removeAllViews()
        for (csv in listaNombres ){
            val button = AppCompatButton(this).apply {
                text = csv
                setBackgroundColor(Color.CYAN)
                setTextColor(Color.BLACK)
                layoutParams = LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(16, 16, 16, 16)
                }
                setOnClickListener {
                    Toast.makeText(this@MostrasDatos, "Presionaste: $csv", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MostrasDatos, DatosIndividuales::class.java)
                    intent.putExtra("name", csv) // Enviar el nombre a la nueva actividad
                    startActivity(intent)
                }
            }
            container.addView(button)
        }

    }
}