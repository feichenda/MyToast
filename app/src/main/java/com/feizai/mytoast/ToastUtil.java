package com.feizai.mytoast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    private static CustomToast customToast = null;
    private static Toast toast = null;

    public static void showToast(Context context, String content) {
        if (customToast == null) {
            customToast = new CustomToast(context, 3);
        }
        customToast.setText(content);
        customToast.show();
    }

    public static void cancelToast() {
        if (customToast != null) {
            customToast.cancel();
            customToast = null;
        }
    }


    public static void showToast(Context context, String message, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView toastText = (TextView) view.findViewById(R.id.toast_text);
        toastText.setText(message);
        toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    public static void showToast(Context context, int strRes, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView toastText = (TextView) view.findViewById(R.id.toast_text);
        toastText.setText(strRes);
        toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }
}
