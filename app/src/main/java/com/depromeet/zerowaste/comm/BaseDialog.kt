package com.depromeet.zerowaste.comm

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

class BaseDialog<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
    private val widthDP: Float? = null,
    private val heightDP: Float? = null,
    private val onActive: (B) -> Unit
): DialogFragment() {
    private lateinit var mBinding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        onActive(mBinding)
    }

    override fun onResume() {
        super.onResume()
        val param = dialog?.window?.attributes ?: return
        widthDP?.apply { param.width = dpToPx(requireContext(), this).toInt() }
        heightDP?.apply { param.height = dpToPx(requireContext(), this).toInt() }
        dialog?.window?.attributes = param
    }
}