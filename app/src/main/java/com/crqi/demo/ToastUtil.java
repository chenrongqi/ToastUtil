package com.crqi.demo;

import android.content.Context;
import android.widget.Toast;

import com.crqi.toast_util.SuperToast;


/**
 * Created by Mr_immortalZ on 2016/7/15.
 * email : mr_immortalz@qq.com
 */
public class ToastUtil {
    public static void showInfo(Context context, String info) {
        SuperToast.INSTANCE.makeText(info, Toast.LENGTH_SHORT);
    }
}
