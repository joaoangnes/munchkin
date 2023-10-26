package com.example.munchkin.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munchkin.R

// ViewHolder class for player item views in the RecyclerView
class PlayerViewHolder(playerLayout: View) : RecyclerView.ViewHolder(playerLayout) {
    // Views in the player item layout
    val txtPlayerIcon = playerLayout.findViewById<TextView>(R.id.txtPlayerIcon)
    val txtPlayerName = playerLayout.findViewById<TextView>(R.id.txtPlayerName)
    val txtPlayerStrength = playerLayout.findViewById<TextView>(R.id.txtPlayerStrength)
    val txtPlayerLevel = playerLayout.findViewById<TextView>(R.id.txtPlayerLevel)
    val imgGenderIcon = playerLayout.findViewById<ImageView>(R.id.imgGenderIcon)
}
