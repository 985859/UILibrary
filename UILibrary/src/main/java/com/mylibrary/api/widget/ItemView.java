package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.mylibrary.api.R;
import com.mylibrary.api.utils.SystemUtil;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/8/24 8:55
 */
@BindingMethods({
        @BindingMethod(type = ItemView.class, attribute = "item_leftText", method = "setLeftText"),
        @BindingMethod(type = ItemView.class, attribute = "item_leftHint", method = "setLeftHint"),
        @BindingMethod(type = ItemView.class, attribute = "item_leftTextColor", method = "setLeftTextColor"),
        @BindingMethod(type = ItemView.class, attribute = "item_leftIcon", method = "setLeftIcon"),

        @BindingMethod(type = ItemView.class, attribute = "item_rightText", method = "setRightText"),
        @BindingMethod(type = ItemView.class, attribute = "item_rightHint", method = "setRightHint"),
        @BindingMethod(type = ItemView.class, attribute = "item_rightTextColor", method = "setRightTextColor"),
        @BindingMethod(type = ItemView.class, attribute = "item_rightIcon", method = "setRightIcon"),
})
public class ItemView extends LinearLayout {
    private TextView leftView;
    private TextView rightView;
    private ImageView leftImagView;
    private ImageView rightImgView;
    private Context context;

    private int gravity;

    private String leftText;
    private String leftHint;
    private int leftWight;//左边控件占的比例
    private int leftTextSize;
    private int leftTextMinWidth;
    private int leftTextColor;
    private boolean leftTextBold;
    private int leftTextMaxLine;
    private int leftTextGravity;
    private Drawable leftIcon;
    private int leftIconWidth;
    private int leftIconHeight;
    private int leftIconPadding;

    private String rightText;
    private String rightHint;
    private int rightWight;//右边控件占的比例
    private int rightTextSize;
    private int rightTextColor;
    private boolean rightTextBold;
    private int rightTextMaxLine;
    private int rightTextGravity;
    private Drawable rightIcon;
    private int rightIconWidth;
    private int rightIconHeight;
    private int rightIconPadding;
    private int rightTextPadding;


    public ItemView(Context context) {
        this(context, null);

    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        int textColor = Color.parseColor("#333333");
        gravity = array.getInt(R.styleable.ItemView_gravity, -1);
        leftWight = array.getInt(R.styleable.ItemView_item_leftWight, 0);
        leftText = array.getString(R.styleable.ItemView_item_leftText);
        leftHint = array.getString(R.styleable.ItemView_item_leftHint);
        leftTextColor = array.getColor(R.styleable.ItemView_item_leftTextColor, textColor);
        leftTextSize = (int) array.getDimension(R.styleable.ItemView_item_leftTextSize, 12);
        leftTextMinWidth = (int) array.getDimension(R.styleable.ItemView_item_leftTextMinWidth, -1);
        if (leftTextSize != 12) {
            leftTextSize = SystemUtil.px2sp(context, leftTextSize);
        }
        leftTextBold = array.getBoolean(R.styleable.ItemView_item_leftTextBold, false);
        leftTextMaxLine = array.getInt(R.styleable.ItemView_item_leftTextMaxLine, -1);
        leftIcon = array.getDrawable(R.styleable.ItemView_item_leftIcon);
        leftTextGravity = array.getInt(R.styleable.ItemView_item_leftTextGravity, 0);
        leftIconWidth = (int) array.getDimension(R.styleable.ItemView_item_leftIconWidth, 20);
        leftIconHeight = (int) array.getDimension(R.styleable.ItemView_item_leftIconHeight, 20);
        leftIconPadding = (int) array.getDimension(R.styleable.ItemView_item_leftIconPadding, 6);


        rightWight = array.getInt(R.styleable.ItemView_item_rightWight, 1);
        rightText = array.getString(R.styleable.ItemView_item_rightText);
        rightHint = array.getString(R.styleable.ItemView_item_rightHint);
        rightTextColor = array.getColor(R.styleable.ItemView_item_rightTextColor, textColor);
        rightTextSize = (int) array.getDimension(R.styleable.ItemView_item_rightTextSize, 12);
        rightTextMaxLine = array.getInt(R.styleable.ItemView_item_rightTextMaxLine, -1);
        rightTextGravity = array.getInt(R.styleable.ItemView_item_rightTextGravity, 0);
        if (rightTextSize != 12) {
            rightTextSize = SystemUtil.px2sp(context, rightTextSize);
        }
        rightTextBold = array.getBoolean(R.styleable.ItemView_item_rightTextBold, false);
        rightIcon = array.getDrawable(R.styleable.ItemView_item_rightIcon);
        rightIconWidth = (int) array.getDimension(R.styleable.ItemView_item_rightIconWidth, 20);
        rightIconHeight = (int) array.getDimension(R.styleable.ItemView_item_rightIconHeight, 20);
        rightIconPadding = (int) array.getDimension(R.styleable.ItemView_item_rightIconPadding, 6);
        rightTextPadding = (int) array.getDimension(R.styleable.ItemView_item_rightTextPadding, 6);
        array.recycle();
        initView();

        setLeftWight(leftWight);
        setLeftText(leftText);
        setLeftHint(leftHint);
        setLeftTextColor(leftTextColor);
        setLeftTextSize(leftTextSize);
        setLeftTextBold(leftTextBold);
        setLeftIconSize(leftIconWidth, leftIconHeight);
        setLeftIconPadding(leftIconPadding);
        setLeftTextGravity(leftTextGravity);
        setLeftTextMaxLine(leftTextMaxLine);
        setLeftIcon(leftIcon);

        setRightWight(rightWight);
        setRightText(rightText);
        setRightHint(rightHint);
        setRightTextPadding(rightTextPadding);
        setRightTextColor(rightTextColor);
        setRightTextSize(rightTextSize);
        setRightTextBold(rightTextBold);
        setRightIconSize(rightIconWidth, rightIconHeight);
        setRightIconPadding(rightIconPadding);
        setRightTextMaxLine(rightTextMaxLine);
        setRightTextGravity(rightTextGravity);
        setRightIcon(rightIcon);
        if (rightTextMaxLine > 0) {
            rightView.setMaxLines(rightTextMaxLine);
        }
        setLayoutGravity(gravity);
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        leftView = new TextView(context);
        rightView = new TextView(context);
        leftView.setEllipsize(TextUtils.TruncateAt.END);
        rightView.setEllipsize(TextUtils.TruncateAt.END);
        if (leftTextMinWidth != -1)
            leftView.setMinWidth(leftTextMinWidth);
        addView(leftView);
        addView(rightView);

    }

