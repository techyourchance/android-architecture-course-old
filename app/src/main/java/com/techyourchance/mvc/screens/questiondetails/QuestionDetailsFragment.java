package com.techyourchance.mvc.screens.questiondetails;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.mvc.common.permissions.PermissionsHelper;
import com.techyourchance.mvc.questions.FetchQuestionDetailsUseCase;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.controllers.BaseFragment;
import com.techyourchance.mvc.screens.common.dialogs.DialogsEventBus;
import com.techyourchance.mvc.screens.common.dialogs.DialogsManager;
import com.techyourchance.mvc.screens.common.dialogs.promptdialog.PromptDialogEvent;
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator;

public class QuestionDetailsFragment extends BaseFragment implements
        FetchQuestionDetailsUseCase.Listener,
        QuestionDetailsViewMvc.Listener,
        DialogsEventBus.Listener,
        PermissionsHelper.Listener {

    private static final String ARG_QUESTION_ID = "ARG_QUESTION_ID";

    private static final String DIALOG_ID_NETWORK_ERROR = "DIALOG_ID_NETWORK_ERROR";

    private static final String SAVED_STATE_SCREEN_STATE = "SAVED_STATE_SCREEN_STATE";
    public static final int REQUEST_CODE = 1001;

    public static QuestionDetailsFragment newInstance(String questionId) {
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_ID, questionId);
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private enum ScreenState {
        IDLE, QUESTION_DETAILS_SHOWN, NETWORK_ERROR
    }

    private FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;
    private ScreensNavigator mScreensNavigator;
    private DialogsManager mDialogsManager;
    private DialogsEventBus mDialogsEventBus;
    private PermissionsHelper mPermissionsHelper;

    private QuestionDetailsViewMvc mViewMvc;

    private ScreenState mScreenState = ScreenState.IDLE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mScreenState = (ScreenState) savedInstanceState.getSerializable(SAVED_STATE_SCREEN_STATE);
        }
        mPermissionsHelper = getCompositionRoot().getPermissionsHelper();
        mFetchQuestionDetailsUseCase = getCompositionRoot().getFetchQuestionDetailsUseCase();
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mDialogsManager = getCompositionRoot().getDialogsManager();
        mDialogsEventBus = getCompositionRoot().getDialogsEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMvc(container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchQuestionDetailsUseCase.registerListener(this);
        mViewMvc.registerListener(this);
        mDialogsEventBus.registerListener(this);
        mPermissionsHelper.registerListener(this);

        if (mScreenState != ScreenState.NETWORK_ERROR) {
            fetchQuestionDetailsAndNotify();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchQuestionDetailsUseCase.unregisterListener(this);
        mViewMvc.unregisterListener(this);
        mDialogsEventBus.unregisterListener(this);
        mPermissionsHelper.unregisterListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE_SCREEN_STATE, mScreenState);
    }

    private void fetchQuestionDetailsAndNotify() {
        mViewMvc.showProgressIndication();
        mFetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(getQuestionId());
    }

    private String getQuestionId() {
        return getArguments().getString(ARG_QUESTION_ID);
    }

    @Override
    public void onQuestionDetailsFetched(QuestionDetails questionDetails) {
        mScreenState = ScreenState.QUESTION_DETAILS_SHOWN;
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestion(questionDetails);
    }

    @Override
    public void onQuestionDetailsFetchFailed() {
        mScreenState = ScreenState.NETWORK_ERROR;
        mViewMvc.hideProgressIndication();
        mDialogsManager.showUseCaseErrorDialog(DIALOG_ID_NETWORK_ERROR);
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof PromptDialogEvent) {
            switch (((PromptDialogEvent) event).getClickedButton()) {
                case POSITIVE:
                    mScreenState = ScreenState.IDLE;
                    fetchQuestionDetailsAndNotify();
                    break;
                case NEGATIVE:
                    mScreenState = ScreenState.IDLE;
                    break;
            }
        }
    }

    @Override
    public void onLocationRequestClicked() {
        if (mPermissionsHelper.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            mDialogsManager.showPermissionGrantedDialog(null);
        } else {
            mPermissionsHelper.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_CODE);
        }
    }

    @Override
    public void onPermissionGranted(String permission, int requestCode) {
        if (requestCode == REQUEST_CODE) {
            mDialogsManager.showPermissionGrantedDialog(null);
        }
    }

    @Override
    public void onPermissionDeclined(String permission, int requestCode) {
        if (requestCode == REQUEST_CODE) {
            mDialogsManager.showDeclinedDialog(null);
        }
    }

    @Override
    public void onPermissionDeclinedDontAskAgain(String permission, int requestCode) {
        if (requestCode == REQUEST_CODE) {
            mDialogsManager.showPermissionDeclinedCantAskMoreDialog(null);
        }
    }

    @Override
    public void onNavigateUpClicked() {
        mScreensNavigator.navigateUp();
    }

}
