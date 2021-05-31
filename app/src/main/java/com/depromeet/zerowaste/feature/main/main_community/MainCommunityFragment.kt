package com.depromeet.zerowaste.feature.main.main_community

import android.util.Log
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.App
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.databinding.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCommunityFragment :
    BaseFragment<FragmentMainCommunityBinding>(R.layout.fragment_main_community) {

    private val viewModel: MainCommunityViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getMissionTest()
    }

    override fun init() {
        binding.vm = viewModel
        binding.mainHomeCommunityTvTitle.setOnClickListener {
            dialog(R.layout.sample_dialog, 400f) { binding: SampleDialogBinding ->

            }
            bottomSheet("테스트",
                arrayListOf(
                    Pair(0, "인기순1"),
                    Pair(1, "참여순2"),
                    Pair(2, "참여순3"),
                    Pair(3, "참여순4"),
                    Pair(4, "참여순5"),
                    Pair(5, "참여순6"),
                    Pair(6, "참여순7"),
                    Pair(7, "참여순8"),
                    Pair(8, "참여순9"),
                    Pair(9, "참여순10"),
                    Pair(10, "참여순11"),
                    Pair(11, "참여순12"),
                    Pair(12, "참여순13"),
                    Pair(13, "참여순14"),
                    Pair(14, "참여순15"),
                    Pair(15, "참여순16"),
                    Pair(16, "참여순17"),
                    Pair(17, "참여순18"),
                    Pair(18, "참여순19")
                ),
                0
            ) {
                Log.e("selected", it.toString())
            }
        }
    }

}