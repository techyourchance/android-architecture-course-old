package com.techyourchance.mvc.screens.common;

import android.content.Context;
import android.widget.Toast;

import com.techyourchance.mvc.R;

public class MessagesDisplayer {

    private final Context mContext;

    public MessagesDisplayer(Context context) {
        mContext = context;
    }

    public void showUseCaseError() {
        Toast.makeText(mContext, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }
}
