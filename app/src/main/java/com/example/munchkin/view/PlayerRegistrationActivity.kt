package com.example.munchkin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.munchkin.R
import com.example.munchkin.databinding.ActivityPlayerRegistrationBinding
import com.example.munchkin.viewmodel.PlayerViewModel

class PlayerRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerRegistrationBinding
    private lateinit var playerRegistrationViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ViewModel for managing player registration and editing
        playerRegistrationViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        setObserver()

        // Set the header text to indicate whether it's a new player or an edit
        binding.txtHeaderText.text = "New Munchkin"

        // Check if the activity was started with a player ID, indicating an edit
        val playerId = intent.getIntExtra("player_id", -1)
        if (playerId != -1) {
            binding.txtHeaderText.text = "Edit Munchkin"

            // Load the existing player's data for editing
            val player = playerRegistrationViewModel.getPlayerByID(playerId)
            if (player != null) {
                binding.edtPlayerName.setText(player.name)

                // Set the gender radio button based on the player's gender
                when (player.gender) {
                    "Male" -> binding.rbMale.isChecked = true
                    "Female" -> binding.rbFemale.isChecked = true
                }
            } else {
                Log.w("PlayerRegistrationActivity", "Invalid player ID: $playerId")
            }

            // Control the visibility of the delete button for editing
            binding.btnDeletePlayer.visibility = View.VISIBLE
        } else {
            // Control the visibility of the delete button for new players
            binding.btnDeletePlayer.visibility = View.INVISIBLE
        }

        // Action when the save button is clicked
        binding.btnSaveUpdatePlayer.setOnClickListener {
            val playerName = binding.edtPlayerName.text.toString()
            val playerGender = getSelectedGender()

            if (playerId != -1) {
                // Update the player data
                if (playerRegistrationViewModel.update(playerId, playerName, playerGender)) {
                    finish()
                }
            } else {
                // Insert a new player
                if (playerRegistrationViewModel.insert(playerName, playerGender)) {
                    finish()
                }
            }
        }

        // Action when the delete button is clicked
        binding.btnDeletePlayer.setOnClickListener {
            // Delete the player data
            if (playerRegistrationViewModel.delete(playerId)) {
                finish()
            }
        }
    }

    // Function for handling the back button click
    fun onBackButtonClick(view: View) {
        onBackPressedDispatcher.onBackPressed()
    }

    // Set an observer to display toast messages from the ViewModel
    private fun setObserver() {
        playerRegistrationViewModel.getTxtToast().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    // Get the gender selector of the player
    private fun getSelectedGender(): String {
        return when (binding.rgGender.checkedRadioButtonId) {
            R.id.rbMale -> "Male"
            R.id.rbFemale -> "Female"
            else -> ""
        }
    }
}
