package com.techyourchance.mvc.screens.questionslist;

import com.techyourchance.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.techyourchance.mvc.questions.Question;
import com.techyourchance.mvc.screens.common.MessagesDisplayer;
import com.techyourchance.mvc.screens.common.ScreensNavigator;

import java.util.List;

public class QuestionsListController  implements
        QuestionsListViewMvcImpl.Listener, FetchLastActiveQuestionsUseCase.Listener{

    private final FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;
    private final ScreensNavigator mScreensNavigator;
    private final MessagesDisplayer mMessagesDisplayer;

    private QuestionsListViewMvc mViewMvc;

    public QuestionsListController(FetchLastActiveQuestionsUseCase fetchLastActiveQuestionsUseCase,
                                   ScreensNavigator screensNavigator,
                                   MessagesDisplayer messagesDisplayer) {
        mFetchLastActiveQuestionsUseCase = fetchLastActiveQuestionsUseCase;
        mScreensNavigator = screensNavigator;
        mMessagesDisplayer = messagesDisplayer;
    }

    public void bindView(QuestionsListViewMvc viewMvc) {
        mViewMvc = viewMvc;
    }

    public void onStart() {
        mViewMvc.registerListener(this);
        mFetchLastActiveQuestionsUseCase.registerListener(this);

        mViewMvc.showProgressIndication();
        mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestionsAndNotify();
    }

    public void onStop() {
        mViewMvc.unregisterListener(this);
        mFetchLastActiveQuestionsUseCase.unregisterListener(this);
    }

    @Override
    public void onQuestionClicked(Question question) {
        mScreensNavigator.toDialogDetails(question.getId());
    }

    @Override
    public void onLastActiveQuestionsFetched(List<Question> questions) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onLastActiveQuestionsFetchFailed() {
        mViewMvc.hideProgressIndication();
        mMessagesDisplayer.showUseCaseError();
    }
}
