package com.crqi.toast_util

import android.view.View
import androidx.annotation.ColorInt
import com.crqi.toast_util.toast.IToast
import java.lang.ref.WeakReference

/**
 * toast 建造者
 */
class ToastBuilder() : IToast {
    fun setGravity(gravity: Int): IToast? {
        config.mGravity = gravity
        return this@ToastBuilder
    }

    override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): IToast {
        config.mGravity = gravity
        config.mXOffset = xOffset
        config.mYOffset = yOffset
        return this@ToastBuilder
    }

    override fun setDuration(durations: Int): IToast {
        config.mDuration = durations
        return this@ToastBuilder
    }

    /**
     * textView的id,请用android.R.id.message.否则文字设置可能无无效
     */
    override fun setView(view: View?): IToast {
        view?.apply {
            config.view = WeakReference(this)
        }
        return this@ToastBuilder
    }

    override fun setMargin(horizontalMargin: Float, verticalMargin: Float): IToast {
        config.mHorizontalMargin = horizontalMargin
        config.mVerticalMargin = verticalMargin
        return this@ToastBuilder
    }

    override fun setText(text: CharSequence?): IToast {
        msg = text
        return this@ToastBuilder
    }

    fun setTextColor(@ColorInt color: Int): IToast {
        config.toastTextColor = color
        return this@ToastBuilder
    }

    fun setBackgroundColor(@ColorInt color: Int): IToast {
        config.backGroundColor = color
        return this@ToastBuilder
    }

    fun buildToastStyle(): ToastConfig {
        return config
    }

    override fun show() {
        msg?.apply {
            SuperToast.makeText(this, config)
        }
    }

    /**
     * 未实现的方法
     */
    override fun cancel() {
    }

    val config = ToastConfig()
    var msg: CharSequence? = null

}