package com.mylibrary.api.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.mylibrary.api.interfaces.GradientDrawableInterface;

import java.util.Arrays;

import static com.mylibrary.api.interfaces.TypeValues.BL_TR;
import static com.mylibrary.api.interfaces.TypeValues.BOTTOM_TOP;
import static com.mylibrary.api.interfaces.TypeValues.BR_TL;
import static com.mylibrary.api.interfaces.TypeValues.LEFT_RIGHT;
import static com.mylibrary.api.interfaces.TypeValues.LINE;
import static com.mylibrary.api.interfaces.TypeValues.LINEAR;
import static com.mylibrary.api.interfaces.TypeValues.OVAL;
import static com.mylibrary.api.interfaces.TypeValues.RADIAL;
import static com.mylibrary.api.interfaces.TypeValues.RECTANGLE;
import static com.mylibrary.api.interfaces.TypeValues.RIGHT_LEFT;
import static com.mylibrary.api.interfaces.TypeValues.RING;
import static com.mylibrary.api.interfaces.TypeValues.SWEEP;
import static com.mylibrary.api.interfaces.TypeValues.TL_BR;
import static com.mylibrary.api.interfaces.TypeValues.TOP_BOTTOM;
import static com.mylibrary.api.interfaces.TypeValues.TR_BL;
/**
 * @author: hukui
 * @date: 2019/9/20
 * 如果 view 设置了 background  那么 这些属性 都不会生效
 * 请使用自定义属性
 * 在使用自定义属性时  backgroundColor  和渐变色 同时设置时
 * 再设置 endule 时 会默认使用 backgroundColor
 */
public class GradientDrawableHelper implements GradientDrawableInterface {
    GradientDrawable drawable;
    Drawable backgroundDrawable;
    int emptyColor = Color.parseColor("#00000000");
    float strokeWidth;//默认 不画边线
    int strokeColor;
    float dasWidth;
    float dashGap;
    float radius;
    float topLeftRadius;
    float topRightRadius;
    float bottomLeftRadius;
    float bottomRightRadius;
    float attrGradientRadius;


    int backgroundColor;
    int disableBackground;
    int startColor;
    int centerColor;
    int endColor;
    int gradientOrientation = LEFT_RIGHT; //渐变 默认 左→右
    int gradientType = LINEAR;
    int shapeType = LINEAR;

    public GradientDrawable getDrawable() {
        return drawable;
    }

    @SuppressLint("ResourceAsColor")
    public GradientDrawableHelper(Context context, View view, AttributeSet attrs) {
        init(context, view, attrs);
    }

    public GradientDrawableHelper() {
        drawable = new GradientDrawable();
    }

