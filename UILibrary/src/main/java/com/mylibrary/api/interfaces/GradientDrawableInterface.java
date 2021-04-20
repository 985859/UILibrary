package com.mylibrary.api.interfaces;

/**
 * @author: hukui
 * @date: 2019/9/20
 */
public interface GradientDrawableInterface {
    //设置边框线
    void setStroke(int strokeWidth, int strokeColor);
    //设置 边框 虚线
    void setStroke(int strokeWidth, int strokeColor, float dasWidth, float dashGap);
    //设置倒角
    void setRadius(float radius);
    void setRadius(float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius);

    //设置渐变色 isAmend 是否更新渐变色值
    void setGradientColor(int startColor, int endColor);
    void setGradientColor(int startColor, int centerColor, int endColor);
    void setGradientColor(int startColor, int centerColor, int endColor, int orientation);
    void setGradientColor(int startColor, int centerColor, int endColor, int orientation, float gradientGradientRadius, int type);
    void setGradientColor(int startColor, int centerColor, int endColor, int orientation, float gradientGradientRadius, int type, boolean isAmend);
    //设置类型
    void setShapeType(int type);
    //设置背景颜色
    void setBackgroundColor(int color);
}
