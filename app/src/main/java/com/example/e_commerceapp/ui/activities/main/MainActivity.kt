package com.example.e_commerceapp.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityMainBinding
import com.example.e_commerceapp.ui.fragments.profile.ProfileFragment
import com.example.e_commerceapp.ui.dialogs.CustomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        setUpNavigation()
    }

    private fun setUpBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpNavigation() {
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.visibility = when (destination.id) {
                R.id.dashBoardFragment, R.id.cartFragment, R.id.profileFragment,
                R.id.categoryDetailFragment,
                -> View.VISIBLE

                else -> View.GONE
            }
        }

        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
            setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item,navController)
                navController.popBackStack(item.itemId, false)
                true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        when (navHostFragment.childFragmentManager.fragments.firstOrNull()) {
            // is DashBoardFragment -> showExitDialog()
            is ProfileFragment -> showExitDialog()
            else -> super.onBackPressed()
        }
    }

    private fun showExitDialog() {
        CustomDialog(
            this,
            getString(R.string.exit),
            getString(R.string.confirm_exit_dialog),
            getString(R.string.yes),
            getString(R.string.no),
            positiveButtonClickListener = {
                finishAffinity()
            }
        ).show()
    }
}