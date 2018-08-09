package com.techyourchance.mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.questions.FetchQuestionDetailsUseCase;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.controllers.BackPressedListener;
import com.techyourchance.mvc.screens.common.controllers.BaseActivity;
import com.techyourchance.mvc.screens.common.navdrawer.DrawerItems;
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.techyourchance.mvc.screens.common.toastshelper.ToastsHelper;

public class QuestionDetailsActivity extends BaseActivity {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private BackPressedListener mBackPressedListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_content_frame);

        QuestionDetailsFragment fragment;
        if (savedInstanceState == null) {
            fragment = QuestionDetailsFragment.newInstance(getQuestionId());
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frame_content, fragment).commit();
        } else  {
            fragment = (QuestionDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        }
        mBackPressedListener = fragment;
    }

    @Override
    public void onBackPressed() {
        if (!mBackPressedListener.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private String getQuestionId() {
        return getIntent().getStringExtra(EXTRA_QUESTION_ID);
    }
}
