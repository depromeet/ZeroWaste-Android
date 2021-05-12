package com.depromeet.zerowaste.comm

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<T, V: ViewDataBinding>(private val binding: V, private val onDataBind: ((T, V, Int) -> Unit)?) :
    RecyclerView.ViewHolder(binding.root) {

    @Suppress("UNCHECKED_CAST")
    private val baseBindingAdapter: BaseRecycleAdapter<T, V>? by lazy {
        try {
            if(this.bindingAdapter is BaseRecycleAdapter<*, *>) this.bindingAdapter as BaseRecycleAdapter<T, V> else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun bindData(data: T, position: Int) {
        onDataBind?.also {
            it(data, binding, position)
        } ?: baseBindingAdapter?.onDataBind(data, binding, position)
    }

}