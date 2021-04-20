package com.mylibrary.api.utils;

import android.view.Gravity;

import com.hjq.toast.ToastUtils;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/4/12 15:59
 */
public class ToastUtil {
    public static void showShort(CharSequence character) {
        showShort(character, Gravity.CENTER, 0, 0);
    }

    public static void showShort(CharSequence character, int gravity) {
        showShort(character, gravity, 0, 0);
    }

    public static void showShort(CharSequence character, int gravity, int x, int y) {
        ToastUtils.setGravity(gravity, x, y);
        ToastUtils.show(character);
    }

    public static void showShort(int id) {
        showShort(id, Gravity.CENTER, 0, 0);
    }

    public static void showShort(int id, int gravity) {
        showShort(id, gravity, 0, 0);
    }

    public static void showShort(int id, int gravity, int x, int y) {
        ToastUtils.setGravity(gravity, 0, 0);
        ToastUtils.show(id);
    }
}
