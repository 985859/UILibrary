package com.mylibrary.api.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mylibrary.api.R;
import com.mylibrary.api.interfaces.ClassifyData;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.widget.picassoImage.PicassoImageView;

import java.util.List;


/**
 * Created by myuser on 2018/4/17.
 */

public class ClassifyAdapter extends BaseQuickAdapter<ClassifyData, BaseViewHolder> {
    Context context;
    private int textSzie = 12;
    private int textColor = 0;

    public ClassifyAdapter(Context context, List<ClassifyData> data) {
        super(R.layout.itm_classsify_layout, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ClassifyData item) {
        PicassoImageView imageView = baseViewHolder.getView(R.id.adapter_classifyIMG);
        if (StringUtil.isNotEmpty(item.getClassPic())) {
            imageView.setImageUrl(item.getClassPic(), R.drawable.icon_stub);
        } else {
            if (item.getClassIcon() != 0) {
                imageView.setBackgroundResource(item.getClassIcon());
            } else {
                imageView.setBackgroundResource(R.drawable.icon_stub);
            }
        }
        LinearLayout layout = baseViewHolder.getView(R.id.adapter_classify);

        if (!StringUtil.isEmpty(item.getClassName())) {
            baseViewHolder.setText(R.id.adapter_classifyTitle, item.getClassName());
        }
        TextView classifyTitle = baseViewHolder.getView(R.id.adapter_classifyTitle);
        classifyTitle.setTextSize(textSzie);
        if (textColor != 0) {
            classifyTitle.setTextColor(textColor);
        }
    }

    public void setTextSzie(int textSzie) {
        this.textSzie = textSzie;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
