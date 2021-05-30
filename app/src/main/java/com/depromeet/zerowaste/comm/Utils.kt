package com.depromeet.zerowaste.comm

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.bumptech.glide.Glide
import com.depromeet.zerowaste.BuildConfig
import com.depromeet.zerowaste.R


fun getPreference(context: Context): SharedPreferences {
    return EncryptedSharedPreferences.create(
        BuildConfig.SECURE_KEY,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun genLayoutManager(
    context: Context,
    isVertical: Boolean = true,
    @IntRange(from = 1) spanCount: Int = 1,
    isStaggered: Boolean = false
): RecyclerView.LayoutManager {
    val orientation: Int = if (isVertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
    return if (spanCount <= 1) {
        LinearLayoutManager(context, orientation, false)
    } else if (!isStaggered) {
        GridLayoutManager(context, spanCount, orientation, false)
    } else {
        StaggeredGridLayoutManager(spanCount, orientation)
    }
}

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = true): View {
    return inflater().inflate(resource, this, attachToRoot)
}

fun ViewGroup.inflater(): LayoutInflater {
    return LayoutInflater.from(context)
}

fun dpToPx(context: Context, dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )
}

fun spToPx(context: Context, sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        context.resources.displayMetrics
    )
}

fun pxToDp(context: Context, px: Float): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun pxToSp(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.scaledDensity
}

fun dpToSp(context: Context, dp: Float): Float {
    return pxToSp(context, dpToPx(context, dp))
}

fun spToDp(context: Context, sp: Float): Float {
    return pxToDp(context, spToPx(context, sp))
}

/*
* viewpager
* */
@BindingAdapter("isVertical", "spanCount", "isStaggered", requireAll = false)
fun genLayoutManagerInXml(
    view: RecyclerView,
    isVertical: Boolean?,
    spanCount: Int?,
    isStaggered: Boolean?
) {
    view.layoutManager =
        genLayoutManager(view.context, isVertical ?: true, spanCount ?: 1, isStaggered ?: false)
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

@BindingAdapter("loadImageCenterCrop")
fun loadImageCenterCrop(view: ImageView, loadImage: String) {
    Glide.with(view).load(loadImage).centerCrop().into(view)
}

@BindingAdapter("loadImageMissionDifficulty")
fun loadImageMissionDifficulty(view: ImageView, difficulty: String) {
    when (difficulty) {
        "0" -> view.setImageResource(R.drawable.ic_level_0)
        "1" -> view.setImageResource(R.drawable.ic_level_1)
        "2" -> view.setImageResource(R.drawable.ic_level_2)
        "3" -> view.setImageResource(R.drawable.ic_level_3)
        "4" -> view.setImageResource(R.drawable.ic_level_4)
    }
}