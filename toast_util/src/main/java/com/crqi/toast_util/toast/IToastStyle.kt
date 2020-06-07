package com.crqi.toast_util.toast

import android.view.View

/**
 * author : crqi
 * time   : 2019/07/27
 * desc   : 默认样式接口
 */
internal interface IToastStyle {

    fun getGravity(): Int

    fun getXOffset(): Int

    fun getYOffset(): Int

    fun getZ(): Int

    fun getCornerRadius(): Int

    fun getBackgroundColor(): Int

    fun getTextColor(): Int

    fun getTextSize(): Float

    fun getMaxLines(): Int

    fun getPaddingLeft(): Int

    fun getPaddingTop(): Int

    fun getPaddingRight(): Int

    fun getPaddingBottom(): Int

    fun getDuration(): Int

    fun getToastView(): View?

    fun getHorizontalMargin(): Float

    fun getVerticalMargin(): Float

    fun getToastText(): CharSequence?

}

