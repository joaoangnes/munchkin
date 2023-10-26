package com.example.munchkin.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munchkin.R
import com.example.munchkin.model.Player
import com.example.munchkin.view.PlayerRegistrationActivity
import com.example.munchkin.view.PlayerStatsActivity

// Adapter class responsible for binding player data to RecyclerView
class PlayersAdapter(private val context: Context, var players: List<Player>) : RecyclerView.Adapter<PlayerViewHolder>() {

    // Create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        // Inflate the layout for a single player item
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return PlayerViewHolder(itemView)
    }

    // Bind player data to the ViewHolder
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        // Get the player at the current position
        val currentPlayer = players[position]

        // Bind player data to ViewHolder's views
        holder.txtPlayerName.text = currentPlayer.name
        holder.txtPlayerStrength.text = currentPlayer.strength.toString()
        holder.txtPlayerLevel.text = currentPlayer.level.toString()

        // Set the player's icon based on their name and gender
        setIconIDPlayer(currentPlayer, holder)

        // Define click listeners for player name and icon
        val onItemLongClickListener = View.OnLongClickListener { view ->
            // Start the PlayerRegistrationActivity for editing the player
            val intent = Intent(context, PlayerRegistrationActivity::class.java)
            intent.putExtra("player_id", currentPlayer.id)
            context.startActivity(intent)
            true
        }

        val onItemClickListener = View.OnClickListener { view ->
            // Start the PlayerStatsActivity to view player statistics
            val intent = Intent(context, PlayerStatsActivity::class.java)
            intent.putExtra("player_id", currentPlayer.id)
            context.startActivity(intent)
        }

        // Attach click listeners to player name and icon views
        holder.txtPlayerName.setOnClickListener(onItemClickListener)
        holder.txtPlayerName.setOnLongClickListener(onItemLongClickListener)
        holder.txtPlayerIcon.setOnClickListener(onItemClickListener)
        holder.txtPlayerIcon.setOnLongClickListener(onItemLongClickListener)
    }

    // Return the total number of players in the list
    override fun getItemCount(): Int {
        return players.size
    }

    // Set the player's icon and text based on their name and gender
    private fun setIconIDPlayer(currentPlayer: Player, holder: PlayerViewHolder) {
        // Get the first letter of the player's name
        val playerName = currentPlayer.name
        val playerInitial = playerName[0].toString().uppercase()

        // Set the TextView with the player's initial
        holder.txtPlayerIcon.text = playerInitial

        // Check the player's gender and set the appropriate icon
        when (currentPlayer.gender) {
            "Male" -> holder.imgGenderIcon.setImageResource(R.drawable.ic_male)
            "Female" -> holder.imgGenderIcon.setImageResource(R.drawable.ic_female)
        }
    }
}
