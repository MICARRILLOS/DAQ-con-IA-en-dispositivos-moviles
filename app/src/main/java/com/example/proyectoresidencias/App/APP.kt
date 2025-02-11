package com.example.proyectoresidencias.App

import android.content.Intent
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
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.OutputStreamWriter


class APP : AppCompatActivity() {
    private lateinit var btnGuardar: AppCompatButton
    private lateinit var btnArch: AppCompatButton
    private lateinit var nombre: EditText
    private lateinit var peso: EditText
    private lateinit var edad: EditText
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
        btnArch = findViewById(R.id.btnArch)
        peso = findViewById(R.id.peso)
        edad = findViewById(R.id.edad)
    }

    private fun inicioListener() {
        selectEsc1.addOnChangeListener { selectEsc1, value, _ ->
            val df = DecimalFormat("#.##")
            val esc1 = df.format(value)
            numEsc1.text = esc1
        }
        selectEsc2.addOnChangeListener { selectEsc2, value, _ ->
            val df = DecimalFormat("#.##")
            val esc2 = df.format(value)
            numEsc2.text = esc2
        }
        btnGuardar.setOnClickListener {
            val name = nombre.text.toString().trim()
            val peSo = peso.text.toString().trim()
            val edAd = edad.text.toString().trim()
            val escala1 = selectEsc1.values[0].toInt()
            val escala2 = selectEsc2.values[0].toInt()
            if (name.isNotEmpty() && peSo.isNotEmpty() && edAd.isNotEmpty()){
                    guardarDatos(name, peSo, edAd, escala1, escala2)
                // Guardar los datos en un archivo
            } else {
                Toast.makeText(this, "Por favor ingresa los datos", Toast.LENGTH_SHORT).show()
            }
        }
        btnArch.setOnClickListener {
            verDatos()
        }
    }


    private fun guardarDatos(name:String, peSo:String, edAd:String, escala1: Int, escala2:Int) {
        //Al presionar "ARCHIVAR" los campos se ponen en blanco
        nombre.setText("")
        edad.setText("")
        peso.setText("")
        selectEsc1.setValues(0f,0f) //con esto el rangeslider regresa a posición 0
        selectEsc2.setValues(0f,0f)
        val csvData = "$name,$edAd,$peSo,$escala1,$escala2\n"
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Datos.txt") //permite guardar txt en directorio publico Documentos
        try {
            if (!file.exists()) {
                file.createNewFile() // Crear el archivo si no existe
                val writer = OutputStreamWriter(FileOutputStream(file, true))
                writer.write("Nombre, Edad, Peso, Escala 1, Escala 2\n")
                //Se escribe como encabezado del archivo al solo crearse
                writer.close()
            }
            //val writer = OutputStreamWriter(FileOutputStream(file, true))
            val fileWriter = FileWriter(file, true)
            fileWriter.append(csvData)  // Agregar los datos al archivo
            fileWriter.flush()
            fileWriter.close()//Si no se pone este comando no se guarda el dato
            Toast.makeText(this, "Datos guardados con éxito", Toast.LENGTH_SHORT).show()
        } catch (e: IOException){
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show()
        }
        /* Se guarda en la siguiente direccion:
        /storage/self/primary/Android/data/com.example.proyectoresidencias/files/Documents/Datos.csv
         */
    }

    //ABRIR ARCHIVO DESDE LA APP
    private fun verDatos(){
        //De aquí te manda a la otra pantalla
        val intent = Intent(this, MostrasDatos::class.java)
        startActivity(intent)
    }


}
