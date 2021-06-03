package com.depromeet.zerowaste.feature.mission

import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentMissionDetailBinding

class MissionDetailFragment: BaseFragment<FragmentMissionDetailBinding>(R.layout.fragment_mission_detail) {

    override fun init() {
        initTitle()
        initContent()
    }

    private fun initTitle() {
        val endFont = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)
        val startFont = ResourcesCompat.getFont(requireContext(), R.font.kotra_bold)
        binding.missionDetailMotion.setTransitionListener(object: MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                if(p3 > 0.5f && binding.missionDetailTitle.typeface != endFont) {
                    binding.missionDetailTitle.typeface = endFont
                } else if(p3 >= 0 && p3 < 0.5f && binding.missionDetailTitle.typeface != startFont) {
                    binding.missionDetailTitle.typeface = startFont
                }
            }
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })
    }

    private fun initContent() {
        binding.missionDetailNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY && binding.missionDetailMotion.progress != 1f) {
                binding.missionDetailMotion.progress += Float.MIN_VALUE
            }
        })
        binding.missionDetailHowToImg.clipToOutline = true
    }
}