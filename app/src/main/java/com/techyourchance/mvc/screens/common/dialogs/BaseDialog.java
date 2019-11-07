package com.techyourchance.mvc.screens.common.dialogs;

import android.support.v4.app.DialogFragment;

import com.techyourchance.mvc.common.CustomApplication;
import com.techyourchance.mvc.common.dependencyinjection.ControllerCompositionRoot;

public abstract class BaseDialog extends DialogFragment {


    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) requireActivity().getApplication()).getCompositionRoot(),
                    requireActivity()
            );
        }
        return mControllerCompositionRoot;
    }

}
