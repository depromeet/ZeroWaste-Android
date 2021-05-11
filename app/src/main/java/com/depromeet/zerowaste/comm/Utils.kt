package com.depromeet.zerowaste.comm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide


fun genLayoutManager(context: Context, isVertical: Boolean = true, @IntRange(from = 1) spanCount: Int = 1, isStaggered: Boolean = false): RecyclerView.LayoutManager {
    val orientation: Int = if(isVertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
    return if(spanCount <= 1) {
        LinearLayoutManager(context, orientation, false)
    } else if(!isStaggered){
        GridLayoutManager(context, spanCount, orientation, false)
    } else {
        val manager = StaggeredGridLayoutManager(spanCount, orientation)
        manager
    }
}

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = true): View {
    return LayoutInflater.from(context).inflate(resource, this, attachToRoot)
}

fun ViewGroup.inflater(): LayoutInflater {
    return LayoutInflater.from(context)
}


/*
* viewpager
* */
@BindingAdapter("isVertical", "spanCount", "isStaggered", requireAll = false)
fun genLayoutManagerInXml(view: RecyclerView, isVertical: Boolean?, spanCount: Int?, isStaggered: Boolean?) {
    view.layoutManager = genLayoutManager(view.context, isVertical ?: true, spanCount ?: 1, isStaggered ?: false)
}

/*
* imageView
* */
@BindingAdapter("loadImage")
fun loadImage(view: ImageView, loadImage: String) {
    Glide.with(view).load(loadImage).into(view)
}

@BindingAdapter("loadImageCircle")
fun loadImageCircle(view: ImageView, loadImage: String) {
    Glide.with(view).load(loadImage).circleCrop().into(view)
}