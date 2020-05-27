package com.techyourchance.mvc.common.dependencyinjection;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.techyourchance.mvc.common.permissions.PermissionsHelper;
import com.techyourchance.mvc.networking.StackoverflowApi;
import com.techyourchance.mvc.screens.common.dialogs.DialogsEventBus;

public class ActivityCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentActivity mActivity;

    private PermissionsHelper mPermissionsHelper;

    public ActivityCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    public FragmentActivity getActivity() {
        return mActivity;
    }

    public StackoverflowApi getStackoverflowApi() {
        return mCompositionRoot.getStackoverflowApi();
    }

    public DialogsEventBus getDialogsEventBus() {
        return mCompositionRoot.getDialogsEventBus();
    }

    public PermissionsHelper getPermissionsHelper() {
        if (mPermissionsHelper == null) {
            mPermissionsHelper = new PermissionsHelper(getActivity());
        }
        return mPermissionsHelper;
    }
}
