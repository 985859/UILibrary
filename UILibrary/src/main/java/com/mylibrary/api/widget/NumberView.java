package com.mylibrary.api.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.mylibrary.api.R;
import com.mylibrary.api.utils.GradientDrawableHelper;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.utils.ToastUtil;


/**
 * Created by myuser on 2017/7/21.
 * <p>数字选择按钮
 */
@BindingMethods({
        @BindingMethod(type = NumberView.class, attribute = "attr_Number", method = "setNumber"),
        @BindingMethod(type = NumberView.class, attribute = "attr_MaxNumber", method = "setMaxNumber"),
        @BindingMethod(type = NumberView.class, attribute = "attr_MinNumber", method = "setMinNumber"),
})
public class NumberView extends LinearLayout {

    private Context context;
    private VariedTextView numberMinus;
    private VariedTextView numberTV;
    private VariedTextView numberPlus;
    private String toast = "已到达最大数量";
    private int index = -1;
    private OnNumberListener numberListener;


    //控件设置
    private int strokeWidth;
    private int strokeColor;
    private int backgroundColor;
    private int maxNumber = 999999999;
    private int minNumber = 1;
    private int textWidth;// 加减号的宽高
    private int textHeight;
    private int viewRadius;// 加减号的 角度半径
    private int radius;// 控件角度半径
    private boolean showDivider = true;//是否在控件之间加分割线 默认 添加
    //  数字控件 设置
    private int number;
    private int numberTextColor;
    private int numberRadius;
    private int numberBackgroundColor;
    private int numberTextSize;
    private int numberWidth;
    private int numberHeight;

    //  + 设置
    private int plusTextColor;
    private int plusTextColor2;
    private int plusTextSize;
    private int plusBackgroundColor;
    private int plusBackgroundColor2;
    //  - 设置
    private int minusTextColor;
    private int minusTextColor2;
    private int minusTextSize;
    private int minusBackgroundColor;
    private int minusBackgroundColor2;


    GradientDrawableHelper helper;

    public NumberView(Context context) {
        this(context, null);

    }

