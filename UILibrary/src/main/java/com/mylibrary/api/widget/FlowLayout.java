package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mylibrary.api.R;
import com.mylibrary.api.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局
 * Created by admin on 2017/6/15.
 * hukui
 */

public class FlowLayout extends ViewGroup implements View.OnClickListener {

    private int viewBackground = -1;
    private int maxHeight = -1;
    private int selcetBackground = -1;
    ColorStateList textColor;
    ColorStateList textSelcetColor;
    protected int maxIndex;
    protected boolean iscancel;//只有在maxIndex=1  单选有效 默认单选不可取消

    protected List<Integer> selectIds = new ArrayList<>();
    private List data = new ArrayList<>();

    public void setData(List data) {
        if (data == null || data.size() == 0) {
            this.data.clear();
            return;
        }
        this.data = data;
    }

    public void addData(List data) {
        if (data == null || data.size() == 0) {
            return;
        }
        this.data.addAll(data);
    }

    public <T> List<T> getData() {
        return data;
    }

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        viewBackground = array.getResourceId(R.styleable.FlowLayout_flow_view_background, viewBackground);
        selcetBackground = array.getResourceId(R.styleable.FlowLayout_flow_view_select_background, selcetBackground);
        textColor = array.getColorStateList(R.styleable.FlowLayout_flow_TextColor);
        textSelcetColor = array.getColorStateList(R.styleable.FlowLayout_flow_TextSelectColor);
        maxIndex = array.getInt(R.styleable.FlowLayout_flow_maxIndex, 1);
        iscancel = array.getBoolean(R.styleable.FlowLayout_flow_isCancel, false);
        array.recycle();
    }

    private OnFlowSelectListener SelectListener;

    //指定的LayoutParams，我们目前只需要能够识别margin即可，即使用MarginLayoutParams.
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    public void setViewBackgroundResource(int id) {
        viewBackground = id;
    }

    public void setMySearchViewBackgroundResource(int id) {
        selcetBackground = id;
    }

    public void addView(int margin, View view) {
        addView(margin, MarginLayoutParams.WRAP_CONTENT, view);
    }

    public void addView(int margin, int height, View view) {
        MarginLayoutParams params = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, height);
        params.setMargins(margin, margin, margin, margin);
        addView(view, params);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
        if (child instanceof TextView || child instanceof Button) {
            if (textColor != null)
                ((TextView) child).setTextColor(textColor);
        }
        if (viewBackground != -1) {
            child.setBackgroundResource(viewBackground);
        }

    }

    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     */
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
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height
         */
        int lineHeight = 0;
        //获取子view的个数
        int cCount = getChildCount();

        // 遍历每个子元素
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            child.setTag(i);
            child.setOnClickListener(this);
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            if (childWidth > getMeasuredWidth()) {
                child.getLayoutParams().width = getMeasuredWidth() - lp.leftMargin - lp.rightMargin;
            }
            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，累加height 然后开启新行
             */
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth);// 取最大的
                lineWidth = childWidth; // 重新开启新行，开始记录
                // 叠加当前高度，
                height += lineHeight;
                // 开启记录下一行的高度
                lineHeight = childHeight;
            } else
            // 否则累加值lineWidth,lineHeight取最大高度
            {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        if (height < getMinimumHeight()) {
            height = getMinimumHeight();
        }
        int h = (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height;
        if (maxHeight != -1 && h > maxHeight) {
            h = maxHeight;
        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, h);
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<View>();
        int cCount = getChildCount();

        // 遍历所有的孩子
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            // 如果已经需要换行
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<View>();
            }
            /**
             * 如果不需要换行，则累加
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        // 记录最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);
            // 遍历当前行所有的View
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                //计算childView的left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }


    public <T> void setSelectListener(OnFlowSelectListener<T> SelectListener) {
        this.SelectListener = SelectListener;
    }

    @Override
    public void onClick(View v) {
        try {
            int i = (int) v.getTag();
            if (SelectListener != null) {
                if (data != null && i < data.size()) {
                    SelectListener.flowSelect(v, i, data.get(i));
                } else {
                    SelectListener.flowSelect(v, i, null);
                }
            }
            setSelectIndex(i);
        } catch (Exception e) {

        }

    }


    public interface OnFlowSelectListener<T> {
        void flowSelect(View view, int index, T bean);
    }


    public void setCancel(boolean cancel) {
        iscancel = cancel;
    }


    public void setSelectIndex(List<Integer> idList) {
        if (this.selectIds != null && this.selectIds.size() > 0) {
            for (int i = 0; i < this.selectIds.size(); i++) {
                setViewBackground(this.selectIds.get(i));
            }
        }
        this.selectIds.clear();
        if (idList == null || idList.size() == 0) {
            return;
        }
        for (int i = 0; i < idList.size(); i++) {
            setSelcetBackground(idList.get(i));
        }
        this.selectIds.addAll(idList);
    }


    private void setViewBackground(int index) {
        if (index < getChildCount()) {
            if (getChildAt(index) instanceof TextView || getChildAt(index) instanceof Button) {
                if (textColor != null)
                    ((TextView) getChildAt(index)).setTextColor(textColor);
            }
            if (viewBackground != -1)
                getChildAt(index).setBackgroundResource(viewBackground);
        }
    }


    private void setSelcetBackground(int index) {
        if (index < getChildCount()) {
            if (getChildAt(index) instanceof TextView || getChildAt(index) instanceof Button) {
                if (textSelcetColor != null)
                    ((TextView) getChildAt(index)).setTextColor(textSelcetColor);
            }
            if (selcetBackground != -1)
                getChildAt(index).setBackgroundResource(selcetBackground);
        }
    }


    public void setSelectIndex(int position) {
        if (maxIndex == 1) {
            int oldID = -1;
            if (selectIds.size() > 0)
                oldID = selectIds.get(0);
            if (oldID == position) {
                if (iscancel) {
                    selectIds.clear();
                    setViewBackground(oldID);
                } else {
                    return;
                }
            } else {
                selectIds.clear();
                selectIds.add(position);
                if (oldID != -1)
                    setViewBackground(oldID);
                setSelcetBackground(position);
            }
        } else {
            boolean isAdd = true;
            int index = -1;
            for (int i = 0; i < selectIds.size(); i++) {
                if (selectIds.get(i) == position) {
                    isAdd = false;
                    index = i;
                }
            }
            if (isAdd) {
                if (selectIds.size() >= maxIndex) {
                    ToastUtil.showShort( "最多选中" + maxIndex + "项");
                } else {
                    selectIds.add(position);
                    setSelcetBackground(position);
                }
            } else {
                if (index != -1) {
                    selectIds.remove(index);
                    setViewBackground(position);
                }
            }
        }

    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        selectIds.clear();
        data.clear();
        mAllViews.clear();
        mLineHeight.clear();
    }

    public void delearIndex() {
        if (selectIds != null && selectIds.size() > 0) {
            for (int i = selectIds.size() - 1; i >= 0; i--) {
                int index = selectIds.get(i);
                selectIds.remove(i);
                setViewBackground(index);
            }
        }
    }


    public int getSelcedCount() {
        return selectIds == null ? 0 : selectIds.size();
    }

    public List<Integer> getSelcedIds() {
        return selectIds == null ? new ArrayList<>() : selectIds;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public void setTextColor(int textColor) {
        this.textColor = ColorStateList.valueOf(textColor);
    }


    public void setTextSelcetColor(int textSelcetColor) {
        this.textSelcetColor = ColorStateList.valueOf(textSelcetColor);

    }

}
