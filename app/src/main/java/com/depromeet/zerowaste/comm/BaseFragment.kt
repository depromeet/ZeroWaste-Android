package com.depromeet.zerowaste.comm

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.depromeet.zerowaste.R

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes val layoutId: Int
) : Fragment() {
    protected lateinit var binding: B
    private var isInitialized = false

    open var statusBarBackGroundColorString: String = ""
    @ColorRes open var statusBarBackGroundColorRes: Int = -1
    open var isLightStatusBar: Boolean = false

    protected lateinit var preference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preference = getPreference(requireContext())
        val color = when {
            statusBarBackGroundColorString != "" -> Color.parseColor(statusBarBackGroundColorString)
            statusBarBackGroundColorRes != -1 -> ResourcesCompat.getColor(resources, statusBarBackGroundColorRes, null)
            else -> ResourcesCompat.getColor(resources, R.color.sub1, null)
        }
        requireActivity().window.statusBarColor = color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.setSystemBarsAppearance(
                if(isLightStatusBar) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0 ,
               WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility = if(isLightStatusBar) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
        }
        if(!this::binding.isInitialized){
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        if(!isInitialized) {
            init()
            isInitialized = true
        }
    }

    abstract fun init()

    protected fun showToast(msg: String) =
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}