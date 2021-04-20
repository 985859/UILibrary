package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.adapters.ListenerUtil;
import androidx.databinding.adapters.TextViewBindingAdapter;

import com.mylibrary.api.R;
import com.mylibrary.api.databinding.ViewEditBinding;
import com.mylibrary.api.utils.SpannableUtil;
import com.mylibrary.api.utils.SystemUtil;


public class EditView extends ViewGroup {
    private ViewEditBinding binding;
    private boolean edit;
    private int editColor;
    private int editRadius;
    private String title;

    private int titleSize;
    private int titleColor;

    private String text;

    private int textSize;
    private int textColor;
    private int textMarginTop;
    private int textPadding;

    private String hint;
    private int must;
    private int maxLength;
    private int maxHeight = -1;
    private int numberSize;
    private int numberColor;
    private boolean showNumber;
    private TextView numberView;
    private View view;
    int p;

    public EditView(Context context) {
        this(context, null);
    }

    public EditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    Paint paint;

    public EditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        p = SystemUtil.px2dp(context, 2);
        view = inflate(context, R.layout.view_edit, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(view, params);
        view.setTag("layout/view_edit_0");
        binding = DataBindingUtil.bind(view);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditView);
        must = array.getInt(R.styleable.EditView_edit_Must, 0);
        title = array.getString(R.styleable.EditView_edit_Title);
        hint = array.getString(R.styleable.EditView_edit_Hint);
        text = array.getString(R.styleable.EditView_edit_Text);
        edit = array.getBoolean(R.styleable.EditView_edit_Edit, true);
        boolean singLine = array.getBoolean(R.styleable.EditView_edit_SingleLine, true);

        titleSize = array.getDimensionPixelSize(R.styleable.EditView_edit_TitleSize, -1);
        titleColor = array.getColor(R.styleable.EditView_edit_TitleColor, ContextCompat.getColor(context, R.color.blue));

