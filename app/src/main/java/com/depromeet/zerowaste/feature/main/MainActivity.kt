package com.depromeet.zerowaste.feature.main

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseActivity
import com.depromeet.zerowaste.comm.KeepStateNavigator
import com.depromeet.zerowaste.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_navigation_view) as NavHostFragment
        val navController = navHostFragment.navController
        val keepStateNavigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.main_navigation_view)
        navController.navigatorProvider.addNavigator(keepStateNavigator)
        navController.setGraph(R.navigation.navigation_graph)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d(null, "id = ${destination.id}, label = ${destination.label}")
        }
    }
}