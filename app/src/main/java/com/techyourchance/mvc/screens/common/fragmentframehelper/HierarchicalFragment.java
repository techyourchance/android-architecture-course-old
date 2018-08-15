package com.techyourchance.mvc.screens.common.fragmentframehelper;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public interface HierarchicalFragment {
    /**
     * In case of UP navigation when Fragments back-stack is empty, the Fragment returned by this
     * method will be navigated to. If this method returns null, then UP navigation will be
     * delegated to enclosing Activity.
     * @return hierarchical parent Fragment of this Fragment; null this Fragment has no hierarchical
     *         parent
     */
    @Nullable
    Fragment getHierarchicalParentFragment();
}
