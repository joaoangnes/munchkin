package com.example.munchkin.model

import com.example.munchkin.util.Util

class ValidationPlayer {
    companion object {
        // Validate player name
        private fun isPlayerNameValid(name: String): Boolean {
            return name.isNotBlank()
        }

        // Validate player gender
        private fun isPlayerGenderValid(gender: String): Boolean {
            return gender == "Male" || gender == "Female"
        }

        // Validate the registration limit of players
        fun validatePlayerRegistrationLimit(playerCount: Int): Boolean {
            return playerCount < Util.MAX_PLAYERS
        }

        // Validate the entire player registration
        fun isPlayerRegistrationValid(
            name: String,
            gender: String
        ): Boolean {
            return isPlayerNameValid(name) &&
                    isPlayerGenderValid(gender)
        }
    }
}