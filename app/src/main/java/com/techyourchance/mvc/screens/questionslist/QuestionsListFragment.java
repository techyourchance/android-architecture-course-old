package com.techyourchance.mvc.screens.questionslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.mvc.screens.common.controllers.BaseFragment;

public class QuestionsListFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new QuestionsListFragment();
    }

    private static final String SAVED_STATE_CONTROLLER = "SAVED_STATE_CONTROLLER";

    private QuestionsListController mQuestionsListController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        QuestionsListViewMvc viewMvc = getCompositionRoot().getViewMvcFactory().getQuestionsListViewMvc(container);

        mQuestionsListController = getCompositionRoot().getQuestionsListController();
        if (savedInstanceState != null) {
            restoreControllerState(savedInstanceState);
        }
        mQuestionsListController.bindView(viewMvc);

        return viewMvc.getRootView();
    }

    private void restoreControllerState(Bundle savedInstanceState) {
        mQuestionsListController.restoreSavedState(
                (QuestionsListController.SavedState)
                        savedInstanceState.getSerializable(SAVED_STATE_CONTROLLER)
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        mQuestionsListController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mQuestionsListController.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE_CONTROLLER, mQuestionsListController.getSavedState());
    }
}
