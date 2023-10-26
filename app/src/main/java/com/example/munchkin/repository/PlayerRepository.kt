package com.example.munchkin.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.munchkin.model.Player
import com.example.munchkin.util.Util

class PlayerRepository(context: Context) {
    private var dao = PlayerDataBase.getInstance(context).getDao()

    // Insert a player into the database and return true if successful, otherwise return false.
    fun insert(player: Player): Boolean {
        return dao.insert(player) > 0
    }

    // Delete a player from the database.
    fun delete(player: Player) {
        dao.delete(player)
    }

    // Get a player by their unique ID.
    fun getPlayer(id: Int): Player {
        return dao.getPlayer(id)
    }

    // Get a LiveData list of all players in the database.
    fun getPlayers(): LiveData<List<Player>> {
        return dao.getPlayers()
    }

    // Update a player's information in the database.
    fun update(player: Player) {
        dao.update(player)
    }

    // Reset a player's attributes (strength, level, gear, modifier) to default values.
    fun resetPlayer(player: Player) {
        player.strength = Util.DEFAULT_STRENGTH
        player.level = Util.DEFAULT_LEVEL
        player.gear = Util.DEFAULT_GEAR
        player.modifier = Util.DEFAULT_MODIFIER
        dao.update(player)
    }

    // Get the total count of players in the database.
    fun getPlayersCount(): Int {
        return dao.getPlayersCount()
    }
}
