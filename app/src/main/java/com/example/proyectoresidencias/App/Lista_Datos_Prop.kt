package com.example.proyectoresidencias.App

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoresidencias.R
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Lista_Datos_Prop : AppCompatActivity() {
    private lateinit var agregarDatProp: AppCompatButton
    private lateinit var buscarNombreProp: AppCompatEditText
    private lateinit var recyclerViewProp: RecyclerView
    private lateinit var adapterProp: NombresAdapterProp
    private val listaOriginalProp = mutableListOf<String>()
    private val listaNamesProp = mutableListOf<String>()
    private val carpetaDat = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Datos_Propietario")
    private val startForResultProp = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            botones() // Actualizar cuando regresamos de Propietario
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_datos_prop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
        setupRecyclerViewProp()
        botones()
    }

    private fun inicio(){
        buscarNombreProp = findViewById(R.id.buscarNombreProp)
        agregarDatProp = findViewById(R.id.agregarDatProp)
        agregarDatProp.setOnClickListener {
            val intent = Intent(this@Lista_Datos_Prop, Propietario::class.java)
            startForResultProp.launch(intent) // Cambiado a startForResult
        }
    }

    private fun botones(){//Funcion que crea btns con nombre conforme se va guardando datos
        val file = File(carpetaDat, "Datos_Propietario.txt")
        if (file.exists() && file.length() > 0){
            try{
                listaNamesProp.clear()
                listaOriginalProp.clear()
                BufferedReader(FileReader(file)).use { reader ->
                    var primerLinea = true
                    reader.forEachLine { line ->
                        if (!primerLinea && line.isNotBlank()) {
                            listaOriginalProp.add(line)
                            listaNamesProp.add(line)
                        }
                        primerLinea = false
                    }
                }
                if (listaNamesProp.isEmpty()) {
                    listaNamesProp.clear()
                    adapterProp.notifyDataSetChanged()
                    Toast.makeText(this, "Sin datos", Toast.LENGTH_SHORT).show()
                } else {
                    adapterProp.notifyDataSetChanged()
                }
                setupFiltroProp()
            }
            catch (e: Exception){
                Toast.makeText(this, "Error al leer los datos", Toast.LENGTH_SHORT).show()
                listaNamesProp.clear()
                adapterProp.notifyDataSetChanged()
            }
            setupFiltroProp()
        }
        else {
            // Si el archivo no existe, mostrar un mensaje
            listaNamesProp.clear()
            adapterProp.notifyDataSetChanged()
            Toast.makeText(this, "Sin datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFiltroProp() {
        // 1. Asegúrate de tener un EditText con id editTextFiltro en tu layout
        buscarNombreProp = findViewById(R.id.buscarNombreProp)

        // 2. Listener para detectar cambios en el texto
        buscarNombreProp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filtrarNombres(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    private fun filtrarNombres(textoBusqueda: String) {
        if (textoBusqueda.isEmpty()) {
            // Si no hay texto, mostrar todos los elementos
            listaNamesProp.clear()
            listaNamesProp.addAll(listaOriginalProp)
        } else {
            // Algoritmo de filtrado por coincidencia
            val resultados = listaOriginalProp.filter { dato ->
                val nombre = dato.split(",")[0]
                nombre.contains(textoBusqueda, ignoreCase = true)
            }
            // Ordenar por mejor coincidencia (opcional)
            val resultadosOrdenados = resultados.sortedBy { dato ->
                val nombre = dato.split(",")[0]
                // Prioriza coincidencias al inicio del nombre
                nombre.indexOf(textoBusqueda, ignoreCase = true).takeIf { it >= 0 } ?: Int.MAX_VALUE
            }
            // Actualizar la lista mostrada
            listaNamesProp.clear()
            listaNamesProp.addAll(resultadosOrdenados)
        }
        // Notificar al adaptador que los datos cambiaron
        adapterProp.notifyDataSetChanged()
    }

    private fun setupRecyclerViewProp() {
        recyclerViewProp = findViewById(R.id.recyclerViewProp) // Asegúrate de tener este ID en tu layout
        recyclerViewProp.layoutManager = LinearLayoutManager(this)
        adapterProp = NombresAdapterProp(listaNamesProp) { datos, name ->
            Toast.makeText(this, "Presionaste: $name", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DatoIndProp::class.java)
            intent.putExtra("datos", datos)
            startActivity(intent)
        }
        recyclerViewProp.adapter = adapterProp
    }
}