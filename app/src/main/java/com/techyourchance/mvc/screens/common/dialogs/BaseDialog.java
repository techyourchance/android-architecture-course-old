package com.techyourchance.mvc.screens.common.dialogs;

import androidx.fragment.app.DialogFragment;

import com.techyourchance.mvc.common.dependencyinjection.ControllerCompositionRoot;
import com.techyourchance.mvc.screens.common.main.MainActivity;

public abstract class BaseDialog extends DialogFragment {


    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((MainActivity) requireActivity()).getActivityCompositionRoot()
            );
        }
        return mControllerCompositionRoot;
    }

}
