package com.example.proyectoresidencias.App

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
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
    private lateinit var buscarNombre: AppCompatEditText
    private val listaBotones = mutableListOf<AppCompatButton>()
    private val listaNames = mutableListOf<String>()
    private val sharedPrefFile = "MySharedPref"
    private val carpetaDat = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Datos")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mostras_datos)
        //OpenCVLoader.initLocal() // Inicializar OpenCV
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
        barraNombres()
        botones()
    }
    private fun inicio(){
        verDat = findViewById(R.id.verDat)
        container = findViewById(R.id.container)
        buscarNombre = findViewById(R.id.buscarNombre)
    }
    private fun barraNombres(){
        buscarNombre.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarBotones(s.toString()) // Llamar a la función de filtrado
            }
            override fun afterTextChanged(s: Editable?) {}
        })

    }
    private fun botones(){//Funcion que crea btns con nombre conforme se va guardando datos
        val file = File(carpetaDat, "Datos.txt")
        if (file.exists()){
            val reader = BufferedReader(InputStreamReader(file.inputStream()))
            var primerLinea = true
            reader.forEachLine { line ->//lee cada linea del archivo
                if (primerLinea) {
                    primerLinea = false // Ignorar el primer renglón (título)
                } else {
                    listaNames.add(line)//Agrega a la lista todos los datos de la linea
                } // Agrega todos los datos, incluso duplicados
            }
            reader.close()
            listaNames.forEach { datos ->
                val name = datos.split(",")[0]
                //line.split(",") divide la línea en una lista de elementos separados por comas.
                //[0] toma solo el primer elemento de la lista (el dato antes de la primera coma).
                val button = AppCompatButton(this).apply {
                    text = name
                    setBackgroundResource(R.drawable.btn_redondo)
                    setTextColor(Color.WHITE)
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(16, 16, 16, 16)
                    }
                    setOnClickListener {
                        Toast.makeText(this@MostrasDatos, "Presionaste: $name", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MostrasDatos, DatosIndividuales::class.java)
                        intent.putExtra("datos", datos ) // Enviar el nombre a la nueva actividad
                        intent.putExtra("name", name)
                        startActivity(intent)
                    }
                }
                listaBotones.add(button)
                container.addView(button)
            }
        }
        else {
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
    private fun filtrarBotones(texto: String) {
        container.removeAllViews() // Eliminar todos los botones visibles
        listaBotones.forEachIndexed { index, button ->
            if (listaNames[index].contains(texto, ignoreCase = true)) {
                container.addView(button) // Mostrar solo los botones que coincidan
            }
        }
    }
}