        textSize = array.getDimensionPixelSize(R.styleable.EditView_edit_TextSize, -1);
        textColor = array.getColor(R.styleable.EditView_edit_TextColor, ContextCompat.getColor(context, R.color.textColor));
        int inputType = array.getInt(R.styleable.EditView_android_inputType, EditorInfo.TYPE_NULL);
        maxLength = array.getInteger(R.styleable.EditView_android_maxLength, -1);
        editRadius = array.getDimensionPixelSize(R.styleable.EditView_edit_Radius, 0);
        editColor = array.getColor(R.styleable.EditView_edit_Color, -1);
        showNumber = array.getBoolean(R.styleable.EditView_edit_ShowNumber, false);
        numberColor = array.getColor(R.styleable.EditView_edit_NumberColor, Color.parseColor("#8b8b8b"));
        numberSize = array.getDimensionPixelSize(R.styleable.EditView_edit_NumberSize, 24);
        textMarginTop = array.getDimensionPixelSize(R.styleable.EditView_edit_TextMarginTop, SystemUtil.dp2px(getContext(), 6));
        textPadding = array.getDimensionPixelSize(R.styleable.EditView_edit_Padding, 0);
        array.recycle();
        if (inputType != EditorInfo.TYPE_NULL) {
            binding.viewEditText.setInputType(inputType);
        }
        binding.viewEditText.setSingleLine(singLine);
        setTitle(title);
        setTitleColor(titleColor);
        setTitleSize(titleSize);
        setTextMarginTop(textMarginTop);
        setHint(hint);
        setText(text);
        setMaxLength(maxLength);
        setTextSize(textSize);
        setTextColor(textColor);
        setEdit(edit);
        setEditColor(editColor);
        setShowNumber(showNumber);
        if (!showNumber) {
            setTextPadding(textPadding);
        }
        if (!isInEditMode()) {

            binding.viewEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int length = editable.length();
                    if (maxLength != -1) {
                        if (length >= maxLength) {
                            length = maxLength;
                        }
                        if (numberView != null) {
                            numberView.setText(length + "/" + maxLength);
                        }

                    }
                }
            });
        }
    }

    public void setLength(int length) {
        if (maxLength != -1) {
            if (length >= maxLength) {
                length = maxLength;
            }
            binding.viewEditTitle.setText(length + "/" + maxLength);
        }
    }

    public void setEditRadius(int editRadius) {
        this.editRadius = editRadius;
    }

    public void setTextMarginTop(int textMarginTop) {
        this.textMarginTop = textMarginTop;
        binding.viewEditTitle.setPadding(0, 0, 0, textMarginTop);
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setTextPadding(int textPadding) {
        this.textPadding = textPadding;
        if (numberView == null) {
            binding.viewEditText.setPadding(textPadding, textPadding - p, textPadding, textPadding - p);
        } else {
            binding.viewEditText.setPadding(textPadding, textPadding, textPadding, 0);
        }
    }

    public void setEditColor(int editColor) {
        this.editColor = editColor;
        if (editColor != -1) {
            paint = new Paint();
            paint.setColor(editColor); //设置画笔颜色
            paint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
            paint.setStrokeWidth(10f);         //设置画笔宽度为10px
        }
    }

    public void setMust(int must) {
        this.must = must;
        setTitle(title);
    }


    public void setTitle(String title) {
        this.title = title;
        if (must == 1) {
            binding.viewEditTitle.setText(SpannableUtil.setMustTitle(title));
        } else {
            binding.viewEditTitle.setText(title);
        }
    }


    public void setHint(String hint) {
        this.hint = hint;
        binding.viewEditText.setHint(hint);
    }

    public void setText(String text) {
        this.text = text;
        binding.viewEditText.setText(text);
    }

    public String getText() {
        return binding.viewEditText.getText().toString();
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
        binding.viewEditText.setEnabled(edit);
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        if (maxLength != -1) {
            InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
            binding.viewEditText.setFilters(filters);
        }

    }

    public EditText getEditText() {
        return binding.viewEditText;
    }

    public TextView getTitleView() {
        return binding.viewEditTitle;
    }

    public void setTitleSize(int titleSize) {
        if (titleSize == -1) {
            return;
        }
        this.titleSize = titleSize;
        binding.viewEditTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        binding.viewEditTitle.setTextColor(titleColor);
    }

    public void setTextSize(int textSize) {
        if (textSize == -1) {
            return;
        }
        this.textSize = textSize;
        binding.viewEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        binding.viewEditText.setTextColor(textColor);
    }

    public String getHint() {
        return hint;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (editColor != -1 && paint != null) {
            canvas.drawPath(getPath(editRadius, true, true, true, true), paint);
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        //获取子view的个数
        int cCount = getChildCount();
        // 遍历每个子元素
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到child的lp
            LayoutParams lp = child.getLayoutParams();

            int lrMargin = 0;
            int tbMargin = 0;
            if (lp != null && lp instanceof MarginLayoutParams) {
                MarginLayoutParams params = (MarginLayoutParams) lp;
                lrMargin = params.leftMargin + params.rightMargin;
                tbMargin = params.topMargin + params.bottomMargin;

            }
            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + lrMargin;

            if (childWidth > getMeasuredWidth()) {
                width = getMeasuredWidth() - lrMargin;
                child.getLayoutParams().width = width;
            } else {
                if (width < childWidth) {
                    width = childWidth;
                }
            }
            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + tbMargin;
            height += childHeight;
        }

        //设置最小高度
        if (height < getMinimumHeight()) {
            height = getMinimumHeight();
        }
        int h = (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height;
        //设置最大高度
        if (maxHeight != -1 && h > maxHeight) {
            h = maxHeight;
        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, h);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = getPaddingTop();
        view.layout(getPaddingStart(), top, view.getMeasuredWidth(), view.getMeasuredHeight());
        top = view.getMeasuredHeight();
        if (numberView != null) {
            numberView.layout(getPaddingStart(), top, numberView.getMeasuredWidth(), top + numberView.getMeasuredHeight());
        }
    }

    private Path getPath(float radius, boolean topLeft, boolean topRight, boolean bottomRight, boolean bottomLeft) {
        final Path path = new Path();
        final float[] radii = new float[8];
        if (topLeft) {
            radii[0] = radius;
            radii[1] = radius;
        }

        if (topRight) {
            radii[2] = radius;
            radii[3] = radius;
        }

        if (bottomRight) {
            radii[4] = radius;
            radii[5] = radius;
        }

        if (bottomLeft) {
            radii[6] = radius;
            radii[7] = radius;
        }
        int l = getPaddingStart();
        int r = getWidth() - getPaddingEnd();
        int t = getPaddingTop() + binding.viewEditTitle.getHeight();
        int b = getHeight() - getPaddingBottom();
        path.addRoundRect(new RectF(l, t, r, b), radii, Path.Direction.CW);

        return path;
    }


    public void setShowNumber(boolean showNumber) {
        this.showNumber = showNumber;
        if (showNumber) {
            if (numberView != null) {
                numberView.setVisibility(VISIBLE);
            } else {
                addNumberView();
            }
        } else {
            if (numberView != null) {
                numberView.setVisibility(GONE);
            }
        }
    }

    public void setNumberSize(int numberSize) {
        this.numberSize = numberSize;
        if (numberView != null) {
            numberView.setTextSize(TypedValue.COMPLEX_UNIT_PX, numberSize);
        }
    }

    public void setNumberColor(int numberColor) {
        this.numberColor = numberColor;
        if (numberView != null) {
            numberView.setTextColor(numberColor);
        }
    }


    private void addNumberView() {
        if (numberView == null) {
            numberView = new TextView(getContext());
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            setNumberColor(numberColor);
            setNumberSize(numberSize);
            int s = 0;
            if (binding.viewEditText.getText() != null) {
                s = binding.viewEditText.getText().toString().length();
            }
            numberView.setText(s + "/" + maxLength);
            numberView.setPadding(textPadding, 0, textPadding, textPadding - p);
            setTextPadding(textPadding);
            numberView.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            addView(numberView, params);
        }
    }

    @BindingAdapter("app:edit_Title")
    public static void setMenuTitle(EditView view, String title) {
        view.setTitle(title);
    }

    @BindingAdapter("app:edit_Hint")
    public static void setMenuHint(EditView view, String hint) {
        view.setHint(hint);
    }

    @BindingAdapter("app:edit_Edit")
    public static void setEdit(EditView view, boolean edit) {
        view.setEdit(edit);
    }

    @BindingAdapter("app:edit_Text")
    public static void setText(EditView editView, String text) {
        if (editView != null) {
            String edTextString = editView.getText() == null ? "" : editView.getText();
            text = text == null ? "" : text;
            if (edTextString.equalsIgnoreCase(text)) {
                return;
            }
            editView.setText(text);
        }
    }

    @InverseBindingAdapter(attribute = "app:edit_Text", event = "textAttrChanged")
    public static String getValue(EditView view) {
        return view.getText();
    }

    @BindingAdapter(
            value = {"android:beforeTextChanged",
                    "android:onTextChanged",
                    "android:afterTextChanged",
                    "textAttrChanged"},
            requireAll = false)
    public static void setTextWatcher(EditView view,
                                      final TextViewBindingAdapter.BeforeTextChanged before,
                                      final TextViewBindingAdapter.OnTextChanged on,
                                      final TextViewBindingAdapter.AfterTextChanged after,
                                      final InverseBindingListener valueAttrChanged) {
        TextWatcher newWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (before != null) {
                    before.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (on != null) {
                    on.onTextChanged(s, start, before, count);
                }
                if (valueAttrChanged != null) {
                    valueAttrChanged.onChange();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (after != null) {
                    after.afterTextChanged(s);
                }

            }
        };

        TextWatcher oldValue = ListenerUtil.trackListener(view, newWatcher, R.id.textWatcher);
        if (oldValue != null) {
            view.getEditText().removeTextChangedListener(oldValue);
        }
        view.getEditText().addTextChangedListener(newWatcher);
    }


}

