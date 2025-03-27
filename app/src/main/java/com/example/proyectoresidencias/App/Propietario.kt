package com.example.proyectoresidencias.App

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoresidencias.R
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.OutputStreamWriter

class Propietario : AppCompatActivity() {
    private lateinit var nombreProp: EditText
    private lateinit var edadProp: EditText
    private lateinit var pesoProp: EditText
    private lateinit var btnGuardarProp: AppCompatButton
    private val carpetaDat = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Datos_Propietario")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_propietario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        compInicio()
        Listener()
    }
    private fun compInicio(){
        nombreProp = findViewById(R.id.nombreProp)
        edadProp = findViewById(R.id.edadProp)
        pesoProp = findViewById(R.id.pesoProp)
        btnGuardarProp = findViewById(R.id.btnGuardarProp)
    }
    private fun Listener(){
        btnGuardarProp.setOnClickListener {
            val nomProp = nombreProp.text.toString().trim()
            val edProp = edadProp.text.toString().trim()
            val pesProp = pesoProp.text.toString().trim()
            val csvDataProp = "$nomProp,$edProp,$pesProp\n"
            if (nomProp.isNotEmpty() && edProp.isNotEmpty() && pesProp.isNotEmpty()){
                guarDatProp(csvDataProp)//funcion pedir permiso camara
            } else {
                Toast.makeText(this, "Por favor ingresa los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun guarDatProp(csvDataProp: String){
        nombreProp.text.clear()
        edadProp.text.clear()
        pesoProp.text.clear()
        val file = File(carpetaDat, "Datos_Propietario.txt")
        try {
            if (!file.exists()){
                carpetaDat.mkdirs()
                file.createNewFile() // Crear el archivo si no existe
                val writer = OutputStreamWriter(FileOutputStream(file, true))
                writer.write("Nombre, Edad, Peso\n")
                //Se escribe como encabezado del archivo al solo crearse
                writer.close()
                setResult(Activity.RESULT_OK) // Enviar datos de vuelta
                finish() // Cierra el activity actual
            }
            val fileWriter = FileWriter(file, true)
            fileWriter.append(csvDataProp)  // Agregar los datos al archivo
            fileWriter.flush()
            fileWriter.close()//Si no se pone este comando no se guarda el dato
            Toast.makeText(this, "Datos guardados con éxito", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK) // Opcional: puedes enviar datos de vuelta
            finish() // Cierra el activity actual
        } catch (e: IOException){
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show()
        }
    }
}
