package com.mylibrary.api.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mylibrary.api.R;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;


/**
 * Created by myuser on 2018/1/17.
 */

public class TopSearchView extends LinearLayout {
    View view;
    View topView;
    String hint;
    String leftText;
    String rightText;
    String title;

    float leftTextSize;
    float rightTextSize;
    float titleSize;

    int leftTextColor;
    int rightTextColor;
    int titleColor;
    int hintColor;

    Drawable leftImg;
    int rightImg;
    int leftSize;
    float rightSize;
    VariedTextView leftTextView;
    TextView titleView;

    LinearLayout ringhitImgLayout;
    ImageView rightImgView;
    TextView rightTextView;
    MySearchView MySearchView;
    Context context;
    OnClickListener leftImgViewListtener;


    public TopSearchView(Context context) {
        this(context, null);
    }

    @SuppressLint("ResourceAsColor")
    public TopSearchView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.view_top_search, this);
        topView = view.findViewById(R.id.topSearch_View);
        leftTextView = view.findViewById(R.id.topSearch_leftText);
        ringhitImgLayout = view.findViewById(R.id.topSearch_rightImgLayout);
        rightImgView = view.findViewById(R.id.topSearch_rightImg);
        rightTextView = view.findViewById(R.id.topSearch_rightText);
        MySearchView = view.findViewById(R.id.topSearch_selcet);
        titleView = view.findViewById(R.id.topSearch_Title);
        if (Build.VERSION.SDK_INT > 18) {
            ViewGroup.LayoutParams params = topView.getLayoutParams();
            params.height = SystemUtil.getStatusBarHeight(context);
            params.width = SystemUtil.getScreenWidth(context);
            topView.setLayoutParams(params);
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopSearchView);

        hint = a.getString(R.styleable.TopSearchView_topSearchHins);
        title = a.getString(R.styleable.TopSearchView_topSearchTitle);
        hintColor = a.getColor(R.styleable.TopSearchView_topSearchHinsColor, Color.parseColor("#6d6d6d"));
        leftTextSize = a.getDimension(R.styleable.TopSearchView_topSearchLeftSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));
        rightTextSize = a.getDimension(R.styleable.TopSearchView_topSearchRightTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        titleSize = a.getDimension(R.styleable.TopSearchView_topSearchTitleSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);

        leftTextColor = a.getColor(R.styleable.TopSearchView_topSearchLeftTextColor, context.getResources().getColor(R.color.textColor));
        boolean leftShow = a.getBoolean(R.styleable.TopSearchView_topSearchLeftShow, true);
        rightTextColor = a.getColor(R.styleable.TopSearchView_topSearchRightTextColor, context.getResources().getColor(R.color.textColor));
        titleColor = a.getColor(R.styleable.TopSearchView_topSearchTitleColor, context.getResources().getColor(R.color.white));
        leftText = a.getString(R.styleable.TopSearchView_topSearchLeftText);
        rightText = a.getString(R.styleable.TopSearchView_topSearchRightText);
        leftImg = a.getDrawable(R.styleable.TopSearchView_topSearchLeftImg);
        Drawable searchBackground = a.getDrawable(R.styleable.TopSearchView_topSearchBackground);
        Drawable topSearchIMG = a.getDrawable(R.styleable.TopSearchView_topSearchIMG);
        rightImg = a.getResourceId(R.styleable.TopSearchView_topSearchRihtImg, -1);
        leftSize = (int) a.getDimension(R.styleable.TopSearchView_topSearchLeftSize, SystemUtil.dp2px(context, 20));
        rightSize = a.getDimension(R.styleable.TopSearchView_topSearchRihtSize, SystemUtil.dp2px(context, 20));
        int graivy = a.getInt(R.styleable.TopSearchView_topSearchHinsGraiv, 1);
        a.recycle();

        if (graivy == 0) {
            MySearchView.getEdittext().setGravity(Gravity.CENTER);
            MySearchView.getEdittext().setPadding(0, 0, SystemUtil.dp2px(context, 26), 0);
        }

        if (rightImgView != null) {
            rightImgView.getLayoutParams().width = (int) rightSize;
            rightImgView.getLayoutParams().height = (int) rightSize;
            if (rightImg != -1) {
                rightImgView.setBackgroundResource(rightImg);
                ringhitImgLayout.setVisibility(VISIBLE);
            } else {
                ringhitImgLayout.setVisibility(GONE);
            }
        }


        if (leftTextView != null) {
            if (leftShow) {
                leftTextView.setVisibility(VISIBLE);
            } else {
                leftTextView.setVisibility(GONE);
            }
            leftTextView.setText(leftText);
            leftTextView.setTextColor(leftTextColor);
            leftTextView.setDrawableLeftSize(leftSize, leftSize);
            if (leftImg == null) {
                leftImg = getResources().getDrawable(R.drawable.return_whit);
            }
            leftTextView.setLeftDrawable(leftImg);
            leftTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                    if (leftImgViewListtener != null) {
                        leftImgViewListtener.onClick(v);
                    }
                }
            });
        }
        if (rightTextView != null) {
            rightTextView.setText(rightText);
            rightTextView.setTextColor(rightTextColor);
        }
        if (MySearchView != null) {
            MySearchView.setFocusable(false);
            if (topSearchIMG != null) {
                MySearchView.setSearchDrawable(topSearchIMG);
            } else {
                MySearchView.setSearchDrawable(R.drawable.search);
            }

            MySearchView.setQueryHint(hint);
            MySearchView.setQueryHintColor(hintColor);
            if (searchBackground != null) {
                MySearchView.setBackground(searchBackground);
            }
        }


    }

    public TopSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageResource(int id) {
        MySearchView.setSearchDrawable(id);
    }


    public MySearchView getMySearchView() {
        return MySearchView;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
        leftTextView.setText(leftText);
    }


    public void setRightText(CharSequence rightText) {
        this.rightText = rightText.toString();
        if (StringUtil.isNotEmpty(rightText)) {
            rightTextView.setText(rightText);
            rightTextView.setVisibility(VISIBLE);
        } else {
            rightTextView.setVisibility(GONE);
        }

    }


    public TextView getRightTextView() {
        return rightTextView;
    }


    public void setLeftTextSize(float leftTextSize) {
        this.leftTextSize = leftTextSize;
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, leftTextSize);
    }


    public void setRightTextSize(float rightTextSize) {
        this.rightTextSize = rightTextSize;
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, rightTextSize);
    }


    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        leftTextView.setTextColor(leftTextColor);
    }


    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        rightTextView.setTextColor(rightTextColor);
    }


    public void setLeftImg(int imhID) {
        leftTextView.setLeftDrawable(imhID);
    }


    public void setRightImg(int rightImg) {
        this.rightImg = rightImg;
        if (rightImg == -1) {
            rightImgView.setVisibility(GONE);

        } else {
            rightImgView.setVisibility(VISIBLE);
            rightImgView.setBackgroundResource(rightImg);
        }

    }


    public void setLeftSize(int leftSize) {
        this.leftSize = leftSize;
        leftTextView.setDrawableLeftSize(leftSize, leftSize);
    }


    public void setRightSize(int rightSize) {
        this.rightSize = rightSize;
        rightImgView.getLayoutParams().width = (int) rightSize;
        rightImgView.getLayoutParams().height = (int) rightSize;
    }

    public void setLeftImgViewListener(OnClickListener leftImgViewListtener) {
        if (leftImgViewListtener != null) {
            this.leftImgViewListtener = leftImgViewListtener;
            leftTextView.setOnClickListener(leftImgViewListtener);
        }

    }

    public void setMySearchViewListener(OnClickListener listener) {
        if (listener != null)
            MySearchView.setListener(listener);

    }

    public void setSelectHint(String str) {
        if (MySearchView != null)
            MySearchView.setQueryHint(str);

    }

    public String getSelectHint() {
        if (MySearchView != null) {
            return MySearchView.getQueryHint().toString();
        }


        return "";
    }


    public void setSelectHintColor(int color) {
        if (MySearchView != null)
            MySearchView.setQueryHintColor(color);

    }

    public void setRightImgViewListener(OnClickListener rightImgViewListener) {
        if (ringhitImgLayout != null) {
            if (rightImgViewListener != null) {
                ringhitImgLayout.setOnClickListener(rightImgViewListener);
            }
        }

    }

    public void setRightTextListener(OnClickListener rightImgViewListener) {

        if (rightTextView != null) {
            if (rightImgViewListener != null) {
                rightTextView.setOnClickListener(rightImgViewListener);
            }
        }
    }

    public void setTitle(String title) {
        if (StringUtil.isNotEmpty(title)) {
            titleView.setText(title);
            titleView.setVisibility(VISIBLE);
            MySearchView.setVisibility(INVISIBLE);
        } else {
            titleView.setVisibility(GONE);
            MySearchView.setVisibility(VISIBLE);
        }
    }

    public void setShowRight(boolean isShow) {
        if (isShow) {
            ringhitImgLayout.setVisibility(VISIBLE);
            rightTextView.setVisibility(VISIBLE);
        } else {
            ringhitImgLayout.setVisibility(INVISIBLE);
            rightTextView.setVisibility(GONE);
        }
    }


}
