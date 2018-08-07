package com.techyourchance.mvc.screens.questionslist;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.questions.Question;
import com.techyourchance.mvc.screens.common.navdrawer.BaseNavDrawerViewMvc;
import com.techyourchance.mvc.screens.common.navdrawer.DrawerItems;
import com.techyourchance.mvc.screens.common.toolbar.ToolbarViewMvc;
import com.techyourchance.mvc.screens.common.views.BaseObservableViewMvc;
import com.techyourchance.mvc.screens.common.ViewMvcFactory;

import java.util.List;

public class QuestionsListViewMvcImpl extends BaseNavDrawerViewMvc<QuestionsListViewMvc.Listener>
        implements QuestionsListViewMvc, QuestionsRecyclerAdapter.Listener {

    private final ToolbarViewMvc mToolbarViewMvc;

    private final Toolbar mToolbar;
    private final RecyclerView mRecyclerQuestions;
    private final QuestionsRecyclerAdapter mAdapter;
    private final ProgressBar mProgressBar;

    public QuestionsListViewMvcImpl(LayoutInflater inflater,
                                    @Nullable ViewGroup parent,
                                    ViewMvcFactory viewMvcFactory) {
        super(inflater, parent);

        setRootView(inflater.inflate(R.layout.layout_questions_list, parent, false));

        mRecyclerQuestions = findViewById(R.id.recycler_questions);
        mRecyclerQuestions.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new QuestionsRecyclerAdapter(this, viewMvcFactory);
        mRecyclerQuestions.setAdapter(mAdapter);

        mProgressBar = findViewById(R.id.progress);

        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);
        initToolbar();
    }

    private void initToolbar() {
        mToolbarViewMvc.setTitle(getString(R.string.questions_list_screen_title));
        mToolbar.addView(mToolbarViewMvc.getRootView());
        mToolbarViewMvc.enableHamburgerButtonAndListen(new ToolbarViewMvc.HamburgerClickListener() {
            @Override
            public void onHamburgerClicked() {
                openDrawer();
            }
        });
    }

    @Override
    public void onQuestionClicked(Question question) {
        for (Listener listener : getListeners()) {
            listener.onQuestionClicked(question);
        }
    }

    @Override
    protected void onDrawerItemClicked(DrawerItems item) {
        for (Listener listener : getListeners()) {
            switch (item) {
                case QUESTIONS_LIST:
                    listener.onQuestionsListClicked();
            }
        }

    }

    @Override
    public void bindQuestions(List<Question> questions) {
        mAdapter.bindQuestions(questions);
    }

    @Override
    public void showProgressIndication() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
        mProgressBar.setVisibility(View.GONE);
    }
}
