package com.techyourchance.mvc.screens.common.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.common.permissions.PermissionsHelper;
import com.techyourchance.mvc.screens.common.controllers.BackPressDispatcher;
import com.techyourchance.mvc.screens.common.controllers.BackPressedListener;
import com.techyourchance.mvc.screens.common.controllers.BaseActivity;
import com.techyourchance.mvc.screens.common.fragmentframehelper.FragmentFrameWrapper;
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerHelper;
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerViewMvc;
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.techyourchance.mvc.screens.questionslist.QuestionsListFragment;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseActivity implements
        BackPressDispatcher,
        FragmentFrameWrapper,
        NavDrawerViewMvc.Listener,
        NavDrawerHelper {

    private final Set<BackPressedListener> mBackPressedListeners = new HashSet<>();
    private ScreensNavigator mScreensNavigator;
    private PermissionsHelper mPermissionsHelper;

    private NavDrawerViewMvc mViewMvc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mPermissionsHelper = getCompositionRoot().getPermissionsHelper();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getNavDrawerViewMvc(null);
        setContentView(mViewMvc.getRootView());

        if (savedInstanceState == null) {
            mScreensNavigator.toQuestionsList();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onQuestionsListClicked() {
        mScreensNavigator.toQuestionsList();
    }

    @Override
    public void registerListener(BackPressedListener listener) {
        mBackPressedListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackPressedListener listener) {
        mBackPressedListeners.remove(listener);
    }

    @Override
    public void onBackPressed() {
        boolean isBackPressConsumedByAnyListener = false;
        for (BackPressedListener listener : mBackPressedListeners) {
            if (listener.onBackPressed()) {
                isBackPressConsumedByAnyListener = true;
            }
        }
        if (isBackPressConsumedByAnyListener) {
            return;
        }

        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return mViewMvc.getFragmentFrame();
    }

    @Override
    public void openDrawer() {
        mViewMvc.openDrawer();
    }

    @Override
    public void closeDrawer() {
        mViewMvc.closeDrawer();
    }

    @Override
    public boolean isDrawerOpen() {
        return mViewMvc.isDrawerOpen();
    }
}
