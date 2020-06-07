package com.crqi.toast_util.toast

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * 处理7.1.1的阻塞崩溃问题
 * @author
 */
internal class SafeToastContext(base: Context) : ContextWrapper(base) {

    override fun getApplicationContext(): Context {
        return ApplicationContextWrapper(baseContext.applicationContext)
    }

    private inner class ApplicationContextWrapper constructor(base: Context) : ContextWrapper(base) {
        override fun getSystemService(name: String): Any? {
            return if (Context.WINDOW_SERVICE == name) {
                WindowManagerWrapper(baseContext.getSystemService(name) as WindowManager)
            } else super.getSystemService(name)
        }
    }


    private inner class WindowManagerWrapper constructor(private val base: WindowManager) : WindowManager {

        override fun getDefaultDisplay(): Display {
            return base.defaultDisplay
        }

        override fun removeViewImmediate(view: View) {
            base.removeViewImmediate(view)
        }

        override fun addView(view: View, params: ViewGroup.LayoutParams) {
            try {
                Log.d("tag", "WindowManager's addView(view, params) has been hooked.")
                base.addView(view, params)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }

        }


        override fun updateViewLayout(view: View, params: ViewGroup.LayoutParams) {
            base.updateViewLayout(view, params)
        }


        override fun removeView(view: View) {
            base.removeView(view)
        }

    }

}