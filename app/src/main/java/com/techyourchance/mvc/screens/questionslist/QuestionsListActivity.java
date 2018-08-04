package com.techyourchance.mvc.screens.questionslist;

import android.os.Bundle;
import android.widget.Toast;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.networking.StackoverflowApi;
import com.techyourchance.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.techyourchance.mvc.questions.Question;
import com.techyourchance.mvc.screens.common.BaseActivity;
import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsActivity;

import java.util.List;

public class QuestionsListActivity extends BaseActivity implements
        QuestionsListViewMvcImpl.Listener, FetchLastActiveQuestionsUseCase.Listener {

    private StackoverflowApi mStackoverflowApi;

    private FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;

    private QuestionsListViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFetchLastActiveQuestionsUseCase = getCompositionRoot().getFetchLastActiveQuestionsUseCase();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionsListViewMvc(null);

        mViewMvc.registerListener(this);

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFetchLastActiveQuestionsUseCase.registerListener(this);

        mViewMvc.showProgressIndication();
        mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestionsAndNotify();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchLastActiveQuestionsUseCase.unregisterListener(this);
    }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(this, question.getId());
    }

    @Override
    public void onLastActiveQuestionsFetched(List<Question> questions) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onLastActiveQuestionsFetchFailed() {
        mViewMvc.hideProgressIndication();
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }
}
