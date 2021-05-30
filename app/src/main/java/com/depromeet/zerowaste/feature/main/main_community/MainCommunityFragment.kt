package com.depromeet.zerowaste.feature.main.main_community

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.recycleAnimation
import com.depromeet.zerowaste.data.community.Post
import com.depromeet.zerowaste.data.mission.Tag
import com.depromeet.zerowaste.databinding.*
import com.depromeet.zerowaste.feature.main.MainFragmentDirections
import com.depromeet.zerowaste.feature.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainCommunityFragment :
    BaseFragment<FragmentMainCommunityBinding>(R.layout.fragment_main_community) {

    private val viewModel: MainCommunityViewModel by viewModels()

    override fun init() {
        binding.vm = viewModel
    }

}