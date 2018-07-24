package com.techyourchance.mvc.screens.questionslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.questions.Question;

public class QuestionsListAdapter extends ArrayAdapter<Question> {

    private final OnQuestionClickListener mOnQuestionClickListener;

    public interface OnQuestionClickListener {
        void onQuestionClicked(Question question);
    }

    public QuestionsListAdapter(Context context,
                                OnQuestionClickListener onQuestionClickListener) {
        super(context, 0);
        mOnQuestionClickListener = onQuestionClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_question_list_item, parent, false);
        }

        final Question question = getItem(position);

        // bind the data to views
        TextView txtTitle = convertView.findViewById(R.id.txt_title);
        txtTitle.setText(question.getTitle());

        // set listener
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onQuestionClicked(question);
            }
        });

        return convertView;
    }

    private void onQuestionClicked(Question question) {
        mOnQuestionClickListener.onQuestionClicked(question);
    }
}
