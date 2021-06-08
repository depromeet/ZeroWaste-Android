package com.depromeet.zerowaste.feature.mission.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.comm.genLayoutManager
import com.depromeet.zerowaste.data.ParticipateStatus
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.databinding.FragmentMissionDetailBinding
import com.depromeet.zerowaste.databinding.ItemMissionTagBinding
import com.depromeet.zerowaste.feature.mission.certificate.MissionCertFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class MissionDetailFragment: BaseFragment<FragmentMissionDetailBinding>(R.layout.fragment_mission_detail) {

    private val viewModel: MissionDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setMission {
            binding.item = it
        }
    }

    override fun init() {
        viewModel.mission.value?.also { binding.item = it }
        binding.fragment = this
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

        viewModel.mission.observe(this) { mission ->
            binding.missionDetailApproveCnt.text = SpanStrBuilder(requireContext())
                .add("${mission.successfulCount}${resources.getString(R.string.mission_count)} ")
                .add(textId = R.string.mission_detail_approve)
                .build()

            val tagAdapter = BaseRecycleAdapter(R.layout.item_mission_tag){ i: Theme, b: ItemMissionTagBinding, _ -> b.item = i }
            tagAdapter.setData(mission.theme)
            binding.missionDetailTags.layoutManager = genLayoutManager(requireContext(), isVertical = false)
            binding.missionDetailTags.adapter = tagAdapter
        }

    }

    private fun initContent() {
        binding.missionDetailNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY && binding.missionDetailMotion.progress != 1f) {
                binding.missionDetailMotion.progress += Float.MIN_VALUE
            }
        })

        viewModel.mission.observe(this) { mission ->
            binding.missionDetailMakeUser.text = SpanStrBuilder(requireContext())
                .add(mission.creater.nickname, colorRes = R.color.purple)
                .add(textId = R.string.mission_detail_make_user)
                .build()

            binding.missionDetailCheerUpEdit.visibility = if(mission.creater.id == Share.user?.id) View.VISIBLE else View.GONE

            if (mission.participation.status == ParticipateStatus.READY) {
                binding.missionDetailStartBtn.text = resources.getText(R.string.mission_detail_start_participate)
                val endTime = mission.participation.endDate?.time ?: return@observe
                val format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    SimpleDateFormat("HH:mm:ss", resources.configuration.locales[0])
                } else {
                    SimpleDateFormat("HH:mm:ss", resources.configuration.locale)
                }
                lifecycleScope.launch {
                    while (true) {
                        binding.missionDetailStartTxt.text = getString(R.string.mission_detail_limit, format.format(Date(endTime - Date().time)))
                        delay(1000)
                    }
                }
            } else {
                binding.missionDetailStartBtn.text = resources.getText(R.string.mission_detail_start)
                binding.missionDetailStartTxt.text = StringBuilder().append(mission.inProgressCount).append(resources.getString(R.string.mission_detail_participate_users)).toString()
            }
        }
    }

    fun likeClick(mission: Mission) {
        viewModel.toggleLikeMission(mission.id, mission.isLiked)
        {
            if(it == 0) {
                mission.isLiked = !mission.isLiked
                binding.item = mission
            }
        }
    }

    fun backClick() {
        findNavController().popBackStack()
    }

    fun startClick() {
        val mission = binding.item ?: return
        if (mission.participation.status == ParticipateStatus.READY) {
            MissionCertFragment.startMissionCert(this, MissionDetailFragmentDirections.actionMissionDetailFragmentToMissionCertFragment())
        } else {
            viewModel.startParticipate(mission.id) {
                findNavController().navigate(MissionDetailFragmentDirections.actionMissionDetailFragmentToMissionApproveFragment())
            }
        }
    }

}