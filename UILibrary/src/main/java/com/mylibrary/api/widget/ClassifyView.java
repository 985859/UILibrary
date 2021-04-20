package com.mylibrary.api.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.R;
import com.mylibrary.api.adapter.ClassPagerAdapter;
import com.mylibrary.api.adapter.ClassifyAdapter;
import com.mylibrary.api.interfaces.ClassifyData;
import com.mylibrary.api.managelayout.DividerGridItemDecoration;
import com.mylibrary.api.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2017/7/15.
 */

public class ClassifyView extends LinearLayout {
    View view;
    Context context;
    ViewPager classifyPager;
    LinearLayout classifyLayout;
    List<View> views = new ArrayList<>();
    List<View> indicator = new ArrayList<>();
    private int MAX = 6;
    private int columnNumber = 3;
    private int textSzie = 12;
    private int textColor = 0;
    private boolean isAddDecoration = false;

    public void setAddDecoration(boolean addDecoration) {
        isAddDecoration = addDecoration;
    }

    public OnClassifyListener lassifyListener;
    List<? extends ClassifyData> date;
    private ClassPagerAdapter adapter;

    public void setLineAndColumnNumber(int line, int column) {
        this.columnNumber = column;
        this.MAX = line * column;
    }

    public <T extends ClassifyData> void setOnClassifyListener(OnClassifyListener<T> lassifyListener) {
        this.lassifyListener = lassifyListener;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    public ClassifyView(Context context) {
        super(context);
        init(context);
    }

    public ClassifyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClassifyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public List<? extends ClassifyData> getDate() {
        return date;
    }

    public void setDate(List<? extends ClassifyData> dateList) {
        if (dateList != null && dateList.size() > 0) {
            if (date != null) {
                date.clear();
            }
            views.clear();
            indicator.clear();
            classifyLayout.removeAllViews();
            date = dateList;
            int m = dateList.size() / MAX;
            int k = dateList.size() % MAX;
            if (k != 0) {
                m += 1;
            }
            ViewGroup.LayoutParams params = classifyPager.getLayoutParams();
            if (dateList.size() > columnNumber) {
                if (m > 1) {
                    params.height = SystemUtil.dp2px(context, 220);
                } else {
                    params.height = SystemUtil.dp2px(context, 204);
                }

            } else {
                params.height = SystemUtil.dp2px(context, 102);
            }
            classifyPager.setLayoutParams(params);

            for (int i = 0; i < m; i++) {
                RecyclerView recyclerView = new RecyclerView(context);
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnNumber) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                List<ClassifyData> models = new ArrayList<>();
                if (k != 0) {
                    if (i == m - 1) {
                        if (m - 1 == 0 && k == 0) {
                            models.addAll(dateList.subList(i * MAX, MAX));
                        } else {
                            models.addAll(dateList.subList(i * MAX, i * MAX + k));
                        }
                    } else {
                        models.addAll(dateList.subList(i * MAX, (i + 1) * MAX));
                    }
                } else {
                    models.addAll(dateList.subList(i * MAX, (i + 1) * MAX));
                }
                if (isAddDecoration) {
                    recyclerView.addItemDecoration(new DividerGridItemDecoration(context, 2, Color.parseColor("#d7d7d7")));
                }

                final ClassifyAdapter classifyAdapter = new ClassifyAdapter(context, null);
                recyclerView.setAdapter(classifyAdapter);
                classifyAdapter.setTextColor(textColor);
                classifyAdapter.setTextSzie(textSzie);
                classifyAdapter.setNewInstance(models);
                classifyAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                        if (lassifyListener != null) {
                            int index = classifyPager.getCurrentItem() * MAX + position;
                            if (date != null && index >= 0 && index < date.size()) {
                                lassifyListener.onClassifylistener(classifyPager.getCurrentItem(), position, date.get(index));
                            }
                        }
                    }
                });
                views.add(recyclerView);
                ImageView imageView = new ImageView(context);
                MarginLayoutParams marginLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                marginLayoutParams.setMargins(SystemUtil.dp2px(context, 3), 0, 0, 0);
                imageView.setLayoutParams(marginLayoutParams);
                imageView.setBackgroundResource(R.drawable.shape_over_griay);
                classifyLayout.addView(imageView);
                indicator.add(imageView);
            }
            if (indicator.size() > 1) {
                classifyLayout.setVisibility(VISIBLE);
            } else {
                classifyLayout.setVisibility(GONE);
            }

        }
        setLintener(0);
        classifyPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setLintener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter = new ClassPagerAdapter(views);
        classifyPager.setAdapter(adapter);
    }

    private void setLintener(int k) {
        for (int i = 0; i < indicator.size(); i++) {
            if (k == i) {
                indicator.get(i).setBackgroundResource(R.drawable.shape_over_black);
            } else {
                indicator.get(i).setBackgroundResource(R.drawable.shape_over_griay);
            }
        }
    }

    private void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.layout_classify, this);
        if (!isInEditMode()) {
            classifyPager = view.findViewById(R.id.classify_Pager);
            classifyLayout = view.findViewById(R.id.classify_Layout);
        }
    }

    public interface OnClassifyListener<T> {
        public void onClassifylistener(int k, int m, T model);
    }

    public void setTextSzie(int textSzie) {
        this.textSzie = textSzie;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
