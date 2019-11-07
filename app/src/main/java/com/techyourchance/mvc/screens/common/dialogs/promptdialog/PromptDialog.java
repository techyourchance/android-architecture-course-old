package com.techyourchance.mvc.screens.common.dialogs.promptdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.screens.common.dialogs.BaseDialog;
import com.techyourchance.mvc.screens.common.dialogs.DialogsEventBus;

public class PromptDialog extends BaseDialog {

    protected static final String ARG_TITLE = "ARG_TITLE";
    protected static final String ARG_MESSAGE = "ARG_MESSAGE";
    protected static final String ARG_POSITIVE_BUTTON_CAPTION = "ARG_POSITIVE_BUTTON_CAPTION";
    protected static final String ARG_NEGATIVE_BUTTON_CAPTION = "ARG_NEGATIVE_BUTTON_CAPTION";

    public static PromptDialog newPromptDialog(String title, String message, String positiveButtonCaption, String negativeButtonCaption) {
        PromptDialog promptDialog = new PromptDialog();
        Bundle args = new Bundle(4);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_POSITIVE_BUTTON_CAPTION, positiveButtonCaption);
        args.putString(ARG_NEGATIVE_BUTTON_CAPTION, negativeButtonCaption);
        promptDialog.setArguments(args);
        return promptDialog;
    }

    private TextView mTxtTitle;
    private TextView mTxtMessage;
    private AppCompatButton mBtnPositive;
    private AppCompatButton mBtnNegative;

    private DialogsEventBus mDialogsEventBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogsEventBus = getCompositionRoot().getDialogsEventBus();
    }

    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalStateException("arguments mustn't be null");
        }

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_prompt);

        mTxtTitle = dialog.findViewById(R.id.txt_title);
        mTxtMessage = dialog.findViewById(R.id.txt_message);
        mBtnPositive = dialog.findViewById(R.id.btn_positive);
        mBtnNegative = dialog.findViewById(R.id.btn_negative);

        mTxtTitle.setText(getArguments().getString(ARG_TITLE));
        mTxtMessage.setText(getArguments().getString(ARG_MESSAGE));
        mBtnPositive.setText(getArguments().getString(ARG_POSITIVE_BUTTON_CAPTION));
        mBtnNegative.setText(getArguments().getString(ARG_NEGATIVE_BUTTON_CAPTION));

        mBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveButtonClicked();
            }
        });

        mBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNegativeButtonClicked();
            }
        });

        return dialog;
    }


    protected void onPositiveButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.POSITIVE));
    }

    protected void onNegativeButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.NEGATIVE));
    }

}