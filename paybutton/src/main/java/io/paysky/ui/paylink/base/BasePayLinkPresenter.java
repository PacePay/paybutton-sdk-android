package io.paysky.ui.paylink.base;

import io.paysky.ui.paylink.mvp.PayLinkFragmentView;

public class BasePayLinkPresenter<View extends PayLinkFragmentView> {

    public View view;

    public BasePayLinkPresenter(View view) {

        this.view = view;
    }

    public BasePayLinkPresenter() {

    }

    public void attachView(View view) {

        this.view = view;

    }

    public void detachView() {
        this.view = null;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public boolean isViewDetached() {
        return !isViewAttached();
    }
}
