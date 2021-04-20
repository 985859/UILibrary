package com.mylibrary.api.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.Gravity;

import com.mylibrary.api.R;


/**
 * 带倒计时的按钮
 */
public class CountDownTimerButton extends VariedTextView {
    OnTimeListener onTimeListener;

    public void setOnTimeListener(OnTimeListener onTimeListener) {
        this.onTimeListener = onTimeListener;
    }

    // 总倒计时时间
    private static final long MILLIS_IN_FUTURE = 60 * 1000;
    // 每次减去1秒
    private static final long COUNT_DOWN_INTERVAL = 1000;

    CountDownTimer downTimer = new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {
        @SuppressLint("StringFormatMatches")
        @Override
        public void onTick(long millisUntilFinished) {
            if (onTimeListener != null)
                onTimeListener.onTick(millisUntilFinished);
            // 刷新文字
            setText(getContext().getString(R.string.reget_sms_code_countdown, millisUntilFinished / COUNT_DOWN_INTERVAL));
        }

        @Override
        public void onFinish() {
            if (onTimeListener != null)
                onTimeListener.onFinish();
            // 重置文字，并恢复按钮为可点击
            setText(R.string.reget_sms_code);
            setEnabled(true);
        }
    };


    public CountDownTimerButton(Context context) {
        this(context, null);
    }

    public CountDownTimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);

    }

    public CountDownTimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // 启动倒计时
    public void startCountDown() {
        // 设置按钮为不可点击，并修改显示背景
        setEnabled(false);
        // 开始倒计时
        downTimer.start();
    }

    // 停止倒计时
    public void stopCountDown() {
        // 重置文字，并恢复按钮为可点击
        downTimer.cancel();
        setText("获取验证码");
        setEnabled(true);
    }

    public interface OnTimeListener {
        public void onTick(long millisUntilFinished);

        public void onFinish();
    }

    public void cancel() {
        downTimer.cancel();
        downTimer = null;
    }
}