package com.mylibrary.api.widget;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.Toolbar;

import com.mylibrary.api.R;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class TopView extends Toolbar {
    boolean isShowLeftIocn = true;
    private TextView titleView;
    private TextView leftView;
    private TextView rightView;

    private CharSequence title = "";
    private int titleColor = Color.parseColor("#ffffff");//默认 白色
    private int titleSize = 16;

    private CharSequence leftText = "";
    private int leftTextColor = Color.parseColor("#ffffff");//默认 白色
    private int leftTextSize = 13;
    private Drawable leftIcon;
    private int leftIconWidth;
    private int leftIconHight;
    private int leftIconPadding;

    private CharSequence rightText = "";
    private int rightTextColor = Color.parseColor("#ffffff");//默认 白色
    private int rightTextSize = 13;
    private Drawable rightIcon;
    private int rightIconWidth;
    private int rightIconHight;
    private int rightIconPadding;
    private int barHeight = 0;
    private int maxTitleWidth = 0;
    private Drawable backgroutDrawable;

    private final ArrayList<View> mHiddenViews = new ArrayList<>();
    private Context context;


    public TopView(Context context) {
        this(context, null);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopView);
        title = array.getString(R.styleable.TopView_attr_Title);
        titleSize = (int) array.getDimension(R.styleable.TopView_attr_TitleSize, 16);
        if (titleSize != 16) {
            titleSize = SystemUtil.px2sp(context, titleSize);
        }
        titleColor = array.getColor(R.styleable.TopView_attr_TitleColor, titleColor);
        leftText = array.getString(R.styleable.TopView_attr_LeftText);
        leftTextSize = (int) array.getDimension(R.styleable.TopView_attr_LeftTextSize, 13);
        if (leftTextSize != 13) {
            leftTextSize = SystemUtil.px2sp(context, leftTextSize);
        }
        leftTextColor = array.getColor(R.styleable.TopView_attr_LeftTextColor, titleColor);
        backgroutDrawable = array.getDrawable(R.styleable.TopView_android_background);
        leftIcon = array.getDrawable(R.styleable.TopView_attr_LeftIcon);
        isShowLeftIocn = array.getBoolean(R.styleable.TopView_attr_LeftIconShow, true);
        leftIconWidth = (int) array.getDimension(R.styleable.TopView_attr_LeftIconWidth, SystemUtil.dp2px(context, 22));
        leftIconHight = (int) array.getDimension(R.styleable.TopView_attr_LeftIconHight, SystemUtil.dp2px(context, 22));
        leftIconPadding = (int) array.getDimension(R.styleable.TopView_attr_LeftIconPadding, SystemUtil.dp2px(context, 4));

        rightText = array.getString(R.styleable.TopView_attr_RightText);
        rightTextSize = (int) array.getDimension(R.styleable.TopView_attr_RightTextSize, 13);
        if (rightTextSize != 13) {
            rightTextSize = SystemUtil.px2sp(context, rightTextSize);
        }
        rightTextColor = array.getColor(R.styleable.TopView_attr_RightTextColor, titleColor);

        rightIcon = array.getDrawable(R.styleable.TopView_attr_RightIcon);
        rightIconWidth = (int) array.getDimension(R.styleable.TopView_attr_RightIconWidth, SystemUtil.dp2px(context, 20));
        rightIconHight = (int) array.getDimension(R.styleable.TopView_attr_RightIconHight, SystemUtil.dp2px(context, 20));
        rightIconPadding = (int) array.getDimension(R.styleable.TopView_attr_RightIconPadding, SystemUtil.dp2px(context, 4));
        if (leftIcon == null && isShowLeftIocn) {
            leftIcon = context.getDrawable(R.drawable.return_whit);
        }
        setLeftView(leftText, leftIcon);
        if (StringUtil.isNotEmpty(rightText) || rightIcon != null) {
            setRightView(rightText, rightIcon);
        }
        if (StringUtil.isNotEmpty(title)) {
            setTitle(title);
        }
        setLeftViewOnClickListener(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Activity activity = getActivityFromView(this);
            if (activity != null) {
                if (activity.getWindow().getAttributes().flags == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) {
                    //沉浸状态栏 开启
                    barHeight = SystemUtil.getStatusBarHeight(context);
                }
            }
        }

        if (backgroutDrawable != null) {
            setBackground(backgroutDrawable);
        } else {
            setBackgroundColor(Color.parseColor("#1b4be9"));
        }

    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int top = barHeight + getPaddingTop();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int right = width - paddingRight;

        if (leftView != null) {
            leftView.layout(paddingLeft, top, paddingLeft + leftView.getMeasuredWidth(), height);
            leftView.setGravity(Gravity.CENTER);
        }
        if (rightView != null) {
            int rl = right - rightView.getMeasuredWidth();
            rightView.layout(rl, top, rl + rightView.getMeasuredWidth(), height);
            rightView.setGravity(Gravity.CENTER);
        }
        if (titleView != null) {
            int lw = (getMeasuredWidth() - titleView.getMeasuredWidth()) / 2;
            titleView.layout(lw, top, lw + titleView.getMeasuredWidth(), height);
            titleView.setGravity(Gravity.CENTER);
        }

    }


    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (titleView == null) {
                titleView = new TextView(context);
                titleView.setSingleLine();
                titleView.setTextSize(titleSize);
                titleView.setGravity(16);
                titleView.setTag(1);
                titleView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                titleView.setEllipsize(TextUtils.TruncateAt.END);
                if (titleColor != 0) {
                    titleView.setTextColor(titleColor);
                }
            }
            if (!isChildOrHidden(titleView)) {
                addSystemView(titleView);
            }
        } else if (titleView != null && isChildOrHidden(titleView)) {
            removeView(titleView);
            mHiddenViews.remove(titleView);
        }
        if (titleView != null) {
            titleView.setText(title);
        }
        this.title = title;
    }

    private boolean isChildOrHidden(View child) {
        return child.getParent() == this || mHiddenViews.contains(child);
    }


    private void addSystemView(View v) {
        final ViewGroup.LayoutParams vlp = v.getLayoutParams();
        final LayoutParams lp;
        if (vlp == null) {
            lp = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(vlp)) {
            lp = generateLayoutParams(vlp);
        } else {
            lp = (LayoutParams) vlp;
        }

        v.setLayoutParams(lp);
        mHiddenViews.add(v);
        addView(v, lp);
    }

    public void setTitleSize(int size) {
        titleSize = size;
        if (titleView != null) {
            titleView.setTextSize(titleSize);
        }
    }

    public void setTitleColor(@ColorInt int color) {
        titleColor = color;
        if (titleView != null) {
            titleView.setTextColor(color);
        }
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setLeftView(CharSequence text, Drawable icon) {
        if (StringUtil.isNotEmpty(text) || icon != null) {
            if (leftView == null) {
                if (leftView == null) {
                    final Context context = getContext();
                    leftView = new TextView(context);
                    leftView.setSingleLine();
                    leftView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                    leftView.setGravity(16);
                    leftView.setPadding(SystemUtil.dp2px(context, 8), 0, SystemUtil.dp2px(context, 8), 0);
                    leftView.setEllipsize(TextUtils.TruncateAt.END);
                }
                if (!isChildOrHidden(leftView)) {
                    addSystemView(leftView);
                }
            }
        } else if (leftView != null && isChildOrHidden(leftView)) {
            removeView(leftView);
            mHiddenViews.remove(leftView);
        }
        if (leftView != null) {
            leftView.setTextSize(leftTextSize);
            if (leftTextColor != 0) {
                leftView.setTextColor(leftTextColor);
            }
            if (StringUtil.isNotEmpty(text)) {
                leftView.setText(text);
            }
            if (icon != null && isShowLeftIocn) {
                icon.setBounds(0, 0, leftIconWidth, leftIconHight);
                leftView.setCompoundDrawables(icon, null, null, null);
                leftView.setCompoundDrawablePadding(leftIconPadding);
            }
        }


    }


    public void setLeftText(CharSequence leftText) {
        this.leftText = leftText;
        setLeftView(leftText, leftIcon);
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        if (leftView != null) {
            leftView.setTextColor(leftTextColor);
        }
    }

    public void setLeftTextSize(int leftTextSize) {
        this.leftTextSize = leftTextSize;
        if (leftView != null) {
            leftView.setTextSize(leftTextSize);
        }
    }

    public void setLeftIcon(Drawable leftIcon) {
        this.leftIcon = leftIcon;
        setLeftView(leftText, leftIcon);
    }

    public void setLeftIcon(int icon) {
        this.leftIcon = getContext().getResources().getDrawable(icon);
        setLeftView(leftText, leftIcon);
    }


    public void setLeftIconSize(int leftIconWidth, int leftIconHight) {
        this.leftIconWidth = leftIconWidth;
        this.leftIconHight = leftIconHight;
        if (leftIcon != null) {
            leftIcon.setBounds(0, 0, leftIconWidth, leftIconHight);
        }
    }

    public void setRightView(CharSequence text, Drawable icon) {
        if (StringUtil.isNotEmpty(text) || icon != null) {
            if (rightView == null) {
                if (rightView == null) {
                    rightView = new TextView(context);
                    rightView.setSingleLine();
                    rightView.setPadding(SystemUtil.dp2px(context, 8), 0, SystemUtil.dp2px(context, 8), 0);
                    rightView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                    rightView.setGravity(16);
                }
                if (!isChildOrHidden(rightView)) {
                    addSystemView(rightView);
                }
            }
        } else if (rightView != null && isChildOrHidden(rightView)) {
            removeView(rightView);
            mHiddenViews.remove(rightView);
        }
        if (rightView != null) {
            rightView.setTextSize(rightTextSize);
            if (rightTextColor != 0) {
                rightView.setTextColor(rightTextColor);
            }
            if (StringUtil.isNotEmpty(text)) {
                rightView.setText(text);
            }
            if (icon != null) {
                icon.setBounds(0, 0, rightIconWidth, rightIconHight);
                rightView.setCompoundDrawables(icon, null, null, null);
            }
            rightView.setCompoundDrawablePadding(rightIconPadding);
        }


    }


    public void setRightText(CharSequence rightText) {
        this.rightText = rightText;
        setRightView(rightText, rightIcon);
    }

    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        if (rightView != null) {
            rightView.setTextColor(rightTextColor);
        }
    }

    public void setRightTextSize(int rightTextSize) {
        this.rightTextSize = rightTextSize;
        if (rightView != null) {
            rightView.setTextSize(rightTextSize);
        }
    }

    public void setRightIcon(Drawable rightIcon) {
        this.rightIcon = rightIcon;
        setRightView(rightText, rightIcon);
    }

    public void setRightIcon(int icon) {
        this.rightIcon = getContext().getResources().getDrawable(icon);
        setRightView(rightText, rightIcon);
    }


    public void setRightIconSize(int rightIconWidth, int rightIconHight) {
        this.rightIconWidth = rightIconWidth;
        this.rightIconHight = rightIconHight;
        if (rightIcon != null) {
            rightIcon.setBounds(0, 0, rightIconWidth, rightIconHight);
        }
    }

    public void setRightViewOnClickListener(OnClickListener listener) {
        if (rightView != null && listener != null) {
            rightView.setOnClickListener(listener);
        }
    }

    public void setLeftViewOnClickListener(final OnClickListener listener) {
        setLeftViewOnClickListener(listener, true);

    }

    public void setLeftViewOnClickListener(final OnClickListener listener, boolean isFinish) {
        if (leftView != null) {
            leftView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    if (isFinish)
                        ((Activity) context).finish();

                }
            });
        }
    }

    /**
     * try get host activity from view.
     * views hosted on floating window like dialog and toast will sure return null.
     *
     * @return host activity; or null if not available
     */
    public Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
