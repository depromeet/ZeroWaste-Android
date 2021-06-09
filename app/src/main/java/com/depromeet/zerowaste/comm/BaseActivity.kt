package com.depromeet.zerowaste.comm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread
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
    private lateinit var mBinding: B
    protected val binding get() = mBinding

    private var loadingView: View? = null
    private var loadCount = 0

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        mBinding.lifecycleOwner = this
        try {
            loadingView = layoutInflater.inflate(R.layout.layout_loading, null)
            (mBinding.root.rootView as ViewGroup).addView(loadingView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        loadingView?.visibility = View.GONE
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(loadCount > 0) return true
        return super.dispatchTouchEvent(ev)
    }

    protected fun showToast(msg: String?) {
        if(msg == null) return
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(this@BaseActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun <V: ViewDataBinding> dialog(@LayoutRes layoutId: Int, widthDP: Float? = null, heightDP: Float? = null, onActive: (V) -> Unit) {
        lifecycleScope.launch(Dispatchers.Main) {
            BaseDialog(layoutId, widthDP, heightDP, onActive).show(supportFragmentManager, layoutId.toString())
        }
    }

    fun <T> bottomSheet(
        title: String,
        contents: List<Pair<T,String>>,
        selected: T? = null,
        onSelect: (T) -> Unit
    ) {
        lifecycleScope.launch(Dispatchers.Main) {
            BaseBottomSheet(title, contents, selected, onSelect).show(supportFragmentManager, title)
        }
    }

    fun startLoad() {
        lifecycleScope.launch(Dispatchers.Main) {
            loadCount++
            loadingView?.visibility = View.VISIBLE
            loadingView?.bringToFront()
        }
    }

    fun finishLoad() {
        lifecycleScope.launch(Dispatchers.Main) {
            loadCount--
            if(loadCount <= 0)  {
                loadCount = 0
                loadingView?.visibility = View.GONE
                mBinding.root.bringToFront()
            }
        }
    }

}

