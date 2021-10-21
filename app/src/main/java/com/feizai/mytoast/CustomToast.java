package com.feizai.mytoast;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.TextView;

public class CustomToast {
    public static final int MESSAGE_TIMEOUT = 0;
    private WindowManager wdm;
    private int time;
    private View mView;
    private WindowManager.LayoutParams params;
    private WorkerHandler mHandler;
    private boolean isShowing = false;

    private final class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_TIMEOUT:
                    cancel();
                    break;
            }
        }
    }

    public CustomToast(Context context, int time) {
        wdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mHandler = new WorkerHandler(Looper.myLooper());

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        mView = inflater.inflate(R.layout.toast_ttx_language, null);

        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.windowAnimations = Animation.INFINITE;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
//        params.x = 30;
//        params.y = 50;

        this.time = time;
    }

    public void setText(String text) {
        ((TextView) mView.findViewById(R.id.languageType)).setText(text);
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        params.gravity = gravity;
        params.x = xOffset;
        params.y = yOffset;
    }

    public void show() {
        if (!isShowing) {
            wdm.addView(mView, params);
            isShowing = true;
        }

        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(MESSAGE_TIMEOUT, time * 1000);
    }


    public void cancel() {
        mHandler.removeCallbacksAndMessages(null);
        if (isShowing)
            wdm.removeView(mView);
        isShowing = false;
    }
}
