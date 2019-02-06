package com.techyourchance.mvc.screens.common.navdrawer;

import android.widget.FrameLayout;

import com.techyourchance.mvc.screens.common.views.ObservableViewMvc;

public interface NavDrawerViewMvc extends ObservableViewMvc<NavDrawerViewMvc.Listener> {

    interface Listener {

        void onQuestionsListClicked();
    }

    FrameLayout getFragmentFrame();

    boolean isDrawerOpen();
    void openDrawer();
    void closeDrawer();

}
