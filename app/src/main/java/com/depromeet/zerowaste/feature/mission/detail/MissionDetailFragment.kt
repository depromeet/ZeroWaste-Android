package com.depromeet.zerowaste.feature.mission.detail

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.*
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.data.ParticipateStatus
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.data.cert.Certificate
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.databinding.FragmentMissionDetailBinding
import com.depromeet.zerowaste.databinding.ItemMissionDetailHeroBinding
import com.depromeet.zerowaste.databinding.ItemMissionDetailHeroImgBinding
import com.depromeet.zerowaste.databinding.ItemMissionTagBinding
import com.depromeet.zerowaste.feature.mission.certificate.MissionCertFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.*

class MissionDetailFragment: BaseFragment<FragmentMissionDetailBinding>(R.layout.fragment_mission_detail) {

    private val viewModel: MissionDetailViewModel by activityViewModels()

    private val heroListAdapter = BaseRecycleAdapter(R.layout.item_mission_detail_hero)
    { item: Certificate, bind: ItemMissionDetailHeroBinding, _ ->
        bind.item = item
        bind.itemMissionDetailHeroImgs.layoutManager = genLayoutManager(requireContext(), isVertical = false)
        val imgsAdapter = BaseRecycleAdapter(R.layout.item_mission_detail_hero_img) { imgItem: String, imgBind: ItemMissionDetailHeroImgBinding, _ -> imgBind.link = imgItem }
        imgsAdapter.setData(item.imgUrls)
        bind.itemMissionDetailHeroImgs.adapter = imgsAdapter
    }

    private var isFirstResume = true

    override fun onResume() {
        super.onResume()
        if(!isFirstResume) viewModel.setMissionData()
        isFirstResume = false
        viewModel.setCertData()
    }

    override fun init() {
        binding.vm = viewModel
        binding.fragment = this
        initTitle()
        initContent()
        initHeroList()
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
            if(mission.userCertifiedCounts > 0) {
                binding.missionDetailApproveCnt.text = SpanStrBuilder(requireContext())
                .add("${mission.userCertifiedCounts}${resources.getString(R.string.mission_count)} ")
                .add(textId = R.string.mission_detail_approve)
                .build()
            }

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

            binding.missionDetailCheerUpEdit.visibility =
                if (mission.creater.id == Share.user?.id) View.VISIBLE else View.GONE

            if (mission.participation.status == ParticipateStatus.READY) {
                binding.missionDetailStartBtn.text =
                    resources.getText(R.string.mission_detail_start_participate)
                val endTime = mission.participation.endDate?.time ?: return@observe
                lifecycleScope.launch {
                    while (true) {
                        binding.missionDetailStartTxt.text = getString(
                            R.string.mission_detail_limit,
                            DifTimeStringConverter.convert(System.currentTimeMillis(), endTime)
                        )
                        delay(1000)
                    }
                }
            } else {
                binding.missionDetailStartBtn.text =
                    resources.getText(R.string.mission_detail_start)
                binding.missionDetailStartTxt.text = StringBuilder().append(mission.inProgressCount)
                    .append(resources.getString(R.string.mission_detail_participate_users))
                    .toString()
            }
        }
    }

    private fun initHeroList() {
        binding.missionDetailHeroList.adapter = heroListAdapter
        viewModel.certificates.observe(this) {
            heroListAdapter.setData(it.subList(0, if(it.size > 3) 2 else it.size - 1))
        }
    }

    fun likeClick(mission: Mission) {
        viewModel.toggleLikeMission(mission.id, mission.isLiked)
        {
            if(it == 0) {
                mission.isLiked = !mission.isLiked
                viewModel.setMission(mission)
            }
        }
    }

    fun backClick() {
        findNavController().popBackStack()
    }

    fun startClick() {
        val mission = viewModel.mission.value ?: return
        if (mission.participation.status == ParticipateStatus.READY) {
            MissionCertFragment.startMissionCert(this, MissionDetailFragmentDirections.actionMissionDetailFragmentToMissionCertFragment(mission.id))
        } else {
            viewModel.startParticipate(mission) {
                findNavController().navigate(MissionDetailFragmentDirections.actionMissionDetailFragmentToMissionApproveFragment())
            }
        }
    }

}