package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mylibrary.api.R;
import com.mylibrary.api.interfaces.GradientDrawableInterface;

import com.mylibrary.api.utils.GradientDrawableHelper;
import com.mylibrary.api.utils.ImageUtils;
import com.mylibrary.api.utils.StringUtil;


/**
 * @user hukui
 * @data 2019/5/22
 * <p>
 * 倒角 边线  虚线边线  背景渐变 等 多变的TextView
 * 如果设置了background  那么 这些属性 都不会生效
 * 请使用自定义属性
 **/
public class VariedTextView extends CompoundButton implements GradientDrawableInterface {

    private Context context;
    private GradientDrawableHelper drawableHelper;
    private int textNormalColor = Color.parseColor("#444444");
    private int textDisableColor;

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


    private boolean enabled;


    public VariedTextView(Context context) {
        this(context, null);
    }

    public VariedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        drawableHelper = new GradientDrawableHelper();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VariedTextView);
        textNormalColor = a.getColor(R.styleable.VariedTextView_android_textColor, textNormalColor);
        enabled = a.getBoolean(R.styleable.VariedTextView_android_enabled, true);
        leftDrawable = a.getDrawable(R.styleable.VariedTextView_android_drawableLeft);
        rightDrawable = a.getDrawable(R.styleable.VariedTextView_android_drawableRight);
        topDrawable = a.getDrawable(R.styleable.VariedTextView_android_drawableTop);
        bottomDrawable = a.getDrawable(R.styleable.VariedTextView_android_drawableBottom);


        drawableLeftWidth = (int) a.getDimension(R.styleable.VariedTextView_attr_drawableLeftWidth, 100);
        drawableLeftHeight = (int) a.getDimension(R.styleable.VariedTextView_attr_drawableLeftHeight, 100);


        drawableRightWidth = (int) a.getDimension(R.styleable.VariedTextView_attr_drawableRightWidth, 100);
        drawableRightHeight = (int) a.getDimension(R.styleable.VariedTextView_attr_drawableRightHeight, 100);

        drawableTopWidth = (int) a.getDimension(R.styleable.VariedTextView_attr_drawableTopWidth, 100);
        drawableTopHeight = (int) a.getDimension(R.styleable.VariedTextView_attr_drawableTopHeight, 100);

        drawableBottomWidth = (int) a.getDimension(R.styleable.VariedTextView_attr_drawableBottomWidth, 100);
        drawableBottomHeight = (int) a.getDimension(R.styleable.VariedTextView_attr_drawableBottomHeight, 100);

        textDisableColor = a.getColor(R.styleable.VariedTextView_attr_textDisableColor, textNormalColor);
        isDrawableLeftAlignFirst = a.getBoolean(R.styleable.VariedTextView_attr_isDrawableLeftAlignFirst, false);
        a.recycle();
        setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
        drawableHelper.init(context, this, attrs);
        setEnabled(enabled);

    }

    public VariedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setStrokeColor(int strokeColor) {
        drawableHelper.setStroke(2, strokeColor);
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

    public void setBackgroundColor(String color) {
        drawableHelper.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enabled = enabled;
        if (drawableHelper != null) {
            drawableHelper.setEnabled(enabled);
        }
        if (enabled) {
            setTextColor(textNormalColor);
        } else {
            setTextColor(textDisableColor);
        }

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


    public void setTextNormalColor(int textNormalColor) {
        this.textNormalColor = textNormalColor;
        setEnabled(enabled);
    }

    public void setTextDisableColor(int textDisableColor) {
        this.textDisableColor = textDisableColor;
        setEnabled(enabled);
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

    public void setLeftDrawable(String url) {
        setUrlDrawable(url, 0);
    }

    public void setRightDrawable(String url) {
        setUrlDrawable(url, 2);
    }

    public void setTopDrawable(String url) {
        setUrlDrawable(url, 1);
    }

    public void setBottomDrawable(String url) {
        setUrlDrawable(url, 3);
    }

    int type;
    private CustomTarget<Bitmap> customTarget = new CustomTarget<Bitmap>() {
        @Override
        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
            switch (type) {
                case 0:
                    leftDrawable = ImageUtils.bitmap2Drawable(context, bitmap);
                    setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
                    break;
                case 1:
                    topDrawable = ImageUtils.bitmap2Drawable(context, bitmap);
                    setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
                    break;
                case 2:
                    rightDrawable = ImageUtils.bitmap2Drawable(context, bitmap);
                    setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
                    break;
                case 3:
                    bottomDrawable = ImageUtils.bitmap2Drawable(context, bitmap);
                    setDrawable(leftDrawable, rightDrawable, topDrawable, bottomDrawable);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onLoadCleared(@Nullable Drawable placeholder) {

        }


    };

    public void setUrlDrawable(String url, int type) {
        this.type = type;
        if (StringUtil.isEmpty(url))
            return;
        int w;
        int h;
        switch (type) {
            case 0:
                w = drawableLeftWidth;
                h = drawableLeftHeight;
                break;
            case 1:
                w = drawableTopWidth;
                h = drawableTopHeight;
                break;
            case 2:
                w = drawableRightWidth;
                h = drawableRightHeight;
                break;
            case 3:
                w = drawableBottomWidth;
                h = drawableBottomHeight;
                break;

            default:
                w = 0;
                h = 0;
        }
        Glide.with(this)
                .asBitmap()
                .load(url)
                .override(w, h)//这里的单位是px
                .into(customTarget);
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


