package com.techyourchance.mvc.screens.common.controllers;

import androidx.fragment.app.Fragment;

import com.techyourchance.mvc.common.dependencyinjection.ControllerCompositionRoot;
import com.techyourchance.mvc.screens.common.main.MainActivity;

public class BaseFragment extends Fragment {

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
