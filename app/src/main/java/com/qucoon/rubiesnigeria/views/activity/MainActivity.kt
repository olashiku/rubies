package com.qucoon.rubiesnigeria.views.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseActivity
import com.qucoon.rubiesnigeria.databinding.ActivityMainBinding
import com.qucoon.rubiesnigeria.repository.rest.WorkerRepository
import com.qucoon.rubiesnigeria.utils.Constant
import com.qucoon.rubiesnigeria.viewmodel.AuthViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeNavigation()
        setupView()
        openSocketConnectivity()
        makeSocketCall()
        requestPermission()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS), 200
        )
    }

     fun checkForPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }


    private fun openSocketConnectivity() {
        authViewModel.openConnection()
    }

    private fun closeSocketConnectivity() {
        authViewModel.closeConnection()
    }

    fun makeSocketCall(action: (() -> Unit)? = null) {
        authViewModel.isOpenLiveData.observe(this) {
            when (it) {
                Constant.success -> {
                    action?.invoke()
                    authViewModel.observeResponse()
                }
                Constant.failed -> {
                    Toast.makeText(this, getString(R.string.lost_connection), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    println("helloworld $it")
                    Toast.makeText(this, " helloworld $it", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initializeNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
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

    override fun onPause() {
        super.onPause()
        closeSocketConnectivity()
    }

    override fun onStop() {
        super.onStop()
        closeSocketConnectivity()
    }

    override fun onResume() {
        super.onResume()
        openSocketConnectivity()
    }

    override fun onRestart() {
        super.onStart()
        openSocketConnectivity()
    }
}