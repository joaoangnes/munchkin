package com.example.munchkin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// Define an Entity for storing player information in the database.
@Entity(tableName = "players")
data class Player(
    // Primary key with auto-generation, representing the player's unique identifier.
    @ColumnInfo @PrimaryKey(autoGenerate = true)
    var id: Int,

    // Player's name stored in the 'name' column.
    @ColumnInfo(name = "name")
    var name: String,

    // Player's gender stored in the 'gender' column.
    @ColumnInfo(name = "gender")
    var gender: String,

    // Player's strength, initialized with a default value of 1.
    @ColumnInfo(name = "strength")
    var strength: Int = 1,

    // Player's level, initialized with a default value of 1.
    @ColumnInfo(name = "level")
    var level: Int = 1,

    // Player's gear score, initialized with a default value of 0.
    @ColumnInfo(name = "gear")
    var gear: Int = 0,

    // Player's modifier score, initialized with a default value of 0.
    @ColumnInfo(name = "modifier")
    var modifier: Int = 0,
){

    // Update the player's strength based on level and gear
    fun updateStrength() {
        strength = level + gear + modifier
    }

    // Increment the player's level by 1
    fun incrementLevel() {
        level++
        updateStrength()
    }
    // Decrement the player's level by 1
    fun decrementLevel() {
        if (level > 1) {
            level--
        }
        updateStrength()
    }

    // Increment the player's gear by 1
    fun incrementGear() {
        gear++
        updateStrength()
    }

    // Decrement the player's gear by 1
    fun decrementGear() {
        gear--
        updateStrength()
    }

    // Increment the player's modifier by 1
    fun incrementModifier() {
        modifier++
        updateStrength()
    }

    // Decrement the player's modifier by 1
    fun decrementModifier() {
        modifier--
        updateStrength()
    }
}