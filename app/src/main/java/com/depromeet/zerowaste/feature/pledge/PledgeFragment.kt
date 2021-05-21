package com.depromeet.zerowaste.feature.pledge

import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentPledgeBinding

class PledgeFragment : BaseFragment<FragmentPledgeBinding>(R.layout.fragment_pledge) {

    private val viewModel: PledgeViewModel by activityViewModels()
    private var isCanStart = false

    override fun init() {
        binding.fragment = this

        var position = 0
        viewModel.editNickname.observe(this) {
            chkCanStart(position, !it.isNullOrEmpty())
        }
        viewModel.actionDone.observe(this) {
            onClickPledge()
        }
        binding.pledgeVpViewpager.adapter = PledgeAdapter(this)
        binding.pledgeVpViewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(p: Int) {
                super.onPageSelected(p)
                position = p
                chkCanStart(position, !viewModel.editNickname.value.isNullOrEmpty())
            }
        })
        binding.dotsIndicator.setViewPager2(binding.pledgeVpViewpager)
    }

    private fun chkCanStart(position: Int, isNicknameEdited: Boolean) {
        if(position == 2 && isNicknameEdited) {
            isCanStart = true
            binding.pledgeBtnStart.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.black, null))
        } else {
            isCanStart = false
            binding.pledgeBtnStart.setBackgroundColor(Color.parseColor("#E3E3E3"))
        }
    }

    fun onClickPledge() {
        if(!isCanStart) return
        viewModel.updatePledgeCode.observe(this) {
            when(it) {
                0 -> findNavController().popBackStack()
                40001 -> showToast(resources.getString(R.string.pledge_nickname_duplicated))
                else -> showToast(resources.getString(R.string.pledge_fail))
            }
        }
        viewModel.updatePledge()
    }
}