    public void init(Context context, View view, AttributeSet attrs) {
        drawable = new GradientDrawable();
        if (context != null && attrs != null) {
            int attr_strokeWidth = context.getResources().getIdentifier("attr_strokeWidth", "attr", context.getPackageName());
            int attr_strokeColor = context.getResources().getIdentifier("attr_strokeColor", "attr", context.getPackageName());
            int attr_dashGap = context.getResources().getIdentifier("attr_dashGap", "attr", context.getPackageName());
            int attr_dasWidth = context.getResources().getIdentifier("attr_dasWidth", "attr", context.getPackageName());
            int attr_cornersRaius = context.getResources().getIdentifier("attr_cornersRaius", "attr", context.getPackageName());
            int attr_topLeftRadius = context.getResources().getIdentifier("attr_topLeftRadius", "attr", context.getPackageName());
            int attr_topRightRadius = context.getResources().getIdentifier("attr_topRightRadius", "attr", context.getPackageName());
            int attr_bottomLeftRadius = context.getResources().getIdentifier("attr_bottomLeftRadius", "attr", context.getPackageName());
            int attr_bottomRightRadius = context.getResources().getIdentifier("attr_bottomRightRadius", "attr", context.getPackageName());
            int attr_background = context.getResources().getIdentifier("attr_background", "attr", context.getPackageName());
            int attr_disableBackground = context.getResources().getIdentifier("attr_disableBackground", "attr", context.getPackageName());
            int attr_startColor = context.getResources().getIdentifier("attr_startColor", "attr", context.getPackageName());
            int attr_centerColor = context.getResources().getIdentifier("attr_centerColor", "attr", context.getPackageName());
            int attr_endColor = context.getResources().getIdentifier("attr_endColor", "attr", context.getPackageName());
            int attr_gradientOrientation = context.getResources().getIdentifier("attr_gradientOrientation", "attr", context.getPackageName());
            int attr_gradientType = context.getResources().getIdentifier("attr_gradientType", "attr", context.getPackageName());
            int attr_GradientRadius = context.getResources().getIdentifier("attr_GradientRadius", "attr", context.getPackageName());
            int attr_ShapeType = context.getResources().getIdentifier("attr_ShapeType", "attr", context.getPackageName());
            int backgroundInt = context.getResources().getIdentifier("android:background", "attr", context.getPackageName());

            int[] attrsInts = {attr_strokeWidth, attr_strokeColor, attr_dashGap, attr_dasWidth, attr_cornersRaius, attr_topLeftRadius, attr_topRightRadius, attr_bottomLeftRadius,
                    attr_bottomRightRadius, attr_background, attr_disableBackground, attr_startColor, attr_centerColor, attr_endColor, attr_gradientOrientation, attr_gradientType, attr_GradientRadius,
                    attr_ShapeType, backgroundInt};
            Arrays.sort(attrsInts);
            TypedArray array = context.obtainStyledAttributes(attrs, attrsInts, 0, 0);
            int attr_strokeWidthIndex = Arrays.binarySearch(attrsInts, attr_strokeWidth);
            int attr_strokeColorIndex = Arrays.binarySearch(attrsInts, attr_strokeColor);
            int attr_dashGapIndex = Arrays.binarySearch(attrsInts, attr_dashGap);
            int attr_dasWidthIndex = Arrays.binarySearch(attrsInts, attr_dasWidth);
            int attr_cornersRaiusIndex = Arrays.binarySearch(attrsInts, attr_cornersRaius);
            int attr_topLeftRadiusIndex = Arrays.binarySearch(attrsInts, attr_topLeftRadius);
            int attr_topRightRadiusIndex = Arrays.binarySearch(attrsInts, attr_topRightRadius);
            int attr_bottomLeftRadiusIndex = Arrays.binarySearch(attrsInts, attr_bottomLeftRadius);
            int attr_bottomRightRadiusIndex = Arrays.binarySearch(attrsInts, attr_bottomRightRadius);
            int attr_backgroundIndex = Arrays.binarySearch(attrsInts, attr_background);
            int attr_disableBackgroundIndex = Arrays.binarySearch(attrsInts, attr_disableBackground);
            int attr_startColorIndex = Arrays.binarySearch(attrsInts, attr_startColor);
            int attr_centerColorIndex = Arrays.binarySearch(attrsInts, attr_centerColor);
            int attr_endColorIndex = Arrays.binarySearch(attrsInts, attr_endColor);
            int attr_gradientTypeIndex = Arrays.binarySearch(attrsInts, attr_gradientType);
            int attr_gradientOrientationIndex = Arrays.binarySearch(attrsInts, attr_gradientOrientation);
            int attr_GradientRadiusIndex = Arrays.binarySearch(attrsInts, attr_GradientRadius);
            int attr_ShapeTypeIndex = Arrays.binarySearch(attrsInts, attr_ShapeType);
            int backgroundIndex = Arrays.binarySearch(attrsInts, backgroundInt);
            strokeWidth = array.getDimension(attr_strokeWidthIndex, 0);//默认 不画边线
            strokeColor = array.getColor(attr_strokeColorIndex, emptyColor);
            dasWidth = array.getDimension(attr_dasWidthIndex, 0);
            dashGap = array.getDimension(attr_dashGapIndex, 0);
            radius = array.getDimension(attr_cornersRaiusIndex, 0);
            topLeftRadius = array.getDimension(attr_topLeftRadiusIndex, 0);
            topRightRadius = array.getDimension(attr_topRightRadiusIndex, 0);
            bottomLeftRadius = array.getDimension(attr_bottomLeftRadiusIndex, 0);
            bottomRightRadius = array.getDimension(attr_bottomRightRadiusIndex, 0);
            backgroundColor = array.getColor(attr_backgroundIndex, emptyColor);
            disableBackground = array.getColor(attr_disableBackgroundIndex, emptyColor);
            startColor = array.getColor(attr_startColorIndex, emptyColor);
            centerColor = array.getColor(attr_centerColorIndex, emptyColor);
            endColor = array.getColor(attr_endColorIndex, emptyColor);
            gradientType = array.getInt(attr_gradientTypeIndex, gradientType);
            gradientOrientation = array.getInt(attr_gradientOrientationIndex, gradientOrientation);
            attrGradientRadius = array.getDimension(attr_GradientRadiusIndex, 0);
            shapeType = array.getInt(attr_ShapeTypeIndex, 0);
            backgroundDrawable = array.getDrawable(backgroundIndex);

            setStroke((int) strokeWidth, strokeColor, dasWidth, dashGap);

            if (radius > 0) {
                setRadius(radius);
            } else {
                setRadius(topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius);
            }

            if (backgroundColor == emptyColor) {
                setGradientColor(startColor, centerColor, endColor, gradientOrientation, attrGradientRadius, gradientType);
            } else {
                setBackgroundColor(backgroundColor);
            }
            setShapeType(shapeType);
        }
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (backgroundDrawable != null) {
                    view.setBackground(backgroundDrawable);
                } else {
                    view.setBackground(drawable);
                }
            } else {
                if (backgroundDrawable != null) {
                    view.setBackgroundDrawable(backgroundDrawable);
                } else {
                    view.setBackgroundDrawable(drawable);
                }
            }
        }
    }

    @Override
    public void setStroke(int strokeWidth, int strokeColor) {
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        setStroke(strokeWidth, strokeColor, 0, 0);
    }

    @Override
    public void setStroke(int strokeWidth, int strokeColor, float dasWidth, float dashGap) {
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.dasWidth = dasWidth;
        this.dashGap = dashGap;
        if (strokeWidth > 0) {
            drawable.setStroke(strokeWidth, strokeColor, dasWidth, dashGap);
        }
    }

    @Override
    public void setRadius(float radius) {
        this.radius = radius;
        drawable.setCornerRadius(radius);
    }

    @Override
    public void setRadius(float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomRightRadius = bottomRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
        drawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
    }

    @Override
    public void setGradientColor(int startColor, int endColor) {
        setGradientColor(startColor, -1, endColor, gradientOrientation, 0, gradientType);
    }

    @Override
    public void setGradientColor(int startColor, int centerColor, int endColor) {
        setGradientColor(startColor, centerColor, endColor, gradientOrientation, 0, gradientType);
    }

    @Override
    public void setGradientColor(int startColor, int centerColor, int endColor, int orientation) {
        setGradientColor(startColor, centerColor, endColor, orientation, 0, gradientType);
    }

    @Override
    public void setGradientColor(int startColor, int centerColor, int endColor, int orientation, float gradientGradientRadius, int type) {
        setGradientColor(startColor, centerColor, endColor, orientation, 0, gradientType, true);
    }

    @Override
    public void setGradientColor(int startColor, int centerColor, int endColor, int orientation, float gradientGradientRadius, int type, boolean isAmend) {
        if (isAmend) {
            this.startColor = startColor;
            this.centerColor = centerColor;
            this.endColor = endColor;
        }
        this.gradientOrientation = orientation;
        this.gradientType = type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int[] colors = null;
            if (centerColor == emptyColor || centerColor == -1) {
                colors = new int[]{startColor, endColor};
            } else {
                colors = new int[]{startColor, centerColor, endColor};
            }
            drawable.setColors(colors);
            drawable.setOrientation(getOrientation(orientation));
            switch (type) {
                case LINEAR:
                    drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                    break;
                case RADIAL:
                    drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
                    drawable.setGradientRadius(gradientGradientRadius);
                    break;
                case SWEEP:
                    drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
                    break;
                default:
                    break;
            }
        } else {
            drawable.setColor(startColor);
        }
    }

    @Override
    public void setShapeType(int type) {
        this.gradientType = type;
        switch (type) {
            case RECTANGLE:
                drawable.setShape(GradientDrawable.RECTANGLE);
                break;
            case OVAL:
                drawable.setShape(GradientDrawable.OVAL);
                break;
            case LINE:
                drawable.setShape(GradientDrawable.LINE);
                break;
            case RING:
                drawable.setShape(GradientDrawable.RING);
                break;
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
         setGradientColor(color,color);
        } else {
        setBackgroundColor(color);
        }
    }
    /**
     * @description 渐变的方向 只有在线性渐变时有效
     */
    private GradientDrawable.Orientation getOrientation(int gradientOrientation) {
        GradientDrawable.Orientation orientation = null;
        switch (gradientOrientation) {
            case TOP_BOTTOM:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case TR_BL:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
            case RIGHT_LEFT:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case BR_TL:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case BOTTOM_TOP:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case BL_TR:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case LEFT_RIGHT:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case TL_BR:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + gradientOrientation);
        }
        return orientation;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            if (backgroundColor == emptyColor) {
                setGradientColor(startColor, centerColor, endColor, gradientOrientation, attrGradientRadius, gradientType, true);
            } else {
                setBackgroundColor(backgroundColor);
            }
        } else {
            if (disableBackground != emptyColor) {
                setBackgroundColor(disableBackground);
            }
        }
    }

    public GradientDrawable getDrawanle() {
        return drawable;
    }
}
