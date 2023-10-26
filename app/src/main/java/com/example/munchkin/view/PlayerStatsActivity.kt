package com.example.munchkin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.munchkin.R
import com.example.munchkin.databinding.ActivityPlayerStatsBinding
import com.example.munchkin.model.Player
import com.example.munchkin.viewmodel.PlayerViewModel

class PlayerStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerStatsBinding
    private lateinit var playerRegistrationViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel for managing player statistics
        playerRegistrationViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)

        // Get the player's ID from the intent
        val playerId = intent.getIntExtra("player_id", -1)
        if (playerId != -1) {
            val player = playerRegistrationViewModel.getPlayerByID(playerId)
            if (player != null) {
                // Load player information and set up button click listeners
                loadPlayerInformation(player)
                setButtonClickListeners(player)
            } else {
                Log.w("PlayerStatsActivity", "Invalid player ID: $playerId")
            }

            // Control the visibility of the "Down" button based on player's level
            binding.btnDownPlayerLevel.isEnabled = player.level != 1
        }
    }

    // Handle the back button click event
    fun onBackButtonClick(view: View) {
        onBackPressedDispatcher.onBackPressed()
    }

    // Set up button click listeners for increasing and decreasing player's stats
    private fun setButtonClickListeners(player: Player) {
        binding.btnUpPlayerLevel.setOnClickListener {
            player.incrementLevel()
            updatePlayerStats(player)
        }

        binding.btnDownPlayerLevel.setOnClickListener {
            player.decrementLevel()
            updatePlayerStats(player)
        }

        binding.btnUpPlayerGear.setOnClickListener {
            player.incrementGear()
            updatePlayerStats(player)
        }

        binding.btnDownPlayerGear.setOnClickListener {
            player.decrementGear()
            updatePlayerStats(player)
        }

        binding.btnUpPlayerModifier.setOnClickListener {
            player.incrementModifier()
            updatePlayerStats(player)
        }

        binding.btnDownPlayerModifier.setOnClickListener {
            player.decrementModifier()
            updatePlayerStats(player)
        }
    }

    // Update player's statistics and refresh the UI
    private fun updatePlayerStats(player: Player) {
        playerRegistrationViewModel.update(
            playerId = player.id,
            updatedPlayerStrength = player.strength,
            updatedPlayerLevel = player.level,
            updatedPlayerGear = player.gear,
            updatedPlayerModifier = player.modifier
        ) // Update player data in the database
        loadPlayerInformation(player) // Update UI to display new stats
        binding.btnDownPlayerLevel.isEnabled = player.level != 1 // Control the visibility of the "Down" button based on player's level
    }

    // Load and display player's information in the UI
    private fun loadPlayerInformation(player: Player) {
        binding.txtPlayerNameStats.text = player.name

        // Check the player's gender and set the appropriate icon
        when (player.gender) {
            "Male" -> binding.imgGenderIconStats.setImageResource(R.drawable.ic_male)
            "Female" -> binding.imgGenderIconStats.setImageResource(R.drawable.ic_female)
        }

        binding.txtStrenghtStats.text = player.strength.toString()
        binding.txtLevelStats.text = player.level.toString()
        binding.txtGearStats.text = player.gear.toString()
        binding.txtModifierStats.text = player.modifier.toString()
    }
}
