package com.depromeet.zerowaste.feature.main

import androidx.activity.addCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.KeepStateNavigator
import com.depromeet.zerowaste.databinding.FragmentMainBinding

class MainFragment: BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun init() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_home_navigation_view) as NavHostFragment
        val navController = navHostFragment.navController
        val keepStateNavigator = KeepStateNavigator(requireContext(), childFragmentManager, R.id.main_home_navigation_view)
        navController.navigatorProvider.addNavigator(keepStateNavigator)
        navController.setGraph(R.navigation.navigation_graph_home)
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null
        binding.mainBottomNavigation.itemTextColor = ResourcesCompat.getColorStateList(resources, R.color.selector_main_bottom_text_color, null)

        viewModel.navDirection.observe(this) {
            findNavController().navigate(it)
        }

        var lastPressedTime = 0L

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val currentTime = System.currentTimeMillis()
            if(lastPressedTime + 2000 > currentTime) {
                requireActivity().finish()
            } else {
                lastPressedTime = currentTime
                showToast(resources.getString(R.string.finish_toast))
            }
        }
    }
}