package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;

import com.mylibrary.api.R;
import com.mylibrary.api.interfaces.GradientDrawableInterface;
import com.mylibrary.api.utils.GradientDrawableHelper;

/**
 * @user hukui
 * @data 2019/5/22
 * <p>
 * 倒角 边线  虚线边线  背景渐变 等 多变的EditText
 **/
public class VariedEditText extends androidx.appcompat.widget.AppCompatEditText implements GradientDrawableInterface {
    GradientDrawableHelper drawableHelper;
    private Context context;
    private int drawableLeftWidth;
    private int drawableLeftHeight;

    private int drawableRightWidth;
    private int drawableRightHeight;

    private int drawableTopWidth;
    private int drawableTopHeight;

    private int drawableBottomWidth;
    private int drawableBottomHeight;

    private Drawable leftDrawable;
    private Drawable rightDrawable;
    private Drawable topDrawable;
    private Drawable bottomDrawable;
    private boolean isDrawableLeftAlignFirst = false;//leftDrawable 是否与第一行文字对齐

    public VariedEditText(Context context) {
        this(context, null);
    }

    public VariedEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public VariedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        drawableHelper = new GradientDrawableHelper();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VariedEditText);
        leftDrawable = a.getDrawable(R.styleable.VariedEditText_android_drawableLeft);
        rightDrawable = a.getDrawable(R.styleable.VariedEditText_android_drawableRight);
        topDrawable = a.getDrawable(R.styleable.VariedEditText_android_drawableTop);
        bottomDrawable = a.getDrawable(R.styleable.VariedEditText_android_drawableBottom);


        drawableLeftWidth = (int) a.getDimension(R.styleable.VariedEditText_attr_drawableLeftWidth, 100);
        drawableLeftHeight = (int) a.getDimension(R.styleable.VariedEditText_attr_drawableLeftHeight, 100);


        drawableRightWidth = (int) a.getDimension(R.styleable.VariedEditText_attr_drawableRightWidth, 100);
        drawableRightHeight = (int) a.getDimension(R.styleable.VariedEditText_attr_drawableRightHeight, 100);

        drawableTopWidth = (int) a.getDimension(R.styleable.VariedEditText_attr_drawableTopWidth, 100);
        drawableTopHeight = (int) a.getDimension(R.styleable.VariedEditText_attr_drawableTopHeight, 100);

        drawableBottomWidth = (int) a.getDimension(R.styleable.VariedEditText_attr_drawableBottomWidth, 100);
        drawableBottomHeight = (int) a.getDimension(R.styleable.VariedEditText_attr_drawableBottomHeight, 100);
        isDrawableLeftAlignFirst = a.getBoolean(R.styleable.VariedEditText_attr_isDrawableLeftAlignFirst, false);
        a.recycle();
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
        drawableHelper.init(context, this, attrs);
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
        drawableHelper.setGradientColor(startColor, centerColor, endColor, orientation, gradientGradientRadius, type, true);
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

    private String lUrl = "";


    public void setDrawable(int leftIcon, int rightIcon, int topIcon, int bottomIcon) {
        if (leftIcon != -1) {
            leftDrawable = AppCompatResources.getDrawable(context, leftIcon);
        } else {
            leftDrawable = null;
        }

        if (rightIcon != -1) {
            rightDrawable = AppCompatResources.getDrawable(context, rightIcon);
        } else {
            rightDrawable = null;
        }


        if (topIcon != -1) {
            topDrawable = AppCompatResources.getDrawable(context, topIcon);
        } else {
            topDrawable = null;
        }

        if (bottomIcon != -1) {
            bottomDrawable = AppCompatResources.getDrawable(context, bottomIcon);
        } else {
            bottomDrawable = null;
        }

        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }


    public void setDrawable(Drawable leftDrawable, Drawable rightDrawable, Drawable topDrawable, Drawable bottomDrawable) {
        if (leftDrawable != null) {
            leftDrawable.setBounds(0, 0, drawableLeftWidth, drawableLeftHeight);
        }
        if (rightDrawable != null) {
            rightDrawable.setBounds(0, 0, drawableRightWidth, drawableRightHeight);
        }
        if (topDrawable != null) {
            topDrawable.setBounds(0, 0, drawableTopWidth, drawableTopHeight);
        }
        if (bottomDrawable != null) {
            bottomDrawable.setBounds(0, 0, drawableBottomWidth, drawableBottomHeight);
        }
        if (isDrawableLeftAlignFirst) {
            // 设置图片的位置，左、上、右、下
            ColorDrawable colorDrawable = new ColorDrawable();
            colorDrawable.setBounds(0, 0, drawableLeftWidth, drawableLeftHeight);
            setCompoundDrawables(colorDrawable, topDrawable, rightDrawable, bottomDrawable);
        } else {
            setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
        }


    }


    public void setDrawableLeftSize(int width, int height) {
        drawableLeftWidth = width;
        drawableLeftHeight = height;
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setDrawableRightSize(int width, int height) {
        drawableRightWidth = width;
        drawableRightHeight = height;
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setDrawableTopSize(int width, int height) {
        drawableTopWidth = width;
        drawableTopHeight = height;
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setDrawableBottomSize(int width, int height) {
        drawableBottomWidth = width;
        drawableBottomHeight = height;
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setLeftDrawable(Drawable leftDrawable) {
        this.leftDrawable = leftDrawable;
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setRightDrawable(Drawable rightDrawable) {
        this.rightDrawable = rightDrawable;
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setTopDrawable(Drawable topDrawable) {
        this.topDrawable = topDrawable;
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setBottomDrawable(Drawable bottomDrawable) {
        this.bottomDrawable = bottomDrawable;
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setLeftDrawable(int id) {
        if (id == -1) {
            leftDrawable = null;
        } else {
            this.leftDrawable = context.getResources().getDrawable(id);
        }
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setRightDrawable(int id) {
        if (id == -1) {
            rightDrawable = null;
        } else {
            this.rightDrawable = context.getResources().getDrawable(id);

        }
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setTopDrawable(int id) {
        if (id == -1) {
            topDrawable = null;
        } else {
            this.topDrawable = context.getResources().getDrawable(id);
        }
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    public void setBottomDrawable(int id) {
        if (id == -1) {
            bottomDrawable = null;
        } else {
            this.bottomDrawable = context.getResources().getDrawable(id);
        }
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        final Drawable buttonDrawable = leftDrawable;
        super.onDraw(canvas);
        int left = getPaddingLeft();
        if (buttonDrawable != null && isDrawableLeftAlignFirst) {
            final int scrollX = getScrollX();
            final int scrollY = getScrollY();
            if (scrollX == 0 && scrollY == 0) {
                buttonDrawable.draw(canvas);
            } else {
                canvas.translate(scrollX - left, scrollY);
                buttonDrawable.draw(canvas);
                canvas.translate(-scrollX + left, -scrollY);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isLayoutRtl() {
        return (getLayoutDirection() == LAYOUT_DIRECTION_RTL);
    }
}
