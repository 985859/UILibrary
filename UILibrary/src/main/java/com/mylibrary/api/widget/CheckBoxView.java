package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.mylibrary.api.R;

public class CheckBoxView extends AppCompatCheckBox {
    private int drawableW;
    private int drawableH;
    private int drawablePadding;
    private int index = -1;
    private boolean mChecked;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public CheckBoxView(Context context) {
        this(context, null);
    }

    public CheckBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxView);
        Drawable drawable = array.getDrawable(R.styleable.CheckBoxView_android_button);
        drawableW = (int) array.getDimension(R.styleable.CheckBoxView_attr_drawableWidth, 54);
        drawableH = (int) array.getDimension(R.styleable.CheckBoxView_attr_drawableHeight, 54);
        drawablePadding = array.getDimensionPixelSize(R.styleable.CheckBoxView_android_drawablePadding, 0);
        array.recycle();
        setButtonDrawable(drawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public CheckBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //一定要在设置 Drawable 之前设置 否者不生效
    public void setDrawbleSize(int drawableW, int drawableH) {
        this.drawableW = drawableW;
        this.drawableH = drawableH;
    }

    @Override
    public void setButtonDrawable(int resId) {
        setButtonDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    @Override
    public void setButtonDrawable(Drawable buttonDrawable) {
        if (buttonDrawable != null) {
            setCompoundDrawablePadding(drawablePadding);
            buttonDrawable.setBounds(0, 0, drawableW, drawableH);
            setCompoundDrawables(buttonDrawable, null, null, null);
        }
    }


    /**
     * @param isCallBack 是否执行回调
     * @return
     * @author hukui
     * @time 2020/8/3
     * @Description
     */
    public void setChecked(boolean checked, boolean isCallBack) {
        super.setChecked(checked);
        if (mChecked != checked) {
            mChecked = checked;
            if (isCallBack) {
                if (mOnCheckedChangeListener != null) {
                    mOnCheckedChangeListener.onCheckedChanged(this, mChecked, index);
                }
            }
        }
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (mChecked != checked) {
            mChecked = checked;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked, index);
            }

        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public static interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param buttonView The compound button view whose state has changed.
         * @param isChecked  The new checked state of buttonView.
         */
        void onCheckedChanged(CheckBoxView buttonView, boolean isChecked, int position);
    }

}
