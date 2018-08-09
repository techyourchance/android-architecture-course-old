package com.techyourchance.mvc.screens.questiondetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.mvc.questions.FetchQuestionDetailsUseCase;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.controllers.BackPressedListener;
import com.techyourchance.mvc.screens.common.controllers.BaseFragment;
import com.techyourchance.mvc.screens.common.navdrawer.DrawerItems;
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.techyourchance.mvc.screens.common.toastshelper.ToastsHelper;

import static com.techyourchance.mvc.screens.questiondetails.QuestionDetailsActivity.EXTRA_QUESTION_ID;

public class QuestionDetailsFragment extends BaseFragment implements
        FetchQuestionDetailsUseCase.Listener, QuestionDetailsViewMvc.Listener, BackPressedListener {

    private static final String ARG_QUESTION_ID = "ARG_QUESTION_ID";

    public static QuestionDetailsFragment newInstance(String questionId) {
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_ID, questionId);
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;

    private ToastsHelper mToastsHelper;
    private ScreensNavigator mScreensNavigator;

    private QuestionDetailsViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFetchQuestionDetailsUseCase = getCompositionRoot().getFetchQuestionDetailsUseCase();
        mToastsHelper = getCompositionRoot().getMessagesDisplayer();
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMvc(container);

        return mViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchQuestionDetailsUseCase.registerListener(this);
        mViewMvc.registerListener(this);

        mViewMvc.showProgressIndication();
        mFetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(getQuestionId());
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchQuestionDetailsUseCase.unregisterListener(this);
        mViewMvc.unregisterListener(this);
    }

    private String getQuestionId() {
        return getArguments().getString(ARG_QUESTION_ID);
    }

    @Override
    public void onQuestionDetailsFetched(QuestionDetails questionDetails) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestion(questionDetails);
    }

    @Override
    public void onQuestionDetailsFetchFailed() {
        mViewMvc.hideProgressIndication();
        mToastsHelper.showUseCaseError();
    }

    @Override
    public void onNavigateUpClicked() {
        onBackPressed();
    }

    @Override
    public void onDrawerItemClicked(DrawerItems item) {
        switch (item) {
            case QUESTIONS_LIST:
                mScreensNavigator.toQuestionsListClearTop();
        }
    }

    @Override
    public boolean onBackPressed() {
        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
            return true;
        } else {
            return false;
        }
    }

}
