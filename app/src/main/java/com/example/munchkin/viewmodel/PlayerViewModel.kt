package com.example.munchkin.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.munchkin.model.Player
import com.example.munchkin.model.ValidationPlayer
import com.example.munchkin.repository.PlayerRepository

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    // LiveData to display toast messages in the UI
    private var txtToast = MutableLiveData<String>()

    // Validation utility for player data
    private var validationPlayer = ValidationPlayer

    // Repository to interact with player data in the database
    private var playerRepository = PlayerRepository(application.applicationContext)

    fun getTxtToast(): LiveData<String> {
        return txtToast
    }

    fun insert(
        name: String,
        gender: String
    ): Boolean {
        // Check if the maximum number of players has been reached and prevent registration
        if (!validationPlayer.validatePlayerRegistrationLimit(playerRepository.getPlayersCount())) {
            txtToast.value = "Maximum number of players reached. Registration failed."
            return false
        }

        // Validate player registration data
        if (!validationPlayer.isPlayerRegistrationValid(name, gender)) {
            txtToast.value = "Invalid player registration data. Registration failed."
            return false
        }

        // Create a new Player object
        val player = Player(id = 0, name = name, gender = gender)

        // Try to save the player object in the database
        if (!playerRepository.insert(player)) {
            txtToast.value = "Failed to save player data to the database."
            return false
        }

        txtToast.value = "Player data saved successfully."
        return true
    }

    fun update(
        playerId: Int,
        updatedPlayerName: String? = null,
        updatedPlayerGender: String? = null,
        updatedPlayerStrength: Int? = null,
        updatedPlayerLevel: Int? = null,
        updatedPlayerGear: Int? = null,
        updatedPlayerModifier: Int? = null
    ): Boolean {
        val player = playerRepository.getPlayer(playerId)

        // Check if the player exists
        if (player != null) {
            // Update the player object with new data if provided
            updatedPlayerName?.let { player.name = it }
            updatedPlayerGender?.let { player.gender = it }
            updatedPlayerStrength?.let { player.strength = it }
            updatedPlayerLevel?.let { player.level = it }
            updatedPlayerGear?.let { player.gear = it }
            updatedPlayerModifier?.let { player.modifier = it }

            // Try to update the player object in the database
            playerRepository.update(player)?.let { updatedPlayer ->
                Log.d("PlayerViewModel", "Player data updated successfully.")
                txtToast.value = "Player data updated successfully."
                return true
            } ?: run {
                Log.d("PlayerViewModel", "Failed to update player data in the database.")
                txtToast.value = "Failed to update player data in the database."
                return false
            }
        } else {
            txtToast.value = "Player not found with ID: $playerId."
            return false
        }
    }

    fun delete(playerId: Int): Boolean {
        val player = playerRepository.getPlayer(playerId)

        if (player != null) {
            // Try to delete the player object in the database
            playerRepository.delete(player)?.let { deletedPlayer ->
                txtToast.value = "Player data deleted successfully."
                return true
            } ?: run {
                txtToast.value = "Failed to delete player data from the database."
                return false
            }
        } else {
            txtToast.value = "Player not found with ID: $playerId."
            return false
        }
    }

    // Reset player attributes in the repository
    fun resetPlayers(player: Player) {
        playerRepository.resetPlayer(player)
    }

    // Get all the players from the repository
    fun getAllPlayers(): LiveData<List<Player>> {
        return playerRepository.getPlayers()
    }

    // Get a player record by ID
    fun getPlayerByID(id: Int): Player {
        return playerRepository.getPlayer(id)
    }
}
