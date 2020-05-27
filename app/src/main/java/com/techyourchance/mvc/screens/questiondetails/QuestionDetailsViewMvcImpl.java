package com.techyourchance.mvc.screens.questiondetails;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.ViewMvcFactory;
import com.techyourchance.mvc.screens.common.toolbar.ToolbarViewMvc;
import com.techyourchance.mvc.screens.common.views.BaseObservableViewMvc;


public class QuestionDetailsViewMvcImpl extends BaseObservableViewMvc<QuestionDetailsViewMvc.Listener>
        implements QuestionDetailsViewMvc {


    private final ToolbarViewMvc mToolbarViewMvc;
    private final Toolbar mToolbar;

    private final TextView mTxtQuestionTitle;
    private final TextView mTxtQuestionBody;
    private final ProgressBar mProgressBar;

    public QuestionDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {

        setRootView(inflater.inflate(R.layout.layout_question_details, parent, false));

        mTxtQuestionTitle = findViewById(R.id.txt_question_title);
        mTxtQuestionBody = findViewById(R.id.txt_question_body);
        mProgressBar = findViewById(R.id.progress);
        mToolbar = findViewById(R.id.toolbar);

        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);

        initToolbar();
    }

    private void initToolbar() {
        mToolbar.addView(mToolbarViewMvc.getRootView());

        mToolbarViewMvc.setTitle(getString(R.string.question_details_screen_title));

        mToolbarViewMvc.enableUpButtonAndListen(new ToolbarViewMvc.NavigateUpClickListener() {
            @Override
            public void onNavigateUpClicked() {
                for (Listener listener : getListeners()) {
                    listener.onNavigateUpClicked();
                }
            }
        });

        mToolbarViewMvc.enableLocationRequestButtonAndListen(new ToolbarViewMvc.LocationRequestListener() {
            @Override
            public void onLocationRequestClicked() {
                for (Listener listener : getListeners()) {
                    listener.onLocationRequestClicked();
                }
            }
        });
    }

    @Override
    public void bindQuestion(QuestionDetails question) {
        String questionTitle = question.getTitle();
        String questionBody = question.getBody();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mTxtQuestionTitle.setText(Html.fromHtml(questionTitle, Html.FROM_HTML_MODE_LEGACY));
            mTxtQuestionBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mTxtQuestionTitle.setText(Html.fromHtml(questionTitle));
            mTxtQuestionBody.setText(Html.fromHtml(questionBody));
        }
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
