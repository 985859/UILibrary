package com.mylibrary.api.utils;


import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import com.mylibrary.api.utils.Span.CenterSpaceImageSpan;
import com.mylibrary.api.utils.Span.RadiusBackgroundSpan;

import java.text.DecimalFormat;

public class SpannableUtil {


    public static void setGradientText(TextView textView, int startColor, int endColor) {
        setGradientText(textView, startColor, endColor, 0);

    }

    public static SpannableStringBuilder setPrice(String price) {
        return setPrice(StringUtil.toDouble(price), "");
    }

    public static SpannableStringBuilder setPrice(String price, String format) {
        return setPrice(StringUtil.toDouble(price), format);
    }

    public static SpannableStringBuilder setPrice(double d) {
        return setPrice(d, "");
    }

    public static SpannableStringBuilder setPrice(double d, String format) {
        String price;
        if (StringUtil.isEmpty(format)) {
            format = "#,##0.##";
        }
        try {
            //format 如 #.##    0.##  0.00
            DecimalFormat decimalFormat = new DecimalFormat(format);
            price = decimalFormat.format(d);//format 返回的是字符串
        } catch (Exception e) {
            price = "";
        }
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        if (StringUtil.isEmpty(price)) {
            return spannableString;
        }
        price = "¥ " + price;
        spannableString.append(price);
        spannableString.setSpan(new RelativeSizeSpan(0.8f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int index = price.indexOf(".");
        if (index > 0 && index < price.length()) {
            spannableString.setSpan(new RelativeSizeSpan(0.8f), index, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    /**
     * @param type 0左右渐变 1 上下渐变
     * @return
     * @author hukui
     * @time 2020/7/27
     * @Description 设置字体颜色渐变
     */
    public static void setGradientText(TextView textView, int startColor, int endColor, int type) {
        if (textView == null) return;
        TextPaint paint = textView.getPaint();
        LinearGradient linearGradient = null;
        switch (type) {
            case 0: //左右渐变
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        int endX = (int) (paint.getTextSize() * textView.getText().length());
                        int width = textView.getWidth();
                        if (endX > width) {
                            endX = width;
                        }
                        LinearGradient linearGradient = new LinearGradient(
                                0f, 0f, endX, 0f,
                                startColor,
                                endColor,
                                Shader.TileMode.CLAMP
                        );
                        if (linearGradient != null)
                            paint.setShader(linearGradient);
                        textView.invalidate();
                    }
                });

                break;
            case 1: //上下渐变
                linearGradient = new LinearGradient(0, 0, 0, paint.descent() - paint.ascent(),
                        startColor, endColor, Shader.TileMode.REPEAT);
                if (linearGradient != null)
                    paint.setShader(linearGradient);
                textView.invalidate();
                break;
            default:
                break;
        }

    }

    //设置删除线
    public static SpannableStringBuilder setStrikethrough(CharSequence text) {
        if (StringUtil.isEmpty(text)) {
            text = "";
        }
        return setStrikethrough(text, 0, text.length());
    }

    //设置删除线
    public static SpannableStringBuilder setStrikethrough(CharSequence text, int start, int end) {
        if (StringUtil.isEmpty(text)) {
            text = "";
        }
        SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
        spannableString.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    public static SpannableStringBuilder setTextColor(CharSequence text, int start, int end, int color) {
        if (StringUtil.isEmpty(text)) {
            text = "";
        }
        SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
        spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    public static SpannableStringBuilder setStartTip(String tip, String text, int tipTextSize, int textSize, int radius, int color, int tipColor) {
        SpannableStringBuilder builder = new SpannableStringBuilder(tip + " " + text);
        //构造文字背景圆角
        RadiusBackgroundSpan span = new RadiusBackgroundSpan(color, tipColor, radius, textSize);
        builder.setSpan(span, 0, tip.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //构造文字大小
        AbsoluteSizeSpan spanSize = new AbsoluteSizeSpan(tipTextSize);
        builder.setSpan(spanSize, 0, tip.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //构造文字大小
        AbsoluteSizeSpan spanSizeLast = new AbsoluteSizeSpan(textSize);
        builder.setSpan(spanSizeLast, tip.length(), builder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder setEndTip(String tip, String text, int tipTextSize, int textSize, int radius, int color, int tipColor) {
        if (text == null) {
            text = "";
        }
        if (tip == null) {
            tip = "";
        }
        String s = text + " ";
        SpannableStringBuilder builder = new SpannableStringBuilder(s + tip);

        //构造文字背景圆角
        RadiusBackgroundSpan span = new RadiusBackgroundSpan(color, tipColor, radius, textSize);
        builder.setSpan(span, s.length(), s.length() + tip.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //构造文字大小
        AbsoluteSizeSpan spanSize = new AbsoluteSizeSpan(tipTextSize);
        builder.setSpan(spanSize, s.length(), s.length() + tip.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //构造文字大小
        AbsoluteSizeSpan spanSizeLast = new AbsoluteSizeSpan(textSize);
        builder.setSpan(spanSizeLast, 0, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return builder;
    }
    /**
     * @param icon   drawable  的ID
     * @param width  单位 dp
     * @param height 单位 dp
     * @return
     * @author hukui
     * @time 2020/9/25
     * @Description
     */
    public static ImageSpan getImagSpan(Context context, int icon, int width, int height) {
        Drawable drawable = context.getResources().getDrawable(icon);
        drawable.setBounds(0, 0, SystemUtil.dp2px(context, width), SystemUtil.dp2px(context, height));
        CenterSpaceImageSpan imageSpan = new CenterSpaceImageSpan(drawable, 0, 10);
        return imageSpan;
    }

    public static SpannableStringBuilder setMustTitle(String title) {
        if (StringUtil.isEmpty(title)) {
            title = "";
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(title);
        builder.append(" *");
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fe2552")), builder.length() - 1, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;

    }
}
