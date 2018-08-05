package com.techyourchance.mvc.screens.common.toastshelper;

import android.content.Context;
import android.widget.Toast;

import com.techyourchance.mvc.R;

public class ToastsHelper {

    private final Context mContext;

    public ToastsHelper(Context context) {
        mContext = context;
    }

    public void showUseCaseError() {
        Toast.makeText(mContext, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }
}
