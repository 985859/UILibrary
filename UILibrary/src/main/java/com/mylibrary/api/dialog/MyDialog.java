package com.mylibrary.api.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mylibrary.api.R;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.widget.VariedTextView;

/**
 * Created by myuser on 2017/11/25.
 * 自定对话框
 */

public class MyDialog extends Dialog implements View.OnClickListener {
    public TextView titleText;
    public TextView contentText;
    private VariedTextView positiveButton;
    private VariedTextView negativeButton;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private LinearLayout imgLayou;
    private ImageView contentIMG;
    private int imgID = -1;
    private int contentTextSize = 12;
    private int contentTextColor = Color.parseColor("#333333");
    public MyDialog(@NonNull Context context) {
        //默认的样式
        this(context, R.style.dialog);
    }
    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public MyDialog(Context context, int themeResId, String content) {
        this(context, themeResId, content, null);
    }

    public MyDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);

        this.content = content;
        this.listener = listener;
    }
    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MyDialog setTitle(String title) {
        this.title = title;
        if (StringUtil.isNotEmpty(title)) {
            if (titleText != null) {
                titleText.setText(title);
            }
        }
        return this;
    }

    public MyDialog setPositiveButton(String name) {
        this.positiveName = name;
        if (positiveButton != null) {
            if (StringUtil.isNotEmpty(positiveName)) {
                positiveButton.setText(positiveName);
                positiveButton.setVisibility(View.VISIBLE);
            } else {
                positiveButton.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public MyDialog setNegativeButton(String name) {
        this.negativeName = name;
        if (negativeButton != null) {
            if (StringUtil.isNotEmpty(name)) {
                negativeButton.setVisibility(View.VISIBLE);
                negativeButton.setText(name);
            } else {
                negativeButton.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public MyDialog setNegativeButtonBackgrontColor(int color) {
        if (negativeButton != null) {
            negativeButton.setBackgroundColor(color);
        }
        return this;
    }

    public MyDialog setPositiveButtonBackgrontColor(int color) {
        if (positiveButton != null) {
            positiveButton.setBackgroundColor(color);
        }
        return this;
    }

    public MyDialog setNegativeButtonTextColor(int color) {
        if (negativeButton != null) {
            negativeButton.setTextColor(color);
        }
        return this;
    }

    public MyDialog setNegativeButtonRaius(float bottomRightRadius, float bottomLeftRadius) {
        if (negativeButton != null) {
            negativeButton.setRadius(0, 0, bottomRightRadius, bottomLeftRadius);
        }
        return this;
    }

    public MyDialog setPositiveButtonRaius(float bottomRightRadius, float bottomLeftRadius) {
        if (positiveButton != null) {
            positiveButton.setRadius(0, 0, bottomRightRadius, bottomLeftRadius);
        }
        return this;
    }

    public MyDialog setPositiveButtonTextColor(int color) {
        if (positiveButton != null) {
            positiveButton.setTextColor(color);
        }
        return this;
    }

    public MyDialog setContent(String content) {
        this.content = content;
        if (StringUtil.isNotEmpty(content)) {
            if (contentText != null) {
                contentText.setText(content);
            }
        }
        return this;
    }
    public MyDialog setContentImg(int imgID) {
        this.imgID = imgID;
        if (contentIMG != null) {
            if (imgID != -1) {
                contentIMG.setBackgroundResource(imgID);
                imgLayou.setVisibility(View.VISIBLE);
                titleText.setVisibility(View.GONE);
            } else {
                imgLayou.setVisibility(View.GONE);
                titleText.setVisibility(View.VISIBLE);
            }
        }
        return this;
    }
    public MyDialog setListener(OnCloseListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        setCanceledOnTouchOutside(false);
        initView();
    }


    private void initView() {
        contentText = findViewById(R.id.dialog_Content);
        titleText = findViewById(R.id.dialog_Title);
        positiveButton = findViewById(R.id.dialog_Affrim);
        imgLayou = findViewById(R.id.dialog_IMGLayout);
        contentIMG = findViewById(R.id.dialog_IMG);
        positiveButton.setOnClickListener(this);
        negativeButton = findViewById(R.id.dialog_Cancel);
        negativeButton.setOnClickListener(this);
        contentText.setText(content);
        setPositiveButton(positiveName);
        setNegativeButton(negativeName);
        if (!TextUtils.isEmpty(title)) {
            titleText.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.dialog_Cancel) {
            if (listener != null) {
                listener.onClick(this, false);
            }
        } else if (i == R.id.dialog_Affrim) {
            if (listener != null) {
                listener.onClick(this, true);
            }

        }
        dismiss();
    }


    public void setContentTextSize(int contentTextSize) {
        this.contentTextSize = contentTextSize;
        if (contentText != null)
            contentText.setTextSize(contentTextSize);
    }
    public void setContentTextColor(int contentTextColor) {
        this.contentTextColor = contentTextColor;
        if (contentText != null)
            contentText.setTextColor(contentTextColor);
    }
    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }

    public MyDialog setCancelables(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }


}
