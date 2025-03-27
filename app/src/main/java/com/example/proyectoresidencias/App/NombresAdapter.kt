package com.example.proyectoresidencias.App

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoresidencias.R

class NombresAdapter(private val nombres: List<String>, private val onClick: (String, String) -> Unit) :
    RecyclerView.Adapter<NombresAdapter.NombreViewHolder>() {

    class NombreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: AppCompatButton = view.findViewById(R.id.item_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NombreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nombre, parent, false)
        return NombreViewHolder(view)
    }

    override fun onBindViewHolder(holder: NombreViewHolder, position: Int) {
        val datos = nombres[position]
        val name = datos.split(",")[0]
        holder.button.text = name
        holder.button.setOnClickListener {
            onClick(datos, name)
        }
    }

    override fun getItemCount() = nombres.size
}