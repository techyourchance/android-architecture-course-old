package com.techyourchance.mvc.screens.common.dialogs;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.screens.common.dialogs.promptdialog.PromptDialog;

public class DialogsManager {

    private final Context mContext;
    private final FragmentManager mFragmentManager;

    public DialogsManager(Context context, FragmentManager fragmentManager) {
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    public void showUseCaseErrorDialog(@Nullable String tag) {
        DialogFragment dialogFragment = PromptDialog.newPromptDialog(
                getString(R.string.error_network_call_failed_title),
                getString(R.string.error_network_call_failed_message),
                getString(R.string.error_network_call_failed_positive_button_caption),
                getString(R.string.error_network_call_failed_negative_button_caption)
        );
        dialogFragment.show(mFragmentManager, tag);
    }

    private String getString(int stringId) {
        return mContext.getString(stringId);
    }
}
