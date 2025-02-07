package com.example.proyectoresidencias.App


import android.icu.text.DecimalFormat
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoresidencias.R
import com.google.android.material.slider.RangeSlider
import java.io.File
import java.io.FileWriter
import java.io.IOException


class APP : AppCompatActivity() {

    private lateinit var btnGuardar: AppCompatButton
    private lateinit var nombre: EditText
    private lateinit var numEsc1: TextView
    private lateinit var numEsc2: TextView
    private lateinit var selectEsc1: RangeSlider
    private lateinit var selectEsc2: RangeSlider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        compInicio()
        inicioListener()

    }

    private fun compInicio() {
        numEsc1 = findViewById(R.id.numEsc1)
        selectEsc1 = findViewById(R.id.selectEsc1)
        numEsc2 = findViewById(R.id.numEsc2)
        selectEsc2 = findViewById(R.id.selectEsc2)
        nombre = findViewById(R.id.nombre)
        btnGuardar = findViewById(R.id.btnGuardar)
    }

    private fun inicioListener() {
        selectEsc1.addOnChangeListener { selectEsc1, value, _ ->
            val df = DecimalFormat("#.##")
            val esc1 = df.format(value)
            numEsc1.text = "$esc1"
        }
        selectEsc2.addOnChangeListener { selectEsc2, value, _ ->
            val df = DecimalFormat("#.##")
            val esc2 = df.format(value)
            numEsc2.text = "$esc2"
        }

        btnGuardar.setOnClickListener {
            val name = nombre.text.toString().trim()
            val escala1 = selectEsc1.values[0].toInt()
            val escala2 = selectEsc2.values[0].toInt()
            if (name.isNotEmpty()) {
                // Guardar el nombre en un archivo
                guardarDatos(name, escala1, escala2)
            } else {
                Toast.makeText(this, "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun guardarDatos(name:String, escala1:Int, escala2:Int) {
        
        val csvData = "$name,$escala1,$escala2\n"
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Datos.csv")

        try {
            if (!file.exists()) {
                file.createNewFile() // Crear el archivo si no existe
            }

            val fileWriter = FileWriter(file, true)
            fileWriter.append(csvData)  // Agregar los datos al archivo
            fileWriter.flush()
            fileWriter.close()

            Toast.makeText(this, "Datos guardados con éxito", Toast.LENGTH_SHORT).show()

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show()
        }
        /* Se guarda en la siguiente direccion
        /storage/self/primary/Android/data/com.example.proyectoresidencias/files/Documents/Datos.csv
         */


    }

}