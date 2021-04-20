package com.mylibrary.api.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.mylibrary.api.interfaces.GradientDrawableInterface;
import com.mylibrary.api.utils.GradientDrawableHelper;

/**
 * @user hukui
 * @data 2019/5/22
 * <p>
 * 倒角 边线  虚线边线  背景渐变 等 多变的TextView
 **/
public class VariedConstraintLayout extends ConstraintLayout implements GradientDrawableInterface {
    GradientDrawableHelper drawableHelper;

    public VariedConstraintLayout(Context context) {
        this(context, null);
    }

    public VariedConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawableHelper = new GradientDrawableHelper(context, this, attrs);
    }

    public VariedConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setStroke(int strokeWidth, int strokeColor) {
        drawableHelper.setStroke(strokeWidth, strokeColor);
    }

    @Override
    public void setStroke(int strokeWidth, int strokeColor, float dasWidth, float dashGap) {
        drawableHelper.setStroke(strokeWidth, strokeColor, dasWidth, dashGap);
    }

    @Override
    public void setRadius(float radius) {
        drawableHelper.setRadius(radius);
    }

    @Override
    public void setRadius(float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        drawableHelper.setRadius(topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius);
    }

    @Override
    public void setGradientColor(int startColor, int endColor) {
        drawableHelper.setGradientColor(startColor, -1, endColor);
    }

    @Override
    public void setGradientColor(int startColor, int centerColor, int endColor) {
        drawableHelper.setGradientColor(startColor, centerColor, endColor);
    }

    @Override
    public void setGradientColor(int startColor, int centerColor, int endColor, int orientation) {
        drawableHelper.setGradientColor(startColor, centerColor, endColor, orientation);
    }

    @Override
    public void setGradientColor(int startColor, int centerColor, int endColor, int orientation, float gradientGradientRadius, int type) {
        drawableHelper.setGradientColor(startColor, centerColor, endColor, orientation, gradientGradientRadius, type);
    }

    @Override
    public void setGradientColor(int startColor, int centerColor, int endColor, int orientation, float gradientGradientRadius, int type, boolean isAmend) {
        drawableHelper.setGradientColor(startColor, centerColor, endColor, orientation, gradientGradientRadius, type, isAmend);
    }

    @Override
    public void setShapeType(int type) {
        drawableHelper.setShapeType(type);
    }

    @Override
    public void setBackgroundColor(int color) {
        drawableHelper.setBackgroundColor(color);
    }
}
