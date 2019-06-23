package com.example.triviaquiz;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/*
 * TODO: https://www.youtube.com/watch?v=tlgrX3HF6AI <-- next video
 *
 * */
public class Quiz_Activity extends AppCompatActivity {
    private TextView questionTV;
    private TextView countDownTV;
    private TextView scoreTV;
    private TextView questionCountTV;
    private Button confirmBtn;
    private RadioButton option1RB;
    private RadioButton option2RB;
    private RadioButton option3RB;
    private RadioButton option4RB;
    private RadioGroup rbGroup;

    private ColorStateList textColourDefaultRB; //save default RB text color to change later according to correct answer

    private ArrayList<Question> questionsList;

    private int questionCounter;
    private int questionTotalCount;
    private Question currentQuestion;
    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_);
        initialize();
    }

    private void initialize() {
        questionTV = findViewById(R.id.questionTV);
        countDownTV = findViewById(R.id.countDownTV);
        scoreTV = findViewById(R.id.scoreTV);
        questionCountTV = findViewById(R.id.questionCountTV);
        confirmBtn = findViewById(R.id.confirmBtn);
        option1RB = findViewById(R.id.option1);
        option2RB = findViewById(R.id.option2);
        option3RB = findViewById(R.id.option3);
        option4RB = findViewById(R.id.option4);
        rbGroup = findViewById(R.id.radio_group);

        textColourDefaultRB = option1RB.getTextColors();

        QuizDBHelper dbHelper = new QuizDBHelper(getApplicationContext());
        questionsList = dbHelper.getAllQuestions();
        questionTotalCount = questionsList.size();
        Collections.shuffle(questionsList); // randomizes appearance of questions
        showNextQuestion();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    if (option1RB.isChecked() || option2RB.isChecked() || option3RB.isChecked() || option4RB.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(Quiz_Activity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        option1RB.setTextColor(textColourDefaultRB);
        option2RB.setTextColor(textColourDefaultRB);
        option3RB.setTextColor(textColourDefaultRB);
        option4RB.setTextColor(textColourDefaultRB);

        if (questionCounter < questionTotalCount) {
            currentQuestion = questionsList.get(questionCounter);

            questionTV.setText(currentQuestion.getQuestion());
            option1RB.setText(currentQuestion.getOption1());
            option2RB.setText(currentQuestion.getOption2());
            option3RB.setText(currentQuestion.getOption3());
            option4RB.setText(currentQuestion.getOption4());

            questionCounter++;
            questionCountTV.setText("Question: " + questionCounter + " / " + questionTotalCount);
            answered = false;
            confirmBtn.setText("CONFIRM");
        } else {
            finishQuiz();
        }

    }

    private void checkAnswer() {
        answered = true;
        RadioButton checkedRB = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNumber = rbGroup.indexOfChild(checkedRB) + 1; //bro remember elements start at 0
        if (answerNumber == currentQuestion.getAnswerNr()) {
            score++;
            scoreTV.setText("Score: " + score);
        }
        showSolution();
    }

    // output correct radio button as green
    private void showSolution() {
        option1RB.setTextColor(Color.RED);
        option2RB.setTextColor(Color.RED);
        option3RB.setTextColor(Color.RED);
        option4RB.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                option1RB.setTextColor(Color.GREEN);
                questionTV.setText("Answer 1 is correct!");
                break;
            case 2:
                option2RB.setTextColor(Color.GREEN);
                questionTV.setText("Answer 2 is correct!");
                break;
            case 3:
                option1RB.setTextColor(Color.GREEN);
                questionTV.setText("Answer 3 is correct!");
                break;
            case 4:
                option2RB.setTextColor(Color.GREEN);
                questionTV.setText("Answer 4 is correct!");
                break;
        }

        if(questionCounter < questionTotalCount){
            confirmBtn.setText("NEXT");
        } else{
            confirmBtn.setText("FINISH");
        }
    }

    private void finishQuiz() {
        finish();
    }
}
