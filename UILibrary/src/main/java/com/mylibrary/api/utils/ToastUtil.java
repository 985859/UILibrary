package com.mylibrary.api.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/4/12 15:59
 */
public class ToastUtil {

    private static boolean isShow = true;//默认显示
    private static Toast mToast = null;//全局唯一的Toast

    private static Context context;

    public static void getInstance(Context context) {
        ToastUtil.context = context;
    }

    /*private控制不应该被实例化*/
    private ToastUtil() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 全局控制是否显示Toast
     *
     * @param isShowToast
     */
    public static void controlShow(boolean isShowToast) {
        isShow = isShowToast;
    }

    /*
     * 取消Toast显示
     */
    public void cancelToast() {
        if (isShow && mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort( CharSequence message) {
        if ("token无效".equals(message)) {
            return;
        }
        if (isShow) {
            if (message != null) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(message);
                }
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.show();
            }

        }
    }


    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message, int gravity) {
        if ("token无效".equals(message)) {
            return;
        }
        if (isShow) {
            if (message != null) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(message);
                }
                mToast.setGravity(gravity, 0, 0);
                mToast.show();
            }

        }
    }

    /**
     * 短时间显示Toast
     *

     * @param resId   资源ID:getResources().getString(R.string.xxxxxx);
     */
    public static void showShort(int resId) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(resId);
            }
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param resId   资源ID:getResources().getString(R.string.xxxxxx);
     */
    public static void showShort( int resId, int gravity) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(resId);
            }
            mToast.setGravity(gravity, 0, 0);
            mToast.show();
        }
    }





}
