package com.depromeet.zerowaste.comm

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<T, V: ViewBinding>(private val viewBinding: V, val onDataBind: (T?, V) -> Unit) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(data: T?) {
        onDataBind(data, viewBinding)
    }

}