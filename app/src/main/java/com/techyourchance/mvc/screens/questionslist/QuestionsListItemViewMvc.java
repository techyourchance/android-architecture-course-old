package com.techyourchance.mvc.screens.questionslist;

import android.view.View;

import com.techyourchance.mvc.questions.Question;
import com.techyourchance.mvc.screens.common.ViewMvc;

public interface QuestionsListItemViewMvc extends ViewMvc {

    public interface Listener {
        void onQuestionClicked(Question question);
    }

    void registerListener(Listener listener);

    void unregisterListener(Listener listener);

    void bindQuestion(Question question);
}
