package com.example.munchkin.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.munchkin.model.Player

@Dao
interface PlayerDAO {

    // Insert a player into the database. If a player with the same ID already exists, replace it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: Player): Long

    // Delete a player from the database.
    @Delete
    fun delete(player: Player)

    // Update the information of a player in the database.
    @Update
    fun update(player: Player)

    // Retrieve a player with a specific ID from the database, if it exists.
    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayer(id: Int): Player

    // Retrieve all players from the database. Returns a list, which may be empty.
    @Query("SELECT * FROM players")
    fun getPlayers(): LiveData<List<Player>>

    // Retrieve the number of players in the game.
    @Query("SELECT COUNT(*) FROM players")
    fun getPlayersCount(): Int
}
