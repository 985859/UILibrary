package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mylibrary.api.R;
import com.mylibrary.api.interfaces.SpinnerData;
import com.mylibrary.api.utils.SystemUtil;


/**
 * 自定义 下拉控件
 * Created by myuser on 2017/11/18.
 */

public class MySpinnerView extends LinearLayout {
    private Context context;
    private TextView textView;
    private MyPopupWindow popupWindow;
    private RecyclerView listView;
    private GravityModel mMode;
    private BaseQuickAdapter adapter;
    private int selcetIndex = -1;
    private int screenHeight;
    private int screenWidth;
    private float textSize;
    private int textColer;
    private int checkColer;
    private int iconPadding;
    private CharSequence text;
    private CharSequence checkText;
    private Drawable icon;
    private Drawable checkIcon;
    private int xoff = 0;
    private boolean isFirst;//是否对第一项处理
    private OnItemClickListener itemClickListener;
    private OnItemClickListener onItemClickListener;
    private int iconWidth;
    private int iconHeight;
    private boolean showFull = true;
    private TextView bottomView;

    public MySpinnerView(Context context) {
        this(context, null);
    }

    public MySpinnerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MySpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        // 获取屏幕的高宽
        screenHeight = SystemUtil.getScreenHeight(context);
        screenWidth = SystemUtil.getScreenWidth(context);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MySpinnerView);
        textColer = array.getColor(R.styleable.MySpinnerView_Spinner_textColor, context.getResources().getColor(R.color.textColor));
        checkColer = array.getColor(R.styleable.MySpinnerView_Spinner_checkTextColor, Color.parseColor("#f01b05"));
        showFull = array.getBoolean(R.styleable.MySpinnerView_Spinner_showFull, true);
        icon = array.getDrawable(R.styleable.MySpinnerView_Spinner_Icon);
        checkIcon = array.getDrawable(R.styleable.MySpinnerView_Spinner_checkIcon);
        text = array.getString(R.styleable.MySpinnerView_Spinner_text);
        isFirst = array.getBoolean(R.styleable.MySpinnerView_Spinner_First, false);
        textSize = array.getDimension(R.styleable.MySpinnerView_Spinner_textSize, 12);
        if (textSize != 12) {
            textSize = SystemUtil.px2sp(context, textSize);
        }
        iconPadding = (int) array.getDimension(R.styleable.MySpinnerView_Spinner_IconPadding, 10);
        int ordinal = array.getInt(R.styleable.MySpinnerView_Spinner_gravity_mode, GravityModel.CENTER.ordinal());
        mMode = GravityModel.CENTER.values()[ordinal];
        int dp6 = SystemUtil.dp2px(context, 8);
        iconWidth = (int) array.getDimension(R.styleable.MySpinnerView_Spinner_IconWidth, dp6);
        iconHeight = (int) array.getDimension(R.styleable.MySpinnerView_Spinner_IconHeight, dp6);
        array.recycle();
        if (icon == null) {
            icon = ContextCompat.getDrawable(context, R.drawable.down_up);
        }
        if (checkIcon == null) {
            checkIcon = ContextCompat.getDrawable(context, R.drawable.down_red);
        }
        setIconSize(iconWidth, iconHeight);
        setGravityMeodel(mMode);
        initView();
        initPopupWindow();

    }


    private void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        MarginLayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView = new TextView(context);
        textView.setTextSize(textSize);
        textView.setTextColor(textColer);
        textView.setText(text);
        textView.setMaxLines(1);
        textView.setCompoundDrawablePadding(iconPadding);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setMinHeight(SystemUtil.dp2px(context, 16));
        textView.setCompoundDrawablesRelative(null, null, icon, null);

        addView(textView, params);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        if (showFull) {
                            popupWindow.showAsDropDown(MySpinnerView.this, xoff, 0);
                        } else {
                            bottomView.setAlpha(0);
                            popupWindow.showAsDropDown(MySpinnerView.this, xoff, 0);
                        }

                    }
                }
            }
        });
        itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> baseAdapter, @NonNull View view, int position) {
                if (getAdapter() == null) {
                    return;
                }
                setCheckIndex(position);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(adapter, view, position);
                }
                dimiss();
            }
        };
    }


    private void initPopupWindow() {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams1.height = screenHeight;
        layoutParams1.width = screenWidth;
        layout.setLayoutParams(layoutParams1);
        listView = new RecyclerView(context);
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setBackgroundResource(R.color.white);
        LayoutParams layoutParams2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams2.height = screenHeight / 3;
        layoutParams2.width = screenWidth;
        listView.setLayoutParams(layoutParams2);
        layout.addView(listView);
        bottomView = new TextView(context);
        LayoutParams layoutParams3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams3.height = screenHeight * 2 / 3 + SystemUtil.getNavigationBarHeight(context);
        layoutParams3.width = screenWidth;
        bottomView.setBackgroundColor(Color.BLACK);
        bottomView.setLayoutParams(layoutParams3);
        bottomView.setAlpha((float) 0.4);
        layout.addView(bottomView);
        bottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dimiss();
            }
        });
        popupWindow = new MyPopupWindow(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        // 创建PopupWindow时候指定高宽时showAsDropDown能够自适应
        // 如果设置为wrap_content,showAsDropDown会认为下面空间一直很充足（我以认为这个Google的bug）
        // 备注如果PopupWindow里面有ListView,ScrollView时，一定要动态设置PopupWindow的大小
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        popupWindow.setOutsideTouchable(true);
        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        popupWindow.setTouchable(true);
        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        popupWindow.setFocusable(true);
        popupWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });
    }


    private void dimiss() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    }

    public class MyPopupWindow extends PopupWindow {

        public MyPopupWindow(Context context) {
            super(context);
        }

        public MyPopupWindow(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public MyPopupWindow(View contentView, int width, int height, boolean focusable) {
            super(contentView, width, height, focusable);
        }

        @Override
        public void showAsDropDown(View anchor, int xoff, int yoff) {
            if (Build.VERSION.SDK_INT >= 24) {
                Rect rect = new Rect();
                anchor.getGlobalVisibleRect(rect);
                int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
                if ("Xiaomi".equals(Build.MANUFACTURER)) {
                    h += SystemUtil.getNavigationBarHeight(context) + SystemUtil.getStatusBarHeight(context);
                }
                setHeight(h);
            }
            super.showAsDropDown(anchor, xoff, yoff);
        }


    }

    private void setIconSize(int w, int h) {
        if (checkIcon != null) {
            checkIcon.setBounds(0, 0, w, h);
        }
        if (icon != null) {
            icon.setBounds(0, 0, w, h);
        }

    }
    public void reset() {
        selcetIndex = -1;
        setCheck(false);

    }

    private void setCheck(boolean isChenck) {
        if (isChenck) {
            textView.setTextColor(checkColer);
            textView.setCompoundDrawablesRelative(null, null, checkIcon, null);
            textView.setText(checkText);
        } else {
            textView.setTextColor(textColer);
            textView.setText(text);
            textView.setCompoundDrawablesRelative(null, null, icon, null);
        }
    }


    public enum GravityModel {
        LEFT,
        RIGHT,
        CENTER
    }

    /************对外开放Api************/

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        textView.setTextSize(textSize);
    }

    public void setText(CharSequence text) {
        this.text = text;
        textView.setText(text);
    }

    public void setTextColor(int textColer, int checkColer) {
        this.checkColer = checkColer;
        this.textColer = textColer;
    }

    public void setIcon(Drawable icon, Drawable checkIcon) {
        this.icon = icon;
        this.checkIcon = checkIcon;
    }

    public void setCheckIndex(int position) {
        if (getAdapter() == null) return;
        if (selcetIndex != position) {
            selcetIndex = position;
            if (isFirst) {
                checkText = getAdapter().getData().get(position).getText();
                setCheck(true);
            } else {
                if (position == 0) {
                    setCheck(false);
                } else {
                    checkText = getAdapter().getData().get(position).getText();
                    setCheck(true);
                }
            }
        }
    }

    public void setPopWH(int w, int h) {
        if (listView != null) {
            LayoutParams layoutParams2 = new   LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams2.height = h;
            layoutParams2.width = w;
            listView.setLayoutParams(layoutParams2);
            setSpinnerWith(w);
        }
    }

    public void setSpinnerWith(int with) {
        if (popupWindow != null) {
            popupWindow.setWidth(with);
        }
    }

    public void setGravityMeodel(GravityModel mMode) {

        switch (mMode) {
            case CENTER:
                setGravity(Gravity.CENTER);
                break;
            case RIGHT:
                setGravity(Gravity.CENTER | Gravity.RIGHT);
                break;
            case LEFT:
                setGravity(Gravity.CENTER | Gravity.LEFT);
                break;

            default:
                break;
        }
    }

    public void setXoff(int xoff) {
        this.xoff = xoff;
    }

    public <T extends SpinnerData, BH extends BaseViewHolder> void setAdapter(BaseQuickAdapter<T, BH> adapter) {
        this.adapter = adapter;
        if (listView != null && adapter != null) {
            listView.setAdapter(adapter);
            adapter.setOnItemClickListener(itemClickListener);
        }
    }

    public <T extends SpinnerData, BH extends BaseViewHolder> BaseQuickAdapter<T, BH> getAdapter() {
        return adapter;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
