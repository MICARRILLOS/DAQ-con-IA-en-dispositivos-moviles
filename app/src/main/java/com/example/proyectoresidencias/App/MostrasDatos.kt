package com.example.proyectoresidencias.App

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.proyectoresidencias.R
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


class MostrasDatos : AppCompatActivity() {
    private lateinit var agregarDat: AppCompatButton
    private lateinit var NoDat: TextView
    private lateinit var buscarNombre: AppCompatEditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NombresAdapter
    private val listaOriginal = mutableListOf<String>()
    private val listaNames = mutableListOf<String>()
    private  val carpetaDat= File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Datos")
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Actualizar los datos cuando regresamos de Veterinario
            botones()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mostras_datos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicio()
        setupRecyclerView()
        botones()
    }
    private fun inicio(){
        NoDat = findViewById(R.id.NoDat)
        buscarNombre = findViewById(R.id.buscarNombre)
        agregarDat = findViewById(R.id.agregarDat)
        agregarDat.setOnClickListener {
            val intent = Intent(this@MostrasDatos, Veterinario::class.java)
            startForResult.launch(intent) // Usa el launcher en lugar de startActivity
        }
    }

    private fun botones() {
        val file = File(carpetaDat, "Datos.txt")
        if (file.exists() && file.length() > 0) {
            try {
                listaNames.clear()
                listaOriginal.clear()
                BufferedReader(FileReader(file)).use { reader ->
                    var primerLinea = true
                    reader.forEachLine { line ->
                        if (!primerLinea && line.isNotBlank()) {
                            listaOriginal.add(line)
                            listaNames.add(line)
                        }
                        primerLinea = false
                    }
                }
                if (listaNames.isEmpty()) {
                    mostrarSinDatos()
                } else {
                    NoDat.visibility = View.GONE
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                mostrarErrorLectura()
            }
            setupFiltro()
        }
        else {
            mostrarSinDatos()
        }
    }

    private fun mostrarSinDatos() {
        "No se encontraron datos guardados".also { NoDat.text = it }
        listaNames.clear()
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Sin datos", Toast.LENGTH_SHORT).show()
    }
    private fun mostrarErrorLectura() {
        Toast.makeText(this, "Error al leer los datos", Toast.LENGTH_SHORT).show()
        listaNames.clear()
        adapter.notifyDataSetChanged()
    }

    private fun setupFiltro() {
        buscarNombre = findViewById(R.id.buscarNombre)
        buscarNombre.addTextChangedListener(object : TextWatcher {
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
            listaNames.clear()
            listaNames.addAll(listaOriginal)
        } else {
            // Algoritmo de filtrado por coincidencia
            val resultados = listaOriginal.filter { dato ->
                val nombre = dato.split(",")[0]
                nombre.contains(textoBusqueda, ignoreCase = true)
            }

            // Ordenar por mejor coincidencia
            val resultadosOrdenados = resultados.sortedBy { dato ->
                val nombre = dato.split(",")[0]
                // Prioriza coincidencias al inicio del nombre
                nombre.indexOf(textoBusqueda, ignoreCase = true).takeIf { it >= 0 } ?: Int.MAX_VALUE
            }

            // Actualizar la lista mostrada
            listaNames.clear()
            listaNames.addAll(resultadosOrdenados)
        }

        // Notificar al adaptador que los datos cambiaron
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView) // Asegúrate de tener este ID en tu layout
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NombresAdapter(listaNames) { datos, name ->
            Toast.makeText(this, "Presionaste: $name", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DatosIndividuales::class.java)
            intent.putExtra("datos", datos)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}
