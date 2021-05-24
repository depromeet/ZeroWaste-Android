package com.depromeet.zerowaste.feature.pledge

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PledgeAdapter(fragment: Fragment, private val isNeedPledge: Boolean): FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PledgePhase1Fragment()
            1 -> PledgePhase2Fragment()
            2 -> PledgePhase3Fragment()
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int = if(isNeedPledge) 3 else 2
}