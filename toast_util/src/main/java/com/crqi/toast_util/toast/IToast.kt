package com.crqi.toast_util.toast

import android.view.View

/**
 */
interface IToast {
    fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): IToast?
    fun setDuration(durationMillis: Int): IToast?
    fun setView(view: View?): IToast?
    fun setMargin(horizontalMargin: Float, verticalMargin: Float): IToast?
    fun setText(text: CharSequence?): IToast?
    fun show()
    fun cancel()
}