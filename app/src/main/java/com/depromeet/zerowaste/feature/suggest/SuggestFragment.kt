package com.depromeet.zerowaste.feature.suggest

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.readBytes
import com.depromeet.zerowaste.data.mission.SuggestMission
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestBinding

class SuggestFragment: BaseFragment<FragmentMissionSuggestBinding>(R.layout.fragment_mission_suggest) {

    private val viewModel: SuggestViewModel by viewModels()

    private val pagerAdapter by lazy { SuggestFragmentAdapter(this) }
    private val imm by lazy { requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun init() {
        binding.fragment = this
        initViews()
    }

    private fun initViews() {
        viewModel.checkCanDoNext.observe(this) {
            binding.missionSuggestNext.isEnabled = it
            if(it) binding.missionSuggestNext.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
            else binding.missionSuggestNext.setTextColor(ResourcesCompat.getColor(resources, R.color.gray_2, null))
        }

        val circleGray = ResourcesCompat.getDrawable(resources, R.drawable.ic_circle_gray, null)
        val circleEnable = ResourcesCompat.getDrawable(resources, R.drawable.ic_circle_enable, null)
        binding.missionSuggestContents.offscreenPageLimit = pagerAdapter.itemCount
        binding.missionSuggestContents.isUserInputEnabled = false
        binding.missionSuggestContents.adapter = pagerAdapter
        binding.missionSuggestContents.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            val coefficient: Float = 1f/(pagerAdapter.itemCount - 1)
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                when(position) {
                    0 -> {
                        binding.missionSuggestProgress2.background = circleGray
                        binding.missionSuggestProgress3.background = circleGray
                        binding.missionSuggestProgress4.background = circleGray
                        binding.missionSuggestProgress5.background = circleGray
                    }
                    1 -> {
                        binding.missionSuggestProgress2.background = circleEnable
                        binding.missionSuggestProgress3.background = circleGray
                        binding.missionSuggestProgress4.background = circleGray
                        binding.missionSuggestProgress5.background = circleGray
                    }
                    2 -> {
                        binding.missionSuggestProgress2.background = circleEnable
                        binding.missionSuggestProgress3.background = circleEnable
                        binding.missionSuggestProgress4.background = circleGray
                        binding.missionSuggestProgress5.background = circleGray
                    }
                    3 -> {
                        binding.missionSuggestProgress2.background = circleEnable
                        binding.missionSuggestProgress3.background = circleEnable
                        binding.missionSuggestProgress4.background = circleEnable
                        binding.missionSuggestProgress5.background = circleGray
                    }
                    4 -> {
                        binding.missionSuggestProgress2.background = circleEnable
                        binding.missionSuggestProgress3.background = circleEnable
                        binding.missionSuggestProgress4.background = circleEnable
                        binding.missionSuggestProgress5.background = circleEnable
                    }
                    5 -> {
                        binding.missionSuggestProgress2.background = circleEnable
                        binding.missionSuggestProgress3.background = circleEnable
                        binding.missionSuggestProgress4.background = circleEnable
                        binding.missionSuggestProgress5.background = circleEnable
                    }
                }
                binding.missionSuggestProgressMotion.progress = coefficient * (position + positionOffset)
            }
            override fun onPageSelected(position: Int) {
                viewModel.checkCanDoNext(position)
                when(position) {
                    0 -> binding.missionSuggestMotion.transitionToStart()
                    1 -> binding.missionSuggestMotion.transitionToEnd()
                }
            }
        })
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if(binding.missionSuggestContents.currentItem > 0) backClick()
            else closeClick()
        }
    }

    fun nextClick() {
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        if(binding.missionSuggestContents.currentItem == pagerAdapter.itemCount - 1) {
            val imgUri = viewModel.imgUri.value ?: return showToast("imgUri is null")
            val imgBytes = readBytes(requireContext(), imgUri) ?: return showToast("imgByte is null")
            viewModel.startSuggestMission(imgBytes) finish@{
                val missionId = viewModel.createdMissionId.value ?: return@finish showToast("mission id is null")
                findNavController().navigate(SuggestFragmentDirections.actionMissionSuggestFragmentToMissionSuggestDoneFragment(missionId))
            }
        }
        else {
            binding.missionSuggestContents.currentItem++
        }
    }

    fun backClick() {
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        binding.missionSuggestContents.currentItem--
    }

    fun closeClick() {
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        findNavController().popBackStack()
    }
}