package com.mylibrary.api.utils.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

/***
 * JS交互
 * */
public class WebAppInterface {
    Context context;

    public WebAppInterface(Context context) {
        this.context = context;
    }

    /**
     * @param
     * @return
     * @author: hukui
     * @date: 2019/9/9
     * @description 无参 JS 调用
     */
    @JavascriptInterface
    public void jsCallAndroid() {
        //写逻辑处理
    }

    /**
     * @param arge1 js传递回来的参数
     * @param agre2 js传递回来的参数
     * @param arge3 js传递回来的参数
     * @return
     * @author: hukui
     * @date: 2019/9/9
     * @description 有参 JS 调用
     */
    @JavascriptInterface
    public void jsCallAndroid(String arge1, String agre2, String arge3) {
        //写逻辑处理
    }

    /**
     * @param arge1 js传递回来的参数
     * @param agre2 js传递回来的参数
     * @param arge3 js传递回来的参数
     * @return
     * @author: hukui
     * @date: 2019/9/9
     * @description 有参 JS 调用
     */
    @JavascriptInterface
    public void jsCallAndroid(String arge1, String agre2, String arge3, String arge4) {
        //写逻辑处理
    }
}
