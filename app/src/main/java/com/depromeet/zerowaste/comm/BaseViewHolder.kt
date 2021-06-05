package com.depromeet.zerowaste.comm

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<T, V: ViewDataBinding>(private val binding: V, private val onDataBind: (BaseRecycleAdapter<T,V>.(T, V, Int) -> Unit)?) :
    RecyclerView.ViewHolder(binding.root) {

    @Suppress("UNCHECKED_CAST")
    private val baseBindingAdapter: BaseRecycleAdapter<T, V> by lazy {
        if(this.bindingAdapter is BaseRecycleAdapter<*, *>) this.bindingAdapter as BaseRecycleAdapter<T, V> else throw Exception("BaseViewHolder must use with BaseRecycleAdapter")
    }

    fun bindData(data: T, position: Int) {
        onDataBind?.also {
            it(baseBindingAdapter, data, binding, position)
        } ?: baseBindingAdapter.onDataBind(data, binding, position)
    }

}