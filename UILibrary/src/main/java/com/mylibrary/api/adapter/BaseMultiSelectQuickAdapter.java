package com.mylibrary.api.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mylibrary.api.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/27 10:52
 */
public abstract class BaseMultiSelectQuickAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    protected int maxIndex = 9999999;
    protected boolean isCancel = false;//只有在maxIndex=1  单选有效 默认单选不可取消

    protected List<Integer> selectIds = new ArrayList<>();

    public BaseMultiSelectQuickAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public void setSelectIndex(int position) {
        if (maxIndex == 1) {
            int oldID = -1;
            if (selectIds.size() > 0)
                oldID = selectIds.get(0);
            if (oldID == position) {
                if (isCancel) {
                    selectIds.clear();
                    notifyItemChanged(position);
                } else {
                    return;
                }
            } else {
                selectIds.clear();
                selectIds.add(position);
                if (oldID != -1)
                    notifyItemChanged(oldID);
                notifyItemChanged(position);
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
                    ToastUtil.showShort("最多选中" + maxIndex + "项");
                } else {
                    selectIds.add(position);
                    notifyItemChanged(position);
                }
            } else {
                if (index != -1) {
                    selectIds.remove(index);
                    notifyItemChanged(position);
                }
            }
        }

    }

    public void dealerID() {
        if (selectIds != null && selectIds.size() > 0) {
            for (int i = selectIds.size() - 1; i >= 0; i--) {
                int index = selectIds.get(i);
                selectIds.remove(i);
                notifyItemChanged(index);
            }
        }
    }


    public int getSelectIDSize() {
        return selectIds == null ? 0 : selectIds.size();
    }

    public List<Integer> getSelectIds() {
        return selectIds == null ? new ArrayList<>() : selectIds;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }


}
