package com.crqi.toast_util.toast

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast
import com.crqi.toast_util.SuperToast


/**
 * 系统 toast类
 * 1、对8.0以下的系统显示toast时进行了hook，捕获了阻塞异常
 * 2、对全部的toast的addview进行了hook，捕获了addview异常
 */
internal class SystemToast private constructor(text: CharSequence, config: IToastStyle) : IToast {
    private var mToast: Toast

    init {
        mToast = if (config.getDuration() == Toast.LENGTH_SHORT) {
            getToast(text, Toast.LENGTH_SHORT)
        } else if (config.getDuration() == Toast.LENGTH_LONG) {
            getToast(text, Toast.LENGTH_LONG)
        } else if (config.getDuration() < 3000) {
            getToast(text, Toast.LENGTH_SHORT)
        } else {
            getToast(text, Toast.LENGTH_LONG)
        }
        setGravity(config.getGravity(), config.getXOffset(), config.getYOffset())
        setMargin(config.getHorizontalMargin(), config.getVerticalMargin())
        setView(config.getToastView())
        init(SuperToast.getContext())
        mToast.view.setText(text, config)
        mToast.view.setBackGroundDrawable(config)

    }


    private fun getToast(text: CharSequence, duration: Int, config: IToastStyle? = null): Toast {
        return if (config?.getToastView() == null) {
            Toast.makeText(SuperToast.getContext(), text, duration)
        } else {
            val toast = Toast(SuperToast.getContext())
            toast.view = config.getToastView()
            toast.duration = duration
            toast
        }
    }

    private fun init(context: Context) {
        setContextCompat(mToast.view, SafeToastContext(context))
    }

    private fun setContextCompat(view: View, context: Context) {
        if (Build.VERSION.SDK_INT < 26) {
            try {
                val field = View::class.java.getDeclaredField("mContext")
                field.isAccessible = true
                field.set(view, context)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): SystemToast {
        mToast.setGravity(gravity, xOffset, yOffset)
        return this
    }

    override fun setDuration(durationMillis: Int): IToast {
        mToast.duration = durationMillis
        return this
    }

    /**
     * @param view 传入view
     * @return 自身对象
     */
    override fun setView(view: View?): SystemToast {
        view?.apply {
            mToast.view = view
        }
        return this
    }

    override fun setMargin(horizontalMargin: Float, verticalMargin: Float): SystemToast {
        mToast.setMargin(horizontalMargin, verticalMargin)
        return this
    }

    /**
     * @param text 传入字符串
     * @return 自身对象
     */
    override fun setText(text: CharSequence?): SystemToast? {
        mToast.setText(text)
        return this
    }

    override fun show() {
        mToast.show()
    }

    override fun cancel() {
        mToast.cancel()
    }

    companion object {
        @JvmStatic
        fun makeText(text: CharSequence, config: IToastStyle): IToast {
            return SystemToast(text, config)
        }
    }
}
