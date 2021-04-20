package com.mylibrary.api.dialog;

import android.content.Context;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;

public class DialogUtils {

    public static void showDialog(Context context, String msg) {
        showDialog(context, null, msg, "确定", "", null, true);
    }

    public static void showDialog(Context context, String msg, MyDialog.OnCloseListener listener) {
        showDialog(context, null, msg, "确定", "取消", listener, true);
    }

    public static void showDialog(Context context, String msg, String positive, String negative, MyDialog.OnCloseListener listener) {
        showDialog(context, null, msg, positive, negative, listener, true);
    }

    public static void showDialog(Context context, String msg, String positive, String negative, MyDialog.OnCloseListener listener, boolean cancelable) {
        showDialog(context, null, msg, positive, negative, listener, cancelable);
    }

    public static void showDialog(Context context, String title, String msg, String positive, String negative, MyDialog.OnCloseListener listener, boolean cancelable) {
        MyDialog dialog = new MyDialog(context);
        if (StringUtil.isEmpty(title)) {
            title = "温馨提示";
        }
        int raius = SystemUtil.dp2px(context, 8);
        dialog.setTitle(title)
                .setContent(msg)
                .setPositiveButton(positive)
                .setNegativeButton(negative)
                .setCancelables(cancelable);
        if (listener != null) {
            dialog.setListener(listener);
        }
        dialog.show();
        if (StringUtil.isNotEmpty(positive) && StringUtil.isNotEmpty(negative)) {
            dialog.setPositiveButtonRaius(raius, 0);
            dialog.setNegativeButtonRaius(0, raius);
        } else if (StringUtil.isNotEmpty(positive) && StringUtil.isEmpty(negative)) {
            dialog.setPositiveButtonRaius(raius, raius);
        } else if (StringUtil.isEmpty(positive) && StringUtil.isEmpty(negative)) {
            dialog.setNegativeButtonRaius(raius, raius);
        }
    }



}
