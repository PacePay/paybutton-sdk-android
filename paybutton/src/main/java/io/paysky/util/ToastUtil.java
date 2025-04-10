package io.paysky.util;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.example.paybutton.R;


public class ToastUtil extends NoProguard {

    public static void showShortToast(Context context, @StringRes int message) {

        if (context == null) {
            return;
        }
        showToast(context, context.getString(message), Toast.LENGTH_SHORT);
    }


    public static void showShortToast(Context context, String message) {
        if (message == null || context == null || context.getResources() == null) {
            return;
        }
        showToast(context, message, Toast.LENGTH_SHORT);
    }


    public static void showShortCustomToastColorAndBackground(Context context, String message) {
        if (message == null || context == null || context.getResources() == null) {
            return;
        }
        showCustomToast(context, message, Toast.LENGTH_SHORT);
    }


    public static void showLongToast(Context context, @StringRes int message) {
        showToast(context, context.getString(message), Toast.LENGTH_LONG);
    }

    public static void showLongToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }


    private static void showToast(Context context, String message, int length) {
        if (message == null || context == null || context.getResources() == null) {
            return;
        }
        Toast.makeText(context, message, length).show();
    }

    private static void showCustomToast(Context context, String message, int length) {
        if (message == null || context == null || context.getResources() == null) {
            return;
        }
        Toast toast = Toast.makeText(context, message, length);
        toast.getView().setBackgroundColor(context.getResources().getColor(R.color.black));
        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.WHITE);
        toast.show();
    }
}
