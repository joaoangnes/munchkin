package com.example.munchkin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munchkin.adapters.PlayersAdapter
import com.example.munchkin.databinding.ActivityMainBinding
import com.example.munchkin.viewmodel.PlayerViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var playerAdapter: PlayersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerAdapter = PlayersAdapter(this, emptyList())

        // Set up the RecyclerView to display players' data
        binding.rcvPlayers.layoutManager = LinearLayoutManager(this)
        binding.rcvPlayers.adapter = playerAdapter

        // Initialize the ViewModel for managing player data
        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)

        // Load and display the player data in the RecyclerView
        updateAdapter()

        // Handle the "Add New Player" button click
        binding.btnAddNewPlayer.setOnClickListener {
            startActivity(Intent(this, PlayerRegistrationActivity::class.java))
        }

        // Handle the "Reset Players" button click
        binding.btnResetPlayers.setOnClickListener{
            resetPlayers()
        }
    }

    // Reset all players' attributes and display a toast message
    fun resetPlayers() {
        playerAdapter.players?.forEach { player ->
            playerViewModel.resetPlayers(player)
        }
        Toast.makeText(this, "Reset Players data successfully", Toast.LENGTH_SHORT).show()
        playerAdapter.notifyDataSetChanged()
    }

    // Update the RecyclerView adapter with the latest player data
    fun updateAdapter(){
        playerViewModel.getAllPlayers().observe(this) { players ->
            playerAdapter.players = players
            playerAdapter.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()
        updateAdapter()
    }

}
