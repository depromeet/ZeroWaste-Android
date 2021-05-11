package com.depromeet.zerowaste.feature.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.KeepStateNavigator
import com.depromeet.zerowaste.databinding.FragmentMainBinding

class MainFragment: BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun init() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_home_navigation_view) as NavHostFragment
        val navController = navHostFragment.navController
        val keepStateNavigator = KeepStateNavigator(requireContext(), navHostFragment.childFragmentManager, R.id.main_home_navigation_view)
        navController.navigatorProvider.addNavigator(keepStateNavigator)
        navController.setGraph(R.navigation.navigation_graph_home)
        binding.mainBottomNavigation.setupWithNavController(navController)
    }

}