    @SuppressLint("ResourceAsColor")
    public NumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumberView);
        //控件设置
        strokeWidth = (int) array.getDimension(R.styleable.NumberView_attr_strokeWidth, 0);
        strokeColor = array.getColor(R.styleable.NumberView_attr_strokeColor, context.getResources().getColor(R.color.empty));
        backgroundColor = array.getColor(R.styleable.NumberView_attr_background, context.getResources().getColor(R.color.empty));
        radius = (int) array.getDimension(R.styleable.NumberView_attr_Radius, 0);
        viewRadius = (int) array.getDimension(R.styleable.NumberView_attr_ViewRadius, 0);
        maxNumber = array.getInt(R.styleable.NumberView_attr_MaxNumber, maxNumber);
        minNumber = array.getInt(R.styleable.NumberView_attr_MinNumber, minNumber);
        textWidth = (int) array.getDimension(R.styleable.NumberView_attr_TextWidth, SystemUtil.dp2px(context, 24));
        textHeight = (int) array.getDimension(R.styleable.NumberView_attr_TextHeight, SystemUtil.dp2px(context, 24));
        showDivider = array.getBoolean(R.styleable.NumberView_attr_showDivider, true);


        //  数字控件 设置
        number = array.getInt(R.styleable.NumberView_attr_Number, minNumber);
        numberRadius = (int) array.getDimension(R.styleable.NumberView_attr_NumberRadius, 0);
        numberWidth = (int) array.getDimension(R.styleable.NumberView_attr_NumberWidth, SystemUtil.dp2px(context, 30));
        numberHeight = (int) array.getDimension(R.styleable.NumberView_attr_NumberHeight, SystemUtil.dp2px(context, 24));
        numberTextColor = array.getColor(R.styleable.NumberView_attr_NumberTextColor, context.getResources().getColor(R.color.textColor));
        numberBackgroundColor = array.getColor(R.styleable.NumberView_attr_NumberBackgroundColor, context.getResources().getColor(R.color.empty));
        numberTextSize = (int) array.getDimension(R.styleable.NumberView_attr_NumberTextSize, SystemUtil.sp2px(context, 13));

        //  + 设置

        plusTextSize = (int) array.getDimension(R.styleable.NumberView_attr_PlusTextSize, SystemUtil.sp2px(context, 16));
        plusTextColor = array.getColor(R.styleable.NumberView_attr_PlusTextColor, context.getResources().getColor(R.color.textColor));
        plusTextColor2 = array.getColor(R.styleable.NumberView_attr_PlusTextColor2, plusTextColor);
        plusBackgroundColor = array.getColor(R.styleable.NumberView_attr_PlusBackgroundColor, context.getResources().getColor(R.color.empty));
        plusBackgroundColor2 = array.getColor(R.styleable.NumberView_attr_PlusBackgroundColor2, context.getResources().getColor(R.color.empty));
        //  - 设置
        minusTextSize = (int) array.getDimension(R.styleable.NumberView_attr_MinusTextSize, SystemUtil.sp2px(context, 16));
        minusTextColor = array.getColor(R.styleable.NumberView_attr_MinusTextColor, context.getResources().getColor(R.color.textColor));
        minusTextColor2 = array.getColor(R.styleable.NumberView_attr_MinusTextColor2, minusTextColor);
        minusBackgroundColor = array.getColor(R.styleable.NumberView_attr_MinusBackgroundColor, context.getResources().getColor(R.color.empty));
        minusBackgroundColor2 = array.getColor(R.styleable.NumberView_attr_MinusBackgroundColor2, context.getResources().getColor(R.color.empty));
        array.recycle();

        //在setStroke 之前调用
        setShowDivider(showDivider);
        setRadius(radius);
        setViewRadius(viewRadius);
        setStroke(strokeWidth, strokeColor);
        setBackgroundColor(backgroundColor);
        setMaxNumber(maxNumber);
        setMinNumber(minNumber);
        setTextViewSize(textWidth, textHeight);


        setPlusTextSize(plusTextSize);
        setPlusTextColor(plusTextColor);
        setPlusTextberBackgroundColor(plusBackgroundColor);


        setMinusTextColor(minusTextColor);
        setMinusTextSize(minusTextSize);
        setMinusTextberBackgroundColor(minusBackgroundColor);

        setNumber(number);
        setNumberTextColor(numberTextColor);
        setNumberTextSize(numberTextSize);
        setNumberBackgroundColor(numberBackgroundColor);
        setNumberRadius(numberRadius);
        setNumberSzie(numberWidth, numberHeight);

    }


    private void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        helper = new GradientDrawableHelper();
        setBackground(helper.getDrawable());
        numberTV = new VariedTextView(context);
        numberTV.setGravity(Gravity.CENTER);
        numberPlus = new VariedTextView(context);
        numberPlus.setGravity(Gravity.CENTER);
        numberPlus.setText("+");
        numberMinus = new VariedTextView(context);
        numberMinus.setGravity(Gravity.CENTER);
        numberMinus.setText("—");

        addView(numberMinus);
        addView(numberTV);
        addView(numberPlus);
        setListener();

    }

    public void setNumberListener(OnNumberListener numberListener) {
        this.numberListener = numberListener;
    }


    public void setMaxToast(String toast) {
        this.toast = toast;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        helper.setBackgroundColor(backgroundColor);
    }


    public void setRadius(int radius) {
        this.radius = radius;
        helper.setRadius(radius);
        numberMinus.setRadius(radius, 0, 0, radius);
        numberPlus.setRadius(radius, 0, 0, radius);
    }

    public void setMinusTextberBackgroundColor(int minusBackgroundColor) {
        this.minusBackgroundColor = minusBackgroundColor;
        numberMinus.setBackgroundColor(minusBackgroundColor);
    }

    public void setPlusTextberBackgroundColor(int plusBackgroundColor) {
        this.plusBackgroundColor = plusBackgroundColor;
        numberPlus.setBackgroundColor(plusBackgroundColor);
    }

    public void setNumberRadius(int numberRadius) {
        this.numberRadius = numberRadius;
        numberTV.setRadius(numberRadius);

    }

    public void setStroke(int strokeWidth, int strokeColor) {
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        if (strokeWidth > 0) {
            if (showDivider) {
                GradientDrawable drawable = new GradientDrawable();
                drawable.setColor(strokeColor);
                drawable.setSize(strokeWidth, getMeasuredHeight());
                setDividerDrawable(drawable);
                setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            }
            helper.setStroke(strokeWidth, strokeColor);
        }

    }

    //在setStroke 之前调用
    public void setShowDivider(boolean showDivider) {
        this.showDivider = showDivider;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }


    public void setNumberTextColor(int numberTextColor) {
        this.numberTextColor = numberTextColor;
        numberTV.setTextColor(numberTextColor);
    }

    public void setPlusTextColor(int plusTextColor) {
        this.plusTextColor = plusTextColor;
        numberPlus.setTextColor(plusTextColor);
    }

    public void setMinusTextColor(int minusTextColor) {
        this.minusTextColor = minusTextColor;
        numberMinus.setTextColor(minusTextColor);
    }

    public void setNumberBackgroundColor(int numnerBackgroundColor) {
        this.numberBackgroundColor = numnerBackgroundColor;
        numberTV.setBackgroundColor(numnerBackgroundColor);
    }


    public void setNumberSzie(int numberWidth, int numberheight) {
        this.numberWidth = numberWidth;
        this.numberHeight = numberheight;
        numberTV.setWidth(numberWidth);
        numberTV.setHeight(numberheight);
    }

    public void setTextViewSize(int textWidth, int textHeight) {
        this.textWidth = textWidth;
        this.textHeight = textHeight;
        numberPlus.setWidth(textWidth);
        numberPlus.setHeight(textHeight);

        numberMinus.setWidth(textWidth);
        numberMinus.setHeight(textHeight);
    }

    public void setViewRadius(int viewRadius) {
        numberPlus.setRadius(viewRadius);
        numberMinus.setRadius(viewRadius);
    }

    public void setNumberTextSize(int numberTextSize) {
        this.numberTextSize = numberTextSize;
        numberTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, numberTextSize);
    }

    public void setPlusTextSize(int plusTextSize) {
        this.plusTextSize = plusTextSize;
        numberPlus.setTextSize(TypedValue.COMPLEX_UNIT_PX, plusTextSize);
    }

    public void setMinusTextSize(int minusTextSize) {
        this.minusTextSize = minusTextSize;
        numberMinus.setTextSize(TypedValue.COMPLEX_UNIT_PX, minusTextSize);
    }

    public int getNumber() {
        return number;
    }


    public void setNumber(int number) {
        setNumber(number, false);
    }

    public void setNumber(int number, boolean showToas) {
        this.number = number;
        if (numberTV != null) {
            numberTV.setText(String.valueOf(number));
            numberPlus.setEnabled(true);
            numberPlus.setTextColor(plusTextColor);
            numberPlus.setBackgroundColor(plusBackgroundColor);
            numberMinus.setEnabled(true);
            numberMinus.setTextColor(minusTextColor);
            numberMinus.setBackgroundColor(minusBackgroundColor);
            if (number >= maxNumber) {
                numberPlus.setEnabled(false);
                numberPlus.setBackgroundColor(plusBackgroundColor2);
                numberPlus.setTextColor(plusTextColor2);
                if (showToas) {
                    ToastUtil.showShort(toast);
                }
            }
            if (number <= minNumber) {
                numberMinus.setEnabled(false);
                numberMinus.setBackgroundColor(minusBackgroundColor2);
                numberMinus.setTextColor(minusTextColor2);
            }
        }
    }

    private void setListener() {
        numberMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                minus();
            }
        });

        numberPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                plus();
            }
        });
    }

    private void sendNumber(int number, int index) {
        if (numberListener != null) {
            numberListener.onNumberListener(this, number, index);
        }
    }


    private void minus() {
        int num = Integer.valueOf(numberTV.getText().toString().trim());
        if (num > minNumber) {
            number = num - 1;
            setNumber(number, true);
            sendNumber(number, index);
        }
    }

    private void plus() {
        int num = Integer.valueOf(numberTV.getText().toString().trim());
        if (maxNumber != -1) {
            if (num < maxNumber) {
                number = num + 1;
                setNumber(number, true);
                sendNumber(number, index);
            }
        } else {
            number = num + 1;
            setNumber(number, true);
            sendNumber(number, index);
        }

    }


    public interface OnNumberListener {
        public void onNumberListener(NumberView view, int number, int index);
    }

}
