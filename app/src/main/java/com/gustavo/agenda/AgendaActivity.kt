package com.gustavo.agenda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.gustavo.agenda.databinding.AgendaActivityBinding

class AgendaActivity : AppCompatActivity() {

    private lateinit var binding: AgendaActivityBinding

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.agendaFragmentContainer) as NavHostFragment
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AgendaActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.topAppBar)

        val config = AppBarConfiguration(navController.graph)
        binding.topAppBar.setupWithNavController(navController, config)
    }
}