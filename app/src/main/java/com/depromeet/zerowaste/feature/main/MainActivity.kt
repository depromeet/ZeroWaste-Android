package com.depromeet.zerowaste.feature.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseActivity
import com.depromeet.zerowaste.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        viewModel.test.observe(this) {
            Log.d(null, it)
        }
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_navigation_view) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null

        // 해당 ID 만 바텀네비게이션 VISIBLE
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d(null, "id = ${destination.id}, label = ${destination.label}")
            if (destination.id == R.id.mainHomeFragment || destination.id == R.id.mainCommunityFragment || destination.id == R.id.mainMissionFragment || destination.id == R.id.mainProfileFragment) {
                binding.mainBottomNavigation.visibility = View.VISIBLE
            } else {
                binding.mainBottomNavigation.visibility = View.GONE
            }
        }
    }
}