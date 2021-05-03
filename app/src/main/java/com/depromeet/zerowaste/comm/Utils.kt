package com.depromeet.zerowaste.comm

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding

fun genLayoutManager(context: Context, isVertical: Boolean = true, isReverse: Boolean = false, @IntRange(from = 1) spanCount: Int = 1, isStaggered: Boolean = false): RecyclerView.LayoutManager {
    val orientation: Int = if(isVertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
    return if(spanCount <= 1) {
        LinearLayoutManager(context, orientation, isReverse)
    } else if(!isStaggered){
        GridLayoutManager(context, spanCount, orientation, isReverse)
    } else {
        val manager = StaggeredGridLayoutManager(spanCount, orientation)
        manager.reverseLayout = isReverse
        manager
    }
}

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = true): View {
    return LayoutInflater.from(context).inflate(resource, this, attachToRoot)
}

fun ViewGroup.inflater(): LayoutInflater {
    return LayoutInflater.from(context)
}

@BindingAdapter("isVertical", "isReverse", "spanCount", "isStaggered", requireAll = false)
fun genLayoutManagerInXml(view: RecyclerView, isVertical: Boolean?, isReverse: Boolean?, spanCount: Int?, isStaggered: Boolean?) {
    view.layoutManager = genLayoutManager(view.context, isVertical ?: true, isReverse ?: false, spanCount ?: 1, isStaggered ?: false)
}

