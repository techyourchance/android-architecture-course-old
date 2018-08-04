package com.techyourchance.mvc.screens.questionslist;

import android.os.Bundle;

import com.techyourchance.mvc.screens.common.BaseActivity;

public class QuestionsListActivity extends BaseActivity {

    private QuestionsListController mQuestionsListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QuestionsListViewMvc viewMvc = getCompositionRoot().getViewMvcFactory().getQuestionsListViewMvc(null);

        mQuestionsListController = getCompositionRoot().getQuestionsListController();
        mQuestionsListController.bindView(viewMvc);

        setContentView(viewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQuestionsListController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQuestionsListController.onStop();
    }

}
