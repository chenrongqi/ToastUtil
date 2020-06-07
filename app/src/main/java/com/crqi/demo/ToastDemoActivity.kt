package com.crqi.demo

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crqi.toast_util.SuperToast
import com.crqi.toast_util.ToastBuilder

import kotlinx.android.synthetic.main.activity_toast_demo.*

class ToastDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        SuperToast.create(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast_demo)

        bt_simple_toast.setOnClickListener {
            ToastUtil.showInfo(this, "普通toast,采用默认的配置")
        }

        bt_top_toast.setOnClickListener {
            val toastBuilder = ToastBuilder()
            toastBuilder.setGravity(Gravity.TOP)
            toastBuilder.setTextColor(Color.RED)
            SuperToast.makeText("顶部toast，红色文字", toastConfig = toastBuilder.buildToastStyle())
        }
        bt_center_toast.setOnClickListener {
            val toastBuilder = ToastBuilder()
            toastBuilder.setGravity(Gravity.CENTER)
            SuperToast.makeText("中间toast", toastConfig = toastBuilder.buildToastStyle())
        }
        bt_botton_toast.setOnClickListener {
            val toastBuilder = ToastBuilder()
            toastBuilder.setGravity(Gravity.BOTTOM)
            toastBuilder.setBackgroundColor(Color.RED)
            SuperToast.makeText("底部toast，红色背景", toastConfig = toastBuilder.buildToastStyle())
        }

        bt_customview_toast.setOnClickListener {
            val toastBuilder = ToastBuilder()
            toastBuilder.setGravity(Gravity.BOTTOM)
            toastBuilder.setView(setToastView())
            toastBuilder.setDuration(Toast.LENGTH_LONG)
            SuperToast.makeText("自定义view toast", toastConfig = toastBuilder.buildToastStyle())
        }
        bt_blocking_toast.setOnClickListener {
            Handler().postDelayed({
                ToastUtil.showInfo(this, "普通toast,采用默认的配置")
                Thread.sleep(5000)
            }, 1000)
        }
    }

    private fun setToastView(): View {
        val view = LayoutInflater.from(this).inflate(R.layout.view_toast, null)
        val recycle = view.findViewById<RecyclerView>(R.id.view_recycler)
        recycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycle.adapter = object : RecyclerView.Adapter<ToastViewHold>() {
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ToastViewHold {
                return ToastViewHold(
                    LayoutInflater.from(p0.context).inflate(R.layout.view_text, p0, false)
                )
            }

            override fun getItemCount(): Int {
                return 5
            }

            override fun onBindViewHolder(p0: ToastViewHold, p1: Int) {
                p0.itemText.append("item:$p1")
                p0.itemText.setTextColor(Color.WHITE)
            }

        }
        return view
    }

    inner class ToastViewHold(view: View) : RecyclerView.ViewHolder(view) {
        val itemText: TextView by lazy {
            view.findViewById<TextView>(R.id.text)
        }
    }
}
