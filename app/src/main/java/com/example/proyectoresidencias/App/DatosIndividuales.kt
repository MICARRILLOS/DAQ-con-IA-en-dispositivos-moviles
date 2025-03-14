package com.example.proyectoresidencias.App

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoresidencias.R
import java.io.File
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class DatosIndividuales : AppCompatActivity() {
    private lateinit var nombreInd: TextView
    private lateinit var linearfotoInd: LinearLayoutCompat
    private val carpetaDat = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Datos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_individuales)
        OpenCVLoader.initLocal()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val datos = intent.getStringExtra("datos") ?: ""
        linearfotoInd = findViewById(R.id.linearfotoInd)
        nombreInd = findViewById(R.id.nombreInd)
        nombreInd.text = datos
        verfoto(datos)
    }

    private fun verfoto(datos:String){
        val file = File(carpetaDat, "$datos.jpg")
        if (file.exists()){
            val bitmap = BitmapFactory.decodeFile(file.absolutePath) // Convierte el archivo en un Bitmap
            //
            if (bitmap != null) {
                val gaussBitmap = bloques9x9(bitmap)
                val rotatedBitmap = rotateBitmap(gaussBitmap)
                val foto = ImageView(this).apply{
                    setImageBitmap(rotatedBitmap) // Establece la imagen en el ImageView
                    layoutParams = LinearLayout.LayoutParams(
                        dpToPx(250), // Ancho
                        dpToPx(400)  // Alto
                    ) // Ajusta el tamaño del ImageView
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
                linearfotoInd.addView(foto)
            }

        }
        else {
            Toast.makeText(this, "Imagen no encontrada", Toast.LENGTH_SHORT).show()
        }
    }
    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
    private fun rotateBitmap(source: Bitmap): Bitmap {
        val matrix = Matrix().apply { postRotate(90f) } // Aplica la rotación
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    private fun bloques9x9(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val tamBlock = 9//9x9 pixeles

        // Determinar las dimensiones para rellenar la imagen si es necesario
        val newWidth = if (width % 9 == 0) width else (width / 9 + 1) * 9
        val newHeight = if (height % 9 == 0) height else (height / 9 + 1) * 9

        // Crear una imagen nueva con relleno (blanco por defecto)
        val nvoBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(nvoBitmap)
        canvas.drawColor(Color.WHITE)  // Rellenar el fondo de la nueva imagen con blanco
        canvas.drawBitmap(bitmap, 0f, 0f, null)  // Dibuja la imagen original

        // Recorrer la imagen y dividirla en bloques de 9x9 píxeles
        for (y in 0 until newHeight step tamBlock) {
            for (x in 0 until newWidth step tamBlock) {
                // Crear una matriz para almacenar los píxeles del bloque 9x9
                val block = Array(tamBlock) { IntArray(tamBlock) }

                // Llenar el bloque con los píxeles correspondientes
                for (i in 0 until tamBlock) {
                    for (j in 0 until tamBlock) {
                        if (y + i < newHeight && x + j < newWidth) {
                            block[i][j] = nvoBitmap.getPixel(x + j, y + i)
                        } else {
                            // Si se sale de los límites, rellenar con blanco
                            block[i][j] = Color.WHITE
                        }
                    }
                }
                val bloquesFiltrados = filtroGauss(block)
                for (i in 0 until tamBlock) {
                    for (j in 0 until tamBlock) {
                        if (x + j < width && y + i < height) {
                            nvoBitmap.setPixel(x + j, y + i, bloquesFiltrados[i][j])
                        }
                    }
                }
            }
        }

        return nvoBitmap
    }

    private fun filtroGauss(blocks: Array<IntArray>): Array<Array<Int>> {
        val size = blocks.size
        val kernel = arrayOf(
            /*
            doubleArrayOf(0.25, 0.5, 0.25),
            doubleArrayOf(0.5, 1.0, 0.5),
            doubleArrayOf(0.25, 0.5, 0.25)
            */


            doubleArrayOf(0.0625,0.125,0.25,0.125,0.0625),
            doubleArrayOf(0.125,0.25,0.5,0.25,0.125),
            doubleArrayOf(0.25,0.5,1.0,0.5,0.25),
            doubleArrayOf(0.125,0.25,0.5,0.25,0.125),
            doubleArrayOf(0.0625,0.125,0.25,0.125,0.0625)


            /*
            doubleArrayOf(0.015625,0.03125,0.0625,0.125,0.0625,0.03125,0.015625),
            doubleArrayOf(0.03125,0.0625,0.125,0.25,0.125,0.0625,0.03125),
            doubleArrayOf(0.0625,0.125,0.25,0.5,0.25,0.125,0.0625),
            doubleArrayOf(0.125,0.25,0.5,1.0,0.5,0.25,0.125),
            doubleArrayOf(0.0625,0.125,0.25,0.5,0.25,0.125,0.0625),
            doubleArrayOf(0.03125,0.0625,0.125,0.25,0.125,0.0625,0.03125),
            doubleArrayOf(0.015625,0.03125,0.0625,0.125,0.0625,0.03125,0.015625)
            */


        )
        val kernelSum = 2.5

        val bloquesFiltrados = Array(size) { Array(size) { Color.WHITE } }

        for (i in 1 until size - 1) {
            for (j in 1 until size - 1) {
                var redSum = 0.0
                var greenSum = 0.0
                var blueSum = 0.0

                for (ki in -1..1) {
                    for (kj in -1..1) {
                        val pixel = blocks[i + ki][j + kj]
                        val weight = kernel[ki + 1][kj + 1]

                        redSum += Color.red(pixel) * weight
                        greenSum += Color.green(pixel) * weight
                        blueSum += Color.blue(pixel) * weight
                    }
                }

                val newRed = (redSum / kernelSum).toInt().coerceIn(0, 255)
                val newGreen = (greenSum / kernelSum).toInt().coerceIn(0, 255)
                val newBlue = (blueSum / kernelSum).toInt().coerceIn(0, 255)

                bloquesFiltrados[i][j] = Color.rgb(newRed, newGreen, newBlue)
            }
        }
        return bloquesFiltrados
    }

    /*private fun Gauss(bitmap:Bitmap): Bitmap{
        val mat = Mat()
        val bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        org.opencv.android.Utils.bitmapToMat(bmp32, mat)

        // Aplicar filtro Gaussiano
        Imgproc.GaussianBlur(mat, mat, Size(9.0, 9.0), 0.0)

        val resultBitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888)
        org.opencv.android.Utils.matToBitmap(mat, resultBitmap)

        return resultBitmap
    }*/
}