package com.techyourchance.mvc.screens.common.screensnavigator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.techyourchance.mvc.screens.common.controllers.FragmentFrameWrapper;
import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsFragment;
import com.techyourchance.mvc.screens.questionslist.QuestionsListFragment;

public class ScreensNavigator {

    private final FragmentManager mFragmentManager;
    private final FragmentFrameWrapper mFragmentFrameWrapper;

    public ScreensNavigator(FragmentManager fragmentManager, FragmentFrameWrapper fragmentFrameWrapper) {
        mFragmentManager = fragmentManager;
        mFragmentFrameWrapper = fragmentFrameWrapper;
    }

    public void toQuestionDetails(String questionId) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(mFragmentFrameWrapper.getFragmentFrame().getId(), QuestionDetailsFragment.newInstance(questionId)).commit();
    }

    public void toQuestionsList() {
        mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(mFragmentFrameWrapper.getFragmentFrame().getId(), QuestionsListFragment.newInstance()).commit();
    }
}
