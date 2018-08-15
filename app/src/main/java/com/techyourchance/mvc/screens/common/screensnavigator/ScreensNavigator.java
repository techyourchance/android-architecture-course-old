package com.techyourchance.mvc.screens.common.screensnavigator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.techyourchance.mvc.screens.common.fragmentframehelper.FragmentFrameHelper;
import com.techyourchance.mvc.screens.common.fragmentframehelper.FragmentFrameWrapper;
import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsFragment;
import com.techyourchance.mvc.screens.questionslist.QuestionsListFragment;

public class ScreensNavigator {

    private FragmentFrameHelper mFragmentFrameHelper;

    public ScreensNavigator(FragmentFrameHelper fragmentFrameHelper) {
        mFragmentFrameHelper = fragmentFrameHelper;
    }

    public void toQuestionDetails(String questionId) {
        mFragmentFrameHelper.replaceFragment(QuestionDetailsFragment.newInstance(questionId));
    }

    public void toQuestionsList() {
        mFragmentFrameHelper.replaceFragmentAndClearBackstack(QuestionsListFragment.newInstance());
    }

    public void navigateUp() {
        mFragmentFrameHelper.navigateUp();
    }
}
