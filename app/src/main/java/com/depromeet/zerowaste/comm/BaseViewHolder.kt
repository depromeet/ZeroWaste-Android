package com.depromeet.zerowaste.comm

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<T, V: ViewDataBinding>(private val viewBinding: V, private val onDataBind: ((T, V, Int) -> Unit)?) :
    RecyclerView.ViewHolder(viewBinding.root) {

    @Suppress("UNCHECKED_CAST")
    private val baseBindingAdapter: BaseRecycleAdapter<T,V>? by lazy {
        if(this.bindingAdapter is BaseRecycleAdapter<*, *>) this.bindingAdapter as BaseRecycleAdapter<T, V> else null
    }

    fun bindData(data: T, position: Int) {
        onDataBind?.also {
            it(data, viewBinding, position)
        } ?: baseBindingAdapter?.onDataBind(data, viewBinding, position)
    }

}