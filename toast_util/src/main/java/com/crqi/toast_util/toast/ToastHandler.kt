package com.crqi.toast_util.toast

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.crqi.toast_util.ToastConfig
import java.util.concurrent.ArrayBlockingQueue

/**
 * time   : 2018/11/12
 * desc   : 自定义消息管理类
 * 管理toast 信息队列，添加，移除，循环阻塞
 */
internal class ToastHandler(private val mToast: ToastHelper) : Handler(Looper.getMainLooper()) {

    // 吐司队列
    private val mQueue = ArrayBlockingQueue<IToastStyle>(MAX_TOAST_CAPACITY)

    fun setText(s: ToastConfig) {
        if (mQueue.isEmpty() || !mQueue.contains(s)) {
            // 添加一个元素并返回true，如果队列已满，则返回false
            if (!mQueue.offer(s)) {
                // 移除队列头部元素并添加一个新的元素
                mQueue.poll()
                mQueue.offer(s)
            }
        }
    }

    fun show() {
        sendEmptyMessage(TYPE_SHOW)
    }

    fun cancel() {
        sendEmptyMessage(TYPE_CANCEL)
    }

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            TYPE_SHOW -> {
                // 返回队列头部的元素，如果队列为空，则返回null
                if (mQueue.isEmpty()) return
                val toast = mQueue.remove()
                if (toast != null) {
                    mToast.show(toast)
                    // 等这个 Toast 显示完后再继续显示
                    sendEmptyMessageDelayed(TYPE_CONTINUE, (toast.getDuration() + SHOW_INTERVAL).toLong())
                }
            }
            TYPE_CONTINUE -> {
                if (!mQueue.isEmpty()) {
                    sendEmptyMessage(TYPE_SHOW)
                }
            }
            TYPE_CANCEL -> {
                mQueue.clear()
                mToast.cancel()
            }
            else -> {
            }
        }
    }

    companion object {

        private val TYPE_SHOW = 1 // 显示吐司
        private val TYPE_CONTINUE = 2 // 继续显示
        private val TYPE_CANCEL = 3 // 取消显示
        // 最大吐司的容量
        private val MAX_TOAST_CAPACITY = 15
        //两次展示间隔
        private val SHOW_INTERVAL = 1000
    }
}