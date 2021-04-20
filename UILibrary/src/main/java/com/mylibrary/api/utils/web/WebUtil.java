package com.mylibrary.api.utils.web;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author: hukui
 * @date: 2019/8/20
 */
public class WebUtil {
    /**
     * 描述:  加载html JS调用android 方法设置 WebAppInterface 里面写逻辑处理
     *
     * @param
     * @return
     */
    public static void webHtml(String htmlData, WebView web) {
        if (web != null) {
            webHtml(htmlData, web, new WebAppInterface(web.getContext()));
        }
    }

    @SuppressLint("JavascriptInterface")
    public static void webHtml(String htmlData, WebView web, Object o) {
        if (web == null) return;
        if (htmlData != null && !"".equals(htmlData)) {
            web.setVisibility(View.VISIBLE);
            web.setBackgroundColor(0); // 设置背景色
            WebSettings mWebSetting = web.getSettings();
            mWebSetting.setJavaScriptEnabled(true);//支持js
            mWebSetting.setUseWideViewPort(true);//保证页面的完整显示在手机屏幕上
            mWebSetting.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
            mWebSetting.setSupportZoom(false); //设置支持缩放 默认为 true
            mWebSetting.setBuiltInZoomControls(false); // 设置出现缩放工具
            mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL); //支持内容重新布局
            mWebSetting.setAllowFileAccess(true);  //设置可以访问文件
            mWebSetting.setLoadsImagesAutomatically(true);  //支持自动加载图片
            mWebSetting.setDefaultTextEncodingName("utf-8");//设置编码格式
            mWebSetting.setDomStorageEnabled(true);//开启DOM形式存储
            mWebSetting.setCacheMode(WebSettings.LOAD_NO_CACHE); //设置存储模式
            mWebSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通過JS打開新窗口
            mWebSetting.setSupportMultipleWindows(true);//WebView支持多窗口

            web.addJavascriptInterface(o, "android");
            web.setVerticalScrollBarEnabled(false);
            web.setVerticalScrollbarOverlay(false);
            web.setHorizontalScrollBarEnabled(false);
            web.setHorizontalScrollbarOverlay(false);
            web.setHorizontalFadingEdgeEnabled(false);
            web.setVerticalFadingEdgeEnabled(false);
            //h5Box  要与 onClick 方法名对应 如： <input type='button' onClick=h5Box(1,2,3)></input>
            //jsCallAndroidArgs  要与 WebAppInterface 方法名
            String script = "<script type=\"text/javascript\">\n" +
                    "function h5Box(arg,arg1,arg2){\n" +
                    "window.android.jsCallAndroidArgs(arg,arg1,arg2)\n" +
                    "}\n" +
                    "</script>";

            String head = "<head><meta charset=\"utf-8\">"
                    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                    + "<style>img{ width:100%; height:auto;} figure{margin:0}"
                    + "body {" +
                    "margin-right:15px;" +
                    "margin-left:15px;" +
                    "margin-top:15px;" +
                    "font-size:15px;" +
                    "}"
                    + "</style>"
                    + script
                    + "</head>";

            String html = "<!DOCTYPE  html><html>" + head + "<body>" + htmlData + "</body></html>";
            web.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

        } else {
            web.setVisibility(View.GONE);
        }

    }


}
