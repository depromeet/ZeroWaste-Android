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
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.depromeet.zerowaste.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes val layoutId: Int
) : Fragment() {
    private lateinit var mBinding: B
    protected val binding get() = mBinding

    private var isInitialized = false

    open var statusBarBackGroundColorString: String = ""
    @ColorRes open var statusBarBackGroundColorRes: Int = -1
    open var isLightStatusBar: Boolean = true

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
            else -> ResourcesCompat.getColor(resources, R.color.white, null)
        }
        requireActivity().window.statusBarColor = color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.setSystemBarsAppearance(
                if(isLightStatusBar) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility = if(isLightStatusBar) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
        }

        if(!this::mBinding.isInitialized){
            mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.lifecycleOwner = this
        if(!isInitialized) {
            init()
            isInitialized = true
        }
    }

    abstract fun init()

    protected fun showToast(msg: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    protected fun <V: ViewDataBinding> dialog(@LayoutRes layoutId: Int, widthDP: Float? = null, heightDP: Float? = null, onActive: (V) -> Unit) {
        lifecycleScope.launch {
            BaseDialog(layoutId, widthDP, heightDP, onActive).show(parentFragmentManager, layoutId.toString())
        }
    }

    protected fun bottomSheet(
        title: String,
        contents: List<Pair<Int,String>>,
        selectedId: Int? = null,
        onSelect: (Int) -> Unit
    ) {
        lifecycleScope.launch(Dispatchers.Main) {
            BaseBottomSheet(title, contents, selectedId, onSelect).show(parentFragmentManager, title)
        }
    }

}