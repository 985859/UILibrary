package com.mylibrary.api.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mylibrary.api.R;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/4/17 14:50
 */
public class LoadingDialog extends Dialog {
    private String text;

    private TextView textView;

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public LoadingDialog(@NonNull Context context, String text) {
        this(context, R.style.dialog);
        this.text = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.loging_dialog, null);
        textView=findViewById(R.id.tv);
        textView.setText(text);
        setContentView(layout);
    }

    public void setText(String text) {
        this.text = text;
        textView.setText(text);
    }
}
