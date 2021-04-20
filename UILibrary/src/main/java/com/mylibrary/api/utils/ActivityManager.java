package com.mylibrary.api.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by myuser on 2018/1/27.
 * actviity 堆栈管理
 */

public class ActivityManager {

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    private static Stack<Activity> activityStack = new Stack<>();
    private static ActivityManager instance;

    private ActivityManager() {
    }

    /**
     * 单一实例
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public static Activity getTopActivity() {

        return activityStack == null ? null : activityStack.lastElement();
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void finishTopActivity() {
        if (activityStack != null) {
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        }

    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public static void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity = (Activity) iterator.next();
                if (activity.getClass().equals(cls)) {
                    iterator.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    @SuppressWarnings("WeakerAccess")
    public void finishAllActivity() {
        if (activityStack != null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    hideSoftKeyBoard(activityStack.get(i));
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 结束除指定Activity 的所有Activity
     */
    @SuppressWarnings("WeakerAccess")
    public static void finishOutActivity(Class<?> cls) {

        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {

                } else {
                    hideSoftKeyBoard(activity);
                    activity.finish();
                }
            }
        }

    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activityStack != null) {
            if (activity != null) {
                activityStack.remove(activity);
                hideSoftKeyBoard(activity);
                activity.finish();
                activity = null;
            }
        }

    }

    /**
     * 得到指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }

    //隐藏键盘
    private static void hideSoftKeyBoard(Activity activity) {
        View localView = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (localView != null && imm != null) {
            imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }
}




