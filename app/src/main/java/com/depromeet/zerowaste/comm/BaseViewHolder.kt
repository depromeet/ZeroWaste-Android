package com.depromeet.zerowaste.comm

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<T, V: ViewBinding>(private val viewBinding: V, val onDataBind: (T, V, Int) -> Unit) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(data: T, position: Int) {
        onDataBind(data, viewBinding, position)
    }

}