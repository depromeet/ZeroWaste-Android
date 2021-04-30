package com.depromeet.zerowaste.comm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun genLayoutManager(context: Context, isVertical: Boolean = true, isReverse: Boolean = false, spanCount: Int = 1, isStaggered: Boolean = false): RecyclerView.LayoutManager {
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