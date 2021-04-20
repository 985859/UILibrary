package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mylibrary.api.R;
import com.mylibrary.api.interfaces.GradientDrawableInterface;
import com.mylibrary.api.utils.GradientDrawableHelper;

/**
 * @user hukui
 * @data 2019/5/22
 * <p>
 * 倒角 边线  虚线边线  背景渐变 等 多变的TextView
 **/
public class VariedLinearLayout extends LinearLayout implements GradientDrawableInterface {
    private int mMaxHeight = -1;
    private int mMaxWidth = -1;
    GradientDrawableHelper drawableHelper;

    public VariedLinearLayout(Context context) {
        this(context, null);
    }

    public VariedLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public VariedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        drawableHelper = new GradientDrawableHelper(context, this, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VariedLinearLayout);
        mMaxHeight = array.getDimensionPixelOffset(R.styleable.VariedLinearLayout_android_maxHeight, -1);
        mMaxWidth = array.getDimensionPixelOffset(R.styleable.VariedLinearLayout_android_maxWidth, -1);
        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        if (mMaxHeight != -1) {
            if (heightMode == MeasureSpec.EXACTLY) {
                heightSize = heightSize <= mMaxHeight ? heightSize
                        : (int) mMaxHeight;
            }

            if (heightMode == MeasureSpec.UNSPECIFIED) {
                heightSize = heightSize <= mMaxHeight ? heightSize
                        : (int) mMaxHeight;
            }
            if (heightMode == MeasureSpec.AT_MOST) {
                heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
            }

        }
        if (mMaxWidth != -1) {
            if (widthModel == MeasureSpec.EXACTLY) {
                widthSize = widthSize <= mMaxHeight ? widthSize
                        : (int) mMaxHeight;
            }

            if (widthModel == MeasureSpec.UNSPECIFIED) {
                widthSize = widthSize <= mMaxHeight ? widthSize
                        : (int) mMaxHeight;
            }
            if (widthModel == MeasureSpec.AT_MOST) {
                widthSize = widthSize <= mMaxHeight ? widthSize : (int) mMaxHeight;
            }

        }
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        int maxWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthModel);
        super.onMeasure(maxWidthMeasureSpec, maxHeightMeasureSpec);

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

    public void setMaxHeight(int mMaxHeight) {
        this.mMaxHeight = mMaxHeight;
        requestLayout();

    }

    public void setMaxWidth(int mMaxWidth) {
        this.mMaxWidth = mMaxWidth;
    }
}
