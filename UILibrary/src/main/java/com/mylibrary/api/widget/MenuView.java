package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.mylibrary.api.R;
import com.mylibrary.api.databinding.ViewMenuBinding;
import com.mylibrary.api.utils.SpannableUtil;


public class MenuView extends LinearLayout {
    private ViewMenuBinding binding;
    private String title;
    private String hint;
    private String content;
    private boolean showPic;
    private boolean showIcon;
    private int must;
    private Drawable icon;
    private Drawable pic;
    private Context context;
    int titleSize;
    int contentSize;

    private ColorStateList titleColor;
    private ColorStateList contentColor;


    public MenuView(Context context) {
        this(context, null);
    }


    public MenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public MenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.view_menu, null);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setTag("layout/view_menu_0");
        if (!isInEditMode()) {
            binding = DataBindingUtil.bind(view);
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MenuView);
            title = array.getString(R.styleable.MenuView_menu_Title);
            hint = array.getString(R.styleable.MenuView_menu_Hint);
            content = array.getString(R.styleable.MenuView_menu_content);
            icon = array.getDrawable(R.styleable.MenuView_menu_Icon);
            pic = array.getDrawable(R.styleable.MenuView_menu_Pic);
            titleSize = array.getDimensionPixelSize(R.styleable.MenuView_menu_TitleSize, 16);
            contentSize = array.getDimensionPixelSize(R.styleable.MenuView_menu_contentSize, 14);
            int line = array.getInt(R.styleable.MenuView_menu_contentMaxLine, 0);

            titleColor = array.getColorStateList(R.styleable.MenuView_menu_TitleColor);
            contentColor = array.getColorStateList(R.styleable.MenuView_menu_contentColor);

            showPic = array.getBoolean(R.styleable.MenuView_menu_showPic, false);
            showIcon = array.getBoolean(R.styleable.MenuView_menu_showIcon, true);

            contentColor = array.getColorStateList(R.styleable.MenuView_menu_contentColor);

            must = array.getInt(R.styleable.MenuView_menu_Must, 0);
            array.recycle();
            if (icon == null) {
                icon = context.getDrawable(R.drawable.more);
            }
            setMust(must);
            setTitle(title);
            setHint(hint);
            setContent(content);
            setIcon(icon);
            setPic(pic);
            setContentSize(contentSize);
            setTitleSize(titleSize);
            setShowIcon(showIcon);
            setShowPic(showPic);
            setContentMaxLine(line);
            if (titleColor != null) {
                binding.menuTitle.setTextColor(titleColor);
            }
            if (contentColor != null) {
                binding.menuContent.setTextColor(contentColor);
            }
        }
    }


    public void setTitle(String title) {
        this.title = title;
        if (must == 1) {
            binding.menuTitle.setText(SpannableUtil.setMustTitle(title));
        } else {
            binding.menuTitle.setText(title);
        }

    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
        binding.menuTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = ColorStateList.valueOf(titleColor);
        binding.menuContent.setTextColor(this.titleColor);
    }

    public void setHint(String hint) {
        this.hint = hint;
        binding.menuContent.setHint(hint);
    }

    public void setContent(String content) {
        this.content = content;
        binding.menuContent.setText(content);
    }

    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
        binding.menuContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentSize);
    }

    public void setContentColor(int contentColor) {
        this.contentColor = ColorStateList.valueOf(contentColor);
        binding.menuContent.setTextColor(this.contentColor);
    }

    public void setMust(int must) {
        this.must = must;
    }

    @BindingAdapter("menu_Title")
    public static void setMenuTitle(MenuView view, String title) {
        view.setTitle(title);
    }

    @BindingAdapter("menu_content")
    public static void setMenuContent(MenuView view, String content) {
        view.setContent(content);
    }

    @BindingAdapter("menu_Hint")
    public static void setMenuHint(MenuView view, String hint) {
        view.setHint(hint);
    }

    @BindingAdapter("menu_Icon")
    public static void setIcon(MenuView view, Drawable icon) {
        view.setIcon(icon);
    }

    @BindingAdapter("menu_Pic")
    public static void setPic(MenuView view, Drawable pic) {
        view.setPic(pic);
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
        binding.menuIcon.setImageDrawable(icon);
    }

    public void setIcon(int icon) {
        if (icon != -1) {
            this.icon = AppCompatResources.getDrawable(context, icon);
            setIcon(this.icon);
        }
    }

    public void setPic(Drawable pic) {
        this.pic = icon;
        binding.menuPic.setImageDrawable(pic);
    }

    public void setPic(int pic) {
        if (pic != -1) {
            this.icon = AppCompatResources.getDrawable(context, pic);
            setIcon(this.pic);
        }
    }

    public void setShowPic(boolean showPic) {
        this.showPic = showPic;
        if (showPic) {
            binding.menuPic.setVisibility(VISIBLE);
        } else {
            binding.menuPic.setVisibility(GONE);
        }

    }

    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
        if (showIcon) {
            binding.menuIcon.setVisibility(VISIBLE);
        } else {
            binding.menuIcon.setVisibility(GONE);
        }
    }

    public TextView getMenuTitle() {
        return binding.menuTitle;
    }

    public TextView getMenuContent() {
        return binding.menuContent;
    }

    public ImageView getMenuIcon() {
        return binding.menuIcon;
    }

    public void setContentMaxLine(int i) {
        if (i > 0) {
            binding.menuContent.setMaxLines(i);
        }
    }
}

