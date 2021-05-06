package com.depromeet.zerowaste.feature.exam

import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.genLayoutManager
import com.depromeet.zerowaste.data.exam.RecycleSampleData
import com.depromeet.zerowaste.databinding.FragmentRecyclerSampleBinding
import com.depromeet.zerowaste.databinding.FragmentRecyclerSampleItemBinding

class ExamFragment: BaseFragment<FragmentRecyclerSampleBinding>(R.layout.fragment_recycler_sample) {

    override fun init() {
        val adapter = BaseRecycleAdapter(R.layout.fragment_recycler_sample_item, holder)
        for(i in 0..10) {
            adapter.addData(RecycleSampleData(
                "테스트$i-1",
                "테스트$i-2"
            ))
        }
//        binding.recyclerSample.layoutManager = genLayoutManager(requireContext())
//        binding.recyclerSample.layoutManager = genLayoutManager(requireContext(), isVertical = false)
//        binding.recyclerSample.layoutManager = genLayoutManager(requireContext(), isReverse = true)
        binding.recyclerSample.layoutManager = genLayoutManager(requireContext(), spanCount = 2)
        binding.recyclerSample.adapter = adapter
        adapter.needLoadMore = {
            // 스크롤이 가장 마지막 데이터 위치까지 이동했을 때
        }
    }

    private val holder = { data: RecycleSampleData, vBind: FragmentRecyclerSampleItemBinding, position: Int ->
        vBind.text1.text = data.text1
        vBind.text2.text = data.text2
    }

}