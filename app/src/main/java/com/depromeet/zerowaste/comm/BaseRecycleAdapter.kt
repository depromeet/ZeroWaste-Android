package com.depromeet.zerowaste.comm

import android.animation.AnimatorSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

open class BaseRecycleAdapter<T, V : ViewDataBinding>: RecyclerView.Adapter<BaseViewHolder<T, V>> {

    constructor(@LayoutRes layoutId: Int, onDataBind: BaseRecycleAdapter<T,V>.(T, V, Int) -> Unit) {
        this.layoutId = layoutId
        this.onDataBind = onDataBind
    }

    protected constructor(@LayoutRes layoutId: Int) {
        this.layoutId = layoutId
        this.onDataBind = null
    }

    private val layoutId: Int
    private val onDataBind: (BaseRecycleAdapter<T,V>.(T, V, Int) -> Unit)?

    private val items = mutableListOf<T>()
    private var mLastPosition = -1
    var isLoadAnimFirstOnly = true
    var loadAnimation : ((View) -> AnimatorSet)? = { AnimatorSet() }

    var attachedRecyclerView: RecyclerView? = null
        private set
    var needLoadMore: (() -> Unit)? = null

    private val scrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val orientation = when(val manager = recyclerView.layoutManager) {
                is LinearLayoutManager -> manager.orientation
                is StaggeredGridLayoutManager -> manager.orientation
                else -> null
            }
            if((orientation == RecyclerView.VERTICAL && !recyclerView.canScrollVertically(1)) ||
                (orientation == RecyclerView.HORIZONTAL
                        && (!recyclerView.canScrollHorizontally(1) && recyclerView.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR) ||
                        !recyclerView.canScrollHorizontally(-1) && recyclerView.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL)) {
                recyclerView.post { needLoadMore?.also { it() } }
            }
        }
    }

    open fun onDataBind(item: T, bind: V, position: Int) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(DataBindingUtil.inflate(parent.inflater(), layoutId, parent, false), onDataBind)

    override fun onBindViewHolder(holder: BaseViewHolder<T, V>, position: Int) = holder.bindData(items[position], position)

    override fun getItemCount() = items.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        attachedRecyclerView = recyclerView
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeOnScrollListener(scrollListener)
        attachedRecyclerView = null
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<T, V>) {
        super.onViewAttachedToWindow(holder)
        if(!isLoadAnimFirstOnly || holder.layoutPosition > mLastPosition) {
            loadAnimation?.let { it(holder.itemView) }?.start()
        }
        if(mLastPosition < holder.layoutPosition) mLastPosition = holder.layoutPosition
    }

    open fun setData(data: Collection<T>?) {
        if(data == null) return
        if (data !== items) {
            if (!data.isEmpty()) {
                items.clear()
                items.addAll(data)
                mLastPosition = -1
                notifyDataSetChanged()
            } else {
                items.clear()
                mLastPosition = -1
                notifyDataSetChanged()
            }
        } else if (!data.isEmpty()) {
            items.clear()
            val newList = ArrayList(data)
            items.addAll(newList)
            mLastPosition = -1
            notifyDataSetChanged()
        }
    }

    open fun changeData(data: T?, @IntRange(from = 0) position: Int) {
        if(data == null) return
        items[position] = data
        notifyItemChanged(position)
    }

    open fun changeData(data: Collection<T>?, @IntRange(from = 0) position: Int) {
        if(data == null) return
        for(i in itemCount - 1 downTo 0) {
            if(i <= position) items.removeLast()
        }
        items.addAll(data)
        notifyItemRangeInserted(position, data.size)
        compatibilityDataSizeChanged(data.size)
    }

    open fun addData(data: T?) {
        if(data == null) return
        items.add(data)
        notifyItemInserted(items.size)
        compatibilityDataSizeChanged(1)
    }

    open fun addData(@IntRange(from = 0) position: Int, newData: Collection<T>?) {
        if(newData == null) return
        items.addAll(position, newData)
        notifyItemRangeInserted(position, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    open fun addData(newData: Collection<T>?) {
        if(newData == null) return
        items.addAll(newData)
        notifyItemRangeInserted(items.size - newData.size, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    open fun getItems(): List<T> {
        return items
    }

    open fun getItem(@IntRange(from = 0) position: Int): T? {
        return items.getOrNull(position)
    }

    open fun getItemPosition(item: T): Int {
        return if (items.isNotEmpty()) items.indexOf(item) else -1
    }

    private fun compatibilityDataSizeChanged(size: Int) {
        if (items.size == size) {
            notifyDataSetChanged()
        }
    }
}

fun recycleAnimation(lambda: (View) -> AnimatorSet) = lambda