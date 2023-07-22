package com.qucoon.rubiesnigeria.activity

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeNavigation()
        setupView()
    }

    private fun initializeNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView, navController)
    }
    fun toggleBottomNavigationView(status: Boolean) {
        when (status) {
            true -> binding.bottomNavigationView.visibility = View.VISIBLE
            false -> binding.bottomNavigationView.visibility = View.GONE
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
        binding.bottomNavigationView.visibility = View.GONE
    }
}