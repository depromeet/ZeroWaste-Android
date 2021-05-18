package com.depromeet.zerowaste.comm

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.*
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

class SpanStrBuilder(private val context: Context) {

    private var index = 0
    private var lastLength = 0
    private var targetStr = ""

    private val lengths: HashMap<Int, Pair<Int, Int>> = HashMap()
    private val typefaces: HashMap<Int, Typeface?> = HashMap()
    private val colors: HashMap<Int, Int?> = HashMap()
    private val sizes: HashMap<Int, Float?> = HashMap()
    private val underLines: HashSet<Int> = HashSet()
    private val backColors: HashMap<Int, Int?> = HashMap()

    fun add(
        text: String,
        @FontRes fontRes: Int? = null,
        typeface: Typeface? = null,
        @ColorRes colorRes: Int? = null,
        @ColorInt color: Int? = null,
        sp: Float? = null,
        isUnderLine: Boolean = false,
        @ColorRes backColorRes: Int? = null,
        @ColorInt backColor: Int? = null,
    ): SpanStrBuilder {
        lengths[index] = Pair(lastLength, lastLength + text.length)
        fontRes?.also {
            typefaces[index] = ResourcesCompat.getFont(context, it)
        } ?: typeface?.also { typefaces[index] = it }
        colorRes?.also {
            colors[index] = ResourcesCompat.getColor(context.resources, it, null)
        } ?: color?.also { colors[index] = it }
        sp?.also {
            sizes[index] = it
        }
        if(isUnderLine) underLines.add(index)
        backColorRes?.also {
            backColors[index] = ResourcesCompat.getColor(context.resources, it, null)
        } ?: backColor?.also { backColors[index] = it }
        lastLength += text.length
        targetStr += text
        index++
        return this
    }

    fun build(): SpannableStringBuilder {
        val res = SpannableStringBuilder(targetStr)
        lengths.forEach { entry ->
            val index = entry.key
            val start = entry.value.first
            val end = entry.value.second
            typefaces[index]?.also {
                res.setSpan(CustomTypefaceSpan(it), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            colors[index]?.also {
                res.setSpan(ForegroundColorSpan(it), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            sizes[index]?.also {
                res.setSpan(AbsoluteSizeSpan(spToDp(context, it).toInt(),true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            if(underLines.contains(index)) {
                res.setSpan(UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            backColors[index]?.also {
                res.setSpan(BackgroundColorSpan(it), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        clear()
        return res
    }

    private fun clear() {
        index = 0
        lastLength = 0
        targetStr = ""
        lengths.clear()
        typefaces.clear()
        colors.clear()
        sizes.clear()
        underLines.clear()
        backColors.clear()
    }

    inner class CustomTypefaceSpan(private val newType: Typeface) : TypefaceSpan(null) {
        override fun updateDrawState(ds: TextPaint) {
            applyCustomTypeFace(ds, newType)
        }
        override fun updateMeasureState(paint: TextPaint) {
            applyCustomTypeFace(paint, newType)
        }
        private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
            val oldStyle: Int
            val old: Typeface = paint.typeface
            oldStyle = old.style
            val fake = oldStyle and tf.style.inv()
            if (fake and Typeface.BOLD != 0) {
                paint.isFakeBoldText = true
            }
            if (fake and Typeface.ITALIC != 0) {
                paint.textSkewX = -0.25f
            }
            paint.typeface = tf
        }
    }
}