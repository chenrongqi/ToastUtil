package com.crqi.toast_util.toast

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt

/**
 * 设置toast 文本，同时会设置字体颜色最大行数等信息
 */
internal fun View.setText(text: CharSequence, config: IToastStyle?) {
    val textView = findViewById<TextView?>(android.R.id.message)
    //设置自定义颜色
    if (textView != null) {
        config?.apply {
            setTextViewParameter(textView, config)
        }
        textView.text = text
    } else if (this is ViewGroup) {
        for (i in 0..childCount) {
            val textView = getChildAt(i)
            if (textView is TextView) {
                config?.apply {
                    setTextViewParameter(
                        textView,
                        config
                    )
                }
                textView.text = text
                break
            }
        }
    }
}

/**
 * 设置文本属性，
 * 文字大小，文字颜色，最大行数
 * textView的padding
 */
internal fun setTextViewParameter(textView: TextView, config: IToastStyle) {
    textView.setTextColor(config.getTextColor())
    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.getTextSize())
    if (config.getMaxLines() > 0) {
        // 设置最大显示行数
        textView.maxLines = config.getMaxLines()
    }
    if(config.getToastView()!=null){
        //使用了系统的toast样式时不支持自定义toast的padding
        textView.setPadding(config.getPaddingLeft(),config.getPaddingTop(),config.getPaddingRight(),config.getPaddingBottom())
    }
}

/**
 * 扩展设置toast通用背景色，包括z
 */
internal fun View.setBackGroundDrawable(config: IToastStyle) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // 设置 Z 轴阴影
        z = config.getZ().toFloat()
    }
    background = generateRoundDrawable(
        config.getCornerRadius().toFloat(),
        config.getCornerRadius().toFloat(),
        config.getBackgroundColor(),
        0,
        config.getBackgroundColor()
    )
}

/**
 * 生成圆角背景,可自定义边框颜色
 * @param topRadius         顶部圆角大小(单位：px)
 * @param bottomRadius      底部圆角大小(单位：px)
 * @param borderColor       边框颜色
 * @param borderWidth       边框宽度单位：(px)
 * @param fillColor         内部填充的颜色
 */
private fun generateRoundDrawable(
    topRadius: Float = 0.0F,
    bottomRadius: Float = 0.0F,
    @ColorInt borderColor: Int = Color.TRANSPARENT,
    borderWidth: Int = 0,
    @ColorInt fillColor: Int
): Drawable {
    val outerRadii = floatArrayOf(
        topRadius,
        topRadius,
        topRadius,
        topRadius,
        bottomRadius,
        bottomRadius,
        bottomRadius,
        bottomRadius
    )
    val gradientDrawable = GradientDrawable()
    gradientDrawable.cornerRadii = outerRadii
    gradientDrawable.setColor(fillColor)
    gradientDrawable.setStroke(borderWidth, borderColor)
    return gradientDrawable
}

fun dp2px(context: Context?, dpVal: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpVal, context?.resources?.displayMetrics
    ).toInt()
}

fun dp2px(context: Context?, dpVal: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpVal.toFloat(), context?.resources?.displayMetrics
    ).toInt()
}