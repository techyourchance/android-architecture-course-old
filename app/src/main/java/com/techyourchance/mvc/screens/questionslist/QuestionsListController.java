package com.techyourchance.mvc.screens.questionslist;

import com.techyourchance.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.techyourchance.mvc.questions.Question;
import com.techyourchance.mvc.screens.common.controllers.BackPressDispatcher;
import com.techyourchance.mvc.screens.common.controllers.BackPressedListener;
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.techyourchance.mvc.screens.common.toastshelper.ToastsHelper;

import java.util.List;

public class QuestionsListController  implements
        QuestionsListViewMvc.Listener, FetchLastActiveQuestionsUseCase.Listener, BackPressedListener {

    private final FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;
    private final ScreensNavigator mScreensNavigator;
    private final ToastsHelper mToastsHelper;
    private final BackPressDispatcher mBackPressDispatcher;

    private QuestionsListViewMvc mViewMvc;

    public QuestionsListController(FetchLastActiveQuestionsUseCase fetchLastActiveQuestionsUseCase,
                                   ScreensNavigator screensNavigator,
                                   ToastsHelper toastsHelper,
                                   BackPressDispatcher backPressDispatcher) {
        mFetchLastActiveQuestionsUseCase = fetchLastActiveQuestionsUseCase;
        mScreensNavigator = screensNavigator;
        mToastsHelper = toastsHelper;
        mBackPressDispatcher = backPressDispatcher;
    }

    public void bindView(QuestionsListViewMvc viewMvc) {
        mViewMvc = viewMvc;
    }

    public void onStart() {
        mViewMvc.registerListener(this);
        mFetchLastActiveQuestionsUseCase.registerListener(this);
        mBackPressDispatcher.registerListener(this);

        mViewMvc.showProgressIndication();
        mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestionsAndNotify();
    }

    public void onStop() {
        mViewMvc.unregisterListener(this);
        mFetchLastActiveQuestionsUseCase.unregisterListener(this);
        mBackPressDispatcher.unregisterListener(this);
    }

    @Override
    public void onQuestionClicked(Question question) {
        mScreensNavigator.toQuestionDetails(question.getId());
    }

    @Override
    public void onQuestionsListClicked() {
        // this is the questions list screen - no-op
    }

    @Override
    public void onLastActiveQuestionsFetched(List<Question> questions) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onLastActiveQuestionsFetchFailed() {
        mViewMvc.hideProgressIndication();
        mToastsHelper.showUseCaseError();
    }

    public boolean onBackPressed() {
        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
            return true;
        } else {
            return false;
        }
    }
}
