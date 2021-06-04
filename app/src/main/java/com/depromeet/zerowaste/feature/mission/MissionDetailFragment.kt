package com.depromeet.zerowaste.feature.mission

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.comm.genLayoutManager
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.databinding.FragmentMissionDetailBinding
import com.depromeet.zerowaste.databinding.ItemMissionTagBinding

class MissionDetailFragment: BaseFragment<FragmentMissionDetailBinding>(R.layout.fragment_mission_detail) {

    private val viewModel: MissionDetailViewModel by activityViewModels()

    override fun init() {
        binding.item = viewModel.mission.value ?: return
        binding.fragment = this
        initTitle()
        initContent()
    }

    private fun initTitle() {
        val mission = binding.item ?: return
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
        binding.missionDetailApproveCnt.text = SpanStrBuilder(requireContext())
            .add("${mission.successfulCount}${resources.getString(R.string.mission_count)} ")
            .add(textId = R.string.mission_detail_approve)
            .build()

        val tagAdapter = BaseRecycleAdapter(R.layout.item_mission_tag){ i: Theme, b: ItemMissionTagBinding, _ -> b.item = i }
        tagAdapter.setData(mission.theme)
        binding.missionDetailTags.layoutManager = genLayoutManager(requireContext(), isVertical = false)
        binding.missionDetailTags.adapter = tagAdapter
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

    fun likeClick() {
        val mission = binding.item ?: return
        viewModel.toggleLikeMission(mission.id, mission.isLiked)
        {
            if(it == 0) {
                mission.isLiked = !mission.isLiked
                binding.item = mission
            }
        }
    }
}