    private void setLayoutGravity(int gravity) {
        switch (gravity) {
            case 1:
                setGravity(Gravity.TOP);
                break;
            case 2:
                setGravity(Gravity.BOTTOM);
                break;
            case 3:
                setGravity(Gravity.CENTER_VERTICAL);
                break;
            case 4:
                setGravity(Gravity.CENTER);
                break;
            default:
                break;
        }
    }


    public void setLeftWight(int leftWight) {
        this.leftWight = leftWight;
        if (leftWight == -1) return;
        LayoutParams params = (LayoutParams) leftView.getLayoutParams();
        params.weight = leftWight;

    }


    public void setLeftText(int leftText) {
        setLeftText(context.getString(leftText));
    }

    public void setLeftText(CharSequence leftText) {
        if (TextUtils.isEmpty(leftText)) leftText = "";
        this.leftText = leftText.toString();
        leftView.setText(leftText);
    }

    public void setLeftHint(CharSequence leftHint) {
        if (leftHint == null) {
            leftHint = "";
        }
        leftView.setHint(leftHint);
    }

    public void setLeftTextSize(int leftTextSize) {
        this.leftTextSize = leftTextSize;
        leftView.setTextSize(leftTextSize);
    }

    public void setLeftTextMaxLine(int leftTextMaxLine) {
        this.leftTextMaxLine = leftTextMaxLine;
        if (leftTextMaxLine != -1) {
            leftView.setMaxLines(leftTextMaxLine);
        }

    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        leftView.setTextColor(leftTextColor);
    }

    public void setLeftTextBold(boolean leftTextBold) {
        this.leftTextBold = leftTextBold;
        leftView.getPaint().setFakeBoldText(leftTextBold);
    }


    public void setLeftTextGravity(int leftTextGravity) {
        leftView.setGravity(getGravity(leftTextGravity));
    }

    private int getGravity(int gravity) {
        int i = 0;
        switch (gravity) {
            case 1:
                return Gravity.START;
            case 2:
                return Gravity.END;
            case 3:
                return Gravity.CENTER;
            default:
                return 0;
        }

    }

    public void setLeftIcon(int leftIcon) {
        Drawable drawable = context.getResources().getDrawable(leftIcon);
        setLeftIcon(drawable);
    }

    /***********要在setLeftIcon（） 之前设置才有效************/
    public void setLeftIconSize(int leftIconWidth, int leftIconHeight) {
        this.leftIconWidth = leftIconWidth;
        this.leftIconHeight = leftIconHeight;
        leftView.setMinHeight(leftIconHeight);
    }

