package com.techyourchance.mvc.screens.questionslist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.common.Constants;
import com.techyourchance.mvc.networking.QuestionSchema;
import com.techyourchance.mvc.networking.QuestionsListResponseSchema;
import com.techyourchance.mvc.networking.StackoverflowApi;
import com.techyourchance.mvc.questions.Question;
import com.techyourchance.mvc.screens.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsListActivity extends BaseActivity implements
        QuestionsListAdapter.OnQuestionClickListener {

    private StackoverflowApi mStackoverflowApi;

    private ListView mLstQuestions;
    private QuestionsListAdapter mQuestionsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_questions_list);

        mLstQuestions = findViewById(R.id.lst_questions);
        mQuestionsListAdapter = new QuestionsListAdapter(this, this);
        mLstQuestions.setAdapter(mQuestionsListAdapter);

        mStackoverflowApi = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StackoverflowApi.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchQuestions();
    }

    private void fetchQuestions() {
        mStackoverflowApi.fetchLastActiveQuestions(Constants.QUESTIONS_LIST_PAGE_SIZE)
                .enqueue(new Callback<QuestionsListResponseSchema>() {
                    @Override
                    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
                        if (response.isSuccessful()) {
                            bindQuestions(response.body().getQuestions());
                        } else {
                            networkCallFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
                        networkCallFailed();
                    }
                } );
    }

    private void bindQuestions(List<QuestionSchema> questionSchemas) {
        List<Question> questions = new ArrayList<>(questionSchemas.size());
        for (QuestionSchema questionSchema : questionSchemas) {
            questions.add(new Question(questionSchema.getId(), questionSchema.getTitle()));
        }
        mQuestionsListAdapter.clear();
        mQuestionsListAdapter.addAll(questions);
        mQuestionsListAdapter.notifyDataSetChanged();
    }

    private void networkCallFailed() {
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQuestionClicked(Question question) {
        Toast.makeText(this, question.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
