package com.techyourchance.mvc.screens.common.views;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;

public abstract class BaseViewMvc implements ViewMvc {
    
    private View mRootView;

    @Override
    public View getRootView() {
        return mRootView;
    }

    protected void setRootView(View rootView) {
        mRootView = rootView;
    }

    protected <T extends View> T findViewById(int id) {
        return getRootView().findViewById(id);
    }

    protected Context getContext() {
        return getRootView().getContext();
    }

    protected String getString(@StringRes int id) {
        return getContext().getString(id);
    }
}
