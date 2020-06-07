package com.crqi.toast_util

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationManagerCompat
import com.crqi.toast_util.toast.CustomToast
import com.crqi.toast_util.toast.SystemToast


/**
 * 超级toast
 * toast 入口类
 */
object SuperToast {
    private var applicationContext: Context? = null
    private var handler = Handler(Looper.getMainLooper())
    private var defaultConfig = ToastConfig()

    /**
     * 必须保证在使用toast功能之前初始化,只需初始化一次
     * 初始化toast
     * @param context:applicationcontext
     */
    fun create(context: Context) {
        create(context, ToastConfig())
    }

    internal fun getContext(): Context {
        if (applicationContext == null) {
            throw NullPointerException("applicationContext must Initialization,please use SuperToast.create(context) first")
        } else {
            return applicationContext!!
        }
    }

    fun create(context: Context, defaultConfig: ToastConfig) {
        applicationContext = context
        SuperToast.defaultConfig = defaultConfig
        CustomToast.createCustomToastHandler(defaultConfig)
    }

    fun makeText(msg: CharSequence, duration: Int) {
        defaultConfig.mDuration = duration
        makeText(msg, defaultConfig)
    }

    /**
     * 展示toast
     * @param msg :展示文案
     * @param toastConfig:toast配置信息，非必传，有默认值。可查看ToatConfig类中的默认值，可通过toastBuilder构造
     */
    fun makeText(msg: CharSequence, toastConfig: ToastConfig?) {
        showToastMain(getContext(), msg, toastConfig)
    }

    /**
     * 在主线程中展示toast
     */
    private fun showToastMain(context: Context, msg: CharSequence, toastConfig: ToastConfig?) {
        if (Thread.currentThread() === Looper.getMainLooper().thread) {
            getToastShow(context, msg, toastConfig)
        } else {
            handler.post { getToastShow(context, msg, toastConfig) }
        }

    }

    /**
     * 获取toast 对象
     */
    private fun getToastShow(context: Context, msg: CharSequence, toastConfig: ToastConfig?) {

        val toast = if (isNotificationEnabled()) {
            SystemToast.makeText(msg, toastConfig ?: defaultConfig)
        } else {
            CustomToast.makeText(context, msg, toastConfig ?: defaultConfig)
        }
        toast?.show()
    }

    /**
     * 通知是否可用
     */
    private fun isNotificationEnabled(): Boolean {

        return if (Build.VERSION.SDK_INT >= 19) {
            val manager = NotificationManagerCompat.from(getContext())
            manager.areNotificationsEnabled()
        } else {
            false
        }
    }


}