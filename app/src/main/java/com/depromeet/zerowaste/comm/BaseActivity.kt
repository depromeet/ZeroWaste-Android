package com.depromeet.zerowaste.comm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.depromeet.zerowaste.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes val layoutId: Int
) : AppCompatActivity() {
    lateinit var binding: B

    private var loadingView: View? = null
    private var loadCount = 0

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        try {
            loadingView = layoutInflater.inflate(R.layout.layout_loading, null)
            (binding.root.rootView as ViewGroup).addView(loadingView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        loadingView?.visibility = View.GONE
    }

    protected fun showToast(msg: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(this@BaseActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun startLoad() {
        lifecycleScope.launch(Dispatchers.Main) {
            loadCount++
            loadingView?.bringToFront()
            loadingView?.visibility = View.VISIBLE
        }
    }

    fun finishLoad() {
        lifecycleScope.launch(Dispatchers.Main) {
            loadCount--
            if(loadCount <= 0)  {
                loadCount = 0
                loadingView?.visibility = View.GONE
                binding.root.bringToFront()
            }
        }
    }

}

