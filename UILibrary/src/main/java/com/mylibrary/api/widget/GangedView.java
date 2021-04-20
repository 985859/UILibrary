package com.mylibrary.api.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.mylibrary.api.R;
import com.mylibrary.api.adapter.GangedAdapter;
import com.mylibrary.api.interfaces.IGangedData;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class GangedView<T extends IGangedData> extends LinearLayout {
    TabLayout tabLayout;
    RecyclerView recyclerView;
    private int titleColor;
    private int TitleSelectColor;

    private int itmTextSize;
    private int itmTextColor;
    private int itmTextSelcetColor;

    private int indicatorHeight;

    private int maxTier = 3;//默认 4 级  0 开始
    private int tier = 0;// 当前层级

    private GangedAdapter gangedAdapter;
    private List<T> data;//数据源

    private List<Integer> selcetPosition = new ArrayList();//选中的索引
    private List<String> selcetID = new ArrayList();//选中的ID
    private List<String> selcetName = new ArrayList();// 选中的 名称
    private OnSelcetLinsenter selcetLinsenter;

    public GangedView(Context context) {
        this(context, null);
    }

    public GangedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GangedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GangedView);
        itmTextSize = array.getDimensionPixelSize(R.styleable.GangedView_gan_TextSize, 12);
        if (itmTextSize != 12) {
            itmTextSize = SystemUtil.px2sp(context, itmTextSize);
        }
        titleColor = array.getColor(R.styleable.GangedView_gan_TitleColor, context.getResources().getColor(R.color.textColor));
        TitleSelectColor = array.getColor(R.styleable.GangedView_gan_TitleSelectColor, context.getResources().getColor(R.color.blue2));

        itmTextColor = array.getColor(R.styleable.GangedView_gan_TextColor, context.getResources().getColor(R.color.textColor));
        itmTextSelcetColor = array.getColor(R.styleable.GangedView_gan_TextSelectColor, context.getResources().getColor(R.color.blue2));
        indicatorHeight = (int) array.getDimension(R.styleable.GangedView_gan_IndicatorHeight, 2);
        array.recycle();

        if (isEnabled()) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_granged, this);
            tabLayout = view.findViewById(R.id.grangedTab);
            tabLayout.addTab(tabLayout.newTab().setText("请选择"));
            recyclerView = view.findViewById(R.id.grangedRec);
            recyclerView.setLayoutManager(new TopLinearLayoutManager(context));
            gangedAdapter = new GangedAdapter(null);
            recyclerView.setAdapter(gangedAdapter);

            setTabTextColors(titleColor, TitleSelectColor);
            setItmTextSize(itmTextSize);
            setItmTextSelcetColor(itmTextSelcetColor);
            setItmTextColor(itmTextColor);
            setIndicatorHeight(indicatorHeight);
            setListener();
        }
    }

    //根据ID 设置选中的内容
    public void setSelcetID(List<String> idList) {
        if (idList != null && idList.size() > 0) {
            tabLayout.removeAllTabs();
            selcetName.clear();
            selcetPosition.clear();
            selcetID.clear();
            tier = 0;
            List<T> IGangedDatas = data;
            if (IGangedDatas != null && IGangedDatas.size() > 0) {
                for (int i = 0; i < idList.size(); i++) {
                    tier = i;
                    if (tier > maxTier) {
                        break;
                    }
                    IGangedDatas = getListIGangedData(IGangedDatas, idList.get(i), tier);
                }
            }
            if (selcetName != null && selcetName.size() > 0) {
                for (int i = 0; i < selcetName.size(); i++) {
                    if (i == selcetName.size() - 1) {
                        gangedAdapter.setSelectPosition(selcetPosition.get(i));
                        gangedAdapter.notifyItemChanged(selcetPosition.get(i));
                        tabLayout.addTab(tabLayout.newTab().setText(selcetName.get(i)), true);
                    } else {
                        tabLayout.addTab(tabLayout.newTab().setText(selcetName.get(i)));
                    }
                }
            } else {
                tabLayout.addTab(tabLayout.newTab().setText("请选择"), true);
            }
            if (tier < maxTier && getData(tier + 1) != null && getData(tier + 1).size() > 0) {
                gangedAdapter.setList(getData(tier + 1));
                tabLayout.addTab(tabLayout.newTab().setText("请选择"), true);
            }
        } else {
            tabLayout.removeAllTabs();
            selcetName.clear();
            selcetPosition.clear();
            selcetID.clear();
            tier = 0;
            tabLayout.addTab(tabLayout.newTab().setText("请选择"), true);
        }
    }

    private void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 记录当前显示的层级
                tier = tab.getPosition();
                gangedAdapter.setList(getData(tier));
                if (selcetPosition.size() > tier) {
                    recyclerView.smoothScrollToPosition(selcetPosition.get(tier));
                    gangedAdapter.setSelectPosition(selcetPosition.get(tier));
                }
                if ("请选择".equals(tab.getText())) {
                    gangedAdapter.setSelectPosition(-1);
                    recyclerView.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        gangedAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setSelectPosition(position);
            }
        });
    }


    private void setSelectPosition(int position) {
        gangedAdapter.setSelectPosition(-1);
        if (tier < tabLayout.getTabCount()) {
            for (int k = tabLayout.getTabCount() - 1; k > tier; k--) {
                tabLayout.removeTabAt(k);
                selcetPosition.remove(k - 1);
                selcetID.remove(k - 1);
                selcetName.remove(k - 1);
            }
            tabLayout.getTabAt(tier).setText(gangedAdapter.getData().get(position).getName());
        }
        addSelcetPositoin(tier, position);
        addSelcetID(tier, gangedAdapter.getData().get(position).getID());
        addSelcetName(tier, gangedAdapter.getData().get(position).getName());
        if (tier == maxTier) {
            gangedAdapter.setSelectPosition(position);
            if (selcetLinsenter != null) {
                selcetLinsenter.onSelcet(selcetID, selcetName);
            }
            return;
            //达到最大层级 不在执行
        }
        List<T> IGangedDatas = getData(tier + 1);
        if (IGangedDatas != null && IGangedDatas.size() > 0) {
            tier += 1;
            tabLayout.addTab(tabLayout.newTab().setText("请选择"), true);
            tabLayout.getTabAt(tier);
            gangedAdapter.setList(IGangedDatas);
            recyclerView.smoothScrollToPosition(0);
            tabLayout.setScrollPosition(tier, 0, true);
        } else {
            gangedAdapter.setSelectPosition(position);
            if (selcetLinsenter != null) {
                selcetLinsenter.onSelcet(selcetID, selcetName);
            }
        }
    }


    private void addSelcetPositoin(int tier, int position) {
        if (selcetPosition.size() > tier) {
            //替换当前层级选中的结果
            selcetPosition.set(tier, position);
        } else {
            //添加当前层级选中的结果
            selcetPosition.add(tier, position);
        }
    }


    private void addSelcetID(int tier, String id) {
        if (selcetID.size() > tier) {
            //替换当前层级选中的结果
            selcetID.set(tier, id);
        } else {
            //添加当前层级选中的结果
            selcetID.add(tier, id);
        }
    }


    private void addSelcetName(int tier, String name) {
        if (selcetName.size() > tier) {
            //替换当前层级选中的结果
            selcetName.set(tier, name);
        } else {
            //添加当前层级选中的结果
            selcetName.add(tier, name);
        }
    }


    private List<T> getData(int tier) {
        List<T> IGangedDatas = data;
        if (IGangedDatas != null && IGangedDatas.size() > 0 && tier > 0) {
            for (int i = 0; i < tier; i++) {
                if (selcetPosition.get(i) != -1) {
                    IGangedDatas = IGangedDatas.get(selcetPosition.get(i)).getChild();
                }
                if (IGangedDatas == null)
                    return IGangedDatas;
            }
        }
        return IGangedDatas;
    }


    //找到数据源中的 指定ID的 子类数据
    private List<T> getListIGangedData(List<T> IGangedDatas, String id, int tier) {
        List<T> data = null;
        if (IGangedDatas != null && IGangedDatas.size() > 0) {
            for (int i = 0; i < IGangedDatas.size(); i++) {
                if (id.equals(IGangedDatas.get(i).getID())) {
                    addSelcetPositoin(tier, i);
                    addSelcetID(tier, IGangedDatas.get(i).getID());
                    addSelcetName(tier, IGangedDatas.get(i).getName());
                    data = IGangedDatas.get(i).getChild();
                    break;
                }

            }
        }
        return data;
    }

    public List<String> getSelcetNames() {
        return selcetName;
    }

    public String getSelcetName(String divided) {
        return StringUtil.slipListToString(selcetName, divided);
    }

    public List<String> getSelcetIds() {
        return selcetID;
    }

    public String getSelcetID(String divided) {
        return StringUtil.slipListToString(selcetID, divided);
    }


    public interface OnSelcetLinsenter {
        void onSelcet(List<String> idLsit, List<String> nameLsit);
    }

    public int getMaxTier() {
        return maxTier;
    }

    public int getTier() {
        return tier;
    }

    /****************** 设置参数*************************/

    public void setTabTextColors(int titleColor, int TitleSelectColor) {
        this.TitleSelectColor = TitleSelectColor;
        this.titleColor = titleColor;
        tabLayout.setTabTextColors(titleColor, TitleSelectColor);
        tabLayout.setSelectedTabIndicatorColor(TitleSelectColor);
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
        Drawable drawable = tabLayout.getTabSelectedIndicator();
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), indicatorHeight);
            tabLayout.setSelectedTabIndicator(drawable);
        }
    }

    public void setItmTextSize(int itmTextSize) {
        this.itmTextSize = itmTextSize;
        gangedAdapter.setTextSize(itmTextSize);
    }

    public void setItmTextColor(int itmTextColor) {
        this.itmTextColor = itmTextColor;
        gangedAdapter.setTextColor(itmTextColor);
    }

    public void setItmTextSelcetColor(int itmTextSelcetColor) {
        this.itmTextSelcetColor = itmTextSelcetColor;
        gangedAdapter.setSelectTextColor(itmTextSelcetColor);
    }

    public void setOnSelcetLinsenter(OnSelcetLinsenter selcetLinsenter) {
        this.selcetLinsenter = selcetLinsenter;
    }

    public void setMaxtier(int maxtier) {
        this.maxTier = maxtier;
    }


    /**
     * @param isReSelcet true 重新选择   false  记录上一次ID 定位到是一次的位置
     * @return
     * @author hukui
     * @time 2020/9/18
     * @Description
     */
    public void setData(List<T> data, boolean isReSelcet) {
        this.data = data;
        gangedAdapter.setList(data);
        if (isReSelcet) {
            setSelcetID(selcetID);
        } else {
            List<String> ids = new ArrayList<>();
            ids.addAll(selcetID);
            setSelcetID(ids);
        }
    }

    public void setData(List<T> data) {
        setData(data, true);
    }

    public List<T> getData() {
        return data;
    }
}
