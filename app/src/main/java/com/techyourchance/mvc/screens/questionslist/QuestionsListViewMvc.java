package com.techyourchance.mvc.screens.questionslist;

import com.techyourchance.mvc.questions.Question;
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerViewMvc;
import com.techyourchance.mvc.screens.common.views.ObservableViewMvc;

import java.util.List;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener>,
        NavDrawerViewMvc {

    public interface Listener {
        void onQuestionClicked(Question question);

        void onQuestionsListClicked();
    }

    void bindQuestions(List<Question> questions);

    void showProgressIndication();

    void hideProgressIndication();

}
