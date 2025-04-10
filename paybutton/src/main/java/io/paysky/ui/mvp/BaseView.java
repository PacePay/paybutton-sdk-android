package io.paysky.ui.mvp;

import android.content.Context;

import androidx.annotation.StringRes;

public interface BaseView {

    void showProgress();

    void showProgress(@StringRes int message);

    Context getContext();

    void dismissProgress();

    void showToast(@StringRes int message);

    void showNoInternetDialog();

    boolean isInternetAvailable();
}
