package com.depromeet.zerowaste.comm

import android.provider.ContactsContract
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseRecycleAdapter<T, V : ViewDataBinding>(@LayoutRes private val layoutId: Int, private val onDataBind: (T, V, Int) -> Unit): RecyclerView.Adapter<BaseViewHolder<T, V>>() {

    private val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(DataBindingUtil.inflate(parent.inflater(), layoutId, parent, false), onDataBind)

    override fun onBindViewHolder(holder: BaseViewHolder<T, V>, position: Int) = holder.bindData(items[position], position)

    override fun getItemCount() = items.size

    open fun setData(data: Collection<T>) {
        if (data !== this.items) {
            this.items.clear()
            if (!data.isNullOrEmpty()) {
                this.items.addAll(data)
            }
        } else {
            if (!data.isNullOrEmpty()) {
                val newList = ArrayList(data)
                this.items.clear()
                this.items.addAll(newList)
            } else {
                this.items.clear()
            }
        }
    }

    open fun addData(data: T) {
        this.items.add(data)
        notifyItemInserted(this.items.size)
        compatibilityDataSizeChanged(1)
    }

    open fun addData(@IntRange(from = 0) position: Int, newData: Collection<T>) {
        this.items.addAll(position, newData)
        notifyItemRangeInserted(position, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    open fun addData(newData: Collection<T>) {
        this.items.addAll(newData)
        notifyItemRangeInserted(this.items.size - newData.size, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    open fun getItem(@IntRange(from = 0) position: Int): T {
        return items[position]
    }

    open fun getItemOrNull(@IntRange(from = 0) position: Int): T? {
        return items.getOrNull(position)
    }

    open fun getItemPosition(item: T?): Int {
        return if (item != null && items.isNotEmpty()) items.indexOf(item) else -1
    }

    private fun compatibilityDataSizeChanged(size: Int) {
        if (this.items.size == size) {
            notifyDataSetChanged()
        }
    }

}