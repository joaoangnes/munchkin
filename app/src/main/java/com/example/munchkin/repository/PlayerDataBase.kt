package com.example.munchkin.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.munchkin.model.Player

@Database(entities = [Player::class], version = 1)
abstract class PlayerDataBase : RoomDatabase() {

    // Abstract method to get the Data Access Object (DAO) for the Player entity.
    abstract fun getDao(): PlayerDAO

    companion object {

        private lateinit var INSTANCE: PlayerDataBase

        // Singleton pattern: Get or create an instance of the PlayerDataBase.
        fun getInstance(context: Context): PlayerDataBase {

            if (!::INSTANCE.isInitialized) {
                // Create the database instance if it doesn't exist.
                INSTANCE = Room.databaseBuilder(context, PlayerDataBase::class.java, "players_db")
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}
