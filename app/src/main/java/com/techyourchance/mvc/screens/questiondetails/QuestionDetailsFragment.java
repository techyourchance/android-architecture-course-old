package com.techyourchance.mvc.screens.questiondetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.mvc.questions.FetchQuestionDetailsUseCase;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.controllers.BaseFragment;
import com.techyourchance.mvc.screens.common.dialogs.DialogsEventBus;
import com.techyourchance.mvc.screens.common.dialogs.DialogsManager;
import com.techyourchance.mvc.screens.common.dialogs.promptdialog.PromptDialogEvent;
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.techyourchance.mvc.screens.common.toastshelper.ToastsHelper;

public class QuestionDetailsFragment extends BaseFragment implements
        FetchQuestionDetailsUseCase.Listener,
        QuestionDetailsViewMvc.Listener,
        DialogsEventBus.Listener {

    private static final String ARG_QUESTION_ID = "ARG_QUESTION_ID";

    private static final String DIALOG_ID_NETWORK_ERROR = "DIALOG_ID_NETWORK_ERROR";

    public static QuestionDetailsFragment newInstance(String questionId) {
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_ID, questionId);
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;

    private ScreensNavigator mScreensNavigator;
    private DialogsManager mDialogsManager;
    private DialogsEventBus mDialogsEventBus;

    private QuestionDetailsViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFetchQuestionDetailsUseCase = getCompositionRoot().getFetchQuestionDetailsUseCase();
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mDialogsManager = getCompositionRoot().getDialogsManager();
        mDialogsEventBus = getCompositionRoot().getDialogsEventBus();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMvc(container);

        return mViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchQuestionDetailsUseCase.registerListener(this);
        mViewMvc.registerListener(this);
        mDialogsEventBus.registerListener(this);

        mViewMvc.showProgressIndication();

        if (!DIALOG_ID_NETWORK_ERROR.equals(mDialogsManager.getShownDialogTag())) {
            mFetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(getQuestionId());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchQuestionDetailsUseCase.unregisterListener(this);
        mViewMvc.unregisterListener(this);
        mDialogsEventBus.unregisterListener(this);
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
        mDialogsManager.showUseCaseErrorDialog(DIALOG_ID_NETWORK_ERROR);
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof PromptDialogEvent) {
            switch (((PromptDialogEvent) event).getClickedButton()) {
                case POSITIVE:
                    mFetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(getQuestionId());
                    break;
                case NEGATIVE:
                    break;
            }
        }
    }

    @Override
    public void onNavigateUpClicked() {
        mScreensNavigator.navigateUp();
    }

}
