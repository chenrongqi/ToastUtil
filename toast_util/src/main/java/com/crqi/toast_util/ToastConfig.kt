package com.crqi.toast_util

import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.crqi.toast_util.toast.IToastStyle
import com.crqi.toast_util.toast.dp2px

import java.lang.ref.WeakReference

/**
 * 默认的toast样式
 * 注意未设置自定义view时，默认的toastView不支持设置padding
 * padding是加在展示文案的textView上
 */
class ToastConfig : IToastStyle {
    override fun getToastText(): CharSequence? {
        return text
    }

    override fun getHorizontalMargin(): Float {
        return mHorizontalMargin
    }

    override fun getVerticalMargin(): Float {
        return mVerticalMargin
    }

    override fun getToastView(): View? {
        return view?.get()
    }

    override fun getPaddingLeft(): Int {
        return dp2px(SuperToast.getContext(), padLeft)
    }

    override fun getPaddingTop(): Int {
        return dp2px(SuperToast.getContext(), padTop)
    }

    override fun getGravity(): Int {
        return mGravity

    }

    override fun getXOffset(): Int {
        return mXOffset
    }

    override fun getYOffset(): Int {
        return dp2px(SuperToast.getContext(), mYOffset)
    }


    override fun getZ(): Int {
        return viewZ
    }

    override fun getCornerRadius(): Int {
        return dp2px(SuperToast.getContext(), cRadius)
    }

    override fun getBackgroundColor(): Int {
        return backGroundColor
    }

    override fun getTextColor(): Int {
        return toastTextColor
    }

    override fun getTextSize(): Float {
        return dp2px(SuperToast.getContext(), fontSize).toFloat()
    }

    override fun getMaxLines(): Int {
        return testLines
    }

    override fun getPaddingRight(): Int {
        return dp2px(SuperToast.getContext(), padLeft)
    }

    override fun getPaddingBottom(): Int {
        return dp2px(SuperToast.getContext(), padTop)
    }

    override fun getDuration(): Int {
        return mDuration
    }

    companion object {
        @JvmStatic
        fun creatConfig(duration: Int): ToastConfig {
            val config = ToastConfig()
            config.mDuration = duration
            return config
        }
    }

    var mDuration = Toast.LENGTH_SHORT
    var mGravity = Gravity.BOTTOM
    var mXOffset = 0
    var mYOffset = 24
    var fontSize = 16f
    var testLines = 3
    var padTop = 12
    var padLeft = 20
    var cRadius = 12
    var viewZ = 3
    var backGroundColor = -0x78000000
    var toastTextColor = -0x11000001
    var mHorizontalMargin: Float = 0.toFloat()
    var mVerticalMargin: Float = 0.toFloat()

    var view: WeakReference<View>? = null
    var text: CharSequence? = null


    /**
     * 只copy属性,不copy view和text
     */
    fun copy(config: ToastConfig?) {
        if (config == null) return
        this.mDuration = config.mDuration
        this.mVerticalMargin = config.mVerticalMargin
        this.mHorizontalMargin = config.mHorizontalMargin
        this.mYOffset = config.mYOffset
        this.mGravity = config.mGravity
        this.backGroundColor = config.backGroundColor
        this.cRadius = config.cRadius
        this.padLeft = config.padLeft
        this.padTop = config.padTop
        this.testLines = config.testLines
        this.fontSize = config.fontSize
        this.toastTextColor = config.toastTextColor
        this.viewZ = config.viewZ
    }

}