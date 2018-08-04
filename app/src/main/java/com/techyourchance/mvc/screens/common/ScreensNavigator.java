package com.techyourchance.mvc.screens.common;

import android.content.Context;

import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsActivity;

public class ScreensNavigator {

    private final Context mContext;

    public ScreensNavigator(Context context) {
        mContext = context;
    }

    public void toDialogDetails(String questionId) {
        QuestionDetailsActivity.start(mContext, questionId);
    }
}
