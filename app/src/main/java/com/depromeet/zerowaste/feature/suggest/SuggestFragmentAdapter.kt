package com.depromeet.zerowaste.feature.suggest

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SuggestFragmentAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SuggestPhase1Fragment()
            1 -> SuggestPhase2Fragment()
            2 -> SuggestPhase3Fragment()
            3 -> SuggestPhase4Fragment()
            4 -> SuggestPhase5Fragment()
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = 5
}