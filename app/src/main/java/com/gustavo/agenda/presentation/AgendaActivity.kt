package com.gustavo.agenda.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.gustavo.agenda.R
import com.gustavo.agenda.databinding.AgendaActivityBinding

class AgendaActivity: AppCompatActivity() {

    lateinit var binding: AgendaActivityBinding

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.agendaFragmentContainer) as NavHostFragment
    }

    private val navigation by lazy {
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            navigation.navigateUp()
        }
    }
}