    public void setLeftIconPadding(int leftIconPadding) {
        this.leftIconPadding = leftIconPadding;
    }

    /***********要在setLeftIcon（） 之前设置才有效************/
    public void setLeftIcon(Drawable leftIcon) {
        this.leftIcon = leftIcon;
        if (leftIcon != null) {
            if (leftIcon != null) {
                leftIcon.setBounds(0, 0, leftIconWidth, leftIconHeight);
            }
            if (leftImagView == null) {
                MarginLayoutParams params = new LayoutParams(leftIconWidth, leftIconHeight);
                leftImagView = new ImageView(context);
                params.setMargins(0, 0, leftIconPadding, 0);

                addView(leftImagView, 0, params);
            }
            leftImagView.setImageDrawable(leftIcon);
        } else {
            if (leftImagView != null) {
                removeView(leftImagView);
                leftImagView = null;
            }
        }
    }

    public void setRightWight(int rightWight) {
        this.rightWight = rightWight;
        LayoutParams params = (LayoutParams) rightView.getLayoutParams();
        params.weight = rightWight;
    }

    public void setRightText(int rightText) {
        setRightText(context.getString(rightText));
    }

    public void setRightText(CharSequence rightText) {
        if (TextUtils.isEmpty(rightText)) {
            rightText = "";
            rightView.setVisibility(GONE);
        } else {
            rightView.setVisibility(VISIBLE);
        }
        this.rightText = rightText.toString();
        rightView.setText(rightText);
    }

    public void setRightHint(CharSequence rightHint) {
        if (rightHint != null) {
            rightView.setVisibility(VISIBLE);
            rightView.setHint(rightHint);
        }
    }


    public void setRightTextPadding(int rightTextPadding) {
        this.rightTextPadding = rightTextPadding;
        rightView.setPadding(rightTextPadding, 0, 0, 0);
    }


    public void setRightTextMaxLine(int rightTextMaxLine) {
        this.rightTextMaxLine = rightTextMaxLine;
        if (rightTextMaxLine != -1) {
            rightView.setMaxLines(rightTextMaxLine);
        }

    }

    public void setRightTextGravity(int rightTextGravity) {
        this.rightTextGravity = getGravity(rightTextGravity);
        rightView.setGravity(this.rightTextGravity);
    }

    public void setRightTextSize(int rightTextSize) {
        this.rightTextSize = rightTextSize;
        rightView.setTextSize(rightTextSize);
    }

    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        rightView.setTextColor(rightTextColor);
    }

    public void setRightTextBold(boolean rightTextBold) {
        this.rightTextBold = rightTextBold;
        rightView.getPaint().setFakeBoldText(rightTextBold);
    }

    /***********setRightIcon（） 之前设置才有效************/
    public void setRightIconSize(int rightIconWidth, int rightIconHeight) {
        this.rightIconWidth = rightIconWidth;
        this.rightIconHeight = rightIconHeight;
        rightView.setMinHeight(rightIconHeight);
    }

    public void setRightIconPadding(int rightIconPadding) {
        this.rightIconPadding = rightIconPadding;
    }

    /***********要在setLeftIcon（） 之前设置才有效************/
    public void setRightIcon(int rightIcon) {
        Drawable drawable = context.getResources().getDrawable(rightIcon);
        setRightIcon(drawable);
    }

    public void setRightIcon(Drawable rightIcon) {
        this.rightIcon = rightIcon;
        if (rightIcon != null) {
            if (rightIcon != null) {
                rightIcon.setBounds(0, 0, rightIconWidth, rightIconHeight);
            }
            if (rightImgView == null) {
                rightImgView = new ImageView(context);
                MarginLayoutParams params = new LayoutParams(rightIconWidth, rightIconHeight);
                params.setMargins(rightIconPadding, 0, 0, 0);
                if (leftImagView != null) {
                    addView(rightImgView, 3, params);
                    int leftMaxWidth = getMeasuredWidth() - getPaddingStart() - getPaddingEnd() - rightView.getWidth()
                            - rightIconWidth - leftIconWidth - leftIconPadding - rightIconPadding;
                    leftView.setMaxWidth(leftMaxWidth);
                } else {
                    addView(rightImgView, 2, params);
                }
            }
            rightImgView.setImageDrawable(rightIcon);
        } else {
            if (rightImgView != null) {
                removeView(rightImgView);
                rightImgView = null;
            }
        }
    }


    public TextView getLeftView() {
        return leftView;
    }
    public TextView getRightView() {
        return rightView;
    }
    public ImageView getRightImgView() {
        return rightImgView;
    }
}
