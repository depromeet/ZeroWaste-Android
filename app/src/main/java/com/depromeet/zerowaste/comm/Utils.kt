package com.depromeet.zerowaste.comm

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.depromeet.zerowaste.BuildConfig
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.data.Theme
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


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
* All View
* */
@BindingAdapter("customWidthDP")
fun customWidthDP(view: View, width: Number) {
    val param = view.layoutParams
    param.width = if(width is Int) {
        if(width == ViewGroup.LayoutParams.WRAP_CONTENT || width == ViewGroup.LayoutParams.MATCH_PARENT) {
            width
        } else {
            dpToPx(view.context, width.toFloat()).toInt()
        }
    } else {
        dpToPx(view.context, width.toFloat()).toInt()
    }
    view.layoutParams = param
}

@BindingAdapter("customHeightDP")
fun customHeightDP(view: View, height: Number) {
    val param = view.layoutParams
    param.height = if(height is Int) {
        if(height == ViewGroup.LayoutParams.WRAP_CONTENT || height == ViewGroup.LayoutParams.MATCH_PARENT) {
             height
        } else {
            dpToPx(view.context, height.toFloat()).toInt()
        }
    } else {
        dpToPx(view.context, height.toFloat()).toInt()
    }
    view.layoutParams = param
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

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, loadImage: Uri?) {
    Glide.with(view).load(loadImage).into(view)
}

@BindingAdapter("loadImage", "imageRadius", requireAll = true)
fun loadImageRadius(view: ImageView, loadImage: String?, imageRadius: Float) {
    Glide.with(view).load(loadImage).transform(RoundedCorners(dpToPx(view.context,imageRadius).toInt())).into(view)
}

@BindingAdapter("loadImage", "imageRadius", requireAll = true)
fun loadImageRadius(view: ImageView, loadImage: Uri?, imageRadius: Float) {
    Glide.with(view).load(loadImage).transform(RoundedCorners(dpToPx(view.context,imageRadius).toInt())).into(view)
}

@BindingAdapter("loadImageRank")
fun loadImageRank(view: ImageView, rank: Int) {
    when (rank) {
        1 -> view.setImageResource(R.drawable.ic_rank_1)
        2 -> view.setImageResource(R.drawable.ic_rank_2)
        3 -> view.setImageResource(R.drawable.ic_rank_3)
    }
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
fun loadImageMissionDifficulty(view: ImageView, difficulty: Difficulty) {
    when (difficulty.level) {
        0 -> view.setImageResource(R.drawable.ic_level_0)
        1 -> view.setImageResource(R.drawable.ic_level_1)
        2 -> view.setImageResource(R.drawable.ic_level_2)
        3 -> view.setImageResource(R.drawable.ic_level_3)
        4 -> view.setImageResource(R.drawable.ic_level_4)
    }
}

@BindingAdapter("loadImageMissionDifficultyOff")
fun loadImageMissionDifficultyOff(view: ImageView, difficulty: Difficulty?) {
    when (difficulty?.level) {
        0 -> view.setImageResource(R.drawable.ic_level_0_off)
        1 -> view.setImageResource(R.drawable.ic_level_1_off)
        2 -> view.setImageResource(R.drawable.ic_level_2_off)
        3 -> view.setImageResource(R.drawable.ic_level_3_off)
        4 -> view.setImageResource(R.drawable.ic_level_4_off)
    }
}

@BindingAdapter("loadTxtMissionDifficulty")
fun loadTxtMissionDifficulty(view: TextView, difficulty: Difficulty?) {
    view.text = when (difficulty?.level) {
        0 -> view.resources.getText(R.string.mission_level_0_txt)
        1 -> view.resources.getText(R.string.mission_level_1_txt)
        2 -> view.resources.getText(R.string.mission_level_2_txt)
        3 -> view.resources.getText(R.string.mission_level_3_txt)
        4 -> view.resources.getText(R.string.mission_level_4_txt)
        else -> ""
    }
}

@BindingAdapter("setChips")
fun setChips(view: ChipGroup, themes: List<Theme>) {
    for (theme in themes) {
        val chip = Chip(view.context)
        chip.apply {
            text = theme.toString()
            setChipBackgroundColorResource(R.color.gray_5)
            setTextColor(view.resources.getColor(R.color.black))
            textSize = 13f
            chipCornerRadius =4f
            chipEndPadding =  4f
            chipStartPadding =  4f
            chipMinHeight =  24f
            view.addView(this)
        }
    }
}