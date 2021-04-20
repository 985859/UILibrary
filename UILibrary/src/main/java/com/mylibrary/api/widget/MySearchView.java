package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;

import com.mylibrary.api.R;
import com.mylibrary.api.utils.SystemUtil;

public class MySearchView extends SearchView {
    private Context context;
    private boolean iconifiedByDefault;
    private Drawable closeDrawable;
    private Drawable searchDrawable;
    private Drawable queryBackground;
    private int closeWidth;
    private int closeHeight;
    private int searchWidth;
    private int searchHeight;
    private int queryHintColor;
    private int textSize;
    private int textColor;
    private ImageView mCollapsedIcon;// iconifiedByDefault="false" 搜索按钮
    private ImageView mCloseButton;//删除按钮
    private ImageView mSearchButton;//iconifiedByDefault="true"  搜索按钮
    private AppCompatAutoCompleteTextView searchText;//
    private LinearLayout linearLayout;
    private View mSearchPlate;//输入框的背景

    public MySearchView(Context context) {
        this(context, null);
    }

    public MySearchView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.searchViewStyle);
    }

    public MySearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        int defaultSize = SystemUtil.dp2px(context, 16);
        mSearchButton = findViewById(R.id.search_button);
        mCloseButton = findViewById(R.id.search_close_btn);
        mCollapsedIcon = findViewById(R.id.search_mag_icon);
        searchText = findViewById(R.id.search_src_text);
        linearLayout = findViewById(R.id.search_edit_frame);
        mSearchPlate = findViewById(R.id.search_plate);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MySearchView);
        closeDrawable = array.getDrawable(R.styleable.MySearchView_closeIcon);

        closeWidth = (int) array.getDimension(R.styleable.MySearchView_closeIconWidth, defaultSize);
        closeHeight = (int) array.getDimension(R.styleable.MySearchView_closeIconHeight, defaultSize);

        searchDrawable = array.getDrawable(R.styleable.MySearchView_searchIcon);
        searchWidth = (int) array.getDimension(R.styleable.MySearchView_searchIconWidth, defaultSize);
        searchHeight = (int) array.getDimension(R.styleable.MySearchView_searchIconHeight, defaultSize);

        queryHintColor = array.getColor(R.styleable.MySearchView_queryHintColor, Color.parseColor("#8b8b8b"));
        textColor = array.getColor(R.styleable.MySearchView_textColor, Color.parseColor("#333333"));
        textSize = (int) array.getDimension(R.styleable.MySearchView_textSize, 14);

        queryBackground = array.getDrawable(R.styleable.MySearchView_queryBackground);
        iconifiedByDefault = array.getBoolean(R.styleable.MySearchView_iconifiedByDefault, false);

        array.recycle();

        if (linearLayout != null) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            params.leftMargin = SystemUtil.dp2px(context, 3);
        }
        setCloseSize(closeWidth, closeHeight);
        setCloseDrawable(closeDrawable);
        setSearchSize(searchWidth, searchHeight);
        setSearchDrawable(searchDrawable);
        setQueryHintColor(queryHintColor);
        setTextColor(textColor);
        setTextSize(textSize);
        setIconifiedByDefault(iconifiedByDefault);
        searchText.setFocusable(false);
        if (queryBackground != null) {
            ViewCompat.setBackground(mSearchPlate, queryBackground);
        } else {
            ViewCompat.setBackground(mSearchPlate, new ColorDrawable(0x00000000));
        }

    }


    public void setCloseDrawable(int closeDrawable) {
        setCloseDrawable(AppCompatResources.getDrawable(context, closeDrawable));
    }

    public void setSearchDrawable(int searchDrawable) {
        setSearchDrawable(AppCompatResources.getDrawable(context, searchDrawable));
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        searchText.setOnClickListener(l);
        super.setOnClickListener(l);
    }

    public void setCloseDrawable(Drawable closeDrawable) {
        this.closeDrawable = closeDrawable;
        if (closeDrawable != null && mCloseButton != null) {
            closeDrawable.setBounds(0, 0, closeWidth, closeHeight);
            mCloseButton.setImageDrawable(closeDrawable);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mCloseButton.getLayoutParams();
            params.width = closeWidth;
            params.height = closeWidth;
            params.rightMargin = SystemUtil.dp2px(context, 3);
            mCloseButton.setPadding(4, 2, 2, 0);
            mCloseButton.setLayoutParams(params);
        }
    }

    public void setSearchDrawable(Drawable searchDrawable) {
        this.searchDrawable = searchDrawable;
        if (searchDrawable != null) {
            searchDrawable.setBounds(0, 0, searchWidth, searchHeight);
            if (mSearchButton != null) {
                mSearchButton.setImageDrawable(searchDrawable);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mSearchButton.getLayoutParams();
                params.width = searchWidth;
                params.height = searchHeight;
                mSearchButton.setLayoutParams(params);
            }
            if (mCollapsedIcon != null) {
                mCollapsedIcon.setImageDrawable(searchDrawable);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mCollapsedIcon.getLayoutParams();
                params.width = searchWidth;
                params.height = searchHeight;
                mCollapsedIcon.setLayoutParams(params);
            }
        }

    }


    /**
     * 设置大小 要在设置图标之前设置
     **/
    public void setCloseSize(int closeWidth, int closeHeight) {
        this.closeWidth = closeWidth;
        this.closeHeight = closeHeight;
    }

    /**
     * 设置大小 要在设置图标之前设置
     **/
    public void setSearchSize(int searchWidth, int searchHeight) {
        this.searchWidth = searchWidth;
        this.searchHeight = searchHeight;
    }


    public void setQueryHintColor(int queryHintColor) {
        this.queryHintColor = queryHintColor;
        if (searchText != null) {
            searchText.setHintTextColor(queryHintColor);
        }
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        if (searchText != null) {
            searchText.setTextSize(textSize);
        }
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if (searchText != null) {
            searchText.setTextColor(textColor);
        }
    }


    public void setListener(OnClickListener listener) {
        if (listener != null) {
            searchText.setOnClickListener(listener);
            mCollapsedIcon.setOnClickListener(listener);
        }

    }

    public TextView getEdittext() {
        return searchText;
    }

    public void setEditFocusable(boolean focusable) {
        searchText.setFocusable(focusable);
        searchText.setFocusableInTouchMode(focusable);
        searchText.requestFocus();

    }

    /**
     * 搜索框内 搜索图标监听
     **/
    public void setSearchCollapsedListener(OnClickListener listener) {
        if (listener != null && mCollapsedIcon != null) {
            mCollapsedIcon.setOnClickListener(listener);
        }
    }
}
