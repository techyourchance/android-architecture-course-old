package com.techyourchance.mvc.screens.questiondetails;

import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.navdrawer.DrawerItems;
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerViewMvc;
import com.techyourchance.mvc.screens.common.views.ObservableViewMvc;
import com.techyourchance.mvc.screens.common.views.ViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener>,
        NavDrawerViewMvc {

    public interface Listener {
        void onNavigateUpClicked();

        void onDrawerItemClicked(DrawerItems item);
    }

    void bindQuestion(QuestionDetails question);

    void showProgressIndication();

    void hideProgressIndication();
}
