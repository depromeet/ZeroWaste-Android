package com.depromeet.zerowaste.comm

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.databinding.LayoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BaseBottomSheet(
    private val title: String,
    private val contents: List<Pair<Int,String>>,
    private val selectedId: Int? = null,
    private val onSelect: (Int) -> Unit): BottomSheetDialogFragment() {

    private lateinit var binding: LayoutBottomSheetBinding
    private val radioWithPositionMap: HashMap<Int, Int> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_bottom_sheet, container, false)
        binding.layoutBottomSheetTitle.text = title
        genRadios()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val baseDialog = dialog
        if (baseDialog !is BottomSheetDialog) return
        baseDialog.behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        val matrix = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().display?.getRealMetrics(matrix)
        } else {
            requireActivity().windowManager.defaultDisplay.getMetrics(matrix)
        }
        val maxScrollHeight = (matrix.heightPixels * 0.7).toInt()
        baseDialog.setOnShowListener {
            if(binding.layoutBottomSheetScroll.height > maxScrollHeight) {
                val params = binding.layoutBottomSheetScroll.layoutParams
                params.height = maxScrollHeight
                binding.layoutBottomSheetScroll.layoutParams = params
            }
        }
    }


    private fun genRadios() {
        contents.forEachIndexed { i, content ->
            layoutInflater.inflate(R.layout.layout_bottom_sheet_radio_btn, binding.layoutBottomSheetRadioGroup)
            val radioBtn = binding.layoutBottomSheetRadioGroup[i] as RadioButton
            val newId = View.generateViewId()
            radioWithPositionMap[newId] = content.first
            radioBtn.id = newId
            radioBtn.text = content.second
            if(content.first == selectedId) {
                radioBtn.typeface = ResourcesCompat.getFont(requireContext(), R.font.apple_sd_gothic_neo_b)
                binding.layoutBottomSheetRadioGroup.check(newId)
            }
        }
        binding.layoutBottomSheetRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            radioWithPositionMap[checkedId]?.also {
                onSelect(it)
                dismiss()
            }
        }
    }
}