package com.crqi.toast_util.toast

import android.app.Application
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.crqi.toast_util.SuperToast
import com.crqi.toast_util.ToastConfig
import java.lang.ref.WeakReference

/**
 * 不需要权限的toast
 * 思想：
 * 1、view使用原生toast的view XToast，通过自定义继承toast同时ToastHelper代理show
 * 2、通过当前activity(WindowHelper)获取到的windowManager，将toast的view放入(ToastHelper代理实现)
 * 3、ToastHandler 队列管理
 */
internal object CustomToast : IToast {
    //toast处理类
    private val toastHelper: ToastHelper by lazy {
        ToastHelper(getContext() as Application)
    }

    // toast 消息队列管理类
    private val sToastHandler: ToastHandler by lazy {
        ToastHandler(toastHelper)
    }

    //当前toast 配置信息
    private lateinit var curToast: ToastConfig

    //默认的自定义view
    private val defaultToastView: TextView by lazy {
        val view = createTextView(
            getContext(),
            curToast
        )
        view
    }


    override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): IToast {
        curToast.mGravity = gravity
        curToast.mXOffset = xOffset
        curToast.mYOffset = yOffset
        return this
    }

    override fun setDuration(durationMillis: Int): IToast {
        curToast.mDuration = durationMillis
        return this
    }


    override fun setView(view: View?): IToast? {
        if (view != null) {
            curToast.view = WeakReference(view)
        }
        return this
    }


    override fun setMargin(horizontalMargin: Float, verticalMargin: Float): IToast {
        curToast.mHorizontalMargin = horizontalMargin
        curToast.mVerticalMargin = verticalMargin
        return this
    }

    override fun setText(text: CharSequence?): IToast? {
        val toast = ToastConfig()
        toast.copy(curToast)
        toast.view = curToast.view
        toast.text = text
        sToastHandler.setText(toast)
        return this
    }

    override fun show() {
        sToastHandler.show()
    }

    override fun cancel() {
        sToastHandler.cancel()
    }

    /**
     * 模拟构造一个toast
     */
    fun makeText(context: Context, text: CharSequence, config: ToastConfig): IToast? {
        return createToast(config)
            .setText(text)
    }

    /**
     * 初始化自定义toast的handler
     */
    fun createCustomToastHandler(config: ToastConfig) {
        sToastHandler
        curToast = config

    }

    /**
     * 模拟构造一个toast
     */
    private fun createToast(config: ToastConfig): IToast {
        //使其初始化
        createCustomToastHandler(config)
        dealToastGravity(config)
        if (curToast.getToastView() == null) {
            setView(defaultToastView)
        }
        setCustomToastDuration(config)
        return this
    }

    /**
     * 获取上下文，注意会抛出异常
     */
    private fun getContext(): Context {
        return SuperToast.getContext()
    }

    /**
     * 处理反向布局
     */
    private fun dealToastGravity(config: ToastConfig) {
        val gravity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Gravity.getAbsoluteGravity(
                config.getGravity(),
                getContext().resources.configuration.layoutDirection
            )
        } else {
            config.getGravity()
        }
        //源数据填充
        setGravity(
            gravity,
            config.mXOffset,
            config.mYOffset
        )
    }

    /**
     * 转换自定义展示时间
     */
    private fun setCustomToastDuration(config: ToastConfig): Int {
        if (config.getDuration() == Toast.LENGTH_LONG) {
            config.mDuration = 3500
        } else if (config.getDuration() == Toast.LENGTH_SHORT) {
            config.mDuration = 2500
        }
        return config.getDuration()
    }

    /**
     * 生成默认的 TextView 对象
     */
    private fun createTextView(context: Context, config: IToastStyle): TextView {
        val textView = TextView(context)
        textView.id = android.R.id.message
        textView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return textView
